/**
 * 
 */
package org.nickgrealy.commons.conversion;

/**
 * Adds base functionality to the IConverter.
 * 
 * @author nick.grealy
 */
public abstract class AbstractConverter<X> implements IConverter<X> {

    /**
     * Determines if the given class is a target class.
     * 
     * @param clazz
     *            Class<?>
     * @return true if class is a target class.
     */
    public boolean isTargetClass(Class<?> clazz) {
        return getTargetClasses().contains(clazz);
    }

}
