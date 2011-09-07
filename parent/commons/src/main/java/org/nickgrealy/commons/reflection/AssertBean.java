package org.nickgrealy.commons.reflection;

import static org.nickgrealy.commons.validation.Assert.ACTUAL;
import static org.nickgrealy.commons.validation.Assert.check;

import java.util.Map;
import java.util.Map.Entry;


/**
 * Singleton implementation of the {@link IAssertBean} interface.
 * 
 * @author nick.grealy
 */
public final class AssertBean implements IAssertBean {

    private static final IAssertBean INSTANCE = new AssertBean();

    private final IBeanUtil beanUtil;

    private AssertBean() {
        beanUtil = BeanUtil.getInstance();
    }

    /**
     * Returns the singleton.
     * 
     * @return singleton.
     */
    public static IAssertBean getInstance() {
        return INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, String... fields) {
        if (expected == null) {
            check(ACTUAL, actual).isNull();
        } else {
            check(ACTUAL, actual).isNotNull();
            for (String field : fields) {
                beanUtil.getProperty(expected, field).equals(beanUtil.getProperty(actual, field));
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void assertEquals(Object expected, Object actual, Map<String, String> fieldsMap) {
        if (expected == null) {
            check(ACTUAL, actual).isNull();
        } else {
            check(ACTUAL, actual).isNotNull();
            for (Entry<String, String> entry : fieldsMap.entrySet()) {
                beanUtil.getProperty(expected, entry.getKey()).equals(beanUtil.getProperty(actual, entry.getValue()));
            }
        }
    }

}
