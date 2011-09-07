package org.nickgrealy.commons.util;

import java.util.List;

/**
 * 
 * @author nick.grealy
 * @param <A>
 * @param <B>
 * @param <V>
 */
public interface ITwoDimensionalMap<A, B, V> {

    void put(A a, B b, V v);

    void put(A a, List<B> b1, V v);

    V get(A a, B b);

    boolean containsKey(A a, B b);

}
