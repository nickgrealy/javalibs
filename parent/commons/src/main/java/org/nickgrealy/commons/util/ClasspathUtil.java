/**
 * 
 */
package org.nickgrealy.commons.util;

import java.io.InputStream;

/**
 * See {@link org.springframework.util.ResourceUtils}
 * 
 * @author nick.grealy
 */
public class ClasspathUtil {

    public static InputStream getResourceAsStream(String resource) {
        return ClasspathUtil.class.getClassLoader().getResourceAsStream(resource);

    }

}
