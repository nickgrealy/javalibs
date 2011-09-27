package org.nickgrealy.commons.util;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * @author nickgrealy@gmail.com
 */
public class OneToManyMapTest {

    private String test1 = "test1";
    private String test2 = "test2";
    private Set<String> list1 = new HashSet<String>(Arrays.asList("value1", "value2"));
    private Set<String> list2 = new HashSet<String>(Arrays.asList("value3", "value2"));

    private OneToManyMap<String, String, Set<String>> map;

    @Before
    public void setUp() {
        map = new OneToManyMap(TreeSet.class);
    }

    @Test
    public void put1() {
        map.put(test1, list1);
        map.put(test2, list2);
        assertTrue(map.containsKey(test1));
        assertTrue(map.containsKey(test2));
        Iterator<String> iter1 = map.get(test1).iterator();
        assertEquals("value1", iter1.next());
        assertEquals("value2", iter1.next());
        Iterator<String> iter2 = map.get(test2).iterator();
        assertEquals("value2", iter2.next());
        assertEquals("value3", iter2.next());
    }

    @Test
    public void put2() {
        map.put(test1, list1);
        map.put(test1, list2);
        assertTrue(map.containsKey(test1));
        assertFalse(map.containsKey(test2));
        Set<String> list = map.get(test1);
        assertEquals(3, list.size());
        Iterator<String> iterator = list.iterator();
        assertEquals("value1", iterator.next());
        assertEquals("value2", iterator.next());
        assertEquals("value3", iterator.next());
    }

    @Test
    public void put3() {
        map.put(test1, list1);
        map.put(test1, "value3");
        Set<String> list = map.get(test1);
        assertEquals(3, list.size());
        Iterator<String> iterator = list.iterator();
        assertEquals("value1", iterator.next());
        assertEquals("value2", iterator.next());
        assertEquals("value3", iterator.next());
    }
}
