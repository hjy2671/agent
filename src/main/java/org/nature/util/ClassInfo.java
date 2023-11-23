package org.nature.util;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo extends ClassVisitor {
    private int version;
    private int access;
    private String name;
    private String signature;
    private String superName;
    private String[] interfaces;
    private final List<String> annotationType = new ArrayList<>();

    public ClassInfo(int api) {
        super(api);
    }

    public static ClassInfo getClassInfo(ClassReader reader, int parsingOptions) {
        final ClassInfo classInfo = new ClassInfo(Opcodes.ASM9);
        reader.accept(classInfo, parsingOptions);
        return classInfo;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        this.annotationType.add(descriptor.concat(".").concat(String.valueOf(visible)));
        return null;
    }

    public int getVersion() {
        return version;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getSuperName() {
        return superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public List<String> getAnnotationType() {
        return annotationType;
    }
}
