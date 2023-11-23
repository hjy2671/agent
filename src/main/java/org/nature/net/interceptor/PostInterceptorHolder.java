package org.nature.net.interceptor;

import org.nature.net.HttpPackage;

public class PostInterceptorHolder {

    private final static InterceptorHolder holder = new InterceptorHolder();

    public static void putInterceptor(PostInterceptor interceptor) {
        holder.putInterceptor(interceptor);
    }

    public static HttpPackage doIntercept(HttpPackage httpPackage) {
        return holder.doIntercept(httpPackage, true);
    }

}
