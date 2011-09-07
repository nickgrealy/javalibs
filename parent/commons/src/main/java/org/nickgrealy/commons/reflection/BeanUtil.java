package org.nickgrealy.commons.reflection;

import static org.nickgrealy.commons.validation.Assert.check;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.nickgrealy.commons.exceptions.BeanException;

/**
 * The BeanUtil class facilitates:
 * <ul>
 * <li>creating an Object from a Class.</li>
 * <li>getting/setting fields on an Object.</li>
 * <li>copying fields between two Objects.</li>
 * </ul>
 * 
 * @author nick.grealy
 */
public final class BeanUtil implements IBeanUtil {

    private static final String PROPERTIES = "properties";
    private static final String TO = "to";
    private static final String FROM = "from";

    /** {@inheritDoc} */
    @Override
    public <I> I createBean(Class<I> clazz) {
        try {
            Constructor<I> constructor = clazz.getDeclaredConstructor();
            I returnVal;
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
                returnVal = constructor.newInstance();
                constructor.setAccessible(false);
            } else {
                returnVal = constructor.newInstance();
            }
            return returnVal;
        } catch (InstantiationException e) {
            throw new BeanException(e);
        } catch (IllegalAccessException e) {
            throw new BeanException(e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(e);
        } catch (InvocationTargetException e) {
            throw new BeanException(e);
        } catch (SecurityException e) {
            throw new BeanException(e);
        } catch (NoSuchMethodException e) {
            throw new BeanException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public <I> I createBean(Class<I> clazz, Map<String, Object> properties) {
        final I bean = createBean(clazz);
        copyProperties(bean, properties);
        return bean;
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty(Object object, String field, Object value) {
        try {
            final Field field2 = object.getClass().getDeclaredField(field);
            if (field2.isAccessible()) {
                field2.set(object, value);
            } else {
                field2.setAccessible(true);
                field2.set(object, value);
                field2.setAccessible(false);
            }
        } catch (SecurityException e) {
            throw new BeanException(e);
        } catch (NoSuchFieldException e) {
            throw new BeanException(e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(e);
        } catch (IllegalAccessException e) {
            throw new BeanException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object getProperty(Object object, String field) {
        try {
            final Field field2 = object.getClass().getDeclaredField(field);
            Object returnVal;
            if (field2.isAccessible()) {
                returnVal = field2.get(object);
            } else {
                field2.setAccessible(true);
                returnVal = field2.get(object);
                field2.setAccessible(false);
            }
            return returnVal;
        } catch (SecurityException e) {
            throw new BeanException(e);
        } catch (NoSuchFieldException e) {
            throw new BeanException(e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(e);
        } catch (IllegalAccessException e) {
            throw new BeanException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void copyProperties(Object object, Map<String, Object> properties) {
        check("object", object).isNotNull();
        check(PROPERTIES, properties).isNotNull();
        for (Entry<String, Object> entry : properties.entrySet()) {
            setProperty(object, entry.getKey(), entry.getValue());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void copyProperties(Object from, Object to) {
        check(FROM, from).isNotNull();
        check(TO, to).isNotNull();
        if (!from.getClass().equals(to.getClass())) {
            throw new BeanException("From bean class must match To bean class!");
        }
        // TODO Implement
        throw new org.nickgrealy.commons.exceptions.NotYetImplemented();
    }

    /** {@inheritDoc} */
    @Override
    public void copyProperties(Object from, Object to, String... properties) {
        check(FROM, from).isNotNull();
        check(TO, to).isNotNull();
        check(PROPERTIES, properties).isNotNull();
        for (String field : properties) {
            setProperty(to, field, getProperty(from, field));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void copyProperties(Object from, Object to, Map<String, String> properties) {
        check(FROM, from).isNotNull();
        check(TO, to).isNotNull();
        check(PROPERTIES, properties).isNotNull();
        for (Entry<String, String> entry : properties.entrySet()) {
            setProperty(to, entry.getValue(), getProperty(from, entry.getKey()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void copyProperties(Object from, Object to, int classLevel) {
        check(FROM, from).isNotNull();
        check(TO, to).isNotNull();
        if (!from.getClass().equals(to.getClass())) {
            throw new BeanException("From bean class must match To bean class!");
        }
        // TODO Implement
        throw new org.nickgrealy.commons.exceptions.NotYetImplemented();
    }

}
