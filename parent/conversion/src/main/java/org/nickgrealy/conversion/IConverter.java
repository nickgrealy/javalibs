package org.nickgrealy.conversion;

import java.util.Set;

/**
 * Interface for converting an object to a target class. For ANY references to
 * classes, ALWAYS use the Object class as opposed to it's respective primitive
 * class.
 * 
 * @author nickgrealy@gmail.com
 * @param <X>
 *            Base object type.
 */
public interface IConverter<X> {

    /**
     * Converts the given object to the target class.
     * 
     * @param fromObject
     *            given object.
     * @param targetClass
     *            target class.
     * @param <A>
     *            type of the target object.
     * @return converted object.
     */
    <A> A convert(X fromObject, Class<A> targetClass);

    /**
     * Returns a list of the handled target classes.
     * 
     * @return {@link List} of {@link Class}'s.
     */
    Set<Class<?>> getTargetClasses();

    /**
     * Returns the base class.
     * 
     * @return {@link Class}.
     */
    Class<X> getBaseClass();

}
