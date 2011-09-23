/**
 * 
 */
package org.nickgrealy.commons.util;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author nickgrealy@gmail.com
 */
public final class StringUtil {

    /*
     * No external dependencies, so make this class a "utility" class. i.e.
     * static methods, final class, private constructor.
     */

    private static final String DELIMITER = "delimiter";
    private static final String COLLECTION = "collection";
    private static final String ARRAY = "array";

    private static final String ARR_START = "[";
    private static final String ARR_END = "]";
    public static final String ARR_DELIM = ", ";
    public static final String BLANK_DELIM = "";

    private StringUtil() {
    }

    public static String toString(Object... array) {
        check(ARRAY, array).isNotNull();
        return toString(Arrays.asList(array));
    }

    public static String toString(Collection<?> collection) {
        check(COLLECTION, collection).isNotNull();
        return concat(ARR_DELIM, collection).prepend(ARR_START).append(ARR_END).toString();
    }

    public static SimpleStringBuilder concat(String delimiter, Object... array) {
        check(ARRAY, array).isNotNull();
        return concat(delimiter, Arrays.asList(array));
    }

    public static SimpleStringBuilder concat(String delimiter, Collection<?> collection) {
        check(COLLECTION, collection).isNotNull();
        check(DELIMITER, collection).isNotNull();
        final SimpleStringBuilder sb = new SimpleStringBuilder();
        final Iterator<?> iterator = collection.iterator();
        if (iterator.hasNext()) {
            sb.append(iterator.next());
            while (iterator.hasNext()) {
                sb.append(delimiter).append(iterator.next());
            }
        }
        return sb;
    }
}
