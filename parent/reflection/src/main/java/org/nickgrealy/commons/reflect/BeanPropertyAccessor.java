package org.nickgrealy.commons.reflect;

import org.nickgrealy.commons.exception.BeanException;
import org.nickgrealy.commons.exception.ReflectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.String.format;
import static org.nickgrealy.commons.exception.BeanException.DEFAULT_CONSTRUCTOR;

/**
 * The BeanUtil class facilitates:
 * <ul>
 * <li>creating an Object from a Class.</li>
 * <li>getting/setting fields on an Object.</li>
 * <li>copying fields between two Objects.</li>
 * </ul>
 *
 * @author nickgrealy@gmail.com
 */
public final class BeanPropertyAccessor implements IBeanPropertyAccessor {

    public static final IBeanPropertyAccessor INSTANCE = new BeanPropertyAccessor();

    private static final String PROPERTIES = "properties";
    private static final String TARGET = "target";
    private static final String FROM = "from";

	private static final int NOT_INSTANTIABLE_MODIFIERS = Modifier.ABSTRACT
			| Modifier.INTERFACE;

    private BeanPropertyAccessor(){
    }
    
	@Override
    public boolean isInstantiable(Class<?> clazz){
    	return (clazz.getModifiers() & NOT_INSTANTIABLE_MODIFIERS) == 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <I> I createBean(Class<I> clazz) {
        try {
            final Constructor<I> constructor = clazz.getDeclaredConstructor();
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
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        } catch (IllegalAccessException e) {
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        } catch (InvocationTargetException e) {
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        } catch (SecurityException e) {
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        } catch (NoSuchMethodException e) {
            throw new BeanException(clazz, DEFAULT_CONSTRUCTOR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <I> I createBean(Class<I> clazz, Map<String, Object> properties) {
        final I bean = createBean(clazz);
        copyProperties(bean, properties);
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperty(Object object, String field, Object value) {
            final Field field2 = getFieldRecursively(object.getClass(),field);
            setProperty(object, field2, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperty(Object object, Field field, Object value) {
        try {
            if (field.isAccessible()) {
                field.set(object, value);
            } else {
                field.setAccessible(true);
                field.set(object, value);
                field.setAccessible(false);
            }
        } catch (SecurityException e) {
            throw new BeanException(field, e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(field, e);
        } catch (IllegalAccessException e) {
            throw new BeanException(field, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getFieldRecursively(Class<?> clazz, String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getFieldRecursively(clazz.getSuperclass(), field);
            } else {
                throw new BeanException(clazz, field, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperty(Object object, String field) {
        final Field field2 = getFieldRecursively(object.getClass(), field);
        return getProperty(object, field2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperty(Object object, Field field) {
        try {
            Object returnVal;
            if (field.isAccessible()) {
                returnVal = field.get(object);
            } else {
                field.setAccessible(true);
                returnVal = field.get(object);
                field.setAccessible(false);
            }
            return returnVal;
        } catch (SecurityException e) {
            throw new BeanException(field, e);
        } catch (IllegalArgumentException e) {
            throw new BeanException(field, e);
        } catch (IllegalAccessException e) {
            throw new BeanException(field, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object to, Map<String, Object> properties) {
        assertNotNull(TARGET, to);
        assertNotNull(PROPERTIES, properties);
        for (Entry<String, Object> entry : properties.entrySet()) {
            setProperty(to, entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object from, Object to) {
        copyProperties(from, to, DEFAULT_CLASS_DEPTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object from, Object to, String... properties) {
        assertNotNull(FROM, from);
        assertNotNull(TARGET, to);
        assertNotNull(PROPERTIES, properties);
        for (String field : properties) {
            setProperty(to, field, getProperty(from, field));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object from, Object to, Map<String, String> properties) {
        assertNotNull(FROM, from);
        assertNotNull(TARGET, to);
        assertNotNull(PROPERTIES, properties);
        for (Entry<String, String> entry : properties.entrySet()) {
            setProperty(to, entry.getValue(), getProperty(from, entry.getKey()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object from, Object to, int maxClassLevel) {
        copyProperties(from, to, maxClassLevel, Modifier.FINAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyProperties(Object from, Object to, int maxClassLevel, int ignoreFieldsWithModifiers) {
        assertNotNull(FROM, from);
        assertNotNull(TARGET, to);
        if (!from.getClass().isAssignableFrom(to.getClass())) {
            throw new BeanException(format(
                    "'From' bean class must be assignable from the 'To' bean class! fromClass=%s toClass=%s",
                    from.getClass(), to.getClass()));
        }
        // Iterate up through parent classes, copying fields as we go...
        int currCLassLevel = 1;
        Class<?> tmp = from.getClass();
        final int ignoredModifiers = ignoreFieldsWithModifiers & Modifier.FINAL;
        while (currCLassLevel <= maxClassLevel && tmp != null && !tmp.equals(Object.class)) {
            final Field[] fields = tmp.getDeclaredFields();
            for (Field field : fields) {
                // Ignore fields if they have these modifiers...
                if ((field.getModifiers() & ignoredModifiers) == ignoredModifiers) {
                    continue;
                }
                // Do copy...
                setProperty(to, field, getProperty(from, field));
            }
            tmp = tmp.getSuperclass();
            ++currCLassLevel;
        }
    }

    private void assertNotNull(String field, Object object){
        if (object == null){
            throw new ReflectionException(field);
        }

    }
}
