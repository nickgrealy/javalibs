	/**
 * 
 */
    package org.nickgrealy.commons.convert;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.nickgrealy.commons.reflect.BeanMetaModel;
import org.nickgrealy.commons.reflect.BeanMetaModelUtil;
import org.nickgrealy.commons.reflect.SmartBeanBuilder;

/**
 * 
 * @author nickgrealy@gmail.com
 */
@SuppressWarnings("unchecked")
public class SmartBeanBuilderTest {

	@Test
	public void buildBean() {
		// setup
		SmartBeanBuilder builder = new SmartBeanBuilder();
		List<String> fields = Arrays.asList(new String[] { "a", "b", "c", "d",
				"e", "f" });
		List<List<Object>> values = Arrays.asList(Arrays
				.asList(new Object[] { "test", "1", "2.3", "4.5f", "true",
						"<null>" }));
		// logic
		BeanMetaModel bmm = BeanMetaModelUtil.build(TestClass.class, fields, values);
        builder.buildBeans(Arrays.asList(new BeanMetaModel[]{bmm}));
        TestClass bean = (TestClass) bmm.getImplementedBeans()[0];
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
