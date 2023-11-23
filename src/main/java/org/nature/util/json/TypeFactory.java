package org.nature.util.json;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 当创建了自定义的type类，可通过实现自定义的factory，将其作为构造参数传入，来实现去创建自定义type类的对象
 * @author hjy
 * 2023/3/24
 */
public class TypeFactory extends AbstractTypeFactory{

    public TypeFactory(AbstractTypeFactory type) {
        super(type);
    }

    public TypeFactory() {
        super();
    }

    /**
     *会优先去让内部的factory类来创建对象
     */
    @Override
    public AbstractType createType(Class<?> type, Field field, AbstractTypePackage typePackage) {
        if (typeFactory != null) {
            final AbstractType val = typeFactory.createType(type, field);
            if (val != null)
                return val;
        }
        if (type.isArray()) {
            return new ArrayType(type, field);
        } else if (List.class.isAssignableFrom(type)) {
            return new ListType(type, field, typePackage);
        } else if (Map.class.isAssignableFrom(type)) {
            return new MapType(type, field, typePackage);
        }
        return new NormalType(type, field);
    }
}
