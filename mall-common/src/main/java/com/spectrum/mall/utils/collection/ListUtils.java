package com.spectrum.mall.utils.collection;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ListUtils {
    public ListUtils() {
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static <T> T getFirst(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }

    public static <T> T getLast(List<T> list) {
        return isEmpty(list) ? null : list.get(list.size() - 1);
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }

    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... elements) {
        return Lists.newArrayList(elements);
    }

    public static <T> ArrayList<T> newArrayList(Iterable<T> elements) {
        return Lists.newArrayList(elements);
    }

    public static <T> ArrayList<T> newArrayListWithCapacity(int initSize) {
        return new ArrayList(initSize);
    }

    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList();
    }

    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }

    @SafeVarargs
    public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(T... elements) {
        return new CopyOnWriteArrayList(elements);
    }

    public static final <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    public static <T> List<T> emptyListIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    public static <T> List<T> singletonList(T o) {
        return Collections.singletonList(o);
    }

    public static <T> List<T> unmodifiableList(List<? extends T> list) {
        return Collections.unmodifiableList(list);
    }

    public static <T> List<T> synchronizedList(List<T> list) {
        return Collections.synchronizedList(list);
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void sortReverse(List<T> list) {
        Collections.sort(list, Collections.reverseOrder());
    }

    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        Collections.sort(list, c);
    }

    public static <T> void sortReverse(List<T> list, Comparator<? super T> c) {
        Collections.sort(list, Collections.reverseOrder(c));
    }

    public static <T> int binarySearch(List<? extends Comparable<? super T>> sortedList, T key) {
        return Collections.binarySearch(sortedList, key);
    }

    public static <T> int binarySearch(List<? extends T> sortedList, T key, Comparator<? super T> c) {
        return Collections.binarySearch(sortedList, key, c);
    }

    public static void shuffle(List<?> list) {
        Collections.shuffle(list);
    }

    public static <T> List<T> reverse(List<T> list) {
        return Lists.reverse(list);
    }

    public static void shuffle(List<?> list, Random rnd) {
        Collections.shuffle(list, rnd);
    }

    public static <E> List<E> union(List<? extends E> list1, List<? extends E> list2) {
        ArrayList<E> result = new ArrayList(list1.size() + list2.size());
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }

    public static <T> List<T> intersection(List<? extends T> list1, List<? extends T> list2) {
        List<T> result = new ArrayList();
        List<? extends T> smaller = list1;
        List<? extends T> larger = list2;
        if (list1.size() > list2.size()) {
            smaller = list2;
            larger = list1;
        }

        List<T> newSmaller = new ArrayList(smaller);
        Iterator var6 = larger.iterator();

        while(var6.hasNext()) {
            T e = (T) var6.next();
            if (newSmaller.contains(e)) {
                result.add(e);
                newSmaller.remove(e);
            }
        }

        return result;
    }

    public static <T> List<T> difference(List<? extends T> list1, List<? extends T> list2) {
        ArrayList<T> result = new ArrayList(list1);
        Iterator iterator = list2.iterator();

        while(iterator.hasNext()) {
            result.remove(iterator.next());
        }

        return result;
    }

    public static <T> List<T> disjoint(List<? extends T> list1, List<? extends T> list2) {
        List<T> intersection = intersection(list1, list2);
        List<T> towIntersection = union(intersection, intersection);
        return difference(union(list1, list2), towIntersection);
    }
}
