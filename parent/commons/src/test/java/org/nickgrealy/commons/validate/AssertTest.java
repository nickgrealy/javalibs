/**
 * 
 */
package org.nickgrealy.commons.validate;

import static org.nickgrealy.commons.validate.RuntimeAssert.assertNoNullKeysOrValues;
import static org.nickgrealy.commons.validate.RuntimeAssert.check;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nickgrealy.commons.exception.AssertionException;

/**
 * Methods: equalz, isNotNull, isNull, isTrue, isFalse, isInstanceOf,
 * assertNoNullKeysOrValues
 * 
 * @author nickgrealy@gmail.com
 */
public class AssertTest {

    private static final String TEST = "Test";

    private Map<Object, Object> map;

    @Before
    public void setUp() {
        map = new HashMap<Object, Object>();
    }

    /* equalz */

    @Test
    public void equalz1() {
        check(TEST, null).equalz(null);
    }

    @Test(expected = AssertionException.class)
    public void equalz2() {
        check(TEST, TEST).equalz(null);
    }

    @Test(expected = AssertionException.class)
    public void equalz3() {
        check(TEST, null).equalz(TEST);
    }

    @Test
    public void equalz4() {
        check(TEST, TEST).equalz(TEST);
    }

    /* isNotNull */

    @Test(expected = AssertionException.class)
    public void isNotNull1() {
        check(TEST, null).isNotNull();
    }

    @Test
    public void isNotNull2() {
        check(TEST, TEST).isNotNull();
    }

    /* isNull */

    @Test
    public void isNull1() {
        check(TEST, null).isNull();
    }

    @Test(expected = AssertionException.class)
    public void isNull2() {
        check(TEST, TEST).isNull();
    }

    /* isInstanceOf */

    @Test
    public void isInstanceOf1() {
        check(TEST, 12).isInstanceOf(String.class, Integer.class, Long.class);
    }

    @Test
    public void isInstanceOf2() {
        check(TEST, 12).isInstanceOf(Integer.class, Long.class);
    }

    @Test(expected = AssertionException.class)
    public void isInstanceOf3() {
        check(TEST, 12).isInstanceOf(String.class, Long.class);
    }

    /* isTrue */

    @Test
    public void isTrue1() {
        check(TEST, true).isTrue();
    }

    @Test(expected = AssertionException.class)
    public void isTrue2() {
        check(TEST, false).isTrue();
    }

    /* isFalse */

    @Test
    public void isFalse1() {
        check(TEST, false).isFalse();
    }

    @Test(expected = AssertionException.class)
    public void isFalse2() {
        check(TEST, true).isFalse();
    }

    /* isNotPrimitive */

    @Test
    public void isNotPrimitive1() {
        check(TEST, Integer.class).isNotPrimitive();
    }

    @Test(expected = AssertionException.class)
    public void isNotPrimitive2() {
        check(TEST, int.class).isNotPrimitive();
    }

    /* gt */

    @Test
    public void isGt1() {
        check(TEST, 5).isGt(3);
        check(TEST, 5L).isGt(3);
        check(TEST, 5f).isGt(3);
        check(TEST, 5.00).isGt(3);
    }

    @Test(expected = AssertionException.class)
    public void isGt2() {
        check(TEST, 3f).isGt(5);
    }

    @Test(expected = AssertionException.class)
    public void isGt3() {
        check(TEST, "blah").isGt(5);
    }

    /* assertNoNullKeysOrValues */

    @Test(expected = AssertionException.class)
    public void assertNoNullKeysOrValues1() {
        map.put(null, null);
        assertNoNullKeysOrValues(map);
    }

    @Test(expected = AssertionException.class)
    public void assertNoNullKeysOrValues2() {
        map.put(TEST, null);
        assertNoNullKeysOrValues(map);
    }

    @Test(expected = AssertionException.class)
    public void assertNoNullKeysOrValues3() {
        map.put(null, TEST);
        assertNoNullKeysOrValues(map);
    }

    @Test
    public void assertNoNullKeysOrValues4() {
        map.put(TEST, TEST);
        assertNoNullKeysOrValues(map);
    }

}
