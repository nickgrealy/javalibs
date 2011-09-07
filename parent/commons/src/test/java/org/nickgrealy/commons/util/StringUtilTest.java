/**
 * 
 */
package org.nickgrealy.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.nickgrealy.commons.exceptions.AssertionException;

/**
 * 
 * @author nick.grealy
 */
public class StringUtilTest {

    private final StringUtil stringUtil = new StringUtil();

    private final Object[] values1 = { "1", 2, 3L };
    @SuppressWarnings("unchecked")
    private final List<?> values2 = Arrays.asList("1", 2, 3L);
    private final Object[] values3 = { "1", null, 3L };

    @Test
    public void toString1() {
        assertEquals("[1, 2, 3]", stringUtil.toString(values1));
    }

    @Test
    public void toString2() {
        assertEquals("[1, 2, 3]", stringUtil.toString(values2));
    }

    @Test
    public void toString3() {
        assertEquals("[1, null, 3]", stringUtil.toString(values3));
    }

    @Test(expected = AssertionException.class)
    public void toString4() {
        stringUtil.toString((Object[]) null);
    }
}
