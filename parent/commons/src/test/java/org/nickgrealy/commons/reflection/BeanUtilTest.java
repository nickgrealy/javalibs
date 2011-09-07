/**
 * 
 */
package org.nickgrealy.commons.reflection;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nickgrealy.commons.exceptions.BeanException;

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

    private static final String TEST = "test";

    private static final IBeanUtil beanUtil = new BeanUtil();

    private DefaultAccessibleConstructor testBean;
    private Map<String, Object> testMap;

    @Before
    public void setUp() {
        testBean = new DefaultAccessibleConstructor();
        testMap = new HashMap<String, Object>();
    }

    /* createBean(Class) */

    @Test
    public void createBean1() {
        assertTrue(null != beanUtil.createBean(DefaultAccessibleConstructor.class));
    }

    @Test(expected = BeanException.class)
    public void createBean2() {
        beanUtil.createBean(NoAccessibleConstructor1.class);
    }

    @Test(expected = BeanException.class)
    public void createBean3() {
        beanUtil.createBean(NoAccessibleConstructor2.class);
    }

    @Test(expected = BeanException.class)
    public void createBean4() {
        beanUtil.createBean(NoDefaultConstructor.class);
    }

    /* getProperty */

    @Test
    public void getProperty1() {
        assertTrue(TEST.equals(beanUtil.getProperty(testBean, "field1")));
    }

    @Test(expected = BeanException.class)
    public void getProperty2() {
        beanUtil.getProperty(testBean, "field2");
    }

    /* setProperty */

    @Test
    public void setProperty1() {
        String newValue = "test2";
        beanUtil.setProperty(testBean, "field1", newValue);
        assertTrue(newValue.equals(testBean.toString()));
    }

    @Test(expected = BeanException.class)
    public void setProperty2() {
        beanUtil.setProperty(testBean, "field2", "test2");
    }

    /* createBean(Class<I> clazz, Map<String, Object> properties) */

    @Test
    public void createBean5() {
        String newValue = "test2";
        testMap.put("field1", newValue);
        DefaultAccessibleConstructor bean = beanUtil.createBean(DefaultAccessibleConstructor.class, testMap);
        assertTrue(newValue.equals(bean.toString()));
    }

    /* copyProperties(Object object, Map<String, Object> properties); */
    /* copyProperties(Object from, Object to); */
    /* copyProperties(Object from, Object to, String... properties); */
    /* copyProperties(Object from, Object to, Map<String, String> properties); */
    /* copyProperties(Object from, Object to, int classLevel); */

    /* utility classes for testing */

    static class DefaultAccessibleConstructor {

        private String field1 = TEST;

        public DefaultAccessibleConstructor() {
            field1 = TEST;
        }

        @Override
        public String toString() {
            return field1;
        }
    }

    class NoAccessibleConstructor1 {
        public NoAccessibleConstructor1() {
            // NOOP
        }
    }

    static class NoAccessibleConstructor2 {
        private NoAccessibleConstructor2() {
            // NOOP
        }
    }

    static class NoDefaultConstructor {
        public NoDefaultConstructor(String s) {
            // NOOP
        }
    }

}
