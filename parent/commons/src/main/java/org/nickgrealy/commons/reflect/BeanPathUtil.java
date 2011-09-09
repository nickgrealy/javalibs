/**
 * 
 */
package org.nickgrealy.commons.reflect;

import org.nickgrealy.commons.util.StringUtil;

/**
 * 
 * @author nick.grealy
 */
public class BeanPathUtil {

    /*
     * No external dependencies, so make this class a "utility" class. i.e.
     * static methods, final class, private constructor.
     */

    public static final String BEAN_PATH_DELIM_REGEX = "\\.";
    public static final String BEAN_PATH_DELIM = ".";

    public static String[] pathToFields(String beanPath) {
        return beanPath.split(BEAN_PATH_DELIM_REGEX);
    }

    public static String fieldsToPath(String... fields) {
        return StringUtil.concat(BEAN_PATH_DELIM, (Object[]) fields).toString();
    }

}
