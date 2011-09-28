package org.nickgrealy.commons.convert.impl;

import org.nickgrealy.commons.convert.AbstractBaseConverter;
import org.nickgrealy.commons.exception.NotYetImplementedException;
import org.nickgrealy.commons.util.IConverter;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Contains a list of classes, to facilitate converting primitives.
 *
 * @author nickgrealy@gmail.com
 */
public final class PrimitiveConverters {

    private static final List<IConverter<?>> CONVERTERS = Arrays.asList(new IConverter<?>[]{new IntegerConverter()});

    private PrimitiveConverters() {
    }

    /**
     * Returns instances of the converters within this class.
     *
     * @return list of {@link IConverter}'s.
     */
    public static List<IConverter<?>> getAllConverters() {
        return CONVERTERS;
    }

    /* IConverter implementations */

    /**
     * A converter for Integers (and int's) to Objects.
     */
    public static final class IntegerConverter extends AbstractBaseConverter<Integer> {

        public IntegerConverter() {
            super(Boolean.class, String.class);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <A> A doConversion(Integer fromObject, Class<A> targetClass) {
            Object returnVal = null;
            if (Boolean.class.equals(targetClass)) {
                returnVal = new Boolean(fromObject > 0);
            } else if (String.class.equals(targetClass)) {
                returnVal = fromObject.toString();
            } else {
                throw new NotYetImplementedException(format(UNIMPLEMENTED_CLASS_2, fromObject, targetClass));
            }
            return (A) returnVal;
        }

        @Override
        public Class<Integer> getBaseClass() {
            return Integer.class;
        }

    }
}
