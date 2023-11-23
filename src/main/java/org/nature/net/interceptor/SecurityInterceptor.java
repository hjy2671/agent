package org.nature.net.interceptor;

import org.nature.net.exception.AccessDeniedException;
import org.nature.net.Configuration;
import org.nature.net.HttpPackage;
import org.nature.util.ObjectHolder;
import org.nature.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SecurityInterceptor implements PreInterceptor{

    private static List<Pattern> list;

    @Override
    public HttpPackage intercept(HttpPackage httpPackage) {
        final Configuration config = ObjectHolder.get(Configuration.CACHE_KEY, Configuration.class);

        if (StrUtil.isEmpty(config.getIpGroup()))
            return httpPackage;

        if (list == null) {
            toRegx(config.getIpGroup().split(Configuration.IP_SEPARATOR));
        }

        final String ip = httpPackage.getHttpExchange().getRemoteAddress().getAddress().getHostAddress();
        if (config.isAllow()) {
            if (match(ip)) {
                return httpPackage;
            }
        } else {
            if (match(ip)) {
                throw new AccessDeniedException("访问拒绝");
            }
        }
        return httpPackage;
    }

    private boolean match(String ip) {
        for (Pattern pattern : list) {
            if (pattern.matcher(ip).matches()) {
                return true;
            }
        }
        return false;
    }

    private void toRegx(String[] ips) {
        list = Arrays.stream(ips).map(s -> s.replace(".", "\\.").replace("*", "[0-9]+")).map(Pattern::compile).collect(Collectors.toList());
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
