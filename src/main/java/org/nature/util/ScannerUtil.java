package org.nature.util;

import com.sun.jdi.InternalException;
import org.nature.util.handler.ClassByteArrayResolver;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ScannerUtil {

    private static final ClassLoader classLoader = ScannerUtil.class.getClassLoader();
    private static final ByteArrayClassLoader classLoader2 = new ByteArrayClassLoader();

    public static void scan(final String packageName) {
        if (findClassFromLocal(packageName))
            return;
        findClassFromJar(packageName);
    }

    public static void scan(final List<ScanPath> pathList) {
        for (ScanPath path : pathList) {
            scan(path.getPath());
        }
    }

    public static boolean findClassFromLocal(final String packageName) {
        URI uri = null;
        try {
            uri = Objects.requireNonNull(classLoader.getResource(StrUtil.packagePath2filePath(packageName))).toURI();
            if (uri.toString().startsWith("jar"))
                return false;
        } catch (URISyntaxException | NullPointerException e) {
            throw new InternalException("包名错误 ".concat(packageName).concat(" ").concat(e.getMessage()));
        }
        recursiveScanFromLocal(uri, packageName);
        return true;
    }

    private static void recursiveScanFromLocal(URI uri, String packageName) {
        File[] files = Objects.requireNonNull(new File(uri).listFiles());

        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                recursiveScanFromLocal(convertFilePath2Uri(file), packageName + "." + fileName);
            }
            if (isClass(fileName)) {
                try {
                    ClassByteArrayResolver.resolve(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static URI convertFilePath2Uri(File file) {
        String path = null;
        try {
            path = "file:/" + file.getAbsolutePath().replace("\\", "/");
            return new URI(path);
        } catch (URISyntaxException e) {
            throw new InternalException("文件路径有误 " + e.getMessage());
        }
    }

    public static void findClassFromJar(final String packName) {
        String pathName = StrUtil.packagePath2filePath(packName);
        JarFile jarFile = null;
        try {
            URL url = classLoader.getResource(pathName);
            assert url != null;
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            jarFile = jarURLConnection.getJarFile();
        } catch (IOException | NullPointerException e) {
            throw new InternalException("扫描jar包中类时错误 " + e.getMessage());
        } catch (ClassCastException e) {
            throw new InternalException("扫描路径非jar包" + e.getMessage());
        }
        recursiveScanFromJar(jarFile.entries(), pathName, jarFile);

    }

    /**
     * 扫描包路径
     * @param jarEntries jar文件里面的条目
     * @param packagePath 包路径 ”com.xxx“
     * @param jarFile 用来复制新的jarEntries，作递归参数
     */
    private static void recursiveScanFromJar(Enumeration<JarEntry> jarEntries, String packagePath, JarFile jarFile) {
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();

            if (jarEntryName.contains(packagePath) && notCurrentPackagePath(jarEntryName, packagePath)) {

                if (jarEntry.isDirectory()) {
                    //是文件则递归遍历
                    recursiveScanFromJar(
                            locateJarEntries(jarFile.entries(), jarEntryName),
                            filePath2PackagePath(jarEntryName),
                            jarFile);
                }
                if (isClass(jarEntryName)) {
                    try(final InputStream inputStream = jarFile.getInputStream(jarEntry);) {
                        ClassByteArrayResolver.resolve(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static String filePath2PackagePath(String fileName) {
        String clazzName = fileName.replace("/", ".");
        return clazzName.substring(0, clazzName.lastIndexOf("."));
    }

    private static String fileName2ClassName(String fileName) {
        return fileName.replace("/", ".").replace(".class", "");
    }

    private static String fileName2ClassName(String packageName, String fileName) {
        return packageName + "." + fileName.replace(".class", "");
    }

    private static boolean isClass(String fileName) {
        return fileName.endsWith(".class");
    }

    private static boolean notCurrentPackagePath(String fileName, String packagePath) {
        return !fileName.equals(packagePath + "/");
    }

    /**
     * 定位到上次迭代的位置
     * @param jarEntries 待定位的
     * @param jarEntryName 用来定位的名字
     * @return 定位后的JarEntries
     */
    private static Enumeration<JarEntry> locateJarEntries(Enumeration<JarEntry> jarEntries, String jarEntryName) {
        while (jarEntries.hasMoreElements()) {
            if (jarEntries.nextElement().getName().equals(jarEntryName)) {
                return jarEntries;
            }
        }
        return jarEntries;
    }
}
