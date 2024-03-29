/**
 *
 */
package org.nickgrealy.commons.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Methods: mapByField
 *
 * @author nickgrealy@gmail.com
 */
public class MapUtilTest {

    private Collection<TestObject> testObjects;

    @Before
    public void setUp() {
        testObjects = Arrays.asList(new TestObject("a"), new TestObject("b"), new TestObject("c"), new TestObject("b"),
                new TestObject(null));
    }

    @Test
    public void mapByField() {
        Map<String, TestObject> map = new HashMap<String, TestObject>();
        MapUtil.mapByField(testObjects, "field", map);
        assertEquals(3, map.size());
        assertNotNull(map.get("a"));
        assertNotNull(map.get("b"));
        assertNotNull(map.get("c"));
    }

    /* Test Object class */

    class TestObject {
        private final String field;

        public TestObject(String field) {
            this.field = field;
        }

        @Override
        public String toString() {
            return field;
        }

    }

}
