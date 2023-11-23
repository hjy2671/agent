package org.nature.shadow;

import org.nature.util.ObjUtil;
import org.nature.util.ObjectHolder;
import org.nature.util.StrUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

public abstract class AbstractFunctionProxyBuilder {
    protected MethodVisitor mv;
    protected ClassWriter writer;
    protected Class<?> target;
    protected Method method;
    protected AbstractFunctionProxyBuilder contractor;
    protected String newName;
    private static final String objUtilFilePath = StrUtil.packagePath2filePath(ObjUtil.class.getName());
    public static final String HOLDER_PREFIX = "Proxy$";

    protected AbstractFunctionProxyBuilder(ClassWriter writer, Class<?> target, Method targetMethod){
        this.writer = writer;
        this.target = target;
        this.method = targetMethod;
    }

    public final AbstractFunctionProxyBuilder prepare(String newName, String[] interfaces) {
        this.newName = newName;
        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, newName, null, "java/lang/Object", interfaces);
        return this;
    }

    public abstract Object build();

    public final AbstractFunctionProxyBuilder buildConstructor() {
        mv = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return this;
    }

    public AbstractFunctionProxyBuilder buildHead() {
        if (contractor != null)
            return contractor.buildHead();
        mv = writer.visitMethod(Opcodes.ACC_PUBLIC, "invoke", "([Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        mv.visitCode();
        return this;
    }

    public AbstractFunctionProxyBuilder buildBody(String targetHoldKey) {
        if (contractor != null)
            return contractor.buildBody(targetHoldKey);
        final String objHolder = StrUtil.packagePath2filePath(ObjectHolder.class.getName());
        final String targetName = StrUtil.packagePath2filePath(target.getName());
        final Class<?>[] types = method.getParameterTypes();
        StringBuilder builder = new StringBuilder("(");

        mv.visitLdcInsn(targetHoldKey);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, objHolder, "get", "(Ljava/lang/String;)Ljava/lang/Object;", false);
        mv.visitTypeInsn(Opcodes.CHECKCAST, targetName);
        String desc = "";

        for (int i = 0; i < types.length; i++) {
            if (types[i].isPrimitive()) {
                final Class<?> type = ObjUtil.getPrimitiveWrapClass(types[i]);
                final String typeFilePath = StrUtil.packagePath2filePath(type.getName());
                desc = Type.getDescriptor(type);
                builder.append(Type.getType(types[i]));
                parseParameter(desc, i);
                mv.visitTypeInsn(Opcodes.CHECKCAST, typeFilePath);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, typeFilePath, getPrimeClassWrapMethodName(types[i]), getPrimeClassWrapMethodDesc(types[i]), false);
            } else {
                desc = Type.getDescriptor(types[i]);
                builder.append(desc);
                parseParameter(desc, i);
                mv.visitTypeInsn(Opcodes.CHECKCAST, StrUtil.packagePath2filePath(types[i].getName()));
            }

        }
        final Class<?> returnType = method.getReturnType();
        final String name = Type.getDescriptor(returnType);
        builder.append(")").append(name);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetName, method.getName(), builder.toString(), false);

        if (needUnbox(returnType)) {
            final String s = "(" + name + ")L" + StrUtil.packagePath2filePath(ObjUtil.getPrimitiveWrapClass(returnType).getName()) + ";";
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, StrUtil.packagePath2filePath(ObjUtil.getPrimitiveWrapClass(returnType).getName()), "valueOf", s, false);
        }
        if (returnType.getSimpleName().equals("void")) {
            mv.visitInsn(Opcodes.ACONST_NULL);
        }
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(3, 2);
        mv.visitEnd();

        return this;
    }

    private boolean needUnbox(Class<?> c) {
        return c.isPrimitive() && !c.getSimpleName().equals("void");
    }

    private String getPrimeClassWrapMethodName(Class<?> c) {
        return c.getSimpleName().concat("Value");
    }

    private String getPrimeClassWrapMethodDesc(Class<?> c) {
        return "()".concat(Type.getDescriptor(c));
    }

    private void parseParameter(String desc, int index) {
        mv.visitLdcInsn(Type.getType(desc));
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitIntInsn(Opcodes.BIPUSH, index);
        mv.visitInsn(Opcodes.AALOAD);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, objUtilFilePath, "convert", "(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;", false);
    }

    public final AbstractFunctionProxyBuilder contract(AbstractFunctionProxyBuilder contractor) {
        this.contractor = contractor;
        return this;
    }

    public final AbstractFunctionProxyBuilder strike() {
        this.contractor = null;
        return this;
    }



}
