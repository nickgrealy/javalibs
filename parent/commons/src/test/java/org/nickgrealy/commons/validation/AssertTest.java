/**
 * 
 */
package org.nickgrealy.commons.validation;

import static org.nickgrealy.commons.validation.Assert.assertNoNullKeysOrValues;
import static org.nickgrealy.commons.validation.Assert.check;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nickgrealy.commons.exceptions.AssertionException;

/**
 * Methods: equalz, isNotNull, isNull, isTrue, isFalse, isInstanceOf,
 * assertNoNullKeysOrValues
 * 
 * @author nick.grealy
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

    @Ignore
    @Test
    public void isInstanceOf1() {
        // TODO
    }

    @Ignore
    @Test(expected = AssertionException.class)
    public void isInstanceOf2() {
        // TODO
    }

    /* isTrue */

    @Ignore
    @Test
    public void isTrue1() {
        // TODO
    }

    @Ignore
    @Test(expected = AssertionException.class)
    public void isTrue2() {
        // TODO
    }

    /* isFalse */

    @Ignore
    @Test
    public void isFalse1() {
        // TODO
    }

    @Ignore
    @Test(expected = AssertionException.class)
    public void isFalse2() {
        // TODO
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
