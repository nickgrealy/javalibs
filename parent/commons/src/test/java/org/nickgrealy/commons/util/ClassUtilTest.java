package org.nickgrealy.commons.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author nickgrealy@gmail.com
 */
public class ClassUtilTest {

    private Class<?>[] primitivesArray = {byte.class, short.class, int.class, long.class, float.class, double.class, boolean.class, char.class};
    private Class<?>[] assocObjectArray = {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class};
    private Class<?> notAPrimitive = Object.class;

    @Test
    public void isPrimitive() {
        // non primitives...
        Assert.assertFalse(ClassUtil.isPrimitiveClass(notAPrimitive));
        for (Class<?> objectClass : assocObjectArray) {
            Assert.assertFalse(ClassUtil.isPrimitiveClass(objectClass));
        }
        // primitives
        for (Class<?> primitiveClass : primitivesArray) {
            Assert.assertTrue(ClassUtil.isPrimitiveClass(primitiveClass));
        }
    }

    @Test
    public void convertPrimitiveToObject() {
        // assert primitive conversion
        for (int i = 0; i < primitivesArray.length; i++) {
            Assert.assertEquals(assocObjectArray[i], ClassUtil.convertPrimitiveToObjectClass(primitivesArray[i]));
        }
        // assert object (non) conversion.
        Assert.assertEquals(notAPrimitive, ClassUtil.convertPrimitiveToObjectClass(notAPrimitive));
    }

    @Test
    public void buildInheritanceMap() {
        // setup
        HashSet<Class<?>> classesSet = new HashSet<Class<?>>(Arrays.asList(Class1.class, Class3a.class, Class3b.class, Class4.class, Class5.class, Class2.class));
        // logic
        OneToManyMap<Class<?>, Class<?>, Set<Class<?>>> classToParents = ClassUtil.buildInheritanceMap(classesSet);
        // assert
        Assert.assertEquals(5, classToParents.size());
        assertSet(classToParents, null, Class5.class);
        assertSet(classToParents, Class2.class, Class1.class);
        assertSet(classToParents, Class3a.class, Class2.class, Class1.class);
        assertSet(classToParents, Class3b.class, Class2.class, Class1.class);
        assertSet(classToParents, Class4.class, Class3a.class, Class2.class, Class1.class);
    }

    /* utility methods */

    private void assertSet(OneToManyMap<Class<?>, Class<?>, Set<Class<?>>> actual, Class<?> expectedKey, Class<?>... expectedValues) {
        Set<Class<?>> actualValues = actual.get(expectedKey);
        Assert.assertEquals(expectedValues.length, actualValues.size());
        Assert.assertTrue(actualValues.containsAll(Arrays.asList(expectedValues)));
    }

    /* test classes */

    interface Class1 {
    }

    class Class2 implements Class1 {
    }

    abstract class Class3a extends Class2 {
    }

    class Class3b extends Class2 {
    }

    class Class4 extends Class3a {
    }

    class Class5 {
    }
}
