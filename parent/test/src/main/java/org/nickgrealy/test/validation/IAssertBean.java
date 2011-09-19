package org.nickgrealy.test.validation;

import java.util.Map;

public interface IAssertBean {

    /**
     * The default depth to copy fields from. See
     * {@link #copyProperties(Object, Object)}.
     */
    int DEFAULT_CLASS_DEPTH = 3;

    /**
     * Asserts the fields in two objects are equal.
     * <p/>
     * 
     * <b>N.B.</b> Invokes {@link #assertEquals(Object, Object, int)} with
     * maxClassLevel = {@link #DEFAULT_CLASS_DEPTH}.
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     */
    void assertEquals(Object expected, Object actual);

    void assertEquals(Object expected, Object actual, String... properties);

    void assertEquals(Object expected, Object actual, Map<String, String> properties);

    /**
     * Asserts the fields in two objects are equal, down to the maxClassLevel.
     * 
     * <b>N.B.</b>
     * <ul>
     * <li>'from' class must be assignable from the 'to' class.</li>
     * </ul>
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     * @param maxClassLevel
     *            the maximum number of superClasses to traverse up.
     */
    void assertEquals(Object expected, Object actual, int maxClassLevel);

    void assertEquals(Object expected, Object actual, int maxClassLevel, int ignoreFieldsWithModifiers);
}
