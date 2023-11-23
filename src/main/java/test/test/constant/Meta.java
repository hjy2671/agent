package test.test.constant;

import test.test.ClassStruct;
import test.test.Resource;

/**
 * @author hjy
 * 2023/3/30
 */
public interface Meta extends ClassStruct {

    String SEPARATOR = ":";

    /**
     * 通知所有引用当前常量对象的人
     * @param meta 当前常量对象
     */
    void notify(Meta meta);

    /**
     * 更新自己的索引
     * @param index 新索引
     */
    void updateIndex(int index);

    /**
     *
     * @return 自己的索引
     */
    int getIndex();

    /**
     *
     * @return 返回上一次更新时的索引
     */
    int getOldIndex();

    /**
     * 增加引用自己的对象
     * @param quoter 引用者
     */
    void addQuoter(Quoter quoter);

    @Override
    Meta construct(Resource resource);

    String getValue();
}
