package org.nickgrealy.test.validation;

import java.util.Comparator;
import java.util.Map;

/**
 * @author nickgrealy@gmail.com
 */
public class EntryComparator<Key, Gen extends Map.Entry<Key, ?>> implements Comparator<Gen> {

    public static final EntryComparator<Object, Map.Entry<Object, ?>> INSTANCE = new EntryComparator<Object, Map.Entry<Object, ?>>();

    @SuppressWarnings("unchecked")
    @Override
    public int compare(Gen arg0, Gen arg1) {
        if (arg0 == null || arg0.getKey() == null || arg1 == null || arg1.getKey() == null
                || !(arg0.getKey() instanceof Comparable<?>) || !(arg1.getKey() instanceof Comparable<?>)) {
            return 0;
        }
        return ((Comparable<Key>) arg0.getKey()).compareTo((Key) arg1.getKey());
    }
}
