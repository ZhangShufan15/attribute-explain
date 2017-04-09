package com.dangdang.ae.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标记属性解释信息的注解，运行期有效。
 * 
 * @author zhangxiansheng
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeExplain {
	String explaintInfo() default "";
}
