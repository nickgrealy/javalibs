package org.nickgrealy.commons.exceptions;

/**
 * Thrown when an assertion is disproved.
 * 
 * @author nick.grealy
 */
public class AssertionException extends RuntimeException {

    private static final long serialVersionUID = 2301743353648074407L;

    /**
     * Constructs an AssertionException.
     */
    public AssertionException() {
    }

    /**
     * Constructs an AssertionException.
     * 
     * @param message
     *            message
     */
    public AssertionException(String message) {
        super(message);
    }

    /**
     * Constructs an AssertionException.
     * 
     * @param cause
     *            cause
     */
    public AssertionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an AssertionException.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public AssertionException(String message, Throwable cause) {
        super(message, cause);
    }

}
