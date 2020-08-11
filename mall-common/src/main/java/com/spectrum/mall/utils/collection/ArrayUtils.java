package com.spectrum.mall.utils.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.spectrum.mall.utils.text.CheckUtil;

import java.lang.reflect.Array;
import java.util.*;

public abstract class ArrayUtils {
    private static final String ARRAY_PARAM = "入参array";

    public ArrayUtils() {
    }

    public static <T> T[] shuffle(T[] array) {
        List<T> list = new ArrayList(array.length);
        Collections.addAll(list, array);
        Collections.shuffle(list);
        return list.toArray(array);
    }

    public static <T> T[] shuffle(T[] array, Random random) {
        List<T> list = new ArrayList(Arrays.asList(array));
        Collections.shuffle(list, random);
        return list.toArray(array);
    }

    public static <T> T[] concat(T element, T[] array) {
        return ObjectArrays.concat(element, array);
    }

    public static <T> T[] newArray(Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
    }

    public static <T> T[] toArray(List<T> list, Class<T> type) {
        return (T[]) list.toArray((Object[])((Object[])Array.newInstance(type, list.size())));
    }

    public static <T> List<T> asList(T... args) {
        return Arrays.asList(args);
    }

    public static <E> List<E> asList(E first, E[] rest) {
        return Lists.asList(first, rest);
    }

    public static List<Integer> intAsList(int... backingArray) {
        return Ints.asList(backingArray);
    }

    public static List<Long> longAsList(long... backingArray) {
        return Longs.asList(backingArray);
    }

    public static List<Double> doubleAsList(double... backingArray) {
        return Doubles.asList(backingArray);
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static String cocat(Object[] array, String splitStr) {
        StringBuffer sb = new StringBuffer();
        Object[] var3 = array;
        int var4 = array.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Object str = var3[var5];
            if (sb.length() > 0) {
                sb.append(splitStr);
            }

            sb.append(str.toString());
        }

        return sb.toString();
    }

    public static <T> List<T> toArrayList(T[] array) {
        CheckUtil.notEmpty(array, "入参array");
        List<T> list = new ArrayList();
        Object[] var2 = array;
        int var3 = array.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            T t = (T) var2[var4];
            list.add(t);
        }

        return list;
    }
}
