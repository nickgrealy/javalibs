package org.nickgrealy.commons.conversion;

import java.util.List;

import org.nickgrealy.commons.util.TwoDimensionalMap;

public class ConverterFactory {
	
	private TwoDimensionalMap<Class<?>, Class<?>, IConverter<?>> fieldsMap;
	
	public ConverterFactory(List<IConverter<?>> converters){
		fieldsMap = new TwoDimensionalMap<Class<?>, Class<?>, IConverter<?>>();
		for (IConverter<?> converter : converters) {
			List<Class<?>> classes = converter.getConvertableClasses();
			fieldsMap.put(converter.getBaseClass(), classes, converter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <A, B> B convert(A from, Class<B> to){
		if (from == null || to == null){
			return null;
		}
		IConverter<?> converter = fieldsMap.get(from.getClass(), to);
		if (converter == null){
			throw new ConverterNotFoundException("A converter could not be found! fromClass=%s toClass=%s");
		}
		return ((IConverter<A>)converter).convert(from, to);
	}

}
