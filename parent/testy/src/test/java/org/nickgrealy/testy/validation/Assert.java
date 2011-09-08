package org.nickgrealy.testy.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

/**
 * Assert methods.
 * 
 * @author nick.grealy
 */
public final class Assert {

    private static final String ACTUAL = "actual";
    private static final String EXPECTED = "expected";

    private Assert() {
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (checkIfNotNull(expected, actual)) {
            if (expected.getClass().isArray() && actual.getClass().isArray()) {
                org.junit.Assert.assertArrayEquals(message, (Object[]) expected, (Object[]) actual);
            } else if (expected instanceof Collection && actual instanceof Collection) {
                assertEquals(message, (Collection<?>) expected, (Collection<?>) actual);
            } else {
                org.junit.Assert.assertEquals(message, expected, actual);
            }
        }
    }

    public static void assertEquals(String message, Collection<?> expected, Collection<?> actual) {
        if (checkIfNotNull(expected, actual)) {
            org.junit.Assert.assertArrayEquals(expected.toArray(new Object[expected.size()]),
                    actual.toArray(new Object[actual.size()]));
        }
    }

    /**
     * Performs a boiler plate test on the objects. If 'expected' is null,
     * assert 'actual' is null too (and vice versa).
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     * @return true if expected and actual are not null, otherwise false if both
     *         are null.
     */
    public static boolean checkIfNotNull(Object expected, Object actual) {
        return checkIfNotNull(null, expected, actual);
    }

    public static boolean checkIfNotNull(String message, Object expected, Object actual) {
        if (expected == null) {
            assertNull(message != null ? message : ACTUAL, actual);
            return false;
        } else {
            assertNotNull(message != null ? message : ACTUAL, actual);
            return true;
        }
    }

    public static void checkIfNullOrEquals(String field, Object e1, Object a1) {
        if (checkIfNotNull(e1, a1)) {
            assertEquals(field, e1, a1);
        }
    }
}
