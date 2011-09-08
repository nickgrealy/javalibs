/**
 * 
 */
package org.nickgrealy.commons.exception;

/**
 * Used to wrap Bean related exceptions.
 * 
 * @author nick.grealy
 */
public class BeanException extends RuntimeException {

    public static final String DEFAULT_CONSTRUCTOR = "DefaultConstructor";
    public static final String FIELD = "Field=";
    private static final long serialVersionUID = -6830864219098335713L;

    /**
     * Constructs a BeanException.
     * 
     * @param message
     *            String
     */
    public BeanException(String message) {
        super(message);
    }

    /**
     * Constructs a BeanException.
     * 
     * @param message
     *            String
     * @param cause
     *            Throwable
     */
    public BeanException(String field, Throwable cause) {
        super(FIELD + field, cause);
    }

}
