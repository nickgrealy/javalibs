package org.nickgrealy.commons.util;

import static org.nickgrealy.commons.validation.Assert.KEY;
import static org.nickgrealy.commons.validation.Assert.VALUE;
import static org.nickgrealy.commons.validation.Assert.assertNoNullKeysOrValues;
import static org.nickgrealy.commons.validation.Assert.check;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A map wrapper, which will not allow null values to be passed in, or returned
 * from the underlying map.
 * <p/>
 * 
 * This provides the developer with the confidence to know that this map will
 * ONLY ever return valid (i.e. not null) objects.
 * 
 * @author nick.grealy
 * 
 * @param <K>
 * @param <V>
 */
public class NotNullableMap<K, V> implements Map<K, V> {

    private final Map<K, V> underlyingMap;

    /**
     * Constructs a new map.
     */
    public NotNullableMap() {
        super();
        underlyingMap = new HashMap<K, V>();
    }

    /**
     * Wraps an existing map.
     * 
     * @param underlyingMap
     *            the underlying map
     */
    public NotNullableMap(Map<K, V> underlyingMap) {
        super();
        assertNoNullKeysOrValues(underlyingMap);
        this.underlyingMap = underlyingMap;
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        underlyingMap.clear();
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        assertKeyNotNull(key);
        return underlyingMap.containsKey(key);
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        assertValueNotNull(value);
        return underlyingMap.containsValue(value);
    }

    /** {@inheritDoc} */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return underlyingMap.entrySet();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        return underlyingMap.equals(o);
    }

    /** {@inheritDoc} */
    @Override
    public V get(Object key) {
        assertKeyNotNull(key);
        final V value = underlyingMap.get(key);
        assertValueNotNull(value);
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return underlyingMap.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return underlyingMap.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public Set<K> keySet() {
        return underlyingMap.keySet();
    }

    /** {@inheritDoc} */
    @Override
    public V put(K key, V value) {
        assertKeyNotNull(key);
        assertValueNotNull(value);
        return underlyingMap.put(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        assertNoNullKeysOrValues(map);
        underlyingMap.putAll(map);
    }

    /** {@inheritDoc} */
    @Override
    public V remove(Object key) {
        assertKeyNotNull(key);
        return underlyingMap.remove(key);
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return underlyingMap.size();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<V> values() {
        return underlyingMap.values();
    }

    /* utility methods */

    private static void assertValueNotNull(Object value) {
        check(VALUE, value).isNotNull();
    }

    private static void assertKeyNotNull(Object key) {
        check(KEY, key).isNotNull();
    }

}
