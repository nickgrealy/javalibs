package org.nickgrealy.commons.conversion;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;

import org.nickgrealy.commons.exceptions.NotYetImplemented;
import org.nickgrealy.commons.exceptions.UnhandledException;

/**
 * Contains a list of classes, to facilitate converting primitives.
 * 
 * @author nick.grealy
 */
@SuppressWarnings("unchecked")
public final class PrimitiveConverters {

    private static final String UNHANDLED_CLASS_2 = "Target class is not handled! value=%s, targetClass=%s";
    private static final String UNIMPLEMENTED_CLASS_2 = "Target class conversion is not yet implemented! "
            + "value=%s, targetClass=%s";

    private static final List<IConverter<?>> CONVERTERS = Arrays.asList(new IConverter<?>[] { new IntegerConverter() });

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

    static final class IntegerConverter extends AbstractConverter<Integer> {
        private static final List<Class<?>> TARGET_CLASSES = Arrays.asList(new Class<?>[] { Object.class, String.class,
            int.class, Integer.class, boolean.class, Boolean.class, });

        @Override
        public <A> A convert(Integer from, Class<A> toClass) {
            if (!isTargetClass(toClass)) {
                throw new UnhandledException(format(UNHANDLED_CLASS_2, from, toClass));
            }
            A returnVal = null;
            if (Object.class.equals(toClass)) {
                returnVal = (A) from;
            } else if (String.class.equals(toClass)) {
                returnVal = (A) from.toString();
            } else if (int.class.equals(toClass) || Integer.class.equals(toClass)) {
                returnVal = (A) from;
            } else if (boolean.class.equals(toClass) || Boolean.class.equals(toClass)) {
                returnVal = (A) new Boolean(from > 0);
            } else {
                throw new NotYetImplemented(format(UNIMPLEMENTED_CLASS_2, from, toClass));
            }
            return returnVal;
        }

        @Override
        public List<Class<?>> getTargetClasses() {
            return TARGET_CLASSES;
        }

        @Override
        public Class<Integer> getBaseClass() {
            return Integer.class;
        }

    }
}
