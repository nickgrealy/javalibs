/**
 * 
 */
package org.nickgrealy.commons.reflect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author nick.grealy
 */
public class BeanPathTest {

    @Test
    public void pathToFields() {
        final String[] fields = BeanPathUtil.pathToFields("a.b.c");
        assertEquals(3, fields.length);
        assertEquals("a", fields[0]);
        assertEquals("b", fields[1]);
        assertEquals("c", fields[2]);
    }

    @Test
    public void fieldsToPath1() {
        assertEquals("a.b.c", BeanPathUtil.fieldsToPath("a", "b", "c"));
    }

    @Test
    public void fieldsToPath2() {
        assertEquals("", BeanPathUtil.fieldsToPath());
    }

}
