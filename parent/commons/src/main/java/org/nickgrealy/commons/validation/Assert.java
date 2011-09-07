package org.nickgrealy.commons.validation;

import static java.lang.String.format;

import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exceptions.AssertionException;

/**
 * Used to make assertions. Throws a {@link AssertionException} if assertion is
 * disproved.
 * <p/>
 * All assertions have an object identifier, to facilitate object
 * identification.
 * 
 * @author nick.grealy
 * 
 */
public final class Assert {

    public static final String KEY = "key";
    public static final String VALUE = "value";

    public static final String ACTUAL = "actual";
    public static final String EXPECTED = "expected";

    private static final String ASSERT_EQUALS_MESG_3 = "Expected did not match actual! field=%s expected=%s actual=%s";
    private static final String ASSERT_NOTNULL_MESG_1 = "Actual must not be null! field=%s";
    private static final String ASSERT_NULL_MESG_1 = "Actual must be null! field=%s";

    private final Object actual;
    private final String identifier;

    private Assert(String identifier, Object actual) {
        this.actual = actual;
        this.identifier = identifier;
    }

    /**
     * Entry point for creating an "Assert" builder.
     * 
     * @param identifier
     *            String
     * @param actual
     *            Object
     * @return this.
     */
    public static Assert check(String identifier, Object actual) {
        return new Assert(identifier, actual);
    }

    /**
     * Asserts actual is not null.
     * 
     * @return this.
     */
    public Assert isNotNull() {
        if (actual == null) {
            throw new AssertionException(format(ASSERT_NOTNULL_MESG_1, identifier));
        }
        return this;
    }

    /**
     * Asserts an object is null.
     * 
     * @return this.
     */
    public Assert isNull() {
        if (actual != null) {
            throw new AssertionException(format(ASSERT_NULL_MESG_1, identifier));
        }
        return this;
    }

    /**
     * Asserts an object is true. Anything else will cause an
     * {@link AssertionException}.
     * 
     * @return this.
     */
    public Assert isTrue() {
        isNotNull();
        if (!Boolean.TRUE.equals(actual)) {
            throw new AssertionException(format("Actual value is not true! field=% actual=%s", identifier, actual));
        }
        return this;
    }

    /**
     * Asserts an object is false. Anything else will cause an
     * {@link AssertionException}.
     * 
     * @return this.
     */
    public Assert isFalse() {
        isNotNull();
        if (!Boolean.FALSE.equals(actual)) {
            throw new AssertionException(format("Actual value is not false! field=% actual=%s", identifier, actual));
        }
        return this;
    }

    /**
     * Asserts two objects are equal (using .equals method).
     * 
     * @param expected
     *            expected object
     * @return this.
     */
    public Assert equalz(Object expected) {
        if (expected == null) {
            isNull();
        } else if (!expected.equals(actual)) {
            throw new AssertionException(format(ASSERT_EQUALS_MESG_3, identifier, expected, actual));
        }
        return this;
    }

    /**
     * Asserts actual is an instance of clazz.
     * 
     * @param givenClasses
     *            Class<?>...
     * @return this.
     */
    public Assert isInstanceOf(Class<?>... givenClasses) {
        check("givenClasses", givenClasses).isNotNull();
        isNotNull();
        boolean anyTrue = false;
        for (Class<?> clazz : givenClasses) {
            if (actual.getClass().isAssignableFrom(clazz)) {
                anyTrue = true;
                break;
            }
        }
        if (!anyTrue) {
            throw new AssertionException(format(
                    "Actual is not an instance of any givenClasses! identifier=%s givenClasses=%s", identifier,
                    givenClasses));
        }
        return this;
    }

    /**
     * Asserts actual is greater than the given Integer.
     * 
     * @param expected
     *            Integer
     * @return this.
     */
    public Assert gt(Integer expected) {
        isNotNull();
        isInstanceOf(byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class,
                Long.class, float.class, Float.class, double.class, Double.class);
        if ((Integer) actual <= expected) {
            throw new AssertionException(format("Actual is not greater than! identifier=%s actual=%s expected=%s",
                    identifier, actual, expected));
        }
        return this;
    }

    /**
     * Asserts a map contains no null keys or values.
     * 
     * @param map
     *            the map to check.
     */
    public static void assertNoNullKeysOrValues(Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            check(KEY, entry.getKey()).isNotNull();
            check(KEY, entry.getValue()).isNotNull();
        }
    }
}
