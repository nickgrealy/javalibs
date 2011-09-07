package org.nickgrealy.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A TwoDimensionalMap. Suggested uses include:
 * - loading data from csv file.
 * - storing converter methods.
 */
public class TwoDimensionalMap<A, B, V> extends HashMap<A, Map<B, V>> {

	private static final long serialVersionUID = -2776843647697897210L;

	public void put(A a, B b, V v){
		Map<B, V> map = get(a);
		if (map == null){
			map = new HashMap<B, V>();
			put(a, map);
		}	
		map.put(b, v);
	}

	public void put(A a, List<B> b1, V v){
		Map<B, V> map = get(a);
		if (map == null){
			map = new HashMap<B, V>();
			put(a, map);
		}	
		for (B b : b1) {
			map.put(b, v);
		}
	}

	public V get(A a, B b){
		Map<B, V> map = get(a);
		if (map != null){
			return map.get(b);
		}
		return null;
	}

}
