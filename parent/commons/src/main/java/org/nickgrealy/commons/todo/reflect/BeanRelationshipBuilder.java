/**
 * 
 */
package org.nickgrealy.commons.todo.reflect;

import java.util.Collection;
import java.util.Map.Entry;

/**
 * Requires something like a: Class -> Field -> Value -> Bean map... :S
 * 
 * @author nickgrealy@gmail.com
 */
public abstract class BeanRelationshipBuilder {

    // Collection<?> beans,

    // TODO Implement
    public abstract void buildRelationship(Collection<Object> from, Collection<Object> to,
            Entry<String, String> fieldRelationship);

}
