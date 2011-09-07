package org.nickgrealy.commons.reflection;

import java.util.Map;

/**
 * Use reflection to directly access objects.
 * 
 * @author nick.grealy
 */
public interface IBeanUtil {

    <I> I createBean(Class<I> clazz);

    <I> I createBean(Class<I> clazz, Map<String, Object> properties);

    void setProperty(Object object, String field, Object property);

    Object getProperty(Object object, String field);

    void copyProperties(Object object, Map<String, Object> properties);

    void copyProperties(Object from, Object to);

    void copyProperties(Object from, Object to, String... properties);

    void copyProperties(Object from, Object to, Map<String, String> properties);

    void copyProperties(Object from, Object to, int classLevel);

}
