package test.test.util;

/**
 * @author hjy
 * 2023/4/5
 */
public class BytesUtil {

    public static int parseInt(byte[] bytes) {
        int result = 0;
        int shift = 0;
        for (int i = bytes.length - 1; i >= 0 ; i--,shift += 8)
            result += fixByte(bytes[i]) << shift;
        return result;
    }

    public static long parseLong(byte[] bytes) {
        long result = 0;
        int shift = 0;
        for (int i = bytes.length - 1; i >= 0 ; i--,shift += 8)
            result += (long) fixByte(bytes[i]) << shift;
        return result;
    }

    public static float parseFloat(byte[] bytes) {
        return Float.intBitsToFloat(parseInt(bytes));
    }

    public static double parseDouble(byte[] bytes) {
        return Double.longBitsToDouble(parseLong(bytes));
    }

    public static String parseString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte val :bytes){
            builder.append(((char) val));
        }
        return builder.toString();
    }

    public static int fixByte(byte val) {
        return ((val & 128) << 1) + val;
    }
}
