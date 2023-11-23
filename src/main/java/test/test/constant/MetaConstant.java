package test.test.constant;

import test.test.ConstantPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/3/30
 */
public abstract class MetaConstant implements Meta{

    private int oldIndex;
    private int index;
    protected final List<Quoter> quotes = new ArrayList<>();
    protected final ConstantPool pool;

    public MetaConstant(int index, ConstantPool pool) {
        this.index = index;
        this.pool = pool;
    }

    @Override
    public void notify(Meta meta) {
        quotes.forEach(quoter -> quoter.update(meta));
    }

    @Override
    public final void updateIndex(int index) {
        this.oldIndex = this.index;
        this.index = index;
        notify(this);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getOldIndex() {
        return oldIndex;
    }

    @Override
    public void addQuoter(Quoter quoter) {
        quotes.add(quoter);
    }
}
