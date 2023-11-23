package test.test;

import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/3/30
 */
public class MethodPool implements ClassStruct {

    private byte[] countBytes = new byte[METHOD_COUNT_SIZE];
    private int count;
    private List<MethodInfo> methodInfos = new ArrayList<>();


    @Override
    public MethodPool construct(Resource resource) {
        resource.copyAndOffset(countBytes, 0, FIELDS_COUNT_SIZE);
        count = BytesUtil.parseInt(countBytes);
        for (int i = 0; i < count; i++) {
            methodInfos.add(new MethodInfo().construct(resource));
        }
        return this;
    }
}
