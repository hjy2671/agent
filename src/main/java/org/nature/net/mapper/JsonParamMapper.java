package org.nature.net.mapper;

import org.nature.util.handler.SolutionHandler;
import org.nature.util.json.JsonFinder;
import org.nature.util.json.TypeFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hjy
 * 2023/3/26
 */
public class JsonParamMapper extends AbstractRequestParamMapper{

    @Override
    public Object[] mapping(SolutionHandler.ProxyParams proxyParams, String paramStr) {
        Map<String, JsonFinder.GenericType> map = new HashMap<>();
        for (int i = 0; i < proxyParams.getTypes().length; i++) {
            map.put(proxyParams.getName(i), new JsonFinder.GenericType(proxyParams.getType(i), proxyParams.getGeneric(i).getTypeName()));
        }
        final Map<String, Object> result = new JsonFinder(paramStr, new TypeFactory(), map).find();
        final Object[] objects = new Object[proxyParams.paramNum()];
        for (int i = 0; i < proxyParams.getNames().length; i++) {
            final String name = proxyParams.getName(i);
            if (result.containsKey(name)) {
                objects[i] = result.get(name);
            }
        }
        return objects;
    }

}
