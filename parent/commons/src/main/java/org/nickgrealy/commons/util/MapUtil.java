/**
 * 
 */
package org.nickgrealy.commons.util;

import java.util.Collection;
import java.util.Map;

import org.nickgrealy.commons.reflection.BeanUtil;
import org.nickgrealy.commons.reflection.IBeanUtil;

/**
 * Map utilities.
 * 
 * @author nick.grealy
 */
public final class MapUtil {

    private static final IBeanUtil BEAN_UTIL = BeanUtil.getInstance();

    private static final MapUtil INSTANCE = new MapUtil();

    private MapUtil() {
    }

    /**
     * Returns the Singleton.
     * 
     * @return MapUtil
     */
    public static MapUtil getInstance() {
        return INSTANCE;
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
    public <K, V> void mapByField(Collection<V> objects, String field, Map<K, V> map) {
        for (V object : objects) {
            final Object key = BEAN_UTIL.getProperty(object, field);
            if (key != null) {
                map.put((K) key, object);
            }
        }

    }

}
