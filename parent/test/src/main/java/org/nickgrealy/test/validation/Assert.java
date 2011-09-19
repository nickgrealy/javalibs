package org.nickgrealy.test.validation;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Assert methods.
 * 
 * @author nick.grealy
 */
public final class Assert {

    private static final String FIELD_AND_EXPECTED_2 = "field='%s', expected='%s'";

	
	private static final Comparator<? super Entry<Object, ?>> INSTANCE = new EntryComparator<Object, Entry<Object, ?>>();

    private Assert() {
    }

    /**
     * Advanced logic on assertEquals.
     * (No, org.junit.Assert.assertEquals() doesn't do this for some reason.)
     * 
     * @param message
     * @param expected
     * @param actual
     */
	@SuppressWarnings("unchecked")
    public static void assertEquals(String message, Object expected, Object actual) {
        if (assertIfNotNull(message, expected, actual)) {
            if (expected.getClass().isArray() && actual.getClass().isArray()) {
                org.junit.Assert.assertArrayEquals(message, (Object[]) expected, (Object[]) actual);
            } else if (expected instanceof Collection<?> && actual instanceof Collection<?>) {
				assertCollectionsEquals((Collection<?>)expected, (Collection<?>)actual);
            } else if (expected instanceof Map<?, ?> && actual instanceof Map<?, ?>){
            	List<Entry<Object, ?>> expEntrySet = new ArrayList<Entry<Object, ?>>(((Map<Object, ?>)expected).entrySet());
            	List<Entry<Object, ?>> actEntrySet = new ArrayList<Entry<Object, ?>>(((Map<Object, ?>)actual).entrySet());
				Collections.sort(expEntrySet, INSTANCE);
				Collections.sort(actEntrySet, INSTANCE);
            	assertCollectionsEquals(expEntrySet, actEntrySet);
            } else {
                org.junit.Assert.assertEquals(message, expected, actual);
            }
        }
    }
    
    static class EntryComparator<Key, Gen extends Entry<Key, ?>> implements Comparator<Gen>{

		@SuppressWarnings("unchecked")
		@Override
		public int compare(Gen arg0, Gen arg1) {
			if (arg0 == null || arg0.getKey() == null || arg1 == null || arg1.getKey() == null
					|| !(arg0.getKey() instanceof Comparable<?>) || !(arg1.getKey() instanceof Comparable<?>)){
				return 0;
			}
			return ((Comparable<Key>)arg0.getKey()).compareTo((Key)arg1.getKey());
		}
    	
    }

	private static void assertCollectionsEquals(Collection<?> expColl,
			Collection<?> actColl) {
		org.junit.Assert.assertArrayEquals(
				expColl.toArray(new Object[expColl.size()]),
		        actColl.toArray(new Object[actColl.size()]));
	}

    /**
     * Like assertNull and assertNotNull, except it also returns a boolean based on both values 
     * equalling null, or not null.
     * 
     * @param expected
     *            Object
     * @param actual
     *            Object
     * @return true if expected and actual are not null, otherwise false if both
     *         are null.
     */
    public static boolean assertIfNotNull(String field, Object expected, Object actual) {
        String message = format(FIELD_AND_EXPECTED_2, field, expected);
        if (expected == null) {
            assertNull(message, actual);
            return false;
        } else {
            assertNotNull(message, actual);
            return true;
        }
    }
}
