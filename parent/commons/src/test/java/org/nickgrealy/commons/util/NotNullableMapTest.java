package org.nickgrealy.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nickgrealy.commons.exceptions.AssertionException;

/**
 * TODO Test the following methods: constructor, containsKey, containsValue,
 * get, put, putAll, remove.
 * 
 * 
 * @author nick.grealy
 */
public class NotNullableMapTest {

    private static final String TEST = "Test";
    private Map<Object, Object> map;

    @Before
    public void setUp() {
        map = new NotNullableMap<Object, Object>();
    }

    @Ignore
    @Test
    public void testConstructor() {

    }

    /* put */

    @Test(expected = AssertionException.class)
    public void put1() {
        map.put(null, null);
    }

    @Test(expected = AssertionException.class)
    public void put2() {
        map.put(TEST, null);
    }

    @Test(expected = AssertionException.class)
    public void put3() {
        map.put(null, TEST);
    }

    @Test
    public void put4() {
        map.put(TEST, TEST);
    }

    /* get */

    @Test(expected = AssertionException.class)
    public void get1() {
        map.get(null);
    }

    @Test(expected = AssertionException.class)
    public void get2() {
        map.get(TEST);
    }

    @Test
    public void get3() {
        map.put(TEST, TEST);
        assertEquals(TEST, map.get(TEST));
    }
}
