package org.nickgrealy.commons.exceptions;

/**
 * Thrown when (the logic) for an executed section of code has not yet been
 * implemented.
 * <p/>
 * <b>N.B.</b> Use as a placeholder/safety net for incomplete methods.
 * 
 * @author nick.grealy
 */
public class NotYetImplemented extends RuntimeException {

    private static final long serialVersionUID = 6790222551768044978L;

    private static final String METHOD_HAS_NOT_YET_BEEN_IMPLEMENTED = "Method has not yet been implemented!";

    /**
     * Constructs a NotYetImplemented exception with default message.
     */
    public NotYetImplemented() {
        super(METHOD_HAS_NOT_YET_BEEN_IMPLEMENTED);
    }

    /**
     * Constructs a NotYetImplemented exception with the supplied message.
     * 
     * @param message
     *            the exception message
     */
    public NotYetImplemented(String message) {
        super(message);
    }

}
