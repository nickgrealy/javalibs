package org.nickgrealy.conversion.exception;

import static java.lang.String.*;

/**
 * @author nickgrealy@gmail.com
 */
public class BeanBuilderException extends RuntimeException {

    public static final String EXCEPTION_MESSAGE_3 = "Exception occurred building bean! rowNum='%s' colNum='%s' value='%s' targetClass='%s'";

    public BeanBuilderException(int rowNum, int colNum, Object value, Class<?> targetClass, Throwable t) {
        super(format(EXCEPTION_MESSAGE_3, rowNum, colNum, value, targetClass), t);
    }
}
