package org.nickgrealy.test.validation;

import static java.lang.String.format;
import static org.nickgrealy.test.validation.Assert.assertIfNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.reflect.IBeanUtil;

/**
 * Facilitates comparing the fields from two objects for equality.
 * 
 * <b>N.B.</b> A valid BeanUtil will need to be injected.
 * 
 * @author nick.grealy
 */
public class AssertBean implements IAssertBean {

    private static final String BEAN_UTIL = "beanUtil";

    private static final String MUST_BE_ASSIGNABLE_2 = "'expected' bean class must be assignable "
            + "from the 'actual' bean class! expectedClass=%s actualClass=%s";

    private IBeanUtil beanUtil;

    /**
     * Constructs the AssertBean.
     */
    public AssertBean() {
    }

    public void setBeanUtil(IBeanUtil beanUtil) {
    	org.junit.Assert.assertNotNull(BEAN_UTIL, beanUtil);
        this.beanUtil = beanUtil;
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, final String... fields) {
        org.junit.Assert.assertNotNull(BEAN_UTIL, beanUtil);
        if (assertIfNotNull("Objects", expected, actual)) {
            for (String field : fields) {
            	Assert.assertEquals(field, beanUtil.getProperty(expected, field),
                        beanUtil.getProperty(actual, field));
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, Map<String, String> fieldsMap) {
    	org.junit.Assert.assertNotNull(BEAN_UTIL, beanUtil);
        if (assertIfNotNull("Objects", expected, actual)) {
            for (Entry<String, String> entry : fieldsMap.entrySet()) {
            	Assert.assertEquals(entry.toString(), beanUtil.getProperty(expected, entry.getKey()),
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
    	org.junit.Assert.assertNotNull(BEAN_UTIL, beanUtil);
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
                    Assert.assertEquals(field.getName(), beanUtil.getProperty(expected, field),
                            beanUtil.getProperty(actual, field));
                }
                tmp = tmp.getSuperclass();
                ++currCLassLevel;
            }
        }
    }

}
