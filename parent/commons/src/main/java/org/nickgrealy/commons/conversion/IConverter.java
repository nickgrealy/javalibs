package org.nickgrealy.commons.conversion;

import java.util.List;


public interface IConverter<X> {
	
	<A> A convert(X val, Class<A> clazz);
	
	List<Class<?>> getConvertableClasses();
	
	Class<X> getBaseClass();
	
}
