package org.nickgrealy.conversion;

import static org.nickgrealy.conversion.AbstractBaseConverter.getAssignableClass;

import java.util.List;
import java.util.Set;

import org.nickgrealy.commons.exception.ConverterNotFoundException;
import org.nickgrealy.commons.util.ClassUtil;
import org.nickgrealy.commons.util.ITwoDimensionalMap;
import org.nickgrealy.commons.util.TwoDimensionalMap;

/**
 * ConverterFactory facilitates managing a list of {@link IConverter}'s, and
 * exposing them through the convert() method.
 * 
 * @author nickgrealy@gmail.com
 */
public class ConverterFactory {

	private final ClassUtil classUtil = new ClassUtil();

	private final ITwoDimensionalMap<Class<?>, Class<?>, IConverter<?>> fieldsMap;

	/**
	 * Constructor for a ConverterFactory.
	 * 
	 * @param converters
	 *            an array of a list of {@link IConverter}'s.
	 */
	public ConverterFactory(List<? extends IConverter<?>> converters) {
		fieldsMap = new TwoDimensionalMap<Class<?>, Class<?>, IConverter<?>>();
		for (IConverter<?> converter : converters) {
			final Set<Class<?>> classes = converter.getTargetClasses();
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
		// ensure we're NOT dealing with primitives...
		final Class<?> newFrom = classUtil.convertPrimitiveToObjectClass(from);
		final Class<?> newTo = classUtil.convertPrimitiveToObjectClass(to);
		return fieldsMap.containsKey(newFrom, newTo);
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
		// ensure we're NOT dealing with primitives...
		final Class<A> newFrom = (Class<A>) classUtil
				.convertPrimitiveToObjectClass(from.getClass());
		final Class<B> newTo = (Class<B>) classUtil
				.convertPrimitiveToObjectClass(to);
		final Class<?> toClass = getAssignableClass(newTo);
		final IConverter<?> converter = fieldsMap.get(newFrom, toClass);
		if (converter == null) {
			throw new ConverterNotFoundException(newFrom, newTo);
		}
		return ((IConverter<A>) converter).convert(from, newTo);
	}

}
