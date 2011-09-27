package org.nickgrealy.commons.convert;

/**
 * @author nickgrealy@gmail.com
 */
public interface IConverterFactory {

    boolean hasConverter(Class<?> from, Class<?> to);

    @SuppressWarnings("unchecked")
    <A, B> B convert(A from, Class<B> to);
}
