/**
 * 
 */
package org.nickgrealy.commons.conversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nickgrealy.commons.exceptions.ConverterNotFoundException;

/**
 * 
 * @author nick.grealy
 */
public class ConverterFactoryTest {

    private final ConverterFactory factory = new ConverterFactory(PrimitiveConverters.getAllConverters());

    @Test
    public void hasConverter() {
        // true
        assertTrue(factory.hasConverter(Integer.class, String.class));
        assertTrue(factory.hasConverter(Integer.class, boolean.class));
        // false
        assertFalse(factory.hasConverter(Integer.class, ConverterFactoryTest.class));
        assertFalse(factory.hasConverter(ConverterFactoryTest.class, Integer.class));
    }

    @Test
    public void testConversion() {
        int tmp = 12;
        Integer tmp2 = 12;
        assertEquals("12", factory.convert(tmp, String.class));
        assertEquals("12", factory.convert(tmp2, String.class));
    }

    @Test
    public void testConversion2() {
        int tmp = 12;
        assertNull(factory.convert(null, String.class));
        assertNull(factory.convert(tmp, null));
        assertNull(factory.convert(null, null));
    }

    @Test(expected = ConverterNotFoundException.class)
    public void testUnhandledTargetClasses() {
        int tmp = 12;
        assertEquals((Integer) tmp, factory.convert(tmp, Double.class));
    }
}
