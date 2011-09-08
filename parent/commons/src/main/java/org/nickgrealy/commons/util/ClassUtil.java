/**
 * 
 */
package org.nickgrealy.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author nick.grealy
 */
public final class ClassUtil {

    private final Map<Class<?>, Class<?>> primitiveToObjectClassMap = new HashMap<Class<?>, Class<?>>();

    public ClassUtil() {
        // primitive to object map
        primitiveToObjectClassMap.put(byte.class, Byte.class);
        primitiveToObjectClassMap.put(short.class, Short.class);
        primitiveToObjectClassMap.put(int.class, Integer.class);
        primitiveToObjectClassMap.put(long.class, Long.class);
        primitiveToObjectClassMap.put(float.class, Float.class);
        primitiveToObjectClassMap.put(double.class, Double.class);
        primitiveToObjectClassMap.put(boolean.class, Boolean.class);
        primitiveToObjectClassMap.put(char.class, Character.class);
    }

    /**
     * The class equivalent of (manual) autoboxing.
     * 
     * @param clazz
     * @return
     */
    public Class<?> convertPrimitiveToObjectClass(Class<?> clazz) {
        if (isPrimitiveClass(clazz)) {
            return primitiveToObjectClassMap.get(clazz);
        }
        return clazz;
    }

    /**
     * Determines whether the given clazz is a primitive class.
     * 
     * @param clazz
     * @return
     */
    public boolean isPrimitiveClass(Class<?> clazz) {
        return primitiveToObjectClassMap.containsKey(clazz);
    }
}
