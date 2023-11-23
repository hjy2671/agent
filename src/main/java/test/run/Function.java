package test.run;

import org.nature.net.annotation.Solution;

public class Function {

    public static long firstTime() {
        return System.currentTimeMillis();
    }

    public static long lastTime() {
        return System.currentTimeMillis();
    }

    public static void print(long f, long l) {
        System.out.println(l - f + "ms");
    }

}
