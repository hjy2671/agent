package org.nature.util.handler;

import org.nature.util.ClassInfo;
import org.objectweb.asm.ClassReader;

public interface ClassHandler {

    boolean support(ClassInfo info);

    void handle(ClassReader reader);

}
