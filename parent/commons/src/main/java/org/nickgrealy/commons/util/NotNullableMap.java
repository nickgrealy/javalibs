package org.nickgrealy.commons.util;

import org.nickgrealy.commons.util.base.ExtHashMap;
import org.nickgrealy.commons.util.base.ExtMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.nickgrealy.commons.validate.RuntimeAssert.*;

/**
 * A map wrapper, which will not allow null values to be passed in, or returned
 * from the underlying map.
 * <p/>
 * <p/>
 * This provides the developer with the confidence to know that this map will
 * ONLY ever return valid (i.e. not null) objects.
 *
 * @param <K> Key
 * @param <V> Value
 * @author nickgrealy@gmail.com
 */
public class NotNullableMap<K, V> implements ExtMap<K, V> {

    private final ExtMap<K, V> underlyingMap;

    /**
     * Constructs a new map.
     */
    public NotNullableMap() {
        super();
        underlyingMap = new ExtHashMap<K, V>();
    }

    /**
     * Wraps an existing map.
     *
     * @param underlyingMap the underlying map
     */
    public NotNullableMap(ExtMap<K, V> underlyingMap) {
        super();
        assertNoNullKeysOrValues(underlyingMap);
        this.underlyingMap = underlyingMap;
    }

    public Map<K, V> getUnderlyingMap() {
        return underlyingMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        underlyingMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        assertKeyNotNull(key);
        return underlyingMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        assertValueNotNull(value);
        return underlyingMap.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return underlyingMap.entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return underlyingMap.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        assertKeyNotNull(key);
        final V value = underlyingMap.get(key);
        assertValueNotNull(value);
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return underlyingMap.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return underlyingMap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return underlyingMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        assertKeyNotNull(key);
        assertValueNotNull(value);
        return underlyingMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        assertNoNullKeysOrValues(map);
        underlyingMap.putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {
        assertKeyNotNull(key);
        return underlyingMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return underlyingMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        return underlyingMap.values();
    }

    public List<V> get(Collection<K> keys) {
        assertKeyNotNull(keys);
        final List<V> value = underlyingMap.get(keys);
        assertValueNotNull(value);
        return value;
    }

    /* utility methods */

    private static void assertValueNotNull(Object value) {
        check(VALUE, value).isNotNull();
    }

    private static void assertKeyNotNull(Object key) {
        check(KEY, key).isNotNull();
    }

}
