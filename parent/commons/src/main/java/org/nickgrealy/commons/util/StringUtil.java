/**
 * 
 */
package org.nickgrealy.commons.util;

import static org.nickgrealy.commons.validation.Assert.check;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author nick.grealy
 */
public class StringUtil {

    private static final String COLLECTION = "collection";
    private static final String ARRAY = "array";
    private static final String ARR_START = "[";
    private static final String ARR_END = "]";
    private static final String ARR_DELIM = ", ";

    public String toString(Object[] array) {
        check(ARRAY, array).isNotNull();
        return buildString(Arrays.asList(array)).toString();
    }

    public String toString(Collection<?> collection) {
        check(COLLECTION, collection).isNotNull();
        return buildString(collection).toString();
    }

    private StringBuilder buildString(Collection<?> collection) {
        final StringBuilder sb = new StringBuilder(ARR_START);
        final Iterator<?> iterator = collection.iterator();
        if (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        while (iterator.hasNext()) {
            sb.append(ARR_DELIM).append(iterator.next());
        }
        return sb.append(ARR_END);
    }
}
