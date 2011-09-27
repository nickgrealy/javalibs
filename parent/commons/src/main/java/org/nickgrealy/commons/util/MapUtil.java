/**
 * 
 */
package org.nickgrealy.commons.util;

import java.util.Collection;
import java.util.Map;

import org.nickgrealy.commons.reflect.BeanPropertyAccessor;
import org.nickgrealy.commons.reflect.IBeanPropertyAccessor;

/**
 * Map utilities.
 * 
 * <b>N.B.</b> A valid BeanUtil will need to be injected.
 * 
 * @author nickgrealy@gmail.com
 */
public final class MapUtil {

    private static IBeanPropertyAccessor beanPropertyAccessor = BeanPropertyAccessor.INSTANCE;

    /**
     * Constructs a MapUtil.
     */
    private MapUtil() {
    }

    /**
     * Puts the objects into a map, by the given field.
     * 
     * @param objects
     *            Collection<O>
     * @param field
     *            String
     * @param map
     *            Map<K, V>
     * @param <K>
     *            Key
     * @param <V>
     *            Value
     */
    @SuppressWarnings("unchecked")
    public static <K, V, MapType extends Map<K, V>> MapType mapByField(Collection<V> objects, String field, MapType map) {
        for (V object : objects) {
            final Object key = beanPropertyAccessor.getProperty(object, field);
            if (key != null) {
                map.put((K) key, object);
            }
        }
        return map;
    }

}
