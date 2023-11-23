package org.nature.net.mapper;

import org.nature.util.ObjUtil;
import org.nature.util.handler.SolutionHandler;

/**
 * @author hjy
 * 2023/3/26
 */
public class UrlParamMapper extends AbstractRequestParamMapper{
    @Override
    public Object[] mapping(SolutionHandler.ProxyParams proxyParams, String paramStr) {
        Object[] params = new Object[proxyParams.paramNum()];
        if (paramStr == null)
            return params;

        final String[] val = paramStr.split("(=)|(&)");
        int len = val.length;
        for (int i = 0; i < len - (len % 2) - 1; i += 2) {
            for (int j = 0; j < proxyParams.getNames().length; j++) {
                if (val[i].equals(proxyParams.getName(j))) {
                    params[j] = ObjUtil.convert(proxyParams.getType(j), val[i+1]);
                    break;
                }
            }
        }
        return params;
    }
}
