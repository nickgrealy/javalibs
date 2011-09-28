package org.nickgrealy.loader;

/**
 * @author nickgrealy@gmail.com
 */
public class ExtendsBaseBean extends BaseBean {

    String extendsField;


    public ExtendsBaseBean() {
        super();
    }


    public ExtendsBaseBean(String linkedField, String baseField,
                           String extendsField) {
        super(linkedField, baseField);
        this.extendsField = extendsField;
    }


}
