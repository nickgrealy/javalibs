package org.nickgrealy.commons.conversion;

import static java.lang.String.format;

import java.util.List;

import org.nickgrealy.commons.exceptions.ConverterNotFoundException;
import org.nickgrealy.commons.util.ITwoDimensionalMap;
import org.nickgrealy.commons.util.TwoDimensionalMap;

/**
 * ConverterFactory facilitates managing a list of {@link IConverter}'s, and
 * exposing them through the convert() method.
 * 
 * @author nick.grealy
 */
public class ConverterFactory {

    private static final String CONVERTER_NOT_FOUND_2 = "A converter could not be found! fromClass=%s toClass=%s";

    private final ITwoDimensionalMap<Class<?>, Class<?>, IConverter<?>> fieldsMap;

    /**
     * Constructor for a ConverterFactory.
     * 
     * @param converters
     *            a list of {@link IConverter}'s.
     */
    public ConverterFactory(List<IConverter<?>> converters) {
        fieldsMap = new TwoDimensionalMap<Class<?>, Class<?>, IConverter<?>>();
        for (IConverter<?> converter : converters) {
            final List<Class<?>> classes = converter.getTargetClasses();
            fieldsMap.put(converter.getBaseClass(), classes, converter);
        }
    }

    /**
     * Determines whether a converter exists for the from and to objects.
     * 
     * @param from
     *            from class.
     * @param to
     *            to class.
     * @return true if a converter exists, otherwise false.
     */
    public boolean hasConverter(Class<?> from, Class<?> to) {
        return fieldsMap.containsKey(from, to);
    }

    /**
     * Converts an object, to an implementation of the given class.
     * 
     * @param from
     *            the object you want to convert from.
     * @param to
     *            the class of the object you want to convert to.
     * @param <A>
     *            type of the "from" class.
     * @param <B>
     *            type of the "to" class.
     * @return the converted object.
     */
    @SuppressWarnings("unchecked")
    public <A, B> B convert(A from, Class<B> to) {
        if (from == null || to == null) {
            return null;
        }
        final IConverter<?> converter = fieldsMap.get(from.getClass(), to);
        if (converter == null) {
            throw new ConverterNotFoundException(format(CONVERTER_NOT_FOUND_2, from.getClass(), to));
        }
        return ((IConverter<A>) converter).convert(from, to);
    }

}
