package org.nature.net;

import org.nature.util.ScanPath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Configuration {
    public final static String CACHE_KEY = "org.nature.net.configuration";
    public final static String IP_SEPARATOR = "\\|";
    public final static String PARAMETERS_CACHE_KEY_SUFFIX = "PARAMETERS_TYPE_CACHE";

    private final List<ScanPath> scanPath = new ArrayList<>();
    private String ipGroup;
    private boolean isAllow = false;

    {
        scanPath.add(ScanPath.newPath("org.nature.util.handler"));
        scanPath.add(ScanPath.newPath("org.nature.net"));
        scanPath.add(ScanPath.newPath("org.nature.shadow"));
    }

    private int port = 8888;
    private int backlog = 0;

    private String scanPackagePath;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public String getScanPackagePath() {
        return scanPackagePath;
    }

    public void setScanPackagePath(String scanPackagePath) {
        this.scanPackagePath = scanPackagePath;
    }

    public void addScanPath(String path, int priority) {
        scanPath.add(new ScanPath(path, priority));
    }

    public List<ScanPath> getScanPath() {
        scanPath.sort(Comparator.comparingInt(ScanPath::getPriority));
        return scanPath;
    }

    public String getIpGroup() {
        return ipGroup;
    }

    public void setIpGroup(String ipGroup) {
        this.ipGroup = ipGroup;
    }

    public boolean isAllow() {
        return isAllow;
    }

    public void setAllow(boolean allow) {
        isAllow = allow;
    }
}
