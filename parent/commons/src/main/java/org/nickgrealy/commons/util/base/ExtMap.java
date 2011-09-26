package org.nickgrealy.commons.util.base;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author nickgrealy@gmail.com
 */
public interface ExtMap<K, V> extends Map<K, V> {

    List<V> get(Collection<K> keys);
}
