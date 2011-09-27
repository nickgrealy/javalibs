package org.nickgrealy.commons.convert;

import org.nickgrealy.commons.exception.ConverterNotFoundException;
import org.nickgrealy.commons.util.ClassUtil;
import org.nickgrealy.commons.util.IConverter;
import org.nickgrealy.commons.util.TwoDimensionalMap;
import org.nickgrealy.commons.util.base.ITwoDimensionalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static org.nickgrealy.commons.convert.AbstractBaseConverter.getAssignableClass;

/**
 * ConverterFactory facilitates managing a list of {@link IConverter}'s, and
 * exposing them through the convert() method.
 *
 * @author nickgrealy@gmail.com
 */
public class ConverterFactory implements IConverterFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ITwoDimensionalMap<Class<?>, Class<?>, IConverter<?>> fieldsMap;

    /**
     * Constructor for a ConverterFactory.
     *
     * @param converters an array of a list of {@link IConverter}'s.
     */
    public ConverterFactory(List<? extends IConverter<?>> converters) {
        fieldsMap = new TwoDimensionalMap<Class<?>, Class<?>, IConverter<?>>();
        for (IConverter<?> converter : converters) {
            final Set<Class<?>> classes = converter.getTargetClasses();
            logger.debug("Registering converter='{}'", converter);
            for (Class<?> targetClass : classes) {
                Class<?> baseClass = converter.getBaseClass();
                if (fieldsMap.containsKey(baseClass, targetClass)) {
                    logger.warn("Found (and overriding) existing conversion mapping for baseClass->targetClass! mapping='{}->{}' existingConverter='{}' newConverter='{}'",
                            new Object[]{baseClass, targetClass, fieldsMap.get(baseClass, targetClass), converter});
                }
                fieldsMap.put(baseClass, targetClass, converter);
            }
        }
    }

    /**
     * Determines whether a converter exists for the from and to objects.
     *
     * @param from from class.
     * @param to   to class.
     * @return true if a converter exists, otherwise false.
     */
    @Override
    public boolean hasConverter(Class<?> from, Class<?> to) {
        // ensure we're NOT dealing with primitives...
        final Class<?> newFrom = ClassUtil.convertPrimitiveToObjectClass(from);
        final Class<?> newTo = ClassUtil.convertPrimitiveToObjectClass(to);
        return fieldsMap.containsKey(newFrom, newTo);
    }

    /**
     * Converts an object, to an implementation of the given class.
     *
     * @param from the object you want to convert from.
     * @param to   the class of the object you want to convert to.
     * @param <A>  type of the "from" class.
     * @param <B>  type of the "to" class.
     * @return the converted object.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <A, B> B convert(A from, Class<B> to) {
        if (from == null || to == null) {
            return null;
        }
        // ensure we're NOT dealing with primitives...
        final Class<A> newFrom = (Class<A>) ClassUtil
                .convertPrimitiveToObjectClass(from.getClass());
        final Class<B> newTo = (Class<B>) ClassUtil
                .convertPrimitiveToObjectClass(to);
        final Class<?> toClass = getAssignableClass(newTo);
        final IConverter<?> converter = fieldsMap.get(newFrom, toClass);
        if (converter == null) {
            throw new ConverterNotFoundException(newFrom, newTo);
        }
        return ((IConverter<A>) converter).convert(from, newTo);
    }

}
