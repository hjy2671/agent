package test.run;

import org.objectweb.asm.*;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.tools.attach.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class AgentStarter {

    public static String getPath() {
        return AgentStarter.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
    }

    public static String change(String vm, String args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        return change(vm, getPath(), args);
    }

    public static String change(String vm, String path, String args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (args == null)
            return "未修改";
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith(vm)) {
                VirtualMachine virtualMachine = null;
                virtualMachine = VirtualMachine.attach(vmd.id());
                System.out.println(vmd.id());
                virtualMachine.loadAgent(path, args);
                virtualMachine.detach();
            }
        }
        return "修改成功";
    }

    public static void test(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    public static void agentmain(String args, Instrumentation instrumentation) throws UnmodifiableClassException, InterruptedException {
        System.out.println("agent执行了" + args);

        Class<?>[] cls = instrumentation.getAllLoadedClasses();
        final String[] strings = args.split(",");
        test(strings);
        final List<String> list = Arrays.asList(strings);
        for (Class<?> cl : cls) {
            if (cl.getName().startsWith("test"))
                System.out.println(cl.getName());
        }

        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] buffer) throws IllegalClassFormatException {
                if (className.equals(strings[0].replace(".", "/"))) {
                    if (list.contains("remove")) {
                        instrumentation.removeTransformer(this);
                        return null;
                    }
                    System.out.println(className);
                    System.out.println("修改了类");
                    System.out.println(Arrays.toString(classBeingRedefined.getDeclaredMethods()));
                    final ClassReader reader = new ClassReader(buffer);
                    final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                    final ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
                        @Override
                        public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                            final MethodVisitor methodVisitor = super.visitMethod(i, s, s1, s2, strings);
                            if (methodVisitor != null && !"<init>".equals(s) && list.contains(s)) {
                                return new MethodVisitor(api, methodVisitor) {
                                    @Override
                                    public void visitCode() {
                                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "test/run/Function", "firstTime", "()J", false);
                                        super.visitVarInsn(Opcodes.LSTORE, 2);
                                        super.visitCode();
                                    }

                                    @Override
                                    public void visitInsn(int opcode) {
                                        if (opcode == Opcodes.ATHROW || (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                                            super.visitVarInsn(Opcodes.LLOAD, 2);
                                            super.visitMethodInsn(Opcodes.INVOKESTATIC, "test/run/Function", "firstTime", "()J", false);
                                            super.visitMethodInsn(Opcodes.INVOKESTATIC, "test/run/Function", "print", "(JJ)V", false);
                                        }
                                        super.visitInsn(opcode);
                                    }
                                };
                            }
                            return methodVisitor;
                        }
                    };
                    reader.accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                    return writer.toByteArray();
                }

                return buffer;
            }
        }, true);

        for (Class<?> cl : cls) {
            if (cl.getSimpleName().equals("AgentMain"))
                instrumentation.retransformClasses(cl);
        }

    }


}

