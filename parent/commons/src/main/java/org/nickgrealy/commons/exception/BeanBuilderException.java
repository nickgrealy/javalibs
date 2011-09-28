package org.nickgrealy.commons.exception;

import java.lang.reflect.Field;

import static java.lang.String.format;

/**
 * @author nickgrealy@gmail.com
 */
public class BeanBuilderException extends RuntimeException {

    public static final String EXCEPTION_BUILDING_BEAN_3 = "Exception occurred building bean! rowNum='%s' colNum='%s' value='%s' targetClass='%s'";
    public static final String CLASS_NOT_FOUND_1 = "Target class objects could not be found! targetClass='%s'";
    public static final String BEAN_NOT_FOUND_4 = "Target bean with given field value could not be found! rowNum='%s' colNum='%s' targetClass='%s' field='%s' value='%s'";
    public static final String CANNOT_DETERMINE_GENERIC_CLASS_2 = "Cannot determine generic collection type! class='%s' field='%s'";

    public BeanBuilderException(Class<?> targetClass) {
        super(format(CLASS_NOT_FOUND_1, targetClass));
    }

    public BeanBuilderException(Field field) {
        super(format(CANNOT_DETERMINE_GENERIC_CLASS_2, field.getDeclaringClass(), field.getName()));
    }

    public BeanBuilderException(Integer rowNum, Integer colNum, Field targetField, Object targetValue) {
        super(format(BEAN_NOT_FOUND_4, rowNum, colNum, targetField.getDeclaringClass(), targetField.getName(), targetValue));
    }

    public BeanBuilderException(Integer rowNum, Integer colNum, Object value, Class<?> targetClass, Throwable t) {
        super(format(EXCEPTION_BUILDING_BEAN_3, rowNum, colNum, value, targetClass), t);
    }
}
