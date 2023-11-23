package test.test;

import test.test.constant.*;
import test.test.constant.Meta;
import test.test.util.BytesUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantPool implements ClassStruct {

    private final byte[] count = new byte[CONSTANT_POOL_COUNT_SIZE];
    private int len;
    private final Map<Integer, Meta> pool = new LinkedHashMap<>();
    private int index = 0;
    @Override
    public ConstantPool construct(Resource resource) {
        System.arraycopy(resource.resource, resource.current, count, 0, CONSTANT_POOL_COUNT_SIZE);
        resource.current += CONSTANT_POOL_COUNT_SIZE;
        len = BytesUtil.parseInt(count) - 1;

        while (index++ < len) {
            pool.put(index, constructMeta(resource).construct(resource));
        }

        return this;
    }

    public void link(Resource resource) {
        pool.forEach((integer, meta) -> {
            if (meta instanceof Quoter)
                ((Quoter) meta).link(resource);
        });
    }

    /**
     *  double 和 long占2个位置，常量池索引也要加一
     */
    public Meta constructMeta(Resource resource) {
        switch (resource.flag()) {
            case ConstantClassInfo.TAG:
                return new ConstantClassInfo(index, this);
            case ConstantDouble.TAG:
                return new ConstantDouble(++index, this);
            case ConstantFieldRef.TAG:
                return new ConstantFieldRef(index, this);
            case ConstantFloat.TAG:
                return new ConstantFloat(index, this);
            case ConstantInteger.TAG:
                return new ConstantInteger(index, this);
            case ConstantInterfaceMethodRef.TAG:
                return new ConstantInterfaceMethodRef(index, this);
            case ConstantInvokeDynamic.TAG:
                return new ConstantInvokeDynamic(index, this);
            case ConstantLong.TAG:
                return new ConstantLong(++index, this);
            case ConstantMethodRef.TAG:
                return new ConstantMethodRef(index, this);
            case ConstantMethodHandle.TAG:
                return new ConstantMethodHandle(index, this);
            case ConstantMethodType.TAG:
                return new ConstantMethodType(index, this);
            case ConstantNameAndType.TAG:
                return new ConstantNameAndType(index, this);
            case ConstantString.TAG:
                return new ConstantString(index, this);
            case ConstantUTF8.TAG:
                return new ConstantUTF8(index, this);
        }
        throw new RuntimeException("非法的常量池结构".concat(resource.flag() + ""));
    }

    public Meta get(int index) {
        return pool.get(index);
    }

    public int size() {
        return len;
    }

}
