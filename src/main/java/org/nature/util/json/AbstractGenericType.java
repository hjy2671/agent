package org.nature.util.json;

import java.lang.reflect.Field;

public abstract class AbstractGenericType extends AbstractType{

    public static final String DEFAULT_GENERIC = "java.lang.String";

    public AbstractGenericType(Class<?> type, Field field, AbstractTypePackage typePackage) {
        super(type, field, typePackage);
        initTypePackage();
    }

    protected void initTypePackage() {
        if (typePackage == null) {
            if (field == null)
                return;
            typePackage = new TypePackage();
            typePackage.initGenericType(field.getGenericType().getTypeName());
            return;
        }
        typePackage = new TypePackage().initGenericType(typePackage.getGenericType());
    }

}
