package test.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author hjy
 * 2023/4/5
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final byte[] bytes = Files.readAllBytes(new File("D:\\IDEAProject\\Java\\target\\classes\\test\\test\\ClassStruct.class").toPath());
        final ClassFile classFile = new ClassFile(bytes);
        classFile.resolve();

//        final byte[] bytes = {2, 2};
//        System.out.println(NumberUtil.bytesToInt(bytes));

    }

}
