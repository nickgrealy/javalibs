package org.nickgrealy.commons.todo.util;

/**
 * The methods containsKey, get and remove in the {@link java.util.Map} interface are not typed!
 * No point if used in conjunction with a regular Map though.
 *
 * @param <Key>
 * @param <Value>
 */
public interface TypedMap<Key, Value> {

    boolean containsKey(Key key);

    Value get(Key key);

    Value remove(Key key);

}