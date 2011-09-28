package org.nickgrealy.commons.util;

import org.nickgrealy.commons.util.base.ITwoDimensionalMap;

import java.util.Collection;
import java.util.Map;

/**
 * A TwoDimensionalMap.
 */
public class TwoDimensionalMap<A, B, V> extends NotNullableMap<A, NotNullableMap<B, V>> implements
        ITwoDimensionalMap<A, B, V> {

    private Map<B, V> nullSafeGet(A a) {
        NotNullableMap<B, V> map2;
        if (containsKey(a)) {
            map2 = get(a);
        } else {
            map2 = new NotNullableMap<B, V>();
            put(a, map2);
        }
        return map2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(A a, B b, V v) {
        nullSafeGet(a).put(b, v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(A a, Collection<B> b1, V v) {
        for (B b : b1) {
            nullSafeGet(a).put(b, v);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(A a, B b) {
        if (containsKey(a, b)) {
            return get(a).get(b);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(A a, B b) {
        return containsKey(a) && get(a).containsKey(b);
    }

}
