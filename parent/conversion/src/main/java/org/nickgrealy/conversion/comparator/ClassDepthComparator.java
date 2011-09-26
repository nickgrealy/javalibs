package org.nickgrealy.conversion.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sorts classes, putting the deepest child classes first. Can be used on classes which are not "related".
 * 
 * @author nickgrealy@gmail.com
 */
public class ClassDepthComparator extends AbstractDirectionalComparator<Class<?>> {
	
	private Map<Class<?>, Integer> classDepth = new HashMap<Class<?>, Integer>();

	public ClassDepthComparator(List<Class<?>> classes){
        this(true, classes);
    }
	
	public ClassDepthComparator(boolean deepestFirst, List<Class<?>> classes){
        super(deepestFirst);
		// Cache the "depth" of each class...
		for (Class<?> class1 : classes) {
			int depth = 0;
			Class<?> tmp = class1;
			while ((tmp = tmp.getSuperclass()) != null){
				depth++;
			}
			classDepth.put(class1, depth);
		}
	}
	
	
	/**
	 * Compares class "depths". Didn't use isAssignable(), as non related classes cause issues with sorting.
	 */
	@Override
	public int compare(Class<?> arg0, Class<?> arg1) {
		Integer depth0 = classDepth.get(arg0);
		Integer depth1 = classDepth.get(arg1);
		if (depth0 != null && depth1 != null){
			if (depth0 > depth1){
				return getReturnVal0();
			} else if (depth0 < depth1) {
				return getReturnVal1();
			}
		}
		return 0;
	}
	
	
	
}
