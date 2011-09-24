package org.nickgrealy.conversion.reflect;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
