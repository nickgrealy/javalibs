/**
 * 
 */
package org.nickgrealy.commons.util;

import org.nickgrealy.commons.validation.RuntimeAssert;

import java.util.*;

/**
 * TODO Make into an IConverter
 * @author nickgrealy@gmail.com
 */
public final class ClassUtil {

    private ClassUtil(){
    }

    private static final Map<Class<?>, Class<?>> primitiveToObjectClassMap = new HashMap<Class<?>, Class<?>>();

    static {
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
    public static Class<?> convertPrimitiveToObjectClass(Class<?> clazz) {
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
    public static boolean isPrimitiveClass(Class<?> clazz) {
        return primitiveToObjectClassMap.containsKey(clazz);
    }

    /**
     * Builds a {@link OneToManyMap} of classes, to their assignable classes.
     * @param classesSet
     * @return
     */
    public static OneToManyMap<Class<?>, Class<?>, Set<Class<?>>> buildInheritanceMap(final Set<Class<?>> classesSet) {
        // setup
        int size = classesSet.size();
        RuntimeAssert.check("size > 2", size).isGt(2);
        Class<?>[] classes = classesSet.toArray(new Class<?>[size]);
        OneToManyMap<Class<?>, Class<?>, Set<Class<?>>> classToParents = new OneToManyMap(HashSet.class);
        // logic
        int iMax = size - 1;
        for (int i = 0; i < iMax; i++) {
            for (int j = i + 1; j < size; j++) {
                Class<?> a = classes[i];
                Class<?> b = classes[j];
                if (a.isAssignableFrom(b)){
                    classToParents.put(b, a);
                    classesSet.remove(a);
                    classesSet.remove(b);
                } else if (b.isAssignableFrom(a)){
                    classToParents.put(a, b);
                    classesSet.remove(a);
                    classesSet.remove(b);
                }
                // else ignore, they're not related...
            }
        }
        // add remaining non-related classes, under the "null" key.
        classToParents.put(null, classesSet);
        return classToParents;
    }
}
