/**
 * 
 */
package org.nickgrealy.commons.util;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.util.Collection;
import java.util.Map;

import org.nickgrealy.commons.reflect.IBeanUtil;

/**
 * Map utilities.
 * 
 * <b>N.B.</b> A valid BeanUtil will need to be injected.
 * 
 * @author nick.grealy
 */
public final class MapUtil {

    private IBeanUtil beanUtil;

    /**
     * Constructs a MapUtil.
     */
    public MapUtil() {
    }

    public void setBeanUtil(IBeanUtil beanUtil) {
        check("beanUtil", beanUtil).isNotNull();
        this.beanUtil = beanUtil;
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
            check("beanUtil", beanUtil).isNotNull();
            final Object key = beanUtil.getProperty(object, field);
            if (key != null) {
                map.put((K) key, object);
            }
        }

    }

}
