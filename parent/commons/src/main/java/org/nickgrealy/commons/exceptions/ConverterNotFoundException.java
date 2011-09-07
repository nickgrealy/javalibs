/**
 * 
 */
package org.nickgrealy.commons.exceptions;

/**
 * 
 * @author nick.grealy
 */
public class ConverterNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -6042624001127363611L;

    /**
     * Base constructor.
     */
    public ConverterNotFoundException() {
    }

    /**
     * @param message
     */
    public ConverterNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ConverterNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ConverterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
