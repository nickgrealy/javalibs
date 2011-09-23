/**
 *
 */
package org.nickgrealy.conversion;

import org.nickgrealy.commons.reflect.BeanPathUtil;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.reflect.IBeanUtil;
import org.nickgrealy.conversion.exception.BeanBuilderException;
import org.nickgrealy.conversion.impl.StringConverter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

/**
 * Builds beans, not stupid.
 * <p/>
 * <b>N.B.</b> A valid BeanUtil and ConverterFactory will need to be injected.
 * For convenience, the default ConverterFactory uses a StringConverter.
 *
 * @author nickgrealy@gmail.com
 */
public class SmartBeanBuilder<X> {

    private static final String LIST = "list";
    private static final String CLAZZ = "clazz";
    private static final String FIELDS = "fields";
    private static final String CONVERTER_FACTORY = "converterFactory";
    private static final String BEAN_UTIL = "beanUtil";
    private static final String VALUES_LENGTH = "values.length";
    private static final String VALUES = "values";

    private IBeanUtil beanUtil = new BeanUtil();
    private ConverterFactory converterFactory = new ConverterFactory(Arrays.asList(new StringConverter()));
    private final Class<X> clazz;
    private final List<Field> fields;
    private final List<Integer> joinFields;
    private final int numFields;

    public SmartBeanBuilder(Class<X> clazz, String[] fields) {
        check(FIELDS, fields).isNotNull();
        check(CLAZZ, clazz).isNotNull();
        this.numFields = fields.length;
        this.clazz = clazz;
        this.fields = new LinkedList<Field>();
        this.joinFields = new LinkedList<Integer>();
        for (int index = 0; index < numFields; ++index) {
            if (BeanPathUtil.isSingleBeanPath(fields[index])) {
                // Determine which fields can be populated straight away...
                this.fields.add(beanUtil.getFieldRecursively(clazz, fields[index]));
            } else {
                // and which will need to be joined later on...
                String[] beanPaths = BeanPathUtil.pathToFields(fields[index]);
                check(fields[index], beanPaths.length).equalz(2);
                this.fields.add(beanUtil.getFieldRecursively(clazz, beanPaths[0]));
                joinFields.add(index);
            }
        }
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        check("converterFactory", converterFactory).isNotNull();
        this.converterFactory = converterFactory;
    }

    public X buildBean(String... values) {
        List<String[]> valuesList = new LinkedList<String[]>();
        valuesList.add(values);
        return buildBeans(valuesList).get(0);
    }

    public List<X> buildBeans(List<String[]> valuesList) {
        // Check inputs...
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
     * @param checkedValues
     * @return
     */
    private List<X> buildBeansNoChecks(List<String[]> checkedValues) {
        // setup
        int rowNum = 0;
        int colNum;
        String fromValue = null;
        Class<?> targetClass = null;
        List<X> beans = new LinkedList<X>();
        for (String[] values : checkedValues) {
            rowNum++;
            colNum = 0;
            // Create bean...
            X bean = beanUtil.createBean(clazz);
            // Iterate over fields...
            try {
                for (Field field : fields) {
                    if (joinFields.contains(colNum)) {
                        // TODO Join field with object...
                    } else {
                        targetClass = field.getType();
                        fromValue = values[colNum];
                        Object targetValue = converterFactory.convert(fromValue, targetClass);
                        beanUtil.setProperty(bean, field, targetValue);
                    }
                    colNum++;
                }
                beans.add(bean);
            } catch (Throwable t) {
                throw new BeanBuilderException(rowNum, colNum, fromValue, targetClass, t);
            }
        }
        return beans;
    }

}
