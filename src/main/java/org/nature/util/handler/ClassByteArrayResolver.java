package org.nature.util.handler;

import org.nature.util.ClassInfo;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;

public class ClassByteArrayResolver {

    private static byte[] currentClassBytes;
    private static String currentClassName;

    public static void resolve(byte[] bytes) {
        currentClassBytes = bytes;
        resolve(new ClassReader(bytes));
    }

    public static void resolve(InputStream inputStream) {
        ClassReader reader = null;
        try {
            reader = new ClassReader(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resolve(reader);

    }

    public static void resolve(ClassReader reader) {
        final ClassInfo classInfo = ClassInfo.getClassInfo(reader, ClassReader.SKIP_FRAMES);
        currentClassName = reader.getClassName();
        ClassHandlerSelector.select(reader, classInfo);
    }

    public static byte[] getCurrentClassBytes() {
        return currentClassBytes;
    }

    public static byte[] getBytes(ClassReader reader) {
       if (currentClassBytes != null && currentClassName.equals(reader.getClassName())) {
            return currentClassBytes;
        }
        return new ClassWriter(reader, Opcodes.ASM9).toByteArray();
    }

    public static boolean isInterface(int access) {
        return (access & 0x0600) == 0x0600;
    }

    public static boolean isAbstractClass(int access) {
        return (access & 0x0420) == 0x0420;
    }

    public static boolean isAbstract(int access) {
        return (access & 0x0400) == 0x0400;
    }

    public static boolean isNotAbstract(int access) {
        return !isAbstract(access);
    }


}
