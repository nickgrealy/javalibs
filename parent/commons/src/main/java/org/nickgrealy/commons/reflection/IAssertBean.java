package org.nickgrealy.commons.reflection;

import java.util.Map;

public interface IAssertBean {

    /**
     * Asserts the given fields in two objects are equal.
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     * @param fields
     *            String...
     */
    void assertEquals(Object expected, Object actual, String... fields);

    /**
     * Asserts the given fields in two objects are equal.
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     * @param fieldsMap
     *            Map<String, String>
     */
    void assertEquals(Object expected, Object actual, Map<String, String> fieldsMap);
}
