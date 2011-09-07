package org.nickgrealy.commons.conversion;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class PrimitiveConverters {
	
	static final class IntegerConverter implements IConverter<Integer> {

		private Class<?>[] classes = {String.class, Boolean.class, Double.class, int.class};
		
		@Override
		public <A> A convert(Integer val, Class<A> clazz) {
			if (clazz == null){
				return null;
			}
			if (clazz.equals(String.class)){
				return (A) val.toString();
			}
			return null;
		}

		@Override
		public List<Class<?>> getConvertableClasses() {
			return Arrays.asList(classes);
		}

		@Override
		public Class<Integer> getBaseClass() {
			return Integer.class;
		}
		
	}
}
