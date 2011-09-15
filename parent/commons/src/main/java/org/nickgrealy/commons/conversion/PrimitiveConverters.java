package org.nickgrealy.commons.conversion;

import static java.lang.String.format;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        /**
         * 
         */
        private static final String COLLECTION_DELIMITER = ",";

        // byte a02 = -1;
        // short a03 = -1;
        // int a04 = -1;
        // long a05 = -1;
        // float a06 = -1;
        // double a07 = -1;
        // boolean a08 = false;
        // char a09 = '-';

        public StringConverter() {
            super(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class,
                    Character.class, Enum.class, Array.class, Collection.class, List.class, Set.class, Map.class,
                    File.class, URI.class);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <A> A doConversion(String fromObject, Class<A> targetClass) {
            Object returnVal = null;
            if (isAssignableClass(Byte.class, targetClass)) {
                returnVal = new Byte(fromObject);
            } else if (isAssignableClass(Short.class, targetClass)) {
                returnVal = new Short(fromObject);
            } else if (isAssignableClass(Integer.class, targetClass)) {
                returnVal = new Integer(fromObject);
            } else if (isAssignableClass(Long.class, targetClass)) {
                returnVal = new Long(fromObject);
            } else if (isAssignableClass(Float.class, targetClass)) {
                returnVal = new Float(fromObject);
            } else if (isAssignableClass(Double.class, targetClass)) {
                returnVal = new Double(fromObject);
            } else if (isAssignableClass(Boolean.class, targetClass)) {
                returnVal = new Boolean(fromObject);
            } else if (isAssignableClass(Character.class, targetClass)) {
                if (fromObject.length() != 0) {
                    returnVal = new Character(fromObject.charAt(0));
                }
            } else if (isAssignableClass(Enum.class, targetClass)) {
                returnVal = Enum.valueOf((Class<? extends Enum>) targetClass, fromObject);
            } else if (isAssignableClass(Array.class, targetClass)) {
                returnVal = fromObject.split(COLLECTION_DELIMITER);
            } else if (isAssignableClass(Set.class, targetClass)) {
                returnVal = null;
            } else if (isAssignableClass(List.class, targetClass)) {
                returnVal = null;
            } else if (isAssignableClass(Collection.class, targetClass)) {
                returnVal = null;
            } else if (isAssignableClass(Map.class, targetClass)) {
                returnVal = null;
            } else if (isAssignableClass(File.class, targetClass)) {
                returnVal = null;
            } else if (isAssignableClass(URI.class, targetClass)) {
                returnVal = null;
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
