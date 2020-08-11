package com.spectrum.mall.utils.text;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class CheckUtil {
    private static final String NOT_NULL = "不能为空！";
    private static final String CHECK_NOT_SUPPORT = "不支持入参类型的校验！";

    public CheckUtil() {
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static void notNull(Object obj, String message) {
        if (isNull(obj)) {
            throw new IllegalArgumentException(message + "不能为空！");
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && obj.toString().trim().length() == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection)obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map)obj).isEmpty()) {
            return true;
        } else {
            return obj.getClass().isArray() && Array.getLength(obj) == 0;
        }
    }

    public static void notEmpty(Object obj, String message) {
        if (isEmpty(obj)) {
            throw new IllegalArgumentException(message + "不能为空！");
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
