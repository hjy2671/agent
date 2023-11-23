package test.test;

/**
 * @author hjy
 * 2023/4/4
 */
public class Resource {

    protected byte[] resource;
    protected int current;
    protected ClassFile classFile;

    public Resource(byte[] resource, ClassFile classFile) {
        this.resource = resource;
        this.classFile = classFile;
    }

    public Resource copyNextAndOffset(byte[] value, int start, int size) {
        next();
        return copyAndOffset(value, start, size);
    }

    public Resource copyAndOffset(byte[] value, int start, int size) {
        copy(value, start, size);
        offset(size);
        return this;
    }

    public void copy(byte[] value, int start, int size) {
        System.arraycopy(resource, current, value, start, size);
    }

    public void next() {
        current++;
    }

    public void offset(int val) {
        current += val;
    }

    public int flag() {
        return resource[current];
    }

    public byte[] getResource() {
        return resource;
    }

    public ClassFile getClassFile() {
        return classFile;
    }
}
