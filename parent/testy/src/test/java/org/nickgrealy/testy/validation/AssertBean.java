package org.nickgrealy.testy.validation;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;
import static org.nickgrealy.testy.validation.Assert.checkIfNotNull;
import static org.nickgrealy.testy.validation.Assert.checkIfNullOrEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.reflect.IBeanUtil;

/**
 * Facilitates comparing the fields from two objects for equality.
 * 
 * @author nick.grealy
 */
public class AssertBean implements IAssertBean {

    private static final String MUST_BE_ASSIGNABLE_2 = "'expected' bean class must be assignable "
            + "from the 'actual' bean class! expectedClass=%s actualClass=%s";

    private final IBeanUtil beanUtil;

    /**
     * Constructs the AssertBean.
     * 
     * @param beanUtil
     *            required.
     */
    public AssertBean(IBeanUtil beanUtil) {
        assertNotNull("beanUtil", beanUtil);
        this.beanUtil = beanUtil;
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, final String... fields) {
        if (checkIfNotNull(expected, actual)) {
            for (String field : fields) {
                Assert.checkIfNullOrEquals(field, beanUtil.getProperty(expected, field),
                        beanUtil.getProperty(actual, field));
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, Map<String, String> fieldsMap) {
        if (checkIfNotNull(expected, actual)) {
            for (Entry<String, String> entry : fieldsMap.entrySet()) {
                Assert.checkIfNullOrEquals(entry.toString(), beanUtil.getProperty(expected, entry.getKey()),
                        beanUtil.getProperty(actual, entry.getValue()));
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, DEFAULT_CLASS_DEPTH);
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, int maxClassLevel) {
        assertEquals(expected, actual, maxClassLevel, Modifier.FINAL);
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, int maxClassLevel, int ignoreFieldsWithModifiers) {
        if (checkIfNotNull(expected, actual)) {
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
                    checkIfNullOrEquals(field.getName(), beanUtil.getProperty(expected, field),
                            beanUtil.getProperty(actual, field));
                }
                tmp = tmp.getSuperclass();
                ++currCLassLevel;
            }
        }
    }

}
