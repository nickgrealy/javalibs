package org.nickgrealy.commons.reflect;

import org.nickgrealy.commons.util.NotNullableMap;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.nickgrealy.commons.validate.RuntimeAssert.check;

/**
 * Utility to faciliate build {@link BeanMetaModel}'s.
 *
 * @author nickgrealy@gmail.com
 *         s
 */
public class BeanMetaModelUtil {

    private static final IBeanPropertyAccessor BEAN_PROPERTY_ACCESSOR = BeanPropertyAccessor.INSTANCE;

    public static <X> BeanMetaModel build(Class<X> clazz, List<String> fieldsBeanPath,
                                          List<List<Object>> values) {
        // Setup
        List<Field> fields = new LinkedList<Field>();
        Map<Field, String> relationships = new NotNullableMap<Field, String>();
        // Determine which fields have relationships based on bean path...
        for (String field : fieldsBeanPath) {
            // if (bean path has no relationship) ...
            if (BeanPathUtil.isSingleBeanPath(field)) {
                fields.add(BEAN_PROPERTY_ACCESSOR.getFieldRecursively(clazz, field));
            } else {
                String[] beanPaths = BeanPathUtil.pathToFields(field);
                check(field, beanPaths.length).equalz(2);
                Field localField = BEAN_PROPERTY_ACCESSOR.getFieldRecursively(clazz,
                        beanPaths[0]);
                fields.add(localField);
                relationships.put(localField, beanPaths[1]);
            }
        }
        return new BeanMetaModel(clazz, fields, relationships, values);
    }
}