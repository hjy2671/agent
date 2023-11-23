package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantDouble extends MetaConstant{
    public static final int TAG = 6;
    private final byte[] bytes = new byte[8];

    private Double value;

    public ConstantDouble(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(bytes, 0, 4)
                .copyAndOffset(bytes, 4, 4);
        return this;
    }

    @Override
    public String getValue() {
        if (value == null)
            value = BytesUtil.parseDouble(bytes);
        return value.toString();
    }
}
