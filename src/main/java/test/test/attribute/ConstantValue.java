package test.test.attribute;

import test.test.AttributeInfo;
import test.test.Resource;
import test.test.constant.Meta;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/4/12
 */
public class ConstantValue extends AttributeInfo {

    private final byte[] valueIndexBytes = new byte[2];
    private Meta constantValue;

    public ConstantValue(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        resource.copyAndOffset(valueIndexBytes, 0, 2);
        return this;
    }

    @Override
    public void link(Resource resource) {
        super.link(resource);
        constantValue = resource.getClassFile().getConstantPool().get(BytesUtil.parseInt(valueIndexBytes));
    }

    @Override
    public void update(Meta meta) {

    }
}
