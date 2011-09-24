package org.nickgrealy.conversion.reflect;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.nickgrealy.test.validation.Assert;

@SuppressWarnings("unchecked")
public class ChildFirstComparatorTest {

	private List<Class<?>> actual;
	private List<Class<?>> expected;
	
	@After
	public void tearDown(){
		actual = null;
		expected = null;
	}
	
	@Test
	public void testOrdering(){
		actual = Arrays.asList(new Class<?>[]{Class1.class, Class2a.class, Class2b.class, Class3.class});
		expected = Arrays.asList(new Class<?>[]{Class3.class, Class2a.class, Class2b.class, Class1.class});
		// assert
		Collections.sort(actual, new ChildFirstComparator(actual));
		Assert.assertEquals("lists", expected, actual);
	}
	
	class Class1 {
	}
	
	abstract class Class2a extends Class1 {
	}
	
	class Class2b extends Class1 {
	}
	
	class Class3 extends Class2a {
	}
}
