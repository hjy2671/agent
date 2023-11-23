package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantInteger extends MetaConstant{
    public static final int TAG = 3;
    private final byte[] bytes = new byte[4];
    private Integer value;

    public ConstantInteger(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(bytes, 0, 4);
        return this;
    }

    @Override
    public String getValue() {
        if (null == value)
            value = BytesUtil.parseInt(bytes);
        return value.toString();
    }
}
