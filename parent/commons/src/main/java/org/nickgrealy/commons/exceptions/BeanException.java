/**
 * 
 */
package org.nickgrealy.commons.exceptions;

/**
 * Used to wrap Bean related exceptions.
 * 
 * @author nick.grealy
 */
public class BeanException extends RuntimeException {

    private static final long serialVersionUID = -6830864219098335713L;

    /**
     * Constructs a BeanException.
     */
    public BeanException() {
        super();
    }

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
     * @param cause
     *            Throwable
     */
    public BeanException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a BeanException.
     * 
     * @param message
     *            String
     * @param cause
     *            Throwable
     */
    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

}
