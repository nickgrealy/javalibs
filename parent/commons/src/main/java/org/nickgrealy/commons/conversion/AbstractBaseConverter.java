/**
 * 
 */
package org.nickgrealy.commons.conversion;

import static java.lang.String.format;
import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import org.nickgrealy.commons.exception.UnhandledException;
import org.nickgrealy.commons.util.ClassUtil;

/**
 * Adds base functionality to the IConverter.
 * 
 * <b>N.B.</b> If an object's {@link #toString()} method returns the literal
 * "&lt;null&gt;", the object will be converted to null.
 * 
 * @author nick.grealy
 * @param <X>
 *            Base object type. For primitives, use the respective Object (e.g.
 *            int, use Integer).
 */
public abstract class AbstractBaseConverter<X> implements IConverter<X> {

    /**
     * The value which represents 'null'.
     */
    public static final String NULL = "<null>";

    protected static final String UNHANDLED_CLASS_2 = "Target class is not handled! value='%s', targetClass='%s'";
    protected static final String UNIMPLEMENTED_CLASS_2 = "Target class conversion is not yet implemented! "
            + "value='%s', targetClass='%s'";

    private static final String BASE_CLASS = "baseClass";
    private static final String TARGET_CLASS = "targetClass";

    private final ClassUtil classUtil = new ClassUtil();

    private final Set<Class<?>> targetAssignableClasses = new HashSet<Class<?>>();

    /**
     * Creates a converter, with at least one target class.
     * 
     * @param targetClass
     * @param targetClasses
     */
    public AbstractBaseConverter(Class<?> targetClass, Class<?>... targetClasses) {
        final Class<X> baseClass = getBaseClass();
        check(BASE_CLASS, baseClass).isNotNull().isNotPrimitive();
        check(TARGET_CLASS, targetClass).isNotNull().isNotPrimitive();
        this.targetAssignableClasses.add(Object.class);
        this.targetAssignableClasses.add(baseClass);
        this.targetAssignableClasses.add(targetClass);
        for (Class<?> class1 : targetClasses) {
            check(TARGET_CLASS, targetClass).isNotNull().isNotPrimitive();
            this.targetAssignableClasses.add(class1);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Set<Class<?>> getTargetClasses() {
        return targetAssignableClasses;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public final <A> A convert(X fromObject, Class<A> targetClass) {
        if (fromObject == null || targetClass == null) {
            return null;
        }
        // ensure we're NOT dealing with primitives...
        final Class<A> nonPrimClass = (Class<A>) classUtil.convertPrimitiveToObjectClass(targetClass);
        if (!hasTargetClass(nonPrimClass)) {
            throw new UnhandledException(format(UNHANDLED_CLASS_2, fromObject, nonPrimClass));
        }
        Object returnVal = null;
        if (NULL.equals(fromObject)) {
            returnVal = null;
        } else if (getBaseClass().equals(nonPrimClass) || Object.class.equals(nonPrimClass)) {
            returnVal = fromObject;
        } else {
            returnVal = doConversion(fromObject, nonPrimClass);
        }
        return (A) returnVal;
    }

    /**
     * Determines if the given class is a target class.
     * 
     * @param clazz
     *            Class<?>
     * @return true if class is a target class.
     */
    public boolean hasTargetClass(Class<?> clazz) {
        Class<?> assignableClass = getAssignableClass(clazz);
        return getTargetClasses().contains(assignableClass);
    }

    public boolean isAssignableClass(Class<?> expected, Class<?> actual) {
        return expected.equals(getAssignableClass(actual));
    }

    public static Class<?> getAssignableClass(Class<?> clazz) {
        Class<?> assignableClass = clazz;
        if (Enum.class.isAssignableFrom(clazz)) {
            assignableClass = Enum.class;
        } else if (clazz.isArray()) {
            assignableClass = Array.class;
        }
        return assignableClass;
    }

    /**
     * Invoked from the convert method. The boilerplate "checks" for:
     * <ul>
     * <li>if (!hasTargetClass(targetClass)) then throw UnhandledException</li>
     * <li>if (fromObject || targetClass == null) then return null</li>
     * <li>if (targetClass = Object.class || X.class) then return fromObject</li>
     * </ul>
     * have already been performed.
     * 
     * @param fromObject
     *            will never be null.
     * @param targetClass
     *            will never be null.
     * @return converted object.
     */
    protected abstract <A> A doConversion(X fromObject, Class<A> targetClass);

}
