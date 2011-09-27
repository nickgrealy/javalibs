package org.nickgrealy.commons.compare;

import org.nickgrealy.commons.exception.NotComparableException;

import static java.lang.String.*;

/**
 * Sorts classes, putting the deepest child classes first. Can be used on classes which are not "related".
 * 
 * @author nickgrealy@gmail.com
 */
public class InheritanceComparator extends AbstractDirectionalComparator<Class<?>> {

    public static final String NOT_RELATED_EXCEPTION_2 = "Classes are not related! class1='%s' class2='%s'";

    public InheritanceComparator(){
        super();
	}

    public InheritanceComparator(boolean deepestFirst){
        super(deepestFirst);
	}

	/**
	 * Compares class inheritance order.
	 */
	@Override
	public int compare(Class<?> arg0, Class<?> arg1) {
        if (arg0 != null && arg1 != null && !arg0.equals(arg1)){
            if (arg0.isAssignableFrom(arg1)){
                return getReturnVal0();
            } else if (arg1.isAssignableFrom(arg0)){
                return getReturnVal1();
            }   else {
                throw new NotComparableException(format(NOT_RELATED_EXCEPTION_2, arg0, arg1));
            }
        }
		return 0;
	}
	
	
	
}
