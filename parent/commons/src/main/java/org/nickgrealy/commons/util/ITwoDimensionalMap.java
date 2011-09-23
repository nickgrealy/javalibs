package org.nickgrealy.commons.util;

import java.util.Collection;

/**
 * 
 * @author nickgrealy@gmail.com
 * @param <A>
 * @param <B>
 * @param <V>
 */
public interface ITwoDimensionalMap<A, B, V> {

    void put(A a, B b, V v);

    void put(A a, Collection<B> b1, V v);

    V get(A a, B b);

    boolean containsKey(A a, B b);

}
