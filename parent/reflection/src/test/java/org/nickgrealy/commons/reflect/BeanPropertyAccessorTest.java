/**
 *
 */
package org.nickgrealy.commons.reflect;

import org.junit.Before;
import org.junit.Test;
import org.nickgrealy.commons.exception.BeanException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
 * @author nickgrealy@gmail.com
 */
public class BeanPropertyAccessorTest {

    private static final String INVALID_FIELD = "invalidField";
    private static final String FIELD_VALUE = "test2";
    private static final String FIELD1 = "field1";
    private static final String FIELD2 = "field2";
    private static final String TEST = "test";

    private static final IBeanPropertyAccessor BEAN_PROPERTY_ACCESSOR = BeanPropertyAccessor.INSTANCE;

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
        assertTrue(null != BEAN_PROPERTY_ACCESSOR.createBean(DefaultAccessibleConstructor.class));
    }

    @Test(expected = BeanException.class)
    public void createBean2() {
        BEAN_PROPERTY_ACCESSOR.createBean(NoAccessibleConstructor.class);
    }

    @Test(expected = BeanException.class)
    public void createBean3() {
        BEAN_PROPERTY_ACCESSOR.createBean(NoDefaultConstructor.class);
    }

    /* getProperty */

    @Test
    public void getProperty1() {
        bean1.setValue1(TEST);
        assertEquals(TEST, BEAN_PROPERTY_ACCESSOR.getProperty(bean1, FIELD1));
    }

    @Test(expected = BeanException.class)
    public void getProperty2() {
        BEAN_PROPERTY_ACCESSOR.getProperty(bean1, INVALID_FIELD);
    }

    /* setProperty */

    @Test
    public void setProperty1() {
        BEAN_PROPERTY_ACCESSOR.setProperty(bean1, FIELD1, FIELD_VALUE);
        assertFieldsSet(bean1, true, false);
    }

    @Test(expected = BeanException.class)
    public void setProperty2() {
        BEAN_PROPERTY_ACCESSOR.setProperty(bean1, INVALID_FIELD, FIELD_VALUE);
    }

    /* createBean(Class<I> clazz, Map<String, Object> properties) */

    @Test
    public void createBean5() {
        DefaultAccessibleConstructor bean = BEAN_PROPERTY_ACCESSOR.createBean(DefaultAccessibleConstructor.class, fieldValueMap);
        assertFieldsSet(bean, true, false);
    }

    /* copyProperties(Object object, Map<String, Object> properties); */

    @Test
    public void copyProperties1() {
        BEAN_PROPERTY_ACCESSOR.copyProperties(toBean, fieldValueMap);
        assertFieldsSet(toBean, true, false);
    }

    /* copyProperties(Object from, Object to); */

    @Test
    public void copyProperties2() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean2, toBean2);
        assertFieldsSet(toBean2, true, true, true);
    }

    @Test(expected = BeanException.class)
    public void copyProperties2a() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean2, toBean);
        assertFieldsSet(toBean, true, true);
    }

    @Test
    public void copyProperties2b() {
        // setup
        BEAN_PROPERTY_ACCESSOR.setProperty(fromBean, FIELD2, FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean, toBean2);
        // assert
        assertFieldsSet(toBean2, true, true, false);
    }

    @Test
    public void copyProperties2c() {
        int tmp = 12;
        BEAN_PROPERTY_ACCESSOR.copyProperties(tmp, tmp);
    }

    /* copyProperties(Object from, Object to, String... properties); */

    @Test
    public void copyProperties3() {
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean, toBean, FIELD1);
        assertFieldsSet(toBean, true, false);
    }

    /* copyProperties(Object from, Object to, Map<String, String> properties); */

    @Test
    public void copyProperties4() {
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean, toBean, fieldFieldMap);
        assertFieldsSet(toBean, false, true);
    }

    /* copyProperties(Object from, Object to, int classLevel); */

    @Test
    public void copyProperties5() {
        ChildClass fromBean2 = new ChildClass(FIELD_VALUE);
        ChildClass toBean2 = new ChildClass(null);
        BEAN_PROPERTY_ACCESSOR.copyProperties(fromBean2, toBean2, 1);
        assertFieldsSet(toBean2, false, false, true);
    }

    /* getFieldRecursively(Class<?> clazz, String field); */

    @Test
    public void getFieldRecursively1() {
        assertNotNull(BEAN_PROPERTY_ACCESSOR.getFieldRecursively(ExtendsAbstractClass.class, "field2"));
        assertNotNull(BEAN_PROPERTY_ACCESSOR.getFieldRecursively(ExtendsAbstractClass.class, "field1"));
    }

    @Test(expected = BeanException.class)
    public void getFieldRecursively2() {
        assertNotNull(BEAN_PROPERTY_ACCESSOR.getFieldRecursively(ExtendsAbstractClass.class, "field3"));
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

    static abstract class AbstractClass {
        String field1;
    }

    static class ExtendsAbstractClass extends AbstractClass {
        String field2;
    }


}
