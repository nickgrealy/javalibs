/**
 *
 */
package org.nickgrealy.commons.exception;

import java.lang.reflect.Field;

import static java.lang.String.format;

/**
 * Used to wrap Bean related exceptions.
 *
 * @author nickgrealy@gmail.com
 */
public class BeanException extends RuntimeException {

    public static final String DEFAULT_CONSTRUCTOR = "DefaultNoArgsConstructor";
    private static final String EXCEPTION_FIELD_2 = " class='%s' field='%s'";
    private static final String EXCEPTION_CONSTRUCTOR_2 = "Exception occurred attempting to construct the bean! class='%s' field='%s'";
    private static final long serialVersionUID = -6830864219098335713L;

    /**
     * Constructs a BeanException.
     *
     * @param message String
     */
    public BeanException(String message) {
        super(message);
    }

    /**
     * Constructs a BeanException.
     *
     * @param field String
     * @param cause Throwable
     */
    public BeanException(Class<?> clazz, String field, Throwable cause) {
        super(buildMessage(clazz, field), cause);
    }

    public BeanException(Field field, Throwable cause) {
        super(buildMessage(field.getDeclaringClass(), field.getName()), cause);
    }

    public BeanException(String message, Field field) {
        super(message + buildMessage(field.getDeclaringClass(), field.getName()));
    }

    private static String buildMessage(Class<?> clazz, String field) {
        if (DEFAULT_CONSTRUCTOR.equals(field)) {
            return format(EXCEPTION_CONSTRUCTOR_2, clazz, field);
        } else {
            return format(EXCEPTION_FIELD_2, clazz, field);
        }
    }

}
