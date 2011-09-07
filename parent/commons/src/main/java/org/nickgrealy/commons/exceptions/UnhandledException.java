/**
 * 
 */
package org.nickgrealy.commons.exceptions;

/**
 * Thrown when an unhandled object is encountered.
 * 
 * @author nick.grealy
 */
public class UnhandledException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5801318977393015056L;

    /**
     * Base constructor.
     */
    public UnhandledException() {
    }

    /**
     * @param message
     */
    public UnhandledException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public UnhandledException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public UnhandledException(String message, Throwable cause) {
        super(message, cause);
    }

}
