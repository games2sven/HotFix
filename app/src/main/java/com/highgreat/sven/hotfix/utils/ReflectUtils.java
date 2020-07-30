package com.highgreat.sven.hotfix.utils;

import java.lang.reflect.Field;

public class ReflectUtils {

    /**
     * 通过反射获取某对象，并设置私有可访问
     * @param obj  该属性所属类的对象
     * @param clazz 该属性所属类
     * @param field 属性名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object obj,Class clazz,String field) throws
            NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 给dexElements属性赋值
     * @param object 该属性所属类对象PathList对象
     * @param clazz  该属性所属类
     * @param value  值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setField(Object object,Class clazz,Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field dexElements = clazz.getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        dexElements.set(object,value);
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象
     * @param baseDexClassLoader
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getPathList(Object baseDexClassLoader) throws
            ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = Class.forName("dalvik.system.BaseDexClassLoader");
        return getField(baseDexClassLoader,aClass,"pathList");
    }

    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象，再获取dexElements对象
     * @param paramObject
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getDexElements(Object paramObject) throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }

}
