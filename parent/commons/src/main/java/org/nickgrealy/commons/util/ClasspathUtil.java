/**
 * 
 */
package org.nickgrealy.commons.util;

import java.io.InputStream;

/**
 * See {@link org.springframework.util.ResourceUtils}
 * 
 * @author nickgrealy@gmail.com
 */
public final class ClasspathUtil {

    private ClasspathUtil(){
    }

    public static InputStream getResourceAsStream(String resource) {
        return ClasspathUtil.class.getClassLoader().getResourceAsStream(resource);

    }

}
