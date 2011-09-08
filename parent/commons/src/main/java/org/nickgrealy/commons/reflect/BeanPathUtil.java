/**
 * 
 */
package org.nickgrealy.commons.reflect;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 
 * @author nick.grealy
 */
public class BeanPathUtil {

    private static final String BEAN_PATH_DELIM = "\\.";
    private static final String BEAN_PATH = ".";

    public static String[] pathToFields(String beanPath) {
        return beanPath.split(BEAN_PATH_DELIM);
    }

    public static String fieldsToPath(String... fields) {
        StringBuffer sb = new StringBuffer();
        final Iterator<String> iterator = Arrays.asList(fields).iterator();
        if (iterator.hasNext()) {
            sb.append(iterator.next());
            while (iterator.hasNext()) {
                sb.append(BEAN_PATH).append(iterator.next());
            }
        }
        return sb.toString();
    }

}
