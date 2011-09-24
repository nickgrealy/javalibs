/**
 *
 */
package org.nickgrealy.loady;

import org.nickgrealy.loady.csv.CSVBeanLoader;
import org.nickgrealy.loady.csv.CSVFileFilter;
import org.nickgrealy.test.validation.Assert;
import org.nickgrealy.test.validation.IAssertBean;
import org.junit.Ignore;
import org.junit.Test;
import org.nickgrealy.commons.exception.AssertionException;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.conversion.exception.BeanBuilderException;
import org.nickgrealy.test.validation.AssertBean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author nickgrealy@gmail.com
 */
@Ignore
public class CSVBeanFactoryTest {
	
	private IAssertBean assertBean = new AssertBean();

    // tested
    public static final String CLASSPATH_CSV_POJO = "classpath:csv-pojo";
    public static final String CLASSPATH_CSV_INVALID1 = "classpath:csv-invalid1";
    public static final String CLASSPATH_CSV_INVALID2 = "classpath:csv-invalid2";
    public static final String CLASSPATH_CSV_LOAD = "classpath:csv-load";
    public static final String CLASSPATH_CSV_INHERITANCE = "classpath:csv-inheritance";
    public static final String CLASSPATH_CSV_RELATIONSHIPS = "classpath:csv-relationships";

    @Test
    public void csv() throws FileNotFoundException, URISyntaxException {
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
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_POJO);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        // assert
        assertNotNull(csvBeans);
        assertEquals(1, csvBeans.size());
        assertTrue(csvBeans.containsKey(SimpleBean.class));
        List<?> beans = csvBeans.get(SimpleBean.class);
        assertEquals(1, beans.size());
        assertBean.assertEquals(expected, beans.get(0), 1);
    }

    @Test(expected = AssertionException.class)
    public void csvInvalid1Test() throws FileNotFoundException {
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_INVALID1);
        new CSVBeanLoader().loadFolder(csvFolder);
        fail("Should've thrown exception already!");
    }

    @Test(expected = BeanBuilderException.class)
    public void csvInvalid2Test() throws FileNotFoundException {
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_INVALID2);
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
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_INHERITANCE);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        assertEquals(3, csvBeans.size());
        // assert BaseBean
        List<?> tmp = csvBeans.get(BaseBean.class);
        assertEquals(3, tmp.size());
        assertBean.assertEquals(b1, tmp.get(0));
        // assert ExtendsBaseBean has BaseBean properties...
        tmp = csvBeans.get(ExtendsBaseBean.class);
        assertEquals(3, tmp.size());
        assertBean.assertEquals(eb1, tmp.get(0));
        // assert ExtendsAbstractBaseBean has AbstractBaseBean properties...
        tmp = csvBeans.get(ExtendsAbstractBaseBean.class);
        assertEquals(3, tmp.size());
        assertBean.assertEquals(eab1, tmp.get(0));
    }

    @Test
    public void csvRelationshipsTest() throws FileNotFoundException {
        // execute
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_RELATIONSHIPS);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        assertEquals(2, csvBeans.size());
        // TODO Check values
    }

    @Test(timeout = 15000)
    public void csvLoadTest() throws FileNotFoundException {
        // execute - 10,000 objects.
        File csvFolder = ResourceUtils.getFile(CLASSPATH_CSV_LOAD);
        Map<Class<?>, List<?>> csvBeans = new CSVBeanLoader().loadFolder(csvFolder);
        // assert
        assertNotNull(csvBeans);
        assertEquals(1, csvBeans.size());
        assertTrue(csvBeans.containsKey(SimpleBean.class));
        List<?> beans = csvBeans.get(SimpleBean.class);
        assertEquals(10000, beans.size());
    }
}
