package org.nickgrealy.loader;

/**
 * @author nickgrealy@gmail.com
 */
public abstract class AbstractBaseBean {

    String linkedField;
    String baseField;


    public AbstractBaseBean() {
        super();
    }


    public AbstractBaseBean(String linkedField, String baseField) {
        super();
        this.linkedField = linkedField;
        this.baseField = baseField;
    }


}
