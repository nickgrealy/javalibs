package org.nickgrealy.loader;

/**
 * @author nickgrealy@gmail.com
 */
public class ExtendsAbstractBaseBean extends AbstractBaseBean {

    String extendsField;

    
    
	public ExtendsAbstractBaseBean() {
		super();
	}



	public ExtendsAbstractBaseBean(String linkedField, String baseField,
			String extendsField) {
		super(linkedField, baseField);
		this.extendsField = extendsField;
	}
    
    
}


