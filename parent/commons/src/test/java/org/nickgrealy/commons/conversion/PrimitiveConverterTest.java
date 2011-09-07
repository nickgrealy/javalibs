/**
 * 
 */
package org.nickgrealy.commons.conversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.nickgrealy.commons.conversion.PrimitiveConverters.IntegerConverter;
import org.nickgrealy.commons.exceptions.UnhandledException;

/**
 * 
 * @author nick.grealy
 */
public class PrimitiveConverterTest {

    private final AbstractConverter<Integer> integerConverter = new IntegerConverter();

    @Test
    public void testIntegerGetClasses() {
        // setup
        List<Class<?>> expected = Arrays.asList(new Class<?>[] { Object.class, String.class, int.class, Integer.class,
            boolean.class, Boolean.class, });
        List<Class<?>> notExpected = Arrays.asList(new Class<?>[] { PrimitiveConverterTest.class });
        // assert
        assertEquals(Integer.class, integerConverter.getBaseClass());
        List<Class<?>> actual = integerConverter.getTargetClasses();
        assertTrue(actual.containsAll(expected));
        assertFalse(actual.containsAll(notExpected));
    }

    @Test
    public void testIntegerConversion() {
        int tmp = 12;
        assertEquals(tmp, integerConverter.convert(tmp, Object.class));
        assertEquals("12", integerConverter.convert(tmp, String.class));
        assertEquals(tmp, (int) integerConverter.convert(tmp, int.class));
        assertEquals((Integer) tmp, integerConverter.convert(tmp, Integer.class));
        assertEquals(true, integerConverter.convert(1, boolean.class));
        assertEquals(Boolean.FALSE, integerConverter.convert(0, Boolean.class));
    }

    @Test(expected = UnhandledException.class)
    public void testIntegerUnhandledTargetClasses() {
        int tmp = 12;
        assertEquals((Integer) tmp, integerConverter.convert(tmp, Double.class));
    }
}
