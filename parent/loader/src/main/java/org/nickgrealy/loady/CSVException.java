/**
 * 
 */
package org.nickgrealy.loady;

/**
 * 
 * @author nickgrealy@gmail.com
 */
public class CSVException extends RuntimeException {

    private static final long serialVersionUID = 4674587608430698419L;

    public CSVException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public CSVException(String message, Throwable cause) {
        super(message, cause);
    }

}
