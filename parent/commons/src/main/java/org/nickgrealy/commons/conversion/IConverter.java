package org.nickgrealy.commons.conversion;

import java.util.List;

/**
 * Interface for converting an object to a target class.
 * 
 * @author nick.grealy
 * @param <X>
 *            Base object type.
 */
public interface IConverter<X> {

    /**
     * Converts the given object to the target class.
     * 
     * @param value
     *            given object.
     * @param targetClass
     *            target class.
     * @param <A>
     *            type of the target object.
     * @return converted object.
     */
    <A> A convert(X value, Class<A> targetClass);

    /**
     * Returns a list of the handled target classes.
     * 
     * @return {@link List} of {@link Class}'s.
     */
    List<Class<?>> getTargetClasses();

    /**
     * Returns the base class.
     * 
     * @return {@link Class}.
     */
    Class<X> getBaseClass();

}
