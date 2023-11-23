package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantLong extends MetaConstant{

    public static final int TAG = 5;
    private final byte[] bytes = new byte[8];
    private Long value;

    public ConstantLong(int index, ConstantPool pool) {
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
            value = BytesUtil.parseLong(bytes);
        return value.toString();
    }
}
