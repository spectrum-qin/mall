package com.spectrum.mall.utils.bean.test;

import java.lang.reflect.Array;
import java.util.*;

public abstract class Classes {
    private static final HashMap<String, Class<?>> PRIMITIVE_CLASS_MAP = new HashMap();
    private static final HashMap<Class<?>, String> CLASS_TO_SIGNATURE_MAP = new HashMap();

    public Classes() {
    }

    public static Set<Class<?>> getAllTypes(Class<?> clazz) {
        Set<Class<?>> r = new LinkedHashSet();
        Set<Class<?>> set = getClasses(clazz);
        r.addAll(set);
        Iterator var3 = set.iterator();

        while(var3.hasNext()) {
            Class<?> c = (Class)var3.next();
            r.addAll(getInterfaces(c));
        }

        return r;
    }

    public static Set<Class<?>> getAllInterfaces(Class<?> clazz) {
        Set<Class<?>> set = getClasses(clazz);
        Set<Class<?>> r = new LinkedHashSet();
        Iterator var3 = set.iterator();

        while(var3.hasNext()) {
            Class<?> c = (Class)var3.next();
            r.addAll(getInterfaces(c));
        }

        return r;
    }

    public static Class<?>[] getAllInterfacesAsArray(Class<?> clazz) {
        Set<Class<?>> set = getAllInterfaces(clazz);
        int i = 0;
        Class<?>[] r = new Class[set.size()];

        Class c;
        for(Iterator var4 = set.iterator(); var4.hasNext(); r[i++] = c) {
            c = (Class)var4.next();
        }

        return r;
    }

    public static Class<?> primitiveToClass(Class<?> clazz) {
        return clazz.isPrimitive() ? typeNameToClass(clazz.getName()) : clazz;
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (className == null) {
            throw new IllegalArgumentException("parameter className can not be null");
        } else if (classLoader == null) {
            throw new IllegalArgumentException("parameter classLoader can not be null");
        } else {
            try {
                return classLoader.loadClass(className);
            } catch (ClassNotFoundException var7) {
                Class<?> resolvedClass = (Class)PRIMITIVE_CLASS_MAP.get(className);
                if (resolvedClass != null) {
                    return resolvedClass;
                } else if (className.endsWith(";") && className.startsWith("L")) {
                    String typeName = className.substring(1, className.length() - 1);
                    return classLoader.loadClass(typeName);
                } else {
                    int count;
                    int position;
                    String typeName;
                    Class arrayType;
                    if (className.charAt(0) == '[') {
                        count = 0;

                        for(position = className.length(); count < position && className.charAt(count) == '['; ++count) {
                        }

                        typeName = className.substring(count, className.length());
                        arrayType = loadClass(typeName, classLoader);
                        return getArrayClass(arrayType, count);
                    } else if (!className.endsWith("[]")) {
                        throw new ClassNotFoundException("could not load class: " + className + " from classloader: " + classLoader);
                    } else {
                        count = 0;

                        for(position = className.length(); position > 1 && className.substring(position - 2, position).equals("[]"); position -= 2) {
                            ++count;
                        }

                        typeName = className.substring(0, position);
                        arrayType = loadClass(typeName, classLoader);
                        return getArrayClass(arrayType, count);
                    }
                }
            }
        }
    }

    private static Class<?> typeNameToClass(String typeName) {
        typeName = typeName.intern();
        if (typeName == "boolean") {
            return Boolean.class;
        } else if (typeName == "byte") {
            return Byte.class;
        } else if (typeName == "char") {
            return Character.class;
        } else if (typeName == "short") {
            return Short.class;
        } else if (typeName == "int") {
            return Integer.class;
        } else if (typeName == "long") {
            return Long.class;
        } else if (typeName == "float") {
            return Float.class;
        } else if (typeName == "double") {
            return Double.class;
        } else {
            return typeName == "void" ? Void.class : null;
        }
    }

    private static Set<Class<?>> getClasses(Class<?> clazz) {
        Set<Class<?>> r = new LinkedHashSet();
        r.add(clazz);

        for(Class superClass = clazz.getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
            r.add(superClass);
        }

        return r;
    }

    private static Set<Class<?>> getInterfaces(Class<?> clazz) {
        Set<Class<?>> r = new LinkedHashSet();
        LinkedList<Class<?>> stack = new LinkedList();
        stack.addAll(Arrays.asList(clazz.getInterfaces()));

        while(!stack.isEmpty()) {
            Class<?> intf = (Class)stack.removeFirst();
            if (!r.contains(intf)) {
                r.add(intf);
                stack.addAll(Arrays.asList(intf.getInterfaces()));
            }
        }

        return r;
    }

    private static Class<?> getArrayClass(Class<?> type, int dimension) {
        int[] dimensions = new int[dimension];
        return Array.newInstance(type, dimensions).getClass();
    }

    private static String getClassName(Class<?> type) {
        StringBuilder name;
        for(name = new StringBuilder(); type.isArray(); type = type.getComponentType()) {
            name.append('[');
        }

        if (type.isPrimitive()) {
            name.append((String)CLASS_TO_SIGNATURE_MAP.get(type));
        } else {
            name.append('L');
            name.append(type.getName());
            name.append(';');
        }

        return name.toString();
    }

    static {
        PRIMITIVE_CLASS_MAP.put("boolean", Boolean.TYPE);
        PRIMITIVE_CLASS_MAP.put("Z", Boolean.TYPE);
        PRIMITIVE_CLASS_MAP.put("byte", Byte.TYPE);
        PRIMITIVE_CLASS_MAP.put("B", Byte.TYPE);
        PRIMITIVE_CLASS_MAP.put("char", Character.TYPE);
        PRIMITIVE_CLASS_MAP.put("C", Character.TYPE);
        PRIMITIVE_CLASS_MAP.put("short", Short.TYPE);
        PRIMITIVE_CLASS_MAP.put("S", Short.TYPE);
        PRIMITIVE_CLASS_MAP.put("int", Integer.TYPE);
        PRIMITIVE_CLASS_MAP.put("I", Integer.TYPE);
        PRIMITIVE_CLASS_MAP.put("long", Long.TYPE);
        PRIMITIVE_CLASS_MAP.put("J", Long.TYPE);
        PRIMITIVE_CLASS_MAP.put("float", Float.TYPE);
        PRIMITIVE_CLASS_MAP.put("F", Float.TYPE);
        PRIMITIVE_CLASS_MAP.put("double", Double.TYPE);
        PRIMITIVE_CLASS_MAP.put("D", Double.TYPE);
        PRIMITIVE_CLASS_MAP.put("void", Void.TYPE);
        PRIMITIVE_CLASS_MAP.put("V", Void.TYPE);
        CLASS_TO_SIGNATURE_MAP.put(Boolean.TYPE, "Z");
        CLASS_TO_SIGNATURE_MAP.put(Byte.TYPE, "B");
        CLASS_TO_SIGNATURE_MAP.put(Character.TYPE, "C");
        CLASS_TO_SIGNATURE_MAP.put(Short.TYPE, "S");
        CLASS_TO_SIGNATURE_MAP.put(Integer.TYPE, "I");
        CLASS_TO_SIGNATURE_MAP.put(Long.TYPE, "J");
        CLASS_TO_SIGNATURE_MAP.put(Float.TYPE, "F");
        CLASS_TO_SIGNATURE_MAP.put(Double.TYPE, "D");
        CLASS_TO_SIGNATURE_MAP.put(Void.TYPE, "V");
    }
}
