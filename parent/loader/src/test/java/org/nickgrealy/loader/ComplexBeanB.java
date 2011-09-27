package org.nickgrealy.loader;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author nickgrealy@gmail.com
 */
public class ComplexBeanB {

    Long id;
    String name;
    ComplexBeanA simple;
    ComplexBeanA simple2;
    Collection<ComplexBeanA> collection;
    List<ComplexBeanA> list;
    Set<ComplexBeanA> set;

    public ComplexBeanB() {
    }

    public ComplexBeanB(Long id, String name, ComplexBeanA simple, ComplexBeanA simple2, Collection<ComplexBeanA> collection, List<ComplexBeanA> list, Set<ComplexBeanA> set) {
        this.id = id;
        this.name = name;
        this.simple = simple;
        this.simple2 = simple2;
        this.collection = collection;
        this.list = list;
        this.set = set;
    }

    public String toString(){
        return id + " - " + name;
    }
}
