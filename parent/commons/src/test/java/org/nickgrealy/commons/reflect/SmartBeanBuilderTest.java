/**
 * 
 */
package org.nickgrealy.commons.reflect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.nickgrealy.commons.conversion.ConverterFactory;
import org.nickgrealy.commons.conversion.PrimitiveConverters;

/**
 * 
 * @author nick.grealy
 */
public class SmartBeanBuilderTest {

    @Test
    public void buildBean() {
        // setup
        SmartBeanBuilder<TestClass> builder = new SmartBeanBuilder<TestClass>(TestClass.class, "a", "b", "c", "d", "e",
                "f");
        builder.setBeanUtil(new BeanUtil());
        builder.setConverterFactory(new ConverterFactory(PrimitiveConverters.getAllConverters()));
        TestClass bean = builder.buildBean("test", "1", "2.3", "4.5f", "true", "<null>");
        // assert
        assertEquals("test", bean.a);
        assertEquals(1, bean.b);
        assertEquals((Double) 2.3, (Double) bean.c);
        assertEquals((Float) 4.5f, bean.d);
        assertEquals(true, bean.e);
        assertEquals(null, bean.f);
        assertEquals(null, bean.g);

        // TODO Test more types...!
        // * <li>primitives</li>
        // * <li>Objects</li>
        // * <li>Enums</li>
        // * <li>Ids</li>
        // * <li>collections/arrays</li>
    }

    static class TestClass {

        String a;
        int b;
        double c;
        Float d;
        boolean e;
        Object f;
        Boolean g;

    }
}
