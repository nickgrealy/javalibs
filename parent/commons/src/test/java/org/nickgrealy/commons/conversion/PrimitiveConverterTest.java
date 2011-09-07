/**
 * 
 */
package org.nickgrealy.commons.conversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.nickgrealy.commons.conversion.PrimitiveConverters.IntegerConverter;
import org.nickgrealy.commons.conversion.PrimitiveConverters.StringConverter;
import org.nickgrealy.commons.exceptions.UnhandledException;

/**
 * 
 * @author nick.grealy
 */
public class PrimitiveConverterTest {

    private final AbstractBaseConverter<Integer> intConverter = new IntegerConverter();
    private final AbstractBaseConverter<String> stringConverter = new StringConverter();

    @Test
    public void getBaseClass() {
        assertEquals(Integer.class, intConverter.getBaseClass());
    }

    @Test
    public void getTargetClasses() {
        // expected
        List<Class<?>> defaults = Arrays.asList(new Class<?>[] { Object.class, Integer.class });
        List<Class<?>> expected = Arrays.asList(new Class<?>[] { String.class, Boolean.class, });
        List<Class<?>> notExpected = Arrays.asList(new Class<?>[] { int.class, boolean.class,
            PrimitiveConverterTest.class });
        Set<Class<?>> actual = intConverter.getTargetClasses();
        // assert
        assertTrue(actual.containsAll(defaults));
        assertTrue(actual.containsAll(expected));
        assertCollectionDoesNotContain(notExpected, actual);
    }

    @Test
    public void testIntegerConversion() {
        int tmp = 12;
        assertEquals(tmp, intConverter.convert(tmp, Object.class));
        assertEquals("12", intConverter.convert(tmp, String.class));
        assertEquals(tmp, (int) intConverter.convert(tmp, int.class));
        assertEquals((Integer) tmp, intConverter.convert(tmp, Integer.class));
        assertEquals(true, intConverter.convert(1, boolean.class));
        assertEquals(Boolean.FALSE, intConverter.convert(0, Boolean.class));
    }

    @Test
    public void testStringConversion() {
        String tmp = "true";
        assertEquals(tmp, stringConverter.convert(tmp, Object.class));
        assertEquals(tmp, stringConverter.convert(tmp, String.class));
        assertEquals(Boolean.TRUE, stringConverter.convert(tmp, boolean.class));
        assertEquals(Boolean.FALSE, stringConverter.convert("notABool", boolean.class));
        assertEquals((Integer) 12, stringConverter.convert("12", int.class));
        assertEquals((Double) 12.34, stringConverter.convert("12.34", Double.class));
    }

    @Test(expected = UnhandledException.class)
    public void testUnhandledTargetClasses() {
        int tmp = 12;
        assertEquals((Integer) tmp, intConverter.convert(tmp, Double.class));
    }

    @Test(expected = NumberFormatException.class)
    public void testConversionFailed1() {
        stringConverter.convert("notAnInt", int.class);
    }

    /* Utility methods */

    /**
     * Asserts that the actual list, doesn't not contain any of the notexpected
     * list.
     * 
     * @param notexpected
     * @param actual
     */
    private static void assertCollectionDoesNotContain(Collection<?> notexpected, Collection<?> actual) {
        for (Object shouldNotExist : notexpected) {
            assertFalse(shouldNotExist.toString(), actual.contains(shouldNotExist));
        }
    }
}
