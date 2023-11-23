package test.test;

import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/3/30
 */
public class FieldPool implements ClassStruct {

    private byte[] countBytes = new byte[FIELDS_COUNT_SIZE];
    private int count;
    private List<FieldInfo> fieldInfos = new ArrayList<>();

    @Override
    public FieldPool construct(Resource resource) {
        resource.copyAndOffset(countBytes, 0, FIELDS_COUNT_SIZE);
        count = BytesUtil.parseInt(countBytes);
        for (int i = 0; i < count; i++) {
            fieldInfos.add(new FieldInfo().construct(resource));
        }
        return this;
    }
}
