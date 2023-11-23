package org.nature.net.mapper;

import org.nature.util.handler.SolutionHandler;

/**
 * @author hjy
 * 2023/3/26
 */
public abstract class AbstractRequestParamMapper {
    public abstract Object[] mapping(SolutionHandler.ProxyParams proxyParams, String paramStr);
}
