package test.test;

import test.test.constant.Meta;
import test.test.constant.Quoter;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/4/5
 */
public class InterfaceInfo implements ClassStruct, Quoter {

    private byte[] value = new byte[2];
    private int constantIndex;

    @Override
    public void update(Meta meta) {
        constantIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(constantIndex).addQuoter(this);
    }

    @Override
    public InterfaceInfo construct(Resource resource) {
        resource.copyAndOffset(value, 0, 2);
        resource.getClassFile()
                .getConstantPool()
                .get(constantIndex = BytesUtil.parseInt(value))
                .addQuoter(this);
        return this;
    }
}
