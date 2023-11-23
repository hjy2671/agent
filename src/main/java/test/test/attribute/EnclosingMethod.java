package test.test.attribute;

import test.test.AttributeInfo;
import test.test.Resource;
import test.test.constant.Meta;

/**
 * @author hjy
 * 2023/4/12
 */
public class EnclosingMethod extends AttributeInfo {

    private byte[] classIndex = new byte[2];
    //如果当前类没有立即包含在方法或构造函数中，那么method_index项的值必须为零。
    private byte[] methodIndex = new byte[2];

    public EnclosingMethod(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        resource.copyAndOffset(classIndex, 0, 2)
                .copyAndOffset(methodIndex, 0, 2);
        return this;
    }

    @Override
    public void link(Resource resource) {
        super.link(resource);

    }

    @Override
    public void update(Meta meta) {

    }
}
