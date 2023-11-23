package org.nature.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

    public static String packagePath2filePath(String packagePath) {
        return packagePath.replace(".", "/");
    }

    public static String filePath2packagePath(String filePath) {
        return filePath.replace("/", ".");
    }

    public static String ignoreNull(String val) {
        return val == null ? "" : val;
    }

    public static boolean isEmpty(String val) {
        return val == null || val.equals("");
    }

    public static String firstToUp(String val) {
        if (val.length() < 1) {
            return val.toUpperCase();
        }
        return String.valueOf(val.charAt(0)).toUpperCase().concat(val.substring(1));
    }

}
