package org.nature.util.json;

import java.lang.reflect.Field;

/**
 *
 * @author hjy
 * 2023/3/24
 */
public abstract class AbstractTypeFactory {

    protected AbstractTypeFactory typeFactory;

    public AbstractTypeFactory(AbstractTypeFactory type) {
        this.typeFactory = type;
    }

    public AbstractTypeFactory() {
    }

    public abstract AbstractType createType(Class<?> type, Field field, AbstractTypePackage typePackage);

    public AbstractType createType(Class<?> type) {
        return createType(type, null, null);
    };

    public AbstractType createType(Class<?> type, Field field) {
        return createType(type, field, null);
    };

}
