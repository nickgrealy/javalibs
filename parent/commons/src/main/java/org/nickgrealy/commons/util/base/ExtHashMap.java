package org.nickgrealy.commons.util.base;

import sun.security.krb5.internal.KdcErrException;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nickgrealy@gmail.com
 */
public class ExtHashMap<K, V> extends HashMap<K, V> implements ExtMap<K, V>{

    @Override
    public List<V> get(Collection<K> keys) {
        List<V> values = new LinkedList<V>();
        for (K key : keys) {
            values.add(super.get(key));
        }
        return values;
    }
}
