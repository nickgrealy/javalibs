package org.nickgrealy.commons.exception;

import static java.lang.String.format;

/**
 * @author nickgrealy@gmail.com
 */
public class ReflectionException extends RuntimeException {

    private static final String ASSERT_NOTNULL_MESG_1 = "Object must not be null! field=%s";

    public ReflectionException(String field) {
        super(format(ASSERT_NOTNULL_MESG_1, field));
    }
}
