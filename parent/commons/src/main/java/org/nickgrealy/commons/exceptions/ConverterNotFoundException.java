/**
 * 
 */
package org.nickgrealy.commons.exceptions;

import static java.lang.String.format;

/**
 * 
 * @author nick.grealy
 */
public class ConverterNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6042624001127363611L;

    private static final String CONVERTER_NOT_FOUND_2 = "A converter could not be found! fromClass=%s toClass=%s";

    public ConverterNotFoundException(Class<?> from, Class<?> to) {
        super(format(CONVERTER_NOT_FOUND_2, from, to));
    }

}
