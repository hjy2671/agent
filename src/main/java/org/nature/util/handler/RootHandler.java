package org.nature.util.handler;

import org.nature.util.ByteArrayClassLoader;
import org.nature.util.ClassInfo;
import org.nature.util.ReflectUtil;
import org.nature.util.StrUtil;
import org.objectweb.asm.ClassReader;

/**
 * 装载其他的ClassHandler
 * @author hjy
 */
final class RootHandler implements ClassHandler{
    @Override
    public boolean support(ClassInfo info) {
        return isClassHandlerImpl(info) && isNotRootHandlerHandler(info) && !ClassByteArrayResolver.isAbstract(info.getAccess());
    }

    @Override
    public void handle(ClassReader reader) {
        final byte[] bytes = ClassByteArrayResolver.getBytes(reader);
        try {
            final ClassHandler handler = (ClassHandler) ReflectUtil.newInstance(ByteArrayClassLoader.loader.loadClass(StrUtil.filePath2packagePath(reader.getClassName()), bytes));
            ClassHandlerSelector.addClassHandler(handler);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isClassHandlerImpl(ClassInfo info) {
        for (String f : info.getInterfaces()) {
            if (f.equals(StrUtil.packagePath2filePath(ClassHandler.class.getName()))) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotRootHandlerHandler(ClassInfo info) {
        return !info.getName().equals(StrUtil.packagePath2filePath(RootHandler.class.getName()));
    }

}
