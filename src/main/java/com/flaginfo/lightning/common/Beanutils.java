package com.flaginfo.lightning.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.beans.PropertyDescriptor;

/**
 * 
 * 两个相同类型的javaBean合并为一个javaBean
 * 
 * @author zyong
 *
 */
public final class Beanutils {
    // merge two bean by discovering differences
    public static <M> void merge(M target, M destination) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                
//                if (descriptor.getPropertyType() == Long.class) {
//                    Long newValue = (Long) descriptor.getReadMethod().invoke(destination);
//
//                    if (newValue != null && newValue != 0) {
//                        Object defaultValue = descriptor.getReadMethod().invoke(destination);
//                        descriptor.getWriteMethod().invoke(target, defaultValue);
//                    }
//                } else {
//                    Object newValue = descriptor.getReadMethod().invoke(destination);
//
//                    if (newValue != null) {
//                        Object defaultValue = descriptor.getReadMethod().invoke(destination);
//                        descriptor.getWriteMethod().invoke(target, defaultValue);
//                    }
//                }
                
                Object newValue = descriptor.getReadMethod().invoke(destination);

                if (newValue != null) {
                    Object defaultValue = descriptor.getReadMethod().invoke(destination);
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }

            }
        }
    }
    
    /**
     * 合并对象，将src的值合并入des，如果值为null，则不覆盖
     * 
     * @param <T>
     * @param srcObj
     * @param desObj
     */
    public static <T> void mergeObject(T srcObj, T desObj) {
        if (srcObj == null || desObj == null)
            return;

        Field[] fs = srcObj.getClass().getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            try {
                if (Modifier.isStatic(fs[i].getModifiers())) {
                    continue;
                }
                
                fs[i].setAccessible(true);
                Object value = fs[i].get(srcObj);
                if (null != value) {
                    fs[i].set(desObj, value);
                }
                fs[i].setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
