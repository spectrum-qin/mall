package com.spectrum.mall.utils.bean.test;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Objects {
    private static final ConcurrentHashMap<Class<?>, BeanCopier> BEAN_COPIERS = new ConcurrentHashMap();
    private static final Set<Class<?>> PRIMITIVES_AND_WRAPPERS = new HashSet();
    private static final Set<Class<?>> IMMUTABLE_CLASSES;

    public Objects() {
    }

    public static final <T> boolean isEquals(T lhs, T rhs) {
        if (lhs == null && rhs == null) {
            return true;
        } else {
            return lhs != null && rhs != null ? lhs.equals(rhs) : false;
        }
    }

    public static boolean isArray(Object array) {
        return array == null ? false : array.getClass().isArray();
    }

    public static boolean isCloneable(Object obj) {
        if (obj == null) {
            return false;
        } else {
            Class<?> clazz = obj.getClass();
            if (!Cloneable.class.isAssignableFrom(obj.getClass())) {
                return false;
            } else {
                Method method = Reflections.findPublicMethod(clazz, "clone", new Class[0]);
                return method != null;
            }
        }
    }

    public static boolean isPrimitiveOrWrapper(Object obj) {
        return obj == null ? false : PRIMITIVES_AND_WRAPPERS.contains(obj.getClass());
    }

    public static boolean isArrayOfPrimitives(Object array) {
        if (array == null) {
            return false;
        } else {
            Class<?> clazz = array.getClass();
            return clazz.isArray() && clazz.getComponentType().isPrimitive();
        }
    }

    public static boolean isArrayOfPrimitivesOrWrappers(Object array) {
        if (array == null) {
            return false;
        } else {
            Class<?> clazz = array.getClass();
            return clazz.isArray() && PRIMITIVES_AND_WRAPPERS.contains(clazz);
        }
    }

    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz != null && clazz.isPrimitive()) {
            if (Void.TYPE == clazz) {
                return null;
            } else if (Boolean.TYPE == clazz) {
                return Boolean.FALSE;
            } else if (Byte.TYPE == clazz) {
                return 0;
            } else if (Short.TYPE == clazz) {
                return Short.valueOf((short)0);
            } else if (Integer.TYPE == clazz) {
                return 0;
            } else if (Long.TYPE == clazz) {
                return 0L;
            } else if (Character.TYPE == clazz) {
                return '\u0000';
            } else if (Double.TYPE == clazz) {
                return 0.0D;
            } else if (Float.TYPE == clazz) {
                return 0.0F;
            } else {
                throw new RuntimeException("assertion failed, should not reach here, clazz: " + clazz);
            }
        } else {
            return null;
        }
    }

    public static Object copy(Object obj) {
        if (obj == null) {
            return null;
        } else {
            Class<?> clazz = obj.getClass();
            if (isPrimitiveOrWrapper(obj)) {
                return obj;
            } else if (IMMUTABLE_CLASSES.contains(clazz)) {
                return obj;
            } else if (isArray(obj)) {
                return arrayCopy(obj);
            } else if (Set.class.isAssignableFrom(clazz)) {
                return setCopy((Set)obj);
            } else if (Map.class.isAssignableFrom(clazz)) {
                return mapCopy((Map)obj);
            } else if (List.class.isAssignableFrom(clazz)) {
                return listCopy((List)obj);
            } else if (Copyable.class.isAssignableFrom(clazz)) {
                return ((Copyable)obj).copy();
            } else if (Cloneable.class.isAssignableFrom(clazz) && isCloneable(obj)) {
                return cloneCopy(obj);
            } else if (Serializable.class.isAssignableFrom(clazz)) {
                return Serializations.copy((Serializable)obj);
            } else {
                try {
                    Object r = clazz.newInstance();
                    copy(obj, r);
                    return r;
                } catch (Exception var3) {
                    throw new RuntimeException("failed to copy " + obj, var3);
                }
            }
        }
    }

    public static Object arrayCopy(Object array) {
        if (!isArray(array)) {
            throw new IllegalArgumentException("parameter array is not an Array");
        } else {
            int length = Array.getLength(array);
            Object r = Array.newInstance(array.getClass().getComponentType(), length);

            for(int i = 0; i < length; ++i) {
                Object element = Array.get(array, i);
                if (isArray(element)) {
                    Array.set(r, i, arrayCopy(element));
                } else {
                    Array.set(r, i, copy(element));
                }
            }

            return r;
        }
    }

    public static Object setCopy(Set set) {
        if (set == null) {
            return null;
        } else {
            try {
                Set r = (Set)set.getClass().newInstance();
                Iterator var2 = set.iterator();

                while(var2.hasNext()) {
                    Object obj = var2.next();
                    r.add(copy(obj));
                }

                return r;
            } catch (Exception var4) {
                throw new RuntimeException("failed to copy set", var4);
            }
        }
    }

    public static Object mapCopy(Map map) {
        if (map == null) {
            return null;
        } else {
            try {
                Map r = (Map)map.getClass().newInstance();
                Iterator var2 = map.keySet().iterator();

                while(var2.hasNext()) {
                    Object key = var2.next();
                    r.put(key, copy(map.get(key)));
                }

                return r;
            } catch (Exception var4) {
                throw new RuntimeException("failed to copy map", var4);
            }
        }
    }

    public static Object listCopy(List list) {
        if (list == null) {
            return null;
        } else {
            try {
                List r = (List)list.getClass().newInstance();
                Iterator var2 = list.iterator();

                while(var2.hasNext()) {
                    Object obj = var2.next();
                    r.add(copy(obj));
                }

                return r;
            } catch (Exception var4) {
                throw new RuntimeException("failed to copy list", var4);
            }
        }
    }

    public static Object cloneCopy(Object obj) {
        if (obj == null) {
            return obj;
        } else if (!isCloneable(obj)) {
            throw new IllegalArgumentException("parameter obj: " + obj + " is not cloneable");
        } else {
            try {
                Class<?> clazz = obj.getClass();
                Method method = Reflections.findPublicMethod(clazz, "clone", new Class[0]);
                method.setAccessible(true);
                return method.invoke(obj);
            } catch (Exception var3) {
                throw new RuntimeException("failed to clone copy", var3);
            }
        }
    }

    public static void copy(Object src, Object dst) {
        if (src == null) {
            throw new IllegalArgumentException("invalid parameter src");
        } else if (dst == null) {
            throw new IllegalArgumentException("invalid parameter dst");
        } else if (!src.getClass().equals(dst.getClass())) {
            throw new IllegalArgumentException("the class does not match, src: " + src.getClass() + ", dst: " + dst.getClass());
        } else {
            final Class<?> clazz = src.getClass();
            BeanCopier copier = (BeanCopier)Concurrents.getOrCreate(BEAN_COPIERS, clazz, new BeanCreator<BeanCopier>() {
                public BeanCopier create() {
                    return BeanCopier.create(clazz, clazz, false);
                }
            });
            copier.copy(src, dst, (org.springframework.cglib.core.Converter) null);
        }
    }

    public static boolean registerImmutableClass(Class<?> clazz) {
        return IMMUTABLE_CLASSES.add(clazz);
    }

    public static boolean unregisterImmutableClass(Class<?> clazz) {
        return IMMUTABLE_CLASSES.remove(clazz);
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else {
            try {
                return CollectionUtils.sizeIsEmpty(o);
            } catch (IllegalArgumentException var2) {
                return false;
            }
        }
    }

    public static boolean isNumber(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Number) {
            return true;
        } else {
            return o instanceof String ? NumberUtils.isNumber((String)o) : false;
        }
    }

    public static <T> T parseString(String s) {
        try {
            return (T) s;
        } catch (Throwable var2) {
            return null;
        }
    }

    public static String toString(Object o) {
        return o == null ? "" : "" + o;
    }

    static {
        PRIMITIVES_AND_WRAPPERS.add(Boolean.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Boolean.class);
        PRIMITIVES_AND_WRAPPERS.add(Byte.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Byte.class);
        PRIMITIVES_AND_WRAPPERS.add(Character.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Character.class);
        PRIMITIVES_AND_WRAPPERS.add(Double.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Double.class);
        PRIMITIVES_AND_WRAPPERS.add(Float.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Float.class);
        PRIMITIVES_AND_WRAPPERS.add(Integer.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Integer.class);
        PRIMITIVES_AND_WRAPPERS.add(Long.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Long.class);
        PRIMITIVES_AND_WRAPPERS.add(Short.TYPE);
        PRIMITIVES_AND_WRAPPERS.add(Short.class);
        IMMUTABLE_CLASSES = new HashSet();
        IMMUTABLE_CLASSES.add(Boolean.class);
        IMMUTABLE_CLASSES.add(Byte.class);
        IMMUTABLE_CLASSES.add(Character.class);
        IMMUTABLE_CLASSES.add(Double.class);
        IMMUTABLE_CLASSES.add(Float.class);
        IMMUTABLE_CLASSES.add(Integer.class);
        IMMUTABLE_CLASSES.add(Long.class);
        IMMUTABLE_CLASSES.add(Short.class);
        IMMUTABLE_CLASSES.add(Class.class);
        IMMUTABLE_CLASSES.add(String.class);
        IMMUTABLE_CLASSES.add(BigDecimal.class);
        IMMUTABLE_CLASSES.add(BigInteger.class);
        IMMUTABLE_CLASSES.add(Date.class);
        IMMUTABLE_CLASSES.add(java.sql.Date.class);
    }
}
