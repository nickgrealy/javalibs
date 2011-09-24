package org.nickgrealy.conversion.reflect;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nickgrealy.commons.reflect.BeanPathUtil;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.reflect.IBeanUtil;
import org.nickgrealy.commons.util.NotNullableMap;

/**
 * Utility to faciliate build {@link BeanMetaModel}'s.
 * 
 * @author nickgrealy@gmail.com
s */
public class BeanMetaModelUtil {

	private static final IBeanUtil beanUtil = new BeanUtil();

	public static BeanMetaModel build(Class<?> clazz, List<String> fieldsBeanPath,
			List<List<Object>> values) {
		// Setup
		List<Field> fields = new LinkedList<Field>();
		Map<Field, Field> relationships = new NotNullableMap<Field, Field>();
		// Determine which fields have relationships based on bean path...
		for (String field : fieldsBeanPath) {
			// if (bean path has no relationship) ...
			if (BeanPathUtil.isSingleBeanPath(field)) {
				fields.add(beanUtil.getFieldRecursively(clazz, field));
			} else {
				String[] beanPaths = BeanPathUtil.pathToFields(field);
				check(field, beanPaths.length).equalz(2);
				Field localField = beanUtil.getFieldRecursively(clazz,
						beanPaths[0]);
				Field targetField = beanUtil.getFieldRecursively(localField
						.getType(), beanPaths[1]);
				fields.add(localField);
				relationships.put(localField, targetField);
			}
		}
		return new BeanMetaModel(clazz, fields, relationships, values);
	}
}