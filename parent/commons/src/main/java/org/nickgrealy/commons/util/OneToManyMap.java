package org.nickgrealy.commons.util;

import org.nickgrealy.commons.reflect.BeanPropertyAccessor;
import org.nickgrealy.commons.reflect.IBeanPropertyAccessor;
import org.nickgrealy.commons.util.base.NullSafeGetterMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>Example usage:</b><br/>
 * <code>OneToManyMap<Class<?>, Class<?>, Set<Class<?>>> map = new OneToManyMap(HashSet.class);</code>
 *
 * @param <Key>
 * @param <Value>
 * @param <Many>
 */
public class OneToManyMap<Key, Value, Many extends Collection<Value>> extends NullSafeGetterMap<Key, Many> {

    private static final IBeanPropertyAccessor BEAN_PROPERTY_ACCESSOR = BeanPropertyAccessor.INSTANCE;

    /**
     * @param collectionClassImpl must be an implementation of a {@link Collection} class. e.g. {@link HashSet} is ok, but not {@link Set}, as it cannot be instantiated.
     */
    public OneToManyMap(Class<Many> collectionClassImpl) {
        super(collectionClassImpl);
    }

    public Many put(Key key, Value value) {
        Many tmp = nullSafeGet(key);
        tmp.add(value);
        return tmp;
    }

    public Many put(Key key, Many many) {
        Many tmp = nullSafeGet(key);
        tmp.addAll(many);
        return tmp;
    }
}