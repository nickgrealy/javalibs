/**
 * 
 */
package org.nickgrealy.testy.data;

/**
 * Generates test data for the following scenarios.
 * <ul>
 * <li>primitives</li>
 * <li>Objects</li>
 * <li>Enums</li>
 * <li>Ids</li>
 * <li>collections/arrays</li>
 * <li>randomised values</li>
 * <li>boundary testing</li>
 * <li>generated test data w override supplied fields (see BeanUtil)</li>
 * <li>generated test data w generated id</li>
 * </ul>
 * 
 * @author nick.grealy
 */
public final class TestDataGenerator {

    private static long generatedId;

    private TestDataGenerator() {
    }

    public static long getId() {
        return ++generatedId;
    }

    // TODO Implement
}
