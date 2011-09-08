/**
 * 
 */
package org.nickgrealy.commons.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.reflect.IBeanUtil;

/**
 * Tests methods:
 * <ul>
 * <li>&lt;I&gt; I createBean(Class&lt;I&gt; clazz);</li>
 * <li>&lt;I&gt; I createBean(Class&lt;I&gt; clazz, Map<String, Object>
 * properties);</li>
 * <li>void setProperty(Object object, String field, Object property);</li>
 * <li>Object getProperty(Object object, String field);</li>
 * <li>void copyProperties(Object object, Map<String, Object> properties);</li>
 * <li>void copyProperties(Object from, Object to);</li>
 * <li>void copyProperties(Object from, Object to, String... properties);</li>
 * <li>void copyProperties(Object from, Object to, Map<String, String>
 * properties);</li>
 * <li>void copyProperties(Object from, Object to, int classLevel);</li>
 * </ul>
 * 
 * @author nick.grealy
 */
public class BeanUtilTest {

    private static final String INVALID_FIELD = "invalidField";
    private static final String FIELD_VALUE = "test2";
    private static final String FIELD1 = "field1";
    private static final String FIELD2 = "field2";
    private static final String TEST = "test";

    private static final IBeanUtil beanUtil = new BeanUtil();

    private DefaultAccessibleConstructor bean1;
    private DefaultAccessibleConstructor fromBean;
    private DefaultAccessibleConstructor toBean;
    private Map<String, Object> fieldValueMap;
    private Map<String, String> fieldFieldMap;

    @Before
    public void setUp() {
        bean1 = new DefaultAccessibleConstructor(null);
        fromBean = new DefaultAccessibleConstructor(null);
        fromBean.setValue1(FIELD_VALUE);
        toBean = new DefaultAccessibleConstructor(null);
        fieldValueMap = new HashMap<String, Object>();
        fieldValueMap.put(FIELD1, FIELD_VALUE);
        fieldFieldMap = new HashMap<String, String>();
        fieldFieldMap.put(FIELD1, FIELD2);
    }

    /* createBean(Class) */

    @Test
    public void createBean1() {
        assertTrue(null != beanUtil.createBean(DefaultAccessibleConstructor.class));
    }

    @Test(expected = BeanException.class)
    public void createBean2() {
        beanUtil.createBean(NoAccessibleConstructor.class);
    }

    @Test(expected = BeanException.class)
    public void createBean3() {
        beanUtil.createBean(NoDefaultConstructor.class);
    }

    /* getProperty */

    @Test
    public void getProperty1() {
        bean1.setValue1(TEST);
        assertEquals(TEST, beanUtil.getProperty(bean1, FIELD1));
    }

    @Test(expected = BeanException.class)
    public void getProperty2() {
        beanUtil.getProperty(bean1, INVALID_FIELD);
    }

    /* setProperty */

    @Test
    public void setProperty1() {
        beanUtil.setProperty(bean1, FIELD1, FIELD_VALUE);
        assertFieldsSet(bean1, true, false);
    }

    @Test(expected = BeanException.class)
    public void setProperty2() {
        beanUtil.setProperty(bean1, INVALID_FIELD, FIELD_VALUE);
    }

    /* createBean(Class<I> clazz, Map<String, Object> properties) */

    @Test
    public void createBean5() {
        DefaultAccessibleConstructor bean = beanUtil.createBean(DefaultAccessibleConstructor.class, fieldValueMap);
        assertFieldsSet(bean, true, false);
    }

    /* copyProperties(Object object, Map<String, Object> properties); */

    @Test
    public void copyProperties1() {
        beanUtil.copyProperties(toBean, fieldValueMap);
        assertFieldsSet(toBean, true, false);
    }

    /* copyProperties(Object from, Object to); */

    @Test
    public void copyProperties2() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        beanUtil.copyProperties(fromBean2, toBean2);
        assertFieldsSet(toBean2, true, true, true);
    }

    @Test(expected = BeanException.class)
    public void copyProperties2a() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        beanUtil.copyProperties(fromBean2, toBean);
        assertFieldsSet(toBean, true, true);
    }

    @Test
    public void copyProperties2b() {
        // setup
        beanUtil.setProperty(fromBean, FIELD2, FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        beanUtil.copyProperties(fromBean, toBean2);
        // assert
        assertFieldsSet(toBean2, true, true, false);
    }

    @Test
    public void copyProperties2c() {
        int tmp = 12;
        beanUtil.copyProperties(tmp, tmp);
    }

    /* copyProperties(Object from, Object to, String... properties); */

    @Test
    public void copyProperties3() {
        beanUtil.copyProperties(fromBean, toBean, FIELD1);
        assertFieldsSet(toBean, true, false);
    }

    /* copyProperties(Object from, Object to, Map<String, String> properties); */

    @Test
    public void copyProperties4() {
        beanUtil.copyProperties(fromBean, toBean, fieldFieldMap);
        assertFieldsSet(toBean, false, true);
    }

    /* copyProperties(Object from, Object to, int classLevel); */

    @Test
    public void copyProperties5() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        beanUtil.copyProperties(fromBean2, toBean2, 1);
        assertFieldsSet(toBean2, false, false, true);
    }

    /* utility methods */

    private void assertFieldsSet(DefaultAccessibleConstructor bean, boolean field1, boolean field2) {
        assertEquals("field1", field1 ? FIELD_VALUE : null, bean.getValue1());
        assertEquals("field2", field2 ? FIELD_VALUE : null, bean.getValue2());
    }

    private void assertFieldsSet(ChildClass bean, boolean field1, boolean field2, boolean field3) {
        assertEquals("field1", field1 ? FIELD_VALUE : null, bean.getValue1());
        assertEquals("field2", field2 ? FIELD_VALUE : null, bean.getValue2());
        assertEquals("field3", field3 ? FIELD_VALUE : null, bean.getValue3());
    }

    /* utility classes for testing */

    static class DefaultAccessibleConstructor {

        private String field1;
        private String field2;

        @SuppressWarnings("unused")
        private DefaultAccessibleConstructor() {
        }

        public DefaultAccessibleConstructor(String value) {
            field1 = value;
            field2 = value;
        }

        protected void setValue1(String value) {
            field1 = value;
        }

        protected void setValue2(String value) {
            field2 = value;
        }

        protected String getValue1() {
            return field1;
        }

        protected String getValue2() {
            return field2;
        }
    }

    static class ChildClass extends DefaultAccessibleConstructor {
        private String field3 = TEST;

        public ChildClass(String value) {
            super(value);
            field3 = value;
        }

        protected String getValue3() {
            return field3;
        }

        protected void setValue3(String field3) {
            this.field3 = field3;
        }

    }

    class NoAccessibleConstructor {
        public NoAccessibleConstructor() {
            // NOOP
        }
    }

    static class NoDefaultConstructor {
        public NoDefaultConstructor(String s) {
            // NOOP
        }
    }

}
