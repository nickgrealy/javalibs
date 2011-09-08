package org.nickgrealy.commons.conversion;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;

import org.nickgrealy.commons.exception.NotYetImplementedException;

/**
 * Contains a list of classes, to facilitate converting primitives.
 * 
 * @author nick.grealy
 */
public final class PrimitiveConverters {

    private static final List<IConverter<?>> CONVERTERS = Arrays.asList(new IConverter<?>[] { new IntegerConverter(),
        new StringConverter(), });

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
    static final class IntegerConverter extends AbstractBaseConverter<Integer> {

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

    /**
     * A converter for Strings to Objects.
     */
    static final class StringConverter extends AbstractBaseConverter<String> {

        public StringConverter() {
            super(Integer.class, Double.class, Boolean.class, Float.class);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <A> A doConversion(String fromObject, Class<A> targetClass) {
            Object returnVal = null;
            if (Integer.class.equals(targetClass)) {
                returnVal = new Integer(fromObject);
            } else if (Double.class.equals(targetClass)) {
                returnVal = new Double(fromObject);
            } else if (Float.class.equals(targetClass)) {
                returnVal = new Float(fromObject);
            } else if (Boolean.class.equals(targetClass)) {
                returnVal = new Boolean(fromObject);
            } else {
                throw new NotYetImplementedException(format(UNIMPLEMENTED_CLASS_2, fromObject, targetClass));
            }
            return (A) returnVal;
        }

        @Override
        public Class<String> getBaseClass() {
            return String.class;
        }

    }
}
