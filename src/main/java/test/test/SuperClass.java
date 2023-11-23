package test.test;

import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class SuperClass implements ClassStruct{

    private byte[] superClass = new byte[SUPER_CLASS_SIZE];
    private int point2Index;

    @Override
    public SuperClass construct(Resource resource) {
        resource.copyAndOffset(superClass, 0, SUPER_CLASS_SIZE);
        point2Index = BytesUtil.parseInt(superClass);
        return this;
    }
}
