package org.nickgrealy.commons.reflect;

import static org.nickgrealy.commons.validate.RuntimeAssert.check;

import java.lang.reflect.Field;
import java.util.*;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.validate.Validatable;

/**
 * Immutable meta model for a bean object.
 * 
 * @author nickgrealy@gmail.com s
 */
public class BeanMetaModel implements Validatable {

    public static final int FIELD_VALUE_NOT_FOUND = -1;

	public static final String CLASS_VARIABLE = "clazz";

	private Class<?> clazz;
	private List<Field> fields;
	private Map<Field, String> relationships;
	private List<List<Object>> values;
	private Object[] implementedBeans;

	public BeanMetaModel(Class<?> clazz, List<Field> fields,
			Map<Field, String> relationships, List<List<Object>> values) {
		super();
		this.clazz = clazz;
		this.fields = fields;
		this.relationships = relationships;
		this.values = values;
        implementedBeans = new Object[values.size()];
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

	public Map<Field, String> getRelationships() {
		return relationships;
	}

    public Object[] getImplementedBeans() {
        return implementedBeans;
    }

    /* Utility methods */

    /**
     *
     * @param critField
     * @param critValue
     * @return index of the record. {@link #FIELD_VALUE_NOT_FOUND} if matching record not found.
     */
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
        return FIELD_VALUE_NOT_FOUND;
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
