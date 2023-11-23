package org.nature.util.handler;

import com.sun.jdi.InternalException;
import org.nature.net.Configuration;
import org.nature.net.annotation.Get;
import org.nature.net.annotation.Post;
import org.nature.net.annotation.Solution;
import org.nature.shadow.AbstractFunctionProxyBuilder;
import org.nature.shadow.FunctionProxyBuilder;
import org.nature.shadow.ProxyFunction;
import org.nature.util.*;
import org.objectweb.asm.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SolutionHandler implements ClassHandler{
    @Override
    public boolean support(ClassInfo info) {
        return ClassByteArrayResolver.isNotAbstract(info.getAccess()) && hasAnnotation(info);
    }

    @Override
    public void handle(ClassReader reader) {
        final byte[] bytes = ClassByteArrayResolver.getBytes(reader);
        final String className = StrUtil.filePath2packagePath(reader.getClassName());
        Class<?> clazz = null;
        try {
            clazz = ByteArrayClassLoader.loader.loadClass(className, bytes);
        } catch (ClassNotFoundException e) {
            throw new InternalException("加载类失败：".concat(className).concat(e.getMessage()));
        }
        String preKey = getSolutionAnnotationVal(clazz);
        String key = AbstractFunctionProxyBuilder.HOLDER_PREFIX.concat(clazz.getName());
        ObjectHolder.put(key, ReflectUtil.newInstance(clazz));

        for (Method method : clazz.getMethods()) {
            final String sufKey = getMethodAnnotationVal(method);
            if ("".equals(sufKey))
                continue;
            String proxyKey = preKey + sufKey;
            final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            final String name = StrUtil.packagePath2filePath(className.substring(0, clazz.getName().lastIndexOf(".") + 1).concat(AbstractFunctionProxyBuilder.HOLDER_PREFIX + method.getName()));
            final Object result = new FunctionProxyBuilder(writer, clazz, method)
                    .prepare(name, new String[]{StrUtil.packagePath2filePath(ProxyFunction.class.getName())})
                    .buildConstructor()
                    .buildHead()
                    .buildBody(key).build();
            if (ObjectHolder.exists(proxyKey))
                throw new InternalException("重复的url定义：".concat(proxyKey).concat(" in ").concat(className));
            ObjectHolder.put(proxyKey, result);
            ObjectHolder.put(proxyKey.concat(Configuration.PARAMETERS_CACHE_KEY_SUFFIX),
                    new ProxyParams(
                            method.getParameterTypes(),
                            method.getGenericParameterTypes(),
                            getParametersName(reader, method.getName(), new ArrayList<>())));
        }
    }

    private String[] getParametersName(ClassReader reader, String methodName, List<String> list) {
        reader.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals(methodName))
                    return new ParameterMethodVisitor(Opcodes.ASM9, list);
                return null;
            }
        }, ClassReader.SKIP_FRAMES);
        return list.toArray(new String[0]);
    }

    static class ParameterMethodVisitor extends MethodVisitor {
        private final List<String> paramNames;
        public ParameterMethodVisitor(int i, List<String> list) {
            super(i);
            paramNames = list;
        }
        @Override
        public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
            if (!s.equals("this")) {
                paramNames.add(s);
            }
        }
    }

    public static class ProxyParams {
        private final Class<?>[] types;
        private final Type[] generics;
        private final String[] names;

        public ProxyParams(Class<?>[] types, Type[] generics, String[] names) {
            this.types = types;
            this.generics = generics;
            this.names = names;
        }

        public Class<?> getType(int index) {
            return types[index];
        }

        public Type getGeneric(int index) {
            return generics[index];
        }

        public String getName(int index) {
            return names[index];
        }

        public Class<?>[] getTypes() {
            return types;
        }

        public Type[] getGenerics() {
            return generics;
        }

        public String[] getNames() {
            return names;
        }

        public boolean hasParam() {
            return names.length > 0;
        }

        public int paramNum() {
            return names.length;
        }
    }
    private boolean hasAnnotation(ClassInfo info) {
        for (String annotation : info.getAnnotationType()) {
            if (annotation.contains(StrUtil.packagePath2filePath(Solution.class.getName()))) {
                return true;
            }
        }
        return false;
    }

    private String getSolutionAnnotationVal(Class<?> c) {
        final String value = c.getAnnotation(Solution.class).value();
        if (!value.startsWith("/"))
            throw new InternalException("url地址错误：".concat(value).concat(" ").concat(c.getName()));
        return value;
    }

    private String getMethodAnnotationVal(Method method) {
        final Get get = method.getAnnotation(Get.class);
        if (get == null) {
            final Post post = method.getAnnotation(Post.class);
            return post == null ? "" : post.value().concat("POST");
        }
        return get.value().concat("GET");
    }

}
