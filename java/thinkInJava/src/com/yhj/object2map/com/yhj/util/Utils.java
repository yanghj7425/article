package com.yhj.object2map.com.yhj.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static <K, V> Map<K, V> ObjectToMap(Object o) {
        Map<K, V> map = new HashMap<K, V>();
        System.out.println(o.getClass().getSimpleName());
        Field[] fs = o.getClass().getDeclaredFields();
        for (Field f : fs) {
            V entity = null;
            char[] name = f.getName().toCharArray();
            name[0] -= 32;
            String methodName = "get" + String.valueOf(name);
            try {
                Method method = o.getClass().getDeclaredMethod(methodName);
                method.setAccessible(true);
                entity = (V) method.invoke(o);
                map.put((K) String.valueOf(name).toLowerCase(), entity);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("找不到对应方法或者属性首字母是非英文字符");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
