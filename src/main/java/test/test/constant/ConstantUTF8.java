package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantUTF8 extends MetaConstant{

    public static final int TAG = 1;
    private final byte[] len = new byte[2];
    private byte[] bytes;

    private int length;
    private String value;

    public ConstantUTF8(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(len, 0, 2);
        length = BytesUtil.parseInt(len);
        bytes = new byte[length];
        resource.copyAndOffset(bytes, 0, length);
        return this;
    }

    @Override
    public String getValue() {
        if (value == null)
            value = BytesUtil.parseString(bytes);
        return value;
    }


}
