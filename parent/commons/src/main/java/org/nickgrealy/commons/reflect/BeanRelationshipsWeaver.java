package org.nickgrealy.commons.reflect;

import org.nickgrealy.commons.convert.IConverterFactory;
import org.nickgrealy.commons.convert.ConversionConstants;
import org.nickgrealy.commons.exception.BeanBuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Bean builder post processor. Joins each bean's references to each other.
 *
 * @author nickgrealy@gmail.com
 */
public class BeanRelationshipsWeaver extends SmartBeanBuilderProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final IBeanPropertyAccessor BEAN_PROPERTY_ACCESSOR = BeanPropertyAccessor.INSTANCE;

    private IConverterFactory converterFactory;

    public BeanRelationshipsWeaver(IConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public void preProcess(Map<Class<?>, BeanMetaModel> param) {
        // NOOP
    }

    @Override
    public void postProcess(Map<Class<?>, BeanMetaModel> metaModelMap) {
        Collection<BeanMetaModel> metaModels = metaModelMap.values();
        for (BeanMetaModel localMetaModel : metaModels) {
            // if the model has relationships...
            if (!localMetaModel.getRelationships().isEmpty()) {
                List<List<Object>> valuesList = localMetaModel.getValues();
                int valuesListSize = valuesList.size();
                Set<Map.Entry<Field, String>> relationships = localMetaModel.getRelationships().entrySet();

                for (Map.Entry<Field, String> entry : relationships) {
                    // weave bean relationships
                    Field localField = entry.getKey();
                    String targetFieldAsString = entry.getValue();
                    int colNum = localMetaModel.getFields().indexOf(localField);
                    Class<?> localFieldType = localField.getType();
                    boolean isCollection = Collection.class.isAssignableFrom(localFieldType);

                    if (isCollection) {
                        Type[] types = ((ParameterizedType) localField.getGenericType()).getActualTypeArguments();
                        if (types.length > 0) {
                            localFieldType = (Class<?>) types[0];
                        } else {
                            // cannot determine target class type from generic Collection
                            throw new BeanBuilderException(localField);
                        }
                    }

                    // if simple relation, get target meta model...
                    Field targetField = BEAN_PROPERTY_ACCESSOR.getFieldRecursively(localFieldType, targetFieldAsString);
                    if (!metaModelMap.containsKey(targetField.getDeclaringClass())) {
                        // target class objects could not be found...
                        throw new BeanBuilderException(targetField.getDeclaringClass());
                    }
                    BeanMetaModel targetMetaModel = metaModelMap.get(targetField.getDeclaringClass());

                    int rowNum = -1;
                    for (List<Object> values : valuesList) {
                        rowNum++;
                        Object fieldValue = values.get(colNum);
                        if (ConversionConstants.NULL.equals((fieldValue))) {
                            logger.debug("Skipping relationship weaving for null field value. field='{}'", localField);
                            continue;
                        }
                        weaveBeans(localField, targetField, fieldValue, localMetaModel, targetMetaModel, rowNum, colNum,
                                isCollection);
                    }
                }
            }
        }
    }

    // TODO Map fields by value, to improve lookup time of target classes? (analyse performance benefit)

    /**
     * Attempts to join all local bean's relationships, to their respective target beans.
     *
     * @param localField
     * @param targetField
     * @param fieldValue
     * @param localMetaModel
     * @param targetMetaModel
     * @param rowNum
     * @param colNum
     * @param isCollection
     */
    private void weaveBeans(Field localField, Field targetField, Object fieldValue,
                            BeanMetaModel localMetaModel, BeanMetaModel targetMetaModel,
                            int rowNum, int colNum, boolean isCollection) {
        Object localBean = localMetaModel.getImplementedBeans()[rowNum];
        if (!isCollection) {
            // If not a collection... then join to the target bean...
            Object targetBean = getTargetBeanByFieldValue(targetField, fieldValue, targetMetaModel, rowNum, colNum);
            BEAN_PROPERTY_ACCESSOR.setProperty(localBean, localField, targetBean);
        } else {
            // If it is a Collection... then create the collection impl...
            Collection<Object> relations;
            if (Set.class.isAssignableFrom(localField.getType())) {
                relations = new HashSet<Object>();
            } else {
                relations = new LinkedList<Object>();
            }
            BEAN_PROPERTY_ACCESSOR.setProperty(localBean, localField, relations);
            // and join to the target beans...
            Object[] convert = converterFactory.convert(fieldValue, Object[].class);
            for (Object newFieldValue : convert) {
                Object targetBean = getTargetBeanByFieldValue(targetField, newFieldValue, targetMetaModel, rowNum, colNum);
                relations.add(targetBean);
            }
        }
    }

    /**
     * Retrieves an implemented bean from the meta model, whose field matches for the given value.
     *
     * @param targetField
     * @param fieldValue
     * @param metaModel
     * @param rowNum      used only for exception message context.
     * @param colNum      used only for exception message context.
     * @return
     */
    private Object getTargetBeanByFieldValue(Field targetField, Object fieldValue, BeanMetaModel metaModel, int rowNum, int colNum) {
        int targetObjectRowIndex = metaModel.getIndex(targetField, fieldValue);
        if (BeanMetaModel.FIELD_VALUE_NOT_FOUND == targetObjectRowIndex) {
            // target bean not found...
            throw new BeanBuilderException(rowNum, colNum, targetField, fieldValue);
        }
        return metaModel.getImplementedBeans()[targetObjectRowIndex];
    }

}
