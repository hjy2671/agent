package org.nature.util.json;

import org.nature.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Stack;

/**
 * type决定如何处理json字符
 * @author hjy
 * 2023/3/24
 */
public abstract class AbstractType {

    protected Class<?> type;
    protected Field field;
    protected AbstractTypePackage typePackage;

    public AbstractType(Class<?> type, Field field, AbstractTypePackage typePackage) {
        this.type = type;
        this.field = field;
        this.typePackage = typePackage;
    }

    public Class<?> getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    public Class<?> getInnerType(String name) {
        return getInnerField(name).getType();
    }

    public Field getInnerField(String name) {
        return ReflectUtil.getField(type, name);
    }

    public AbstractTypePackage getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(AbstractTypePackage typePackage) {
        this.typePackage = typePackage;
    }

    public abstract Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack);
}
