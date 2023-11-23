package org.nature.net.mapper;

import org.nature.util.handler.SolutionHandler;

/**
 * @author hjy
 * 2023/3/26
 */
public class FormDataParamMapper extends UrlParamMapper{
    private static final String SIGN = "Content-Disposition: form-data; ";

    @Override
    public Object[] mapping(SolutionHandler.ProxyParams proxyParams, String paramStr) {
        final StringBuilder builder = new StringBuilder();
        final String boundary = paramStr.substring(0, paramStr.indexOf(SIGN));
        paramStr = paramStr.replaceAll(SIGN, "");
        for (String item : paramStr.split(boundary)) {
            if (item.startsWith("n")) {
                builder.append(item.replaceAll("name=\"", "").replaceFirst("\"", "=")).append("&");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return super.mapping(proxyParams, builder.toString());
    }
}
