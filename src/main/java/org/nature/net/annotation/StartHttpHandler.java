package org.nature.net.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记起始请求处理类，最后标记的覆盖之前的
 * @author hjy
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface StartHttpHandler {

}
