package test.test.attribute;

import test.test.AttributeInfo;
import test.test.Resource;
import test.test.constant.Meta;

/**
 * @author hjy
 * 2023/4/12
 */
public class AnnotationDefault extends AttributeInfo {

    public AnnotationDefault(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        return null;
    }

    @Override
    public void update(Meta meta) {

    }
}
