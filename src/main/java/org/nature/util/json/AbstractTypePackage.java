package org.nature.util.json;

/**
 * 多个type之间传递泛型
 * @author hjy
 */
public abstract class AbstractTypePackage {

    protected String genericType;

    public String getGenericType() {
        return genericType;
    }

    public AbstractTypePackage  initGenericType(String genericType) {
        final int start = genericType.indexOf('<');
        if (start < 0) {
            this.genericType = AbstractGenericType.DEFAULT_GENERIC;
        } else {
            this.genericType = genericType.substring(start+1, genericType.lastIndexOf('>'));
        }
        return this;
    }
}
