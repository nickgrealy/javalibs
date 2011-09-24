/**
 *
 */
package org.nickgrealy.conversion.reflect;

import org.nickgrealy.commons.exception.NotYetImplementedException;
import org.nickgrealy.commons.reflect.BeanPathUtil;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.reflect.IBeanUtil;
import org.nickgrealy.commons.util.MapUtil;
import org.nickgrealy.commons.util.NotNullableMap;
import org.nickgrealy.conversion.ConverterFactory;
import org.nickgrealy.conversion.exception.BeanBuilderException;
import org.nickgrealy.conversion.impl.StringConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

/**
 * Builds beans, not stupid.
 * <p/>
 * <b>N.B.</b> A valid BeanUtil and ConverterFactory will need to be injected.
 * For convenience, the default ConverterFactory uses a StringConverter.
 * 
 * @author nickgrealy@gmail.com
 */
public class SmartBeanBuilder {

	private static final String LIST = "list";
	private static final String CLAZZ = "clazz";
	private static final String FIELDS = "fields";
	private static final String CONVERTER_FACTORY = "converterFactory";
	private static final String BEAN_UTIL = "beanUtil";
	private static final String VALUES_LENGTH = "values.length";
	private static final String VALUES = "values";

	private static final MapUtil mapUtil = new MapUtil();
	private IBeanUtil beanUtil = new BeanUtil();
	private ConverterFactory converterFactory = new ConverterFactory(Arrays
			.asList(new StringConverter()));
//	private Class<X> clazz;
//	private List<Field> fields;
//	private List<Integer> joinFields;
//	private int numFields;

	public SmartBeanBuilder() {

	}

//	public SmartBeanBuilder(Class<X> clazz, String[] fields) {
//		check(FIELDS, fields).isNotNull();
//		check(CLAZZ, clazz).isNotNull();
//		this.numFields = fields.length;
//		this.clazz = clazz;
//		this.fields = new LinkedList<Field>();
//		this.joinFields = new LinkedList<Integer>();
//		for (int index = 0; index < numFields; ++index) {
//			if (BeanPathUtil.isSingleBeanPath(fields[index])) {
//				// Determine which fields can be populated straight away...
//				this.fields.add(beanUtil.getFieldRecursively(clazz,
//						fields[index]));
//			} else {
//				// and which will need to be joined later on...
//				String[] beanPaths = BeanPathUtil.pathToFields(fields[index]);
//				check(fields[index], beanPaths.length).equalz(2);
//				this.fields.add(beanUtil.getFieldRecursively(clazz,
//						beanPaths[0]));
//				joinFields.add(index);
//			}
//		}
//	}
//
//	public void setConverterFactory(ConverterFactory converterFactory) {
//		check("converterFactory", converterFactory).isNotNull();
//		this.converterFactory = converterFactory;
//	}
//
//	public X buildBean(String... values) {
//		List<String[]> valuesList = new LinkedList<String[]>();
//		valuesList.add(values);
//		return buildBeans(valuesList).get(0);
//	}
//
//	public List<X> buildBeans(List<String[]> valuesList) {
//		// Check inputs...
//		check(CONVERTER_FACTORY, converterFactory).isNotNull();
//		check(LIST, valuesList).isNotNull();
//		for (String[] values : valuesList) {
//			check(VALUES, values).isNotNull();
//			check(VALUES_LENGTH, values.length).equalz(numFields);
//		}
//		return buildBeansNoChecks(valuesList);
//	}

	/**
	 * Build beans from metaModels.
	 * 
	 * @param metaModelsList
	 * @return
	 */
	public NotNullableMap<Class<?>, List<?>> buildBeans(List<BeanMetaModel> metaModelsList){
        // setup 
    	check("metaModelsList", metaModelsList).isNotNull();
		Map<Class<?>, BeanMetaModel> metaModelsMap = mapUtil.mapByField(
				metaModelsList, BeanMetaModel.CLASS_VARIABLE,
				new NotNullableMap<Class<?>, BeanMetaModel>());
		List<Class<?>> buildOrder = new LinkedList<Class<?>>(metaModelsMap.keySet());
		Collections.sort(buildOrder, new ChildFirstComparator(buildOrder));
		// TODO consolidate inheritance bean meta models...
		// build beans
		NotNullableMap<Class<?>, List<?>> beansByClassMap = new NotNullableMap<Class<?>, List<?>>();
		
		for (Class<?> clazz : buildOrder) {
			if (beanUtil.isInstantiable(clazz)){
				// check if first field is an inheritance field...
				BeanMetaModel bmm = metaModelsMap.get(clazz);
				beansByClassMap.put(clazz, buildBeans(bmm));
			}
		}
		return beansByClassMap;
    }
	
	private List<List<Class<?>>> consolidateInheritanceBeanMetaModels(){
		// TODO
		return null;
	}

	public List<Object> buildBeans(BeanMetaModel bmm){
    	// pre-checks and setup...
    	bmm.validate();
    	return buildBeansNoChecks(bmm.getClazz(), bmm.getFields(), bmm.getRelationships(), bmm.getValues());
    }

	/**
	 * Builds the bean, but doesn't do any of the error checking.
	 * 
	 * @param checkedValues
	 * @return
	 */
	private List<Object> buildBeansNoChecks(Class<?> clazz, 
			List<Field> fields, 
			Map<Field, Field> relationships, 
			List<List<Object>> checkedValues) {
		// setup
		int rowNum = 0;
		int colNum;
		Object fromValue = null;
		Class<?> targetClass = null;
		List<Object> beans = new LinkedList<Object>();
		for (List<? extends Object> values : checkedValues) {
			rowNum++;
			colNum = 0;
			// Check class is instantiable...
			if (beanUtil.isInstantiable(clazz)) {
				// Create bean...
				Object bean = beanUtil.createBean(clazz);
				// Iterate over fields...
				try {
					Iterator<Field> fieldsIter = fields.iterator();
					Iterator<? extends Object> valuesIter = values.iterator();
					while (fieldsIter.hasNext() && valuesIter.hasNext()) {
						Field field = fieldsIter.next();
						fromValue = valuesIter.next();
						if (relationships.keySet().contains(field)) {
							// TODO Join field with object...
						} else {
							targetClass = field.getType();
							Object targetValue = converterFactory.convert(
									fromValue, targetClass);
							beanUtil.setProperty(bean, field, targetValue);
						}
						colNum++;
					}
					beans.add(bean);
				} catch (Throwable t) {
					throw new BeanBuilderException(rowNum, colNum, fromValue,
							targetClass, t);
				}
			} else {
				// TODO
				// log.warn(String.format("Cannot instantiate abstract class or interface! class='%s'",
				// clazz));
			}
		}
		return beans;
	}

}
