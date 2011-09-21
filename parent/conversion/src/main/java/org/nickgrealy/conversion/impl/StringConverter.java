package org.nickgrealy.conversion.impl;

import org.nickgrealy.commons.exception.NotYetImplementedException;
import org.nickgrealy.conversion.AbstractBaseConverter;
import org.nickgrealy.conversion.IConverter;
import org.nickgrealy.conversion.SpecialTargetTypes.AnyTarget;
import org.nickgrealy.conversion.SpecialTargetTypes.Array;
import org.nickgrealy.conversion.exception.NotConvertableException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.*;

import static java.lang.String.format;

public final class StringConverter extends AbstractBaseConverter<String> {

    private static final String MAP_KEYVALUE_DELIMITER = ">";
    private static final String COLLECTION_DELIMITER = ",";

    public StringConverter() {
        super(Byte.class, Short.class, Integer.class, Long.class, Float.class,
                Double.class, Boolean.class, Character.class, Enum.class,
                Array.class, Collection.class, List.class, Set.class,
                Map.class, File.class, URI.class, Date.class, DateFormat.class,
                AnyTarget.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> A doConversion(String fromObject, Class<A> targetClass) {
        Object returnVal = null;
        if (isAssignableClass(Byte.class, targetClass)) {
            returnVal = new Byte(fromObject);
        } else if (isAssignableClass(Short.class, targetClass)) {
            returnVal = new Short(fromObject);
        } else if (isAssignableClass(Integer.class, targetClass)) {
            returnVal = new Integer(fromObject);
        } else if (isAssignableClass(Long.class, targetClass)) {
            returnVal = new Long(fromObject);
        } else if (isAssignableClass(Float.class, targetClass)) {
            returnVal = new Float(fromObject);
        } else if (isAssignableClass(Double.class, targetClass)) {
            returnVal = new Double(fromObject);
        } else if (isAssignableClass(Boolean.class, targetClass)) {
            returnVal = new Boolean(fromObject);
        } else if (isAssignableClass(Character.class, targetClass)) {
            if (fromObject.length() != 0) {
                returnVal = new Character(fromObject.charAt(0));
            }
        } else if (isAssignableClass(Enum.class, targetClass)) {
            returnVal = Enum.valueOf((Class<? extends Enum>) targetClass,
                    fromObject);
        } else if (isAssignableClass(Array.class, targetClass)) {
            returnVal = fromObject.split(COLLECTION_DELIMITER);
        } else if (isAssignableClass(Set.class, targetClass)) {
            returnVal = new HashSet(Arrays.asList(fromObject
                    .split(COLLECTION_DELIMITER)));
        } else if (isAssignableClass(Collection.class, targetClass)
                || isAssignableClass(List.class, targetClass)) {
            // e.g. "1,2,3,4"
            returnVal = Arrays.asList(fromObject.split(COLLECTION_DELIMITER));
        } else if (isAssignableClass(Map.class, targetClass)) {
            // e.g. "A>1,B>2"
            String[] entries = fromObject.split(COLLECTION_DELIMITER);
            Map<String, String> map = new HashMap<String, String>();
            for (String entry : entries) {
                String[] keyVal = entry.split(MAP_KEYVALUE_DELIMITER);
                if (keyVal.length == 2) {
                    map.put(keyVal[0], keyVal[1]);
                } else {
                    Class<? extends IConverter<?>> clazz = getClass();
                    throw new NotConvertableException("KEY>VALUE", fromObject,
                            clazz);
                }
            }
            returnVal = map;
        } else if (isAssignableClass(File.class, targetClass)) {
            returnVal = new File(fromObject);
        } else if (isAssignableClass(URI.class, targetClass)) {
            try {
                returnVal = new URI(fromObject);
            } catch (URISyntaxException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            }
        } else if (hasTargetClass(targetClass)
                && !AnyTarget.class.equals(targetClass)) {
            throw new NotYetImplementedException(format(UNIMPLEMENTED_CLASS_2,
                    fromObject, targetClass));
        } else {
            // handle AnyTarget, attempt to build object using constructor...
            try {
                Constructor<A> constructor = targetClass
                        .getDeclaredConstructor(String.class);
                returnVal = constructor.newInstance(fromObject);
            } catch (SecurityException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            } catch (NoSuchMethodException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            } catch (IllegalArgumentException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            } catch (InstantiationException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            } catch (IllegalAccessException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            } catch (InvocationTargetException e) {
                throw new NotConvertableException(targetClass.toString(),
                        fromObject, getClass(), e);
            }
        }
        return (A) returnVal;
    }

    @Override
    public Class<String> getBaseClass() {
        return String.class;
    }

}
