package org.nature.net.interceptor;

import org.nature.net.HttpPackage;

public interface Interceptor {

    HttpPackage intercept(HttpPackage httpPackage);

    int getPriority();

}
