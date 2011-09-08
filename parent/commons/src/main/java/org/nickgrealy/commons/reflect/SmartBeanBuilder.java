/**
 * 
 */
package org.nickgrealy.commons.reflect;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.nickgrealy.commons.conversion.ConverterFactory;
import org.nickgrealy.commons.exception.BeanException;

/**
 * Builds beans, not stupid.
 * 
 * <b>N.B.</b> A valid BeanUtil and ConverterFactory will need to be injected.
 * 
 * @author nick.grealy
 */
public class SmartBeanBuilder<X> {

    private static final String LIST = "list";
    private static final String CLAZZ = "clazz";
    private static final String FIELDS = "fields";
    private static final String CONVERTER_FACTORY = "converterFactory";
    private static final String BEAN_UTIL = "beanUtil";
    private static final String VALUES_LENGTH = "values.length";
    private static final String VALUES = "values";

    private BeanUtil beanUtil;
    private ConverterFactory converterFactory;
    private final Class<X> clazz;
    private final Field[] fields;
    private final int numFields;

    public SmartBeanBuilder(Class<X> clazz, String... fields) {
        check(FIELDS, fields).isNotNull();
        check(CLAZZ, clazz).isNotNull();
        this.numFields = fields.length;
        this.clazz = clazz;
        this.fields = new Field[numFields];
        int index = 0;
        try {
            for (; index < numFields; ++index) {
                this.fields[index] = clazz.getDeclaredField(fields[index]);
            }
        } catch (SecurityException e) {
            throw new BeanException(fields[index], e);
        } catch (NoSuchFieldException e) {
            throw new BeanException(fields[index], e);
        }
    }

    public X buildBean(String... values) {
        List<String[]> valuesList = new LinkedList<String[]>();
        valuesList.add(values);
        return buildBeans(valuesList).get(0);
    }

    public List<X> buildBeans(List<String[]> valuesList) {
        // Check inputs...
        check(BEAN_UTIL, beanUtil).isNotNull();
        check(CONVERTER_FACTORY, converterFactory).isNotNull();
        check(LIST, valuesList).isNotNull();
        for (String[] values : valuesList) {
            check(VALUES, values).isNotNull();
            check(VALUES_LENGTH, values.length).equalz(numFields);
        }
        return buildBeansNoChecks(valuesList);
    }

    /**
     * Builds the bean, but doesn't do any of the error checking.
     * 
     * @param values
     * @return
     */
    private List<X> buildBeansNoChecks(List<String[]> checkedValues) {
        List<X> beans = new LinkedList<X>();
        for (String[] values : checkedValues) {
            // Create bean...
            X bean = beanUtil.createBean(clazz);
            // Iterate over fields...
            for (int i = 0; i < numFields; ++i) {
                Class<?> targetClass = fields[i].getType();
                Object targetValue = converterFactory.convert(values[i], targetClass);
                beanUtil.setProperty(bean, fields[i], targetValue);
            }
            beans.add(bean);
        }
        return beans;
    }

    public void setBeanUtil(BeanUtil beanUtil) {
        check("beanUtil", beanUtil).isNotNull();
        this.beanUtil = beanUtil;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        check("converterFactory", converterFactory).isNotNull();
        this.converterFactory = converterFactory;
    }

}
