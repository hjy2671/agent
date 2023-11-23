package org.nature.util.handler;

import org.nature.net.interceptor.*;
import org.nature.util.ByteArrayClassLoader;
import org.nature.util.ClassInfo;
import org.nature.util.ReflectUtil;
import org.nature.util.StrUtil;
import org.objectweb.asm.ClassReader;

public class InterceptorHandler implements ClassHandler{
    @Override
    public boolean support(ClassInfo info) {
        return isImpl(info) && ClassByteArrayResolver.isNotAbstract(info.getAccess());
    }

    @Override
    public void handle(ClassReader reader) {

        try {
            final Class<?> clazz = ByteArrayClassLoader.loader.loadClass(StrUtil.filePath2packagePath(reader.getClassName()), ClassByteArrayResolver.getBytes(reader));
            if (PreInterceptor.class.isAssignableFrom(clazz)) {
                handlePre(clazz);
            } else if (PostInterceptor.class.isAssignableFrom(clazz)) {
                handlePost(clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void handlePre(Class<?> c) {
        PreInterceptorHolder.putInterceptor((PreInterceptor) ReflectUtil.newInstance(c));
    }

    private void handlePost(Class<?> c) {
        PostInterceptorHolder.putInterceptor((PostInterceptor) ReflectUtil.newInstance(c));
    }

    private boolean isImpl(ClassInfo info) {
        for (String face : info.getInterfaces()) {
            if (face.equals(StrUtil.packagePath2filePath(Interceptor.class.getName()))) {
                return true;
            }
            if (face.equals(StrUtil.packagePath2filePath(PreInterceptor.class.getName()))) {
                return true;
            }
            if (face.equals(StrUtil.packagePath2filePath(PostInterceptor.class.getName()))) {
                return true;
            }
        }
        return false;
    }

}
