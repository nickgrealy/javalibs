/**
 * 
 */
package org.nickgrealy.loady;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.testy.validation.AssertBean;
import org.springframework.util.ResourceUtils;

/**
 * 
 * @author nick.grealy
 */
public class CSVBeanFactoryTest {

    // TODO Ignore just for this commit... enable later!
    @Ignore
    @Test
    public void loadCsv() throws FileNotFoundException, URISyntaxException {
        // setup
        List<Integer> collObj = new ArrayList<Integer>();
        collObj.add(5);
        collObj.add(6);
        collObj.add(7);
        collObj.add(8);
        List<Integer> listObj = new ArrayList<Integer>();
        listObj.add(9);
        listObj.add(10);
        listObj.add(11);
        listObj.add(12);
        Set<Integer> setObj = new HashSet<Integer>();
        setObj.add(13);
        setObj.add(14);
        setObj.add(15);
        setObj.add(16);
        Map<String, Integer> mapObj = new HashMap<String, Integer>();
        mapObj.put("A", 17);
        mapObj.put("B", 18);
        mapObj.put("C", 19);
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
        expected.arrayObj = new Object[] { "1", "2", "3", "4" };
        expected.collObj = collObj;
        expected.listObj = listObj;
        expected.setObj = setObj;
        expected.mapObj = mapObj;
        expected.fileObj = new File("/root/helloworld.txt");
        expected.uriObj = new URI("http://www.google.com");
        // execute
        File csvFolder = ResourceUtils.getFile("classpath:csv");
        Map<Class<?>, List<?>> csvBeans = new CSVBeanFactory().loadCsvFolder(csvFolder);
        // assert
        assertNotNull(csvBeans);
        assertEquals(1, csvBeans.size());
        assertTrue(csvBeans.containsKey(SimpleBean.class));
        List<?> beans = csvBeans.get(SimpleBean.class);
        assertEquals(2, beans.size());
        AssertBean assertBean = new AssertBean();
        assertBean.setBeanUtil(new BeanUtil());
        assertBean.assertEquals(expected, beans.get(0), 1);
    }
}
