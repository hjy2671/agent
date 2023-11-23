package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantFloat extends MetaConstant{
    public static final int TAG = 4;
    private byte[] bytes = new byte[4];
    private Float value;

    public ConstantFloat(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(bytes, 0, 4);
        return this;
    }

    @Override
    public String getValue() {
        if (value == null)
            value = BytesUtil.parseFloat(bytes);
        return value.toString();
    }
}
