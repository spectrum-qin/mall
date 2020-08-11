//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectrum.mall.utils.bean;

import com.esotericsoftware.reflectasm.MethodAccess;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.spectrum.mall.utils.text.CheckUtil;
import com.spectrum.mall.utils.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);
    private static Map<Class<?>, MethodAccess> methodMap = new HashMap();
    private static Map<String, Integer> methodIndexMap = new HashMap();
    private static Map<Class<?>, List<String>> fieldMap = new HashMap();
    private static final byte[] LOCK = new byte[0];

    public BeanUtils() {
    }

    public static boolean propertyExists(Object bean, String name) {
        try {
            getProperty(bean, name);
            return true;
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var3) {
            return false;
        } catch (RuntimeException var4) {
            return false;
        }
    }

    public static void setPropertyLombok(Object bean, String name, Object value) {
        Method[] methods = bean.getClass().getMethods();
        Method method = findMethodByName(methods, "set" + toUpperCaseFirstOne(name));

        try {
            method.invoke(bean, value);
        } catch (Exception var6) {
            log.error("setPropertyLombok exception", var6);
        }

    }

    private static Method findMethodByName(Method[] methods, String name) {
        for(int j = 0; j < methods.length; ++j) {
            if (methods[j].getName().equals(name)) {
                return methods[j];
            }
        }

        return null;
    }

    public static void copyPropertiesExcludeId(Object destObj, Object sourceObj) {
        copyPropertiesExclude(destObj, sourceObj, "id");
    }

    public static <T> T copyPropertiesExcludeId(Class<T> destClazz, Object sourceObj) {
        Object descObj = null;

        try {
            descObj = destClazz.newInstance();
            copyPropertiesExclude(descObj, sourceObj, "id");
        } catch (InstantiationException var4) {
            log.error("InstantiationException", var4);
        } catch (IllegalAccessException var5) {
            log.error("IllegalAccessException", var5);
        } catch (IllegalArgumentException var6) {
            log.error("IllegalArgumentException", var6);
        } catch (SecurityException var7) {
            log.error("SecurityException", var7);
        }

        return (T) descObj;
    }

    public static <T> T copyPropertiesExclude(Class<T> destClazz, Object sourceObj, String... properties) {
        Object descObj = null;

        try {
            descObj = destClazz.newInstance();
            copyPropertiesExclude(descObj, sourceObj, properties);
        } catch (InstantiationException var5) {
            log.error("InstantiationException", var5);
        } catch (IllegalAccessException var6) {
            log.error("IllegalAccessException", var6);
        } catch (IllegalArgumentException var7) {
            log.error("IllegalArgumentException", var7);
        } catch (SecurityException var8) {
            log.error("SecurityException", var8);
        }

        return (T) descObj;
    }

    public static void copyPropertiesExclude(Object desc, Object orgi, String... properties) {
        List<String> list = new ArrayList(properties.length);
        String[] var4 = properties;
        int var5 = properties.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String property = var4[var6];
            list.add(toUpperCaseFirstOne(property));
        }

        copy(desc, orgi, desc.getClass(), orgi.getClass(), true, list);
    }

    public static <T> T copyProperties(Class<T> destClazz, Object sourceObj) {
        return (T) copyProperties((Class)destClazz, sourceObj, (Map)null);
    }

    public static <O, T> List<T> copyList(Class<T> destClazz, List<O> sourceObjList) {
        if (CheckUtil.isEmpty(sourceObjList)) {
            return null;
        } else {
            List<T> resultList = new ArrayList();
            sourceObjList.forEach((o) -> {
                T t = copyProperties(destClazz, o);
                resultList.add(t);
            });
            return resultList;
        }
    }

    public static <T> T copyProperties(Class<T> destClazz, Object sourceObj, Map<String, String> propertyMap) {
        Object descObj = null;

        try {
            descObj = destClazz.newInstance();
            copyProperties(descObj, sourceObj, propertyMap);
        } catch (InstantiationException var5) {
            log.error("InstantiationException", var5);
        } catch (IllegalAccessException var6) {
            log.error("IllegalAccessException", var6);
        } catch (IllegalArgumentException var7) {
            log.error("", var7);
        } catch (SecurityException var8) {
            log.error("IllegalArgumentException", var8);
        }

        return (T) descObj;
    }

    public static void copyProperties(Object desc, Object orgi) {
        copy(desc, orgi, desc.getClass(), orgi.getClass(), false, (List)null, (Map)null);
    }

    public static void copyProperties(Object desc, Object orgi, Map<String, String> params) {
        copy(desc, orgi, desc.getClass(), orgi.getClass(), false, (List)null, params);
    }

    private static void copy(Object desc, Object orgi, Class<?> descClass, Class<?> origClass, boolean flag, List<String> properties) {
        copy(desc, orgi, descClass, origClass, flag, properties, (Map)null);
    }

    private static void copy(Object destObj, Object sourceObj, Class<?> destClass, Class<?> sourceClass, boolean flag, List<String> properties, Map<String, String> params) {
        MethodAccess destMethodAccess = (MethodAccess)methodMap.get(destClass);
        if (destMethodAccess == null) {
            destMethodAccess = cache(destClass);
        }

        MethodAccess orgiMethodAccess = (MethodAccess)methodMap.get(sourceClass);
        if (orgiMethodAccess == null) {
            orgiMethodAccess = cache(sourceClass);
        }

        List<String> fieldList = (List)fieldMap.get(sourceClass);
        Iterator var10 = fieldList.iterator();

        while(true) {
            boolean booleanflag;
            String destField;
            Integer setIndex;
            Object sourceVal;
            do {
                String getKey;
                do {
                    String field;
                    do {
                        if (!var10.hasNext()) {
                            return;
                        }

                        field = (String)var10.next();
                        booleanflag = false;
                        if (field.startsWith("boolean|")) {
                            booleanflag = true;
                            field = field.substring(field.indexOf("|") + 1, field.length());
                            getKey = sourceClass.getName() + ".is" + field;
                        } else {
                            getKey = sourceClass.getName() + ".get" + field;
                        }
                    } while(flag && properties.contains(field));

                    destField = null;
                    if (params != null) {
                        destField = (String)params.get(toLowerCaseFirstOne(field));
                        destField = StringUtils.isEmpty(destField) ? field : toUpperCaseFirstOne(destField);
                    } else {
                        destField = field;
                    }

                    String setkey = destClass.getName() + ".set" + destField;
                    setIndex = (Integer)methodIndexMap.get(setkey);
                } while(setIndex == null);

                int getIndex = (Integer)methodIndexMap.get(getKey);
                sourceVal = orgiMethodAccess.invoke(sourceObj, getIndex, new Object[0]);
            } while(sourceVal == null);

            Method method;
            if (!isPrimitive(sourceVal) && !String.class.equals(sourceVal.getClass()) && !(sourceVal instanceof List) && !BigDecimal.class.equals(sourceVal.getClass()) && !Date.class.equals(sourceVal.getClass())) {
                if (!(sourceVal instanceof List)) {
                    try {
                        if (booleanflag) {
                            method = findMethodByName(destClass.getMethods(), "is" + destField);
                        } else {
                            method = findMethodByName(destClass.getMethods(), "get" + destField);
                        }

                        Type type = method.getGenericReturnType();
                        Class<?> clzz = (Class)type;
                        Object destValObj = copyProperties(clzz, sourceVal);
                        destMethodAccess.invoke(destObj, setIndex, new Object[]{destValObj});
                    } catch (Exception var28) {
                        log.error("操作异常", var28);
                    }
                }
            } else {
                destMethodAccess.invoke(destObj, setIndex, new Object[]{sourceVal});
            }

            try {
                if (destObj != null && !booleanflag) {
                    method = findMethodByName(destClass.getMethods(), "get" + destField);
                    Object val = method.getReturnType().cast(method.invoke(destObj));
                    if (val instanceof List) {
                        Type type = method.getGenericReturnType();
                        if (!"java.util.List".equals(type.getTypeName())) {
                            Class<?> clzz = null;
                            if (type instanceof ParameterizedType) {
                                ParameterizedType pt = (ParameterizedType)type;
                                Type typeTemp = pt.getActualTypeArguments()[0];
                                if ("?".equals(typeTemp.getTypeName())) {
                                    continue;
                                }

                                clzz = (Class)typeTemp;
                                if (isWrapClass(clzz) || String.class.equals(clzz) || BigDecimal.class.equals(clzz) || Date.class.equals(clzz)) {
                                    continue;
                                }
                            }

                            List<?> valList = (List)val;
                            List<Object> listb = new ArrayList();
                            Iterator var25 = valList.iterator();

                            while(var25.hasNext()) {
                                Object valObj = var25.next();
                                Object destValObj = copyProperties(clzz, valObj);
                                listb.add(destValObj);
                            }

                            destMethodAccess.invoke(destObj, setIndex, new Object[]{listb});
                        }
                    }
                }
            } catch (Exception var29) {
                log.error("操作异常", var29);
            }
        }
    }

    private static boolean isPrimitive(Object obj) {
        try {
            return ((Class)obj.getClass().getField("TYPE").get((Object)null)).isPrimitive();
        } catch (Exception var2) {
            return false;
        }
    }

    private static boolean isWrapClass(Class clz) {
        try {
            return ((Class)clz.getField("TYPE").get((Object)null)).isPrimitive();
        } catch (Exception var2) {
            return false;
        }
    }

    private static String toUpperCaseFirstOne(String s) {
        return Character.isUpperCase(s.charAt(0)) ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String toLowerCaseFirstOne(String s) {
        return Character.isLowerCase(s.charAt(0)) ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    public static List<Field> getAllFields(Class<?> orgiClass) {
        List<Field> fieldList = new ArrayList();

        for(Class tempClass = orgiClass; tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object"); tempClass = tempClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
        }

        return fieldList;
    }

    private static MethodAccess cache(Class<?> orgiClass) {
        synchronized(LOCK) {
            MethodAccess methodAccess = MethodAccess.get(orgiClass);
            List<Field> fields = getAllFields(orgiClass);
            List<String> fieldList = new ArrayList();
            Iterator var5 = fields.iterator();

            while(var5.hasNext()) {
                Field field = (Field)var5.next();
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                    String fieldName = StringUtils.capitalize(field.getName());
                    Class<?> type = field.getType();
                    int setIndex;
                    if (Boolean.TYPE.equals(type)) {
                        setIndex = methodAccess.getIndex("is" + fieldName);
                        methodIndexMap.put(orgiClass.getName() + ".is" + fieldName, setIndex);
                        fieldList.add("boolean|" + fieldName);
                    } else {
                        setIndex = methodAccess.getIndex("get" + fieldName);
                        methodIndexMap.put(orgiClass.getName() + ".get" + fieldName, setIndex);
                        fieldList.add(fieldName);
                    }

                    setIndex = methodAccess.getIndex("set" + fieldName);
                    methodIndexMap.put(orgiClass.getName() + ".set" + fieldName, setIndex);
                }
            }

            fieldMap.put(orgiClass, fieldList);
            methodMap.put(orgiClass, methodAccess);
            return methodAccess;
        }
    }
}
