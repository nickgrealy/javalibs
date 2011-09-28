package org.nickgrealy.commons.exception;

import org.nickgrealy.commons.util.IConverter;

import static java.lang.String.format;

public class NotConvertableException extends RuntimeException {

    private static final long serialVersionUID = -2449368635142982513L;
    private static final String VALUE_IS_MALFORMED_3 = "Value to convert is malformed! expected='%s', actual='%s', converter='%s'";

    public NotConvertableException(String expected, String actual, Class<? extends IConverter<?>> converter) {
        super(format(VALUE_IS_MALFORMED_3, expected, actual, converter));
    }

    public NotConvertableException(String expected, String actual, Class<? extends IConverter<?>> converter, Throwable t) {
        super(format(VALUE_IS_MALFORMED_3, expected, actual, converter), t);
    }
}
