package org.nickgrealy.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nickgrealy.commons.exceptions.AssertionException;

/**
 * Test the following methods:
 * <ul>
 * <li>constructor</li>
 * <li>get</li>
 * <li>put</li>
 * <li>containsKey</li>
 * <li>containsValue</li>
 * <li>putAll</li>
 * <li>remove</li>
 * </ul>
 * 
 * 
 * @author nick.grealy
 */
public class NotNullableMapTest {

    private static final String TEST = "Test";

    private NotNullableMap<Object, Object> map;

    private Map<Object, Object> mapPass;
    private Map<Object, Object> mapFail;

    @Before
    public void setUp() {
        map = new NotNullableMap<Object, Object>();
        mapPass = new HashMap<Object, Object>();
        mapFail = new HashMap<Object, Object>();
        mapPass.put(TEST, TEST);
        mapFail.put(TEST, null);
    }

    @Test
    public void testConstructorGoodMap() {
        new NotNullableMap<Object, Object>(mapPass);
    }

    @Test(expected = AssertionException.class)
    public void testConstructorBadMap() {
        new NotNullableMap<Object, Object>(mapFail);
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

    /* containsKey */

    @Test
    public void containsKey1() {
        map.containsKey(TEST);
    }

    @Test(expected = AssertionException.class)
    public void containsKey2() {
        map.containsKey(null);
    }

    /* containsValue */

    @Test
    public void containsValue1() {
        map.containsValue(TEST);
    }

    @Test(expected = AssertionException.class)
    public void containsValue2() {
        map.containsValue(null);
    }

    /* putAll */

    @Test
    public void putAll1() {
        map.putAll(mapPass);
    }

    @Test(expected = AssertionException.class)
    public void putAll2() {
        map.putAll(mapFail);
    }

    /* remove */

    @Test
    public void remove1() {
        map.putAll(mapPass);
        map.remove(TEST);
    }

    @Test(expected = AssertionException.class)
    public void remove2() {
        map.putAll(mapPass);
        map.remove(null);
    }

    /* all else */

    @Test
    public void delegateMethods() {
        assertTrue(map.isEmpty());
        map.putAll(mapPass);
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
        assertEquals(1, map.entrySet().size());
        assertEquals(1, map.keySet().size());
        assertEquals(1, map.values().size());
        assertTrue(mapPass.hashCode() == map.hashCode());
        assertTrue(mapFail.hashCode() != map.hashCode());
        assertTrue(map.equals(mapPass));
        map.clear();
        assertTrue(map.isEmpty());
    }

}
