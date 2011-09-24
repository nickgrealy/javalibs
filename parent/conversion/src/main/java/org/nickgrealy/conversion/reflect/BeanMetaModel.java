package org.nickgrealy.conversion.reflect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Immutable meta model for a bean object.
 * 
 * @author nickgrealy@gmail.com
s */
public class BeanMetaModel {

	private Class<?> clazz;
	private List<Field> fields;
	private Map<Field, Field> relationships;
	private List<List<? extends Object>> values;


	public BeanMetaModel(Class<?> clazz, List<Field> fields,
			Map<Field, Field> relationships, List<List<? extends Object>> values) {
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

	public List<List<? extends Object>> getValues() {
		return values;
	}

	public Map<Field, Field> getRelationships() {
		return relationships;
	}

	public boolean isRelationshipField(Field field) {
		return relationships.containsKey(field);
	}

}
