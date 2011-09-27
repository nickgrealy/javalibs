/**
 *
 */
package org.nickgrealy.commons.reflect;

import org.nickgrealy.commons.convert.ConverterFactory;
import org.nickgrealy.commons.convert.impl.StringConverter;
import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.util.MapUtil;
import org.nickgrealy.commons.util.NotNullableMap;
import org.nickgrealy.commons.convert.ConversionConstants;
import org.nickgrealy.commons.compare.ClassDepthComparator;
import org.nickgrealy.commons.exception.BeanBuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

import static org.nickgrealy.commons.validate.RuntimeAssert.check;

/**
 * Builds beans, not stupid.
 * <p/>
 * <b>N.B.</b> A valid BeanUtil and ConverterFactory will need to be injected.
 * For convenience, the default ConverterFactory uses a StringConverter.
 *
 * @author nickgrealy@gmail.com
 */
public class SmartBeanBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IBeanPropertyAccessor beanPropertyAccessor = BeanPropertyAccessor.INSTANCE;

    private ConverterFactory converterFactory = new ConverterFactory(Arrays.asList(StringConverter.INSTANCE));

    private List<? extends SmartBeanBuilderProcessor> processors = Arrays.asList(new BeanRelationshipsWeaver(converterFactory));

    public SmartBeanBuilder() {
    }

    /* getters / setters */

    public IBeanPropertyAccessor getBeanPropertyAccessor() {
        return beanPropertyAccessor;
    }

    public void setBeanPropertyAccessor(IBeanPropertyAccessor beanPropertyAccessor) {
        this.beanPropertyAccessor = beanPropertyAccessor;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public List<? extends SmartBeanBuilderProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<? extends SmartBeanBuilderProcessor> processors) {
        this.processors = processors;
    }

    /* logic */

    /**
     * Build beans from metaModels.
     *
     * @param metaModelsList
     * @return
     */
    public Map<Class<?>, List<?>> buildBeans(List<BeanMetaModel> metaModelsList) {
        // setup 
        check("metaModelsList", metaModelsList).isNotNull();
        for (BeanMetaModel beanMetaModel : metaModelsList) {
            beanMetaModel.validate();
        }
        Map<Class<?>, BeanMetaModel> metaModelsMap = MapUtil.mapByField(
                metaModelsList, BeanMetaModel.CLASS_VARIABLE,
                new NotNullableMap<Class<?>, BeanMetaModel>());
        List<Class<?>> buildOrder = new LinkedList<Class<?>>(metaModelsMap.keySet());
        Collections.sort(buildOrder, new ClassDepthComparator(buildOrder));
        Map<Class<?>, List<?>> beansByClassMap = new NotNullableMap<Class<?>, List<?>>();
        // pre-process meta models
        logger.info("Pre-processing beans...");
        for (SmartBeanBuilderProcessor processor : processors) {
            processor.preProcess(metaModelsMap);
        }
        // build logic
        logger.info("Building beans...");
        int beanCount = 0;
        for (Class<?> clazz : buildOrder) {
            if (beanPropertyAccessor.isInstantiable(clazz)) {
                BeanMetaModel base = metaModelsMap.get(clazz);
                buildBeans(base, metaModelsMap);
                beansByClassMap.put(clazz, Arrays.asList(base.getImplementedBeans()));
                beanCount += base.getImplementedBeans().length;
            }
        }
        logger.info("Post-processing beans...");
        // post-process meta models
        for (SmartBeanBuilderProcessor processor : processors) {
            processor.postProcess(metaModelsMap);
        }
        logger.info("Bean building complete. Loaded - #ofClasses='{}' #ofBeans='{}'", buildOrder.size(), beanCount);
        return beansByClassMap;
    }

    /* private */

    private void buildBeans(BeanMetaModel base, Map<Class<?>, BeanMetaModel> metaModelsMap) {
        // setup
        int size = base.getValues().size();
        for (int rowNum = 0; rowNum < size; rowNum++) {
            buildBean(null, rowNum, base, metaModelsMap);
        }
    }

    /**
     * Recursive method to build beans.<br/>
     * <b>Behaviour:</b>
     * <ul>
     * <li>checks if bean is already implemented.</li>
     * <li>ignores un-instantiable classes (i.e. abstract and interfaces).</li>
     * <li>constructs the 'bean' if it is null.</li>
     * <li>sets values on the bean object using the 'base' meta model.</li>
     * <li>if the field references a parent meta model (and the value matches a parent's value), it will import fields from the parent object.</li>
     * <li>relations are built by a preprocessor.</li>
     * </ul>
     * <b>N.B.</b> Multiple parent's can be referenced, but it's recommended to only reference one parent field per parent class.
     *
     * @param base
     * @param metaModelsMap
     * @return
     */
    private void buildBean(Object bean, int rowNum, BeanMetaModel base, Map<Class<?>, BeanMetaModel> metaModelsMap) {
        // setup
        Object fromValue = null;
        Class<?> targetClass = null;
        int colNum = 0;
        List<Object> values = base.getValues().get(rowNum);
        // if bean isn't already implemented...
        if (base.getImplementedBeans()[rowNum] == null) {
            // Create bean if it hasn't already...
            if (bean == null) {
                bean = beanPropertyAccessor.createBean(base.getClazz());
            }
            try {
                Iterator<Field> fieldsIter = base.getFields().iterator();
                Iterator<? extends Object> valuesIter = values.iterator();
                // Iterate over fields and values...
                while (fieldsIter.hasNext() && valuesIter.hasNext()) {
                    // setup
                    Field field = fieldsIter.next();
                    fromValue = valuesIter.next();
                    Class<?> fieldClass = field.getDeclaringClass();
                    // logic
                    if (!base.getClazz().equals(fieldClass) && metaModelsMap.containsKey(fieldClass)) {
                        // if field's class is not this class, import parent class values...
                        BeanMetaModel parentMetaModel = metaModelsMap.get(fieldClass);
                        int parentRowNum = parentMetaModel.getIndex(field, fromValue);
                        if (parentRowNum == BeanMetaModel.FIELD_VALUE_NOT_FOUND) {
                            if (!ConversionConstants.NULL.equals(fromValue)) {
                                throw new BeanException("Could not locate bean with the given field value! value=" + fromValue, field);
                            }
                        } else {
                            buildBean(bean, parentRowNum, parentMetaModel, metaModelsMap);
                        }
                    } else if (!base.getRelationships().keySet().contains(field)) {
                        targetClass = field.getType();
                        Object targetValue = converterFactory.convert(fromValue, targetClass);
                        beanPropertyAccessor.setProperty(bean, field, targetValue);
                    }
                    // N.B. Relations are build by the preprocessor...
                    colNum++;
                }
                base.getImplementedBeans()[rowNum] = bean;
            } catch (Throwable t) {
                throw new BeanBuilderException(rowNum, colNum, fromValue, targetClass, t);
            }
        } else {
            logger.debug("Bean is already implemented. class='{}' row='{}' bean='{}'", new Object[]{base.getClazz(), rowNum, base.getImplementedBeans()[rowNum]});
        }

    }
}
