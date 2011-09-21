package org.nickgrealy.conversion.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.nickgrealy.conversion.AbstractBaseConverter;
import org.nickgrealy.conversion.SpecialTargetTypes.Array;
import org.nickgrealy.test.validation.Assert;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringConverterTest {

    private final AbstractBaseConverter<String> stringConverter = new StringConverter();

    /**
     * Byte.class, Short.class, Integer.class, Long.class,
     * Float.class,Double.class, Boolean.class, Character.class, Enum.class
     */
    @Test
    public void testPrimitives() {
        String charStr = "a";
        assertStringConverter(charStr, String.class);
        assertStringConverter((byte) 1, byte.class);
        assertStringConverter((short) 1, short.class);
        assertStringConverter((int) 1, int.class);
        assertStringConverter((long) 1, long.class);
        assertStringConverter((float) 1.23f, float.class);
        assertStringConverter((double) 1.45, double.class);
        assertStringConverter((boolean) true, boolean.class);
        assertStringConverter((char) 'a', char.class);
        assertStringConverter((Enum<?>) TestEnum.Object1, TestEnum.class);
    }

    /**
     * Array.class, Collection.class, List.class, Set.class, Map.class
     */
    @Test
    public void testArraysCollectionsAndMaps() {
        // setup
        List<String> collObj = new ArrayList<String>();
        collObj.add("5");
        collObj.add("6");
        collObj.add("7");
        List<String> listObj = new ArrayList<String>();
        listObj.add("9");
        listObj.add("10");
        listObj.add("11");
        Set<String> setObj = new HashSet<String>();
        setObj.add("13");
        setObj.add("14");
        setObj.add("15");
        Map<String, String> mapObj = new HashMap<String, String>();
        mapObj.put("A", "17");
        mapObj.put("B", "18");
        mapObj.put("C", "19");
        // assertions
        assertStringConverter("2,3,4", new String[]{"2", "3", "4"},
                Array.class);
        assertStringConverter("5,6,7", collObj, Collection.class);
        assertStringConverter("9,10,11", listObj, List.class);
        assertStringConverter("13,14,15", setObj, Set.class);
        assertStringConverter("A>17,B>18,C>19", mapObj, Map.class);
    }

    /**
     * File.class, URI.class, Date.class, DateFormat.class
     *
     * @throws URISyntaxException
     */
    @Test
    public void testOther() throws URISyntaxException {
        assertStringConverter("/root/tmp", new File("/root/tmp"), File.class);
        assertStringConverter("http://www.google.com", new URI(
                "http://www.google.com"), URI.class);
    }

    @Ignore
    @Test
    public void todoDate() {
        assertStringConverter("31/01/2011 12:34:45T+1000", new Date(123), Date.class);
        assertStringConverter("HH:mm:ss", new SimpleDateFormat("HH:mm:ss"),
                DateFormat.class);
    }

    /**
     * AnyTarget.class
     */
    @Test
    public void testConvertToAny() {
        assertStringConverter("helloworld", new TestObject("helloworld"), TestObject.class);
    }

    // TODO Neg tests!

    @Test(expected = NumberFormatException.class)
    public void testConversionFailed1() {
        stringConverter.convert("notAnInt", int.class);
    }

    /* test classes */

    enum TestEnum {
        Object1;
    }

    static class TestObject {
        protected String value;

        public TestObject(String constructor) {
            this.value = constructor;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TestObject && value.equals(((TestObject) obj).value);
        }

    }

    /* Utility methods */

    private void assertStringConverter(Object input, Class<?> outputClass) {
        String inputStr = String.valueOf(input);
        Assert.assertEquals(inputStr, input, stringConverter.convert(inputStr,
                outputClass));
    }

    private void assertStringConverter(String input, Object output,
                                       Class<?> outputClass) {
        Assert.assertEquals(input, output, stringConverter.convert(input, outputClass));
    }

}
