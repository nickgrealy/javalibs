/**
 * 
 */
package org.nickgrealy.loader;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author nickgrealy@gmail.com
 */
public class SimpleBean {

    // null
    Object nullObj1 = new Object();
    Object nullObj2 = new Object();

    // primitives
    public byte a02 = -1;
    short a03 = -1;
    int a04 = -1;
    long a05 = -1;
    float a06 = -1;
    double a07 = -1;
    boolean a08 = false;
    char a09 = '-';
    // primitive objects
    Byte a10 = -1;
    Short a11 = -1;
    Integer a12 = -1;
    Long a13 = new Long(-1);
    Float a14 = new Float(-1);
    Double a15 = new Double(-1);
    Boolean a16 = false;
    Character a17 = '-';
    // string
    String stringObj = "-";
    // enum
    SimpleEnum enumObj = SimpleEnum.INIT;
    // array
    Object[] arrayObj = {};
    // collection
    Collection<?> collObj = Arrays.asList();
    List<?> listObj = Arrays.asList();
    Set<?> setObj = new HashSet();
    // map
    Map<?, ?> mapObj = new HashMap();
    // file
    File fileObj = new File("-");
    // uro
    URI uriObj = null;

}
