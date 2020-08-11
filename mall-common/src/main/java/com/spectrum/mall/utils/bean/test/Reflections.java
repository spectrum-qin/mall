package com.spectrum.mall.utils.bean.test;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Reflections {
    public Reflections() {
    }

    public static boolean isVoid(Class<?> clazz) {
        return clazz == Void.TYPE || clazz == Void.class;
    }

    public static Object getProperty(Object target, String name) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return f.get(target);
    }

    public static Object getFieldValue(Object target, String name) throws Exception {
        Object obj = null;

        try {
            obj = getProperty(target, name);
        } catch (NoSuchFieldException var5) {
            Field f = target.getClass().getSuperclass().getDeclaredField(name);
            f.setAccessible(true);
            obj = f.get(target);
        }

        return obj;
    }

    public static void setProperty(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    public static List<Field> getAllFields(Class<?> clazz) throws Exception {
        return getAllFields(clazz, (Class)null, true);
    }

    public static List<Field> getAllFields(Class<?> clazz, boolean excludeStaticFileds) throws Exception {
        return getAllFields(clazz, (Class)null, excludeStaticFileds);
    }

    public static List<Field> getAllFields(Class<?> clazz, Class<? extends Annotation> annotation, boolean excludeStaticFileds) throws Exception {
        if (clazz == null) {
            return null;
        } else {
            List<Field> r = new LinkedList();

            for(Class parent = clazz; parent != null; parent = parent.getSuperclass()) {
                Field[] var5 = parent.getDeclaredFields();
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Field f = var5[var7];
                    f.setAccessible(true);
                    if ((!excludeStaticFileds || (f.getModifiers() & 8) == 0) && (annotation == null || f.isAnnotationPresent(annotation))) {
                        r.add(f);
                    }
                }
            }

            return r;
        }
    }

    public static String getFieldName(Method m) {
        if (m == null) {
            return null;
        } else if (!isGetterMethod(m) && !isSetterMethod(m)) {
            return null;
        } else {
            StringBuilder r = new StringBuilder();
            if (isIsMethod(m)) {
                r.append(m.getName().substring(2));
            } else if (isGetterMethod(m)) {
                r.append(m.getName().substring(3));
            } else if (isSetterMethod(m)) {
                r.append(m.getName().substring(3));
            }

            r.replace(0, 1, r.substring(0, 1).toLowerCase());
            return r.toString();
        }
    }

    public static boolean isIsMethod(Method method) {
        if (method == null) {
            return false;
        } else if (!method.getName().startsWith("is")) {
            return false;
        } else if (method.getParameterTypes().length != 0) {
            return false;
        } else {
            return method.getReturnType().equals(Boolean.TYPE);
        }
    }

    public static boolean isGetterMethod(Method method) {
        if (method == null) {
            return false;
        } else if (isIsMethod(method)) {
            return true;
        } else if (!method.getName().startsWith("get")) {
            return false;
        } else if (method.getParameterTypes().length != 0) {
            return false;
        } else {
            return !isVoid(method.getReturnType());
        }
    }

    public static boolean isSetterMethod(Method method) {
        if (method == null) {
            return false;
        } else if (!method.getName().startsWith("set")) {
            return false;
        } else if (method.getParameterTypes().length != 1) {
            return false;
        } else {
            return isVoid(method.getReturnType());
        }
    }

    public static Method findGetterMethod(Class<?> clazz, Field field) {
        StringBuilder sb = new StringBuilder(field.getName());
        sb.replace(0, 1, sb.substring(0, 1).toUpperCase());
        sb.insert(0, "get");
        Method r = findPublicMethod(clazz, sb.toString(), new Class[0]);
        if (r == null) {
            if (field.getType().equals(Boolean.TYPE)) {
                sb.replace(0, 3, "is");
            }

            r = findPublicMethod(clazz, sb.toString(), new Class[0]);
        }

        return r;
    }

    public static Method findSetterMethod(Class<?> clazz, Field field) {
        StringBuilder sb = new StringBuilder(field.getName());
        sb.replace(0, 1, sb.substring(0, 1).toUpperCase());
        sb.insert(0, "set");
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            type = Classes.primitiveToClass(type);
        }

        return findPublicMethod(clazz, sb.toString(), new Class[]{type});
    }

    public static Method getPublicMethod(Class<?> clazz, String name, Class<?>[] signatures) throws NoSuchMethodException {
        Method r = findPublicMethod(clazz, name, signatures);
        if (r == null) {
            throw new NoSuchMethodException(clazz.getName() + "." + name + ArrayUtils.toString(signatures));
        } else {
            return r;
        }
    }

    public static Method findPublicMethod(Class<?> clazz, String name, Class<?>[] signatures) {
        if (signatures.length == 0) {
            try {
                return clazz.getMethod(name, signatures);
            } catch (NoSuchMethodException var8) {
                return null;
            } catch (SecurityException var9) {
            }
        }

        List<Method> methods = new ArrayList();
        Method[] var4 = clazz.getMethods();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(name) && matchArguments(signatures, method.getParameterTypes(), false)) {
                methods.add(method);
            }
        }

        if (methods.size() == 0) {
            return null;
        } else if (methods.size() == 1) {
            return (Method)methods.get(0);
        } else {
            Iterator var10 = methods.iterator();

            Method method;
            do {
                if (!var10.hasNext()) {
                    return getMostSpecificMethod(methods, signatures);
                }

                method = (Method)var10.next();
            } while(!matchArguments(signatures, method.getParameterTypes(), true));

            return method;
        }
    }

    private static Method getMostSpecificMethod(List<Method> methods, Class<?>[] signatures) {
        int maxMatches = 0;
        Method method = null;
        Iterator var4 = methods.iterator();

        while(true) {
            while(var4.hasNext()) {
                Method m = (Method)var4.next();
                int matches = 0;
                Class<?>[] paramTypes = m.getParameterTypes();

                for(int i = 0; i < signatures.length; ++i) {
                    Class<?> paramType = paramTypes[i];
                    if (paramType.isPrimitive() && !signatures[i].isPrimitive()) {
                        paramType = Classes.primitiveToClass(paramType);
                    }

                    if (signatures[i] == paramType) {
                        ++matches;
                    }
                }

                if (matches == 0 && maxMatches == 0) {
                    if (method == null) {
                        method = m;
                    } else if (!matchArguments(method.getParameterTypes(), m.getParameterTypes(), false)) {
                        method = m;
                    }
                } else if (matches > maxMatches) {
                    maxMatches = matches;
                    method = m;
                } else if (matches == maxMatches) {
                    method = null;
                }
            }

            return method;
        }
    }

    private static boolean matchArguments(Class<?>[] signatures, Class<?>[] paramTypes, boolean explicit) {
        if (signatures.length != paramTypes.length) {
            return false;
        } else {
            for(int j = 0; j < signatures.length; ++j) {
                Class<?> paramType = paramTypes[j];
                if (paramType.isPrimitive() && !signatures[j].isPrimitive()) {
                    paramType = Classes.primitiveToClass(paramType);
                }

                if (explicit) {
                    if (signatures[j] != paramType) {
                        return false;
                    }
                } else if (signatures[j] != null && !paramType.isAssignableFrom(signatures[j])) {
                    return false;
                }
            }

            return true;
        }
    }
}
