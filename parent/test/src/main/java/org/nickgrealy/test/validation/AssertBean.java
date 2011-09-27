package org.nickgrealy.test.validation;

import static java.lang.String.format;
import static org.nickgrealy.test.validation.Assert.assertIfNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.reflect.BeanPropertyAccessor;
import org.nickgrealy.commons.reflect.IBeanPropertyAccessor;

/**
 * Facilitates comparing the fields from two objects for equality.
 * 
 * <b>N.B.</b> A valid BeanUtil will need to be injected.
 * 
 * @author nickgrealy@gmail.com
 */
public final class AssertBean {

    private static final String MUST_BE_ASSIGNABLE_2 = "'expected' bean class must be assignable "
            + "from the 'actual' bean class! expectedClass=%s actualClass=%s";

    private static final IBeanPropertyAccessor beanPropertyAccessor = BeanPropertyAccessor.INSTANCE;

    /**
     * The default depth to copy fields from. See
     * {@link #assertEquals(Object expected, Object actual)}.
     */
    private static final int DEFAULT_CLASS_DEPTH = 3;

    /**
     * Constructs the AssertBean.
     */
    private AssertBean() {
    }

    /** {@inheritDoc} */
    public static void assertEquals(Object expected, Object actual, final String... fields) {
        if (assertIfNotNull("Objects", expected, actual)) {
            for (String field : fields) {
            	Assert.assertEquals(field, beanPropertyAccessor.getProperty(expected, field),
                        beanPropertyAccessor.getProperty(actual, field));
            }
        }
    }

    /** {@inheritDoc} */
    public static void assertEquals(Object expected, Object actual, Map<String, String> fieldsMap) {
        if (assertIfNotNull("Objects", expected, actual)) {
            for (Entry<String, String> entry : fieldsMap.entrySet()) {
            	Assert.assertEquals(entry.toString(), beanPropertyAccessor.getProperty(expected, entry.getKey()),
                        beanPropertyAccessor.getProperty(actual, entry.getValue()));
            }
        }
    }

    /** {@inheritDoc} */
    public static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, DEFAULT_CLASS_DEPTH);
    }

    /** {@inheritDoc} */
    public static void assertEquals(Object expected, Object actual, int maxClassLevel) {
        assertEquals(expected, actual, maxClassLevel, Modifier.FINAL);
    }

    /** {@inheritDoc} */
    public static void assertEquals(Object expected, Object actual, int maxClassLevel, int ignoreFieldsWithModifiers) {
        if (assertIfNotNull("Objects", expected, actual)) {
            if (!expected.getClass().isAssignableFrom(actual.getClass())) {
                throw new BeanException(format(MUST_BE_ASSIGNABLE_2, expected.getClass(), actual.getClass()));
            }
            // Iterate up through parent classes, asserting fields as we go...
            int currCLassLevel = 1;
            Class<?> tmp = expected.getClass();
            final int ignoredModifiers = ignoreFieldsWithModifiers & Modifier.FINAL;
            while (currCLassLevel <= maxClassLevel && tmp != null && !tmp.equals(Object.class)) {
                final Field[] fields = tmp.getDeclaredFields();
                for (Field field : fields) {
                    // Ignore fields if they have these modifiers...
                    if ((field.getModifiers() & ignoredModifiers) == ignoredModifiers) {
                        continue;
                    }
                    // Do assert...
                    Assert.assertEquals(field.getName(), beanPropertyAccessor.getProperty(expected, field),
                            beanPropertyAccessor.getProperty(actual, field));
                }
                tmp = tmp.getSuperclass();
                ++currCLassLevel;
            }
        }
    }

}
