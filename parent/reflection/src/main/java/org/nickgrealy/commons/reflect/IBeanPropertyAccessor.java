package org.nickgrealy.commons.reflect;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Use reflection to directly access objects.
 * 
 * @author nickgrealy@gmail.com
 */
public interface IBeanPropertyAccessor {

    /**
     * The default depth to copy fields from. See
     * {@link #copyProperties(Object, Object)}.
     */
    int DEFAULT_CLASS_DEPTH = 3;

    boolean isInstantiable(Class<?> clazz);
    
    <I> I createBean(Class<I> clazz);

    <I> I createBean(Class<I> clazz, Map<String, Object> properties);

    void setProperty(Object object, String field, Object property);

    void setProperty(Object object, Field field, Object property);

    Object getProperty(Object object, String field);

    Object getProperty(Object object, Field field);

    Field getFieldRecursively(Class<?> clazz, String field);

    void copyProperties(Object object, Map<String, Object> properties);

    /* copy properties 'from' -> 'to' */

    /**
     * Copies fields from the 'from' object, to the 'to' object.
     * <p/>
     * 
     * <b>N.B.</b> Invokes {@link #copyProperties(Object, Object, int)} with
     * maxClassLevel = {@link #DEFAULT_CLASS_DEPTH}.
     * 
     * @param from
     * @param to
     */
    void copyProperties(Object from, Object to);

    void copyProperties(Object from, Object to, String... properties);

    void copyProperties(Object from, Object to, Map<String, String> properties);

    /**
     * Copies fields from the 'from' object, to the 'to' object, down to the
     * maxClassLevel.
     * <p/>
     * 
     * <b>N.B.</b>
     * <ul>
     * <li>Fields with modifiers {@link java.lang.reflect.Modifier#FINAL} are
     * ignored.</li>
     * <li>'from' class must be assignable from the 'to' class.</li>
     * </ul>
     * 
     * @param from
     *            Object
     * @param to
     *            Object
     * @param maxClassLevel
     *            the maximum number of superClasses to traverse up.
     */
    void copyProperties(Object from, Object to, int maxClassLevel);

    /**
     * Copies fields from the 'from' object, to the 'to' object, down to the
     * maxClassLevel.
     * <p/>
     * 
     * <b>N.B.</b>
     * <ul>
     * <li>Fields with modifiers {@link java.lang.reflect.Modifier#FINAL} are
     * ALWAYS ignored.</li>
     * <li>'from' class must be assignable from the 'to' class.</li>
     * </ul>
     * 
     * @param from
     *            Object
     * @param to
     *            Object
     * @param maxClassLevel
     *            the maximum number of superClasses to traverse up.
     * @param ignoreFieldsWithModifiers
     *            fields with these modifiers will be ignored.
     */
    void copyProperties(Object from, Object to, int maxClassLevel, int ignoreFieldsWithModifiers);

}
