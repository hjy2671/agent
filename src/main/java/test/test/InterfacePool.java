package test.test;

import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/3/30
 */
public class InterfacePool implements ClassStruct {

    private byte[] bytes = new byte[INTERFACES_COUNT_SIZE];
    private int count;
    private List<InterfaceInfo> interfaceInfos = new ArrayList<>();

    @Override
    public InterfacePool construct(Resource resource) {
        resource.copyAndOffset(bytes, 0, INTERFACES_COUNT_SIZE);
        count = BytesUtil.parseInt(bytes);

        int i = 0;
        while (i++ < count) {
            interfaceInfos.add(new InterfaceInfo().construct(resource));
        }
        return this;
    }
}
