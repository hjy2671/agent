package org.nature.util.handler;

import org.nature.util.ClassInfo;
import org.objectweb.asm.ClassReader;
import java.util.ArrayList;
import java.util.List;

public final class ClassHandlerSelector {

    private static final List<ClassHandler> handlerList = new ArrayList<>();
    private static final List<ClassHandler> handlerCache = new ArrayList<>();//防止list的边进边出

    static {
        handlerList.add(new RootHandler());
    }

    /**
     * @return 添加成功返回true
     */
    public static boolean addClassHandler(ClassHandler handler) {
        if (handlerCache.contains(handler))
            return false;
        handlerCache.add(handler);
        return true;
    }

    public static void select(ClassReader reader, ClassInfo classInfo) {

        for (ClassHandler handler : handlerList) {
            if (handler.support(classInfo)) {
                handler.handle(reader);
            }
        }
        cacheToList();
    }

    public static void cacheToList() {
        if (handlerCache.size() > 0) {
            handlerList.addAll(handlerCache);
            handlerCache.clear();
        }
    }

}
