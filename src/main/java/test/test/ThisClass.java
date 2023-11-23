package test.test;

import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/4/4
 */
public class ThisClass implements ClassStruct{

    private byte[] thisClass = new byte[THIS_CLASS_SIZE];
    private int point2Index;

    @Override
    public ThisClass construct(Resource resource) {
        resource.copyAndOffset(thisClass, 0, THIS_CLASS_SIZE);
        point2Index = BytesUtil.parseInt(thisClass);
        return this;
    }
}
