package org.nickgrealy.commons.util.base;

import org.nickgrealy.commons.reflect.BeanPropertyAccessor;
import org.nickgrealy.commons.reflect.IBeanPropertyAccessor;

import java.util.HashMap;

/**
 * @author nickgrealy@gmail.com
 */
public abstract class NullSafeGetterMap<Key, Value> extends HashMap<Key, Value> {

    private static final IBeanPropertyAccessor BEAN_PROPERTY_ACCESSOR = BeanPropertyAccessor.INSTANCE;

    private Class<Value> valueClass = null;

    public NullSafeGetterMap(Class<Value> valueClass){
        this.valueClass = valueClass;
    }

    protected Value nullSafeGet(Key key){
		Value values;
		if (super.containsKey(key)) {
			values = super.get(key);
		} else {
			values = BEAN_PROPERTY_ACCESSOR.createBean(valueClass);
            super.put(key, values);
		}
		return values;
    }
}
