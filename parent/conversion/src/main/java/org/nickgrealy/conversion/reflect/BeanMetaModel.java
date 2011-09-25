package org.nickgrealy.conversion.reflect;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.validation.Validatable;

/**
 * Immutable meta model for a bean object.
 * 
 * @author nickgrealy@gmail.com s
 */
public class BeanMetaModel implements Validatable {

	public static final String CLASS_VARIABLE = "clazz";

	private Class<?> clazz;
	private List<Field> fields;
	private Map<Field, Field> relationships;
	private List<List<Object>> values;
	private Map<Integer, Object> alreadyImplementedBeans = new HashMap<Integer, Object>();

	public BeanMetaModel(Class<?> clazz, List<Field> fields,
			Map<Field, Field> relationships, List<List<Object>> values) {
		super();
		this.clazz = clazz;
		this.fields = fields;
		this.relationships = relationships;
		this.values = values;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<List<Object>> getValues() {
		return values;
	}

	public Map<Field, Field> getRelationships() {
		return relationships;
	}
	
	/* Utility methods */
	
	public void addImplementedBeanOverride(int index, Object bean){
		alreadyImplementedBeans.put(index, bean);
	}
	
	public int getIndex(Field critField, Object critValue){
		// validation
		check("critValue", critValue).isNotNull();
		check("critField", critField).isNotNull();
		if (!hasField(critField)){
			throw new BeanException("Field does not exist in MetaModel!", critField);
		}
		// logic
		int fieldIndex = fields.indexOf(critField);
		int index = 0;
		for (List<Object> valuesList : values) {
			if(critValue.equals(valuesList.get(fieldIndex))){
				return index;
			}
			index++;
		}
		throw new BeanException("Could not locate bean with the given field value! value=" + critValue, critField);
	}
	
	public boolean hasField(Field critField){
		return fields.contains(critField);
	}
	
	/** {@inheritDoc} */
	@Override
	public void validate() {
		check("clazz", clazz).isNotNull();
		check("fields", fields).isNotNull();
		check("relationships", relationships).isNotNull();
		check("values", values).isNotNull();
		// check fields.size() = values.size()...
		int fieldsSize = fields.size();
		for (List<? extends Object> valueList : values) {
			check("valueList.size() == fieldsSize", valueList.size() == fieldsSize).isTrue();
		}
	}

}
