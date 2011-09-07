package org.nickgrealy.commons.validation;

import static java.lang.String.format;

import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exceptions.AssertionException;
import org.nickgrealy.commons.util.ClassUtil;
import org.nickgrealy.commons.util.StringUtil;

/**
 * Used to make assertions. Throws an {@link AssertionException} if the
 * assertion is disproved.
 * <p/>
 * N.B. The constructor takes an object identifier, to facilitate object
 * identification.
 * <p/>
 * Example usage:<br/>
 * <code>check("My string").isNotNull();</code>
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
    private static final String ASSERT_NOTPRIMITIVE_MESG_2 = "Actual must not be a primitive class! field=%s actual=%s";

    private final ClassUtil classUtil = new ClassUtil();
    private final StringUtil stringUtil = new StringUtil();

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
     * Asserts actual is null.
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
     * Asserts actual is not a primitive.
     * 
     * @return this.
     */
    public Assert isNotPrimitive() {
        isNotNull();
        isInstanceOf(Class.class);
        if (classUtil.isPrimitiveClass((Class<?>) actual)) {
            throw new AssertionException(format(ASSERT_NOTPRIMITIVE_MESG_2, identifier, actual));
        }
        return this;
    }

    /**
     * Asserts actual is true.
     * 
     * @return this.
     */
    public Assert isTrue() {
        isNotNull();
        if (!Boolean.TRUE.equals(actual)) {
            throw new AssertionException(format("Actual value is not true! field=%s actual=%s", identifier, actual));
        }
        return this;
    }

    /**
     * Asserts actual is false.
     * 
     * @return this.
     */
    public Assert isFalse() {
        isNotNull();
        if (!Boolean.FALSE.equals(actual)) {
            throw new AssertionException(format("Actual value is not false! field=%s actual=%s", identifier, actual));
        }
        return this;
    }

    /**
     * Asserts actual and expected are equal (using .equals()).
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
     * Asserts actual is an instance of any of the givenClasses.
     * 
     * @param givenClass
     *            Class<?>
     * @param givenClasses
     *            Class<?>...
     * @return this.
     */
    public Assert isInstanceOf(Class<?> givenClass, Class<?>... givenClasses) {
        check("givenClass", givenClass).isNotNull();
        check("givenClasses", givenClasses).isNotNull();
        isNotNull(); // actual is not null
        boolean anyTrue = false;
        if (actual.getClass().isAssignableFrom(givenClass)) {
            anyTrue = true;
        } else {
            for (Class<?> clazz2 : givenClasses) {
                if (actual.getClass().isAssignableFrom(clazz2)) {
                    anyTrue = true;
                    break;
                }
            }
        }
        if (!anyTrue) {
            throw new AssertionException(format(
                    "Actual is not an instance of any givenClasses! identifier=%s actual=%s givenClasses=%s",
                    identifier, actual.getClass(), stringUtil.toString(givenClasses)));
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
    public Assert isGt(Integer expected) {
        // TODO Test logic!
        isNotNull();
        isInstanceOf(int.class, Integer.class, long.class, Long.class, float.class, Float.class, double.class,
                Double.class);
        if (new Double(String.valueOf(actual)) <= expected) {
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
            check(VALUE, entry.getValue()).isNotNull();
        }
    }
}
