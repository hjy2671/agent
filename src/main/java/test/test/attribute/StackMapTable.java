package test.test.attribute;

import test.test.AttributeInfo;
import test.test.Resource;
import test.test.constant.Meta;

/**
 * @author hjy
 * 2023/4/12
 */
public class StackMapTable extends AttributeInfo {

    private byte[] value;

    public StackMapTable(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        value = new byte[len];
        resource.copyAndOffset(value, 0, len);
        return this;
    }

    @Override
    public void update(Meta meta) {

    }
}
