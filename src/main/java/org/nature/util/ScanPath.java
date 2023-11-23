package org.nature.util;

public final class ScanPath {

    private String path;
    private int priority;
    private static int priorityIndex;

    public ScanPath(String path, int priority) {
        this.path = path;
        this.priority = Math.abs(priority) + 256;
    }

    private ScanPath(){}

    public String getPath() {
        return path;
    }

    public int getPriority() {
        return priority;
    }

    public static ScanPath newPath(String path) {
        final ScanPath scanPath = new ScanPath();
        scanPath.path = path;
        scanPath.priority = priorityIndex++;
        return scanPath;
    }
}
