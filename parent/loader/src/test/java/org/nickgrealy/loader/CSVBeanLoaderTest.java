/**
 *
 */
package org.nickgrealy.loader;

import org.nickgrealy.commons.exception.BeanBuilderException;
import org.nickgrealy.loader.csv.CSVBeanLoader;
import org.junit.Test;
import org.nickgrealy.commons.exception.AssertionException;
import org.nickgrealy.test.validation.AssertBean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.*;
import static org.nickgrealy.test.validation.AssertBean.*;

/**
 * @author nickgrealy@gmail.com
 */
public class CSVBeanLoaderTest {
	
    // tested
    public static final String CSV_POJO = "classpath:csv-pojo";
    public static final String CSV_POJO_INVALID1 = "classpath:csv-pojo-invalid1";
    public static final String CSV_POJO_INVALID2 = "classpath:csv-pojo-invalid2";
    public static final String CSV_POJO_LOADTEST = "classpath:csv-pojo-loadtest";
    public static final String CSV_INHERITANCE = "classpath:csv-inheritance";
    public static final String CSV_RELATIONSHIPS = "classpath:csv-relationships";

    @Test
    public void csvPojo() throws FileNotFoundException, URISyntaxException {
        // setup
        List<String> collObj = new ArrayList<String>();
        collObj.add("5");
        collObj.add("6");
        collObj.add("7");
        collObj.add("8");
        List<String> listObj = new ArrayList<String>();
        listObj.add("9");
        listObj.add("10");
        listObj.add("11");
        listObj.add("12");
        Set<String> setObj = new HashSet<String>();
        setObj.add("13");
        setObj.add("14");
        setObj.add("15");
        setObj.add("16");
        Map<String, String> mapObj = new HashMap<String, String>();
        mapObj.put("A", "17");
        mapObj.put("B", "18");
        mapObj.put("C", "19");
        SimpleBean expected = new SimpleBean();
        expected.nullObj1 = null;
        expected.nullObj2 = null;
        expected.a02 = 1;
        expected.a03 = 2;
        expected.a04 = 3;
        expected.a05 = 4;
        expected.a06 = 5.6f;
        expected.a07 = 7.8;
        expected.a08 = true;
        expected.a09 = 'A';
        expected.a10 = 7;
        expected.a11 = 8;
        expected.a12 = 9;
        expected.a13 = new Long(10);
        expected.a14 = 11.12f;
        expected.a15 = 13.14;
        expected.a16 = false;
        expected.a17 = 'B';
        expected.stringObj = "Hello";
        expected.enumObj = SimpleEnum.ENUM02;
        expected.arrayObj = new Object[]{"1", "2", "3", "4"};
        expected.collObj = collObj;
        expected.listObj = listObj;
        expected.setObj = setObj;
        expected.mapObj = mapObj;
        expected.fileObj = new File("/root/helloworld.txt");
        expected.uriObj = new URI("http://www.google.com");
        // execute
        File csvFolder = ResourceUtils.getFile(CSV_POJO);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        // assert
        assertNotNull(csvBeans);
        assertEquals(1, csvBeans.size());
        assertTrue(csvBeans.containsKey(SimpleBean.class));
        List<?> beans = csvBeans.get(SimpleBean.class);
        assertEquals(1, beans.size());
        assertEquals(expected, beans.get(0), 1);
    }

    @Test(expected = AssertionException.class)
    public void csvInvalid1Test() throws FileNotFoundException {
        File csvFolder = ResourceUtils.getFile(CSV_POJO_INVALID1);
        new CSVBeanLoader().loadFolder(csvFolder);
        fail("Should've thrown exception already!");
    }

    @Test(expected = BeanBuilderException.class)
    public void csvInvalid2Test() throws FileNotFoundException {
        File csvFolder = ResourceUtils.getFile(CSV_POJO_INVALID2);
        new CSVBeanLoader().loadFolder(csvFolder);
        fail("Should've thrown exception already!");
    }

    @Test
    public void csvInheritanceTest() throws FileNotFoundException {
        // expected values
        BaseBean b1 = new BaseBean("A", "hungry");
        ExtendsBaseBean eb1 = new ExtendsBaseBean("A", "hungry", "hippo");
        ExtendsAbstractBaseBean eab1 = new ExtendsAbstractBaseBean("A", "hungry", "hippo");
        // execute
        File csvFolder = ResourceUtils.getFile(CSV_INHERITANCE);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        assertEquals(3, csvBeans.size());
        // assert BaseBean
        List<?> tmp = csvBeans.get(BaseBean.class);
        assertEquals(3, tmp.size());
        AssertBean.assertEquals(b1, tmp.get(0));
        // assert ExtendsBaseBean has BaseBean properties...
        tmp = csvBeans.get(ExtendsBaseBean.class);
        assertEquals(3, tmp.size());
        AssertBean.assertEquals(eb1, tmp.get(0));
        // assert ExtendsAbstractBaseBean has AbstractBaseBean properties...
        tmp = csvBeans.get(ExtendsAbstractBaseBean.class);
        assertEquals(3, tmp.size());
        AssertBean.assertEquals(eab1, tmp.get(0));
    }

    @Test
    public void csvRelationshipsTest() throws FileNotFoundException {
        // setup
        ComplexBeanB expected = new ComplexBeanB(1L, "testReferenceOtherBeanClass", null, null, null, null, null);
        // execute
        File csvFolder = ResourceUtils.getFile(CSV_RELATIONSHIPS);
         Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        assertEquals(2, csvBeans.size());

        // ComplexBeanA
        List<?> beansA = csvBeans.get(ComplexBeanA.class);
        assertEquals(10, beansA.size());
        // TODO assert values

        // ComplexBeanB
        ComplexBeanA beanA1 = (ComplexBeanA)beansA.get(0);
        ComplexBeanA beanA2 = (ComplexBeanA)beansA.get(1);
        expected.simple = beanA1;
        expected.simple2 = beanA2;
        expected.list = Arrays.asList(beanA1, beanA2);
        expected.collection = expected.list;
        expected.set = new HashSet<ComplexBeanA>(expected.list);
        // assert
        List<?> beansB = csvBeans.get(ComplexBeanB.class);
        assertEquals(1, beansB.size());
        ComplexBeanB beanB1 = (ComplexBeanB)beansB.get(0);
        AssertBean.assertEquals(expected, beanB1);
    }

    @Test(timeout = 15000)
    public void csvLoadTest() throws FileNotFoundException {
        // execute - 10,000 objects.
        File csvFolder = ResourceUtils.getFile(CSV_POJO_LOADTEST);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        // assert
        assertNotNull(csvBeans);
        assertEquals(1, csvBeans.size());
        assertTrue(csvBeans.containsKey(SimpleBean.class));
        List<?> beans = csvBeans.get(SimpleBean.class);
        assertEquals(10000, beans.size());
    }
}
