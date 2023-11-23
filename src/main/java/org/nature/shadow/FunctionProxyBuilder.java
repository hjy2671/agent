package org.nature.shadow;

import com.sun.jdi.InternalException;
import org.nature.util.ByteArrayClassLoader;
import org.nature.util.ReflectUtil;
import org.nature.util.StrUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class FunctionProxyBuilder extends AbstractFunctionProxyBuilder{
    public FunctionProxyBuilder(ClassWriter writer, Class<?> target, Method method) {
        super(writer, target, method);
    }

    @Override
    public Object build() {

        final byte[] bytes = writer.toByteArray();

        try {
            final Class<?> c = ByteArrayClassLoader.loader.loadClass(StrUtil.filePath2packagePath(newName), bytes);
            return ReflectUtil.newInstance(c);
        } catch (ClassNotFoundException e) {
            throw new InternalException("类加载异常：".concat(newName).concat(" ".concat(e.getMessage())));
        }
    }

}
