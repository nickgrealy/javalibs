/**
 * 
 */
package org.nickgrealy.commons.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.nickgrealy.commons.exception.UnhandledException;
import org.nickgrealy.commons.convert.impl.PrimitiveConverters.IntegerConverter;

/**
 * 
 * @author nickgrealy@gmail.com
 */
public class PrimitiveConverterTest {

    private final AbstractBaseConverter<Integer> intConverter = new IntegerConverter();

    @Test
    public void getBaseClass() {
        assertEquals(Integer.class, intConverter.getBaseClass());
    }

    @Test
    public void getIntegerConverterTargetClasses() {
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

    @Test(expected = UnhandledException.class)
    public void testUnhandledTargetClasses() {
        int tmp = 12;
        assertEquals((Integer) tmp, intConverter.convert(tmp, Double.class));
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
