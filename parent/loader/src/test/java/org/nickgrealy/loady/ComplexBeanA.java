package org.nickgrealy.loady;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author nickgrealy@gmail.com
 */
public class ComplexBeanA {

    Long id;
    String name;
    ComplexBeanA simple;
    ComplexBeanA simple2;
    Collection<ComplexBeanA> collection;
    List<ComplexBeanA> list;
    Set<ComplexBeanA> set;
}