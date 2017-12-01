package com.flaginfo.lightning.common;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

public class GenericUtil {
	/**
	 * 获取参数真正类型
	 * 
	 * @return
	 */
	public static Class getActualClass(Class clazz, int index) {
		Type type = clazz.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return getActualClass(clazz.getSuperclass(), index);
		}
		Type[] types = ((ParameterizedType) type).getActualTypeArguments();
		if (index >= types.length || index < 0) {
			return Object.class;
		}
		if (types[index] instanceof Class) {
			return (Class) types[index];
		}
		return Object.class;
	}
}
