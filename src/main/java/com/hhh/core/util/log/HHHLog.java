package com.hhh.core.util.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface HHHLog {
	String moduleFirst() default "无";//模块一
	String moduleSecond() default "无";//模块二
	String moduleThird() default "无";//模块三
	String info() default "无";//具体描述
}
