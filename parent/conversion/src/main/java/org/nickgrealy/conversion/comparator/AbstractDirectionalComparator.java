package org.nickgrealy.conversion.comparator;

import java.util.Comparator;

/**
 * @author nickgrealy@gmail.com
 */
public abstract class AbstractDirectionalComparator<X> implements Comparator<X> {

    private int returnVal0 = -1;
    private int returnVal1 = 1;

    /**
     * Sorts forwards by default.
     */
    public AbstractDirectionalComparator(){
        // NOOP
    }

    public AbstractDirectionalComparator(boolean orderForwards){
        // Setup if non-default ordering...
        if (!orderForwards){
            returnVal0 = 1;
            returnVal1 = -1;
        }
    }

    public int getReturnVal0() {
        return returnVal0;
    }

    public int getReturnVal1() {
        return returnVal1;
    }
}
