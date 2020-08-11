package com.spectrum.mall.utils.collection;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public abstract class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        } else {
            return collection instanceof List ? (T) ((List)collection).get(0) : collection.iterator().next();
        }
    }

    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        } else if (collection instanceof List) {
            List<T> list = (List)collection;
            return list.get(list.size() - 1);
        } else {
            return Iterators.getLast(collection.iterator());
        }
    }

    public static boolean elementsEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
        return Iterables.elementsEqual(iterable1, iterable2);
    }

    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
        return Collections.min(coll);
    }

    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
        return Collections.min(coll, comp);
    }

    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
        return Collections.max(coll);
    }

    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        return Collections.max(coll, comp);
    }

    public static <T extends Object & Comparable<? super T>> Pair<T, T> minAndMax(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T minCandidate = i.next();
        Object maxCandidate = minCandidate;

        while(i.hasNext()) {
            T next = i.next();
            if (((Comparable)next).compareTo(minCandidate) < 0) {
                minCandidate = next;
            } else if (((Comparable)next).compareTo(maxCandidate) > 0) {
                maxCandidate = next;
            }
        }

        return new ImmutablePair(minCandidate, maxCandidate);
    }

    public static <T> Pair<T, T> minAndMax(Collection<? extends T> coll, Comparator<? super T> comp) {
        Iterator<? extends T> i = coll.iterator();
        T minCandidate = i.next();
        T maxCandidate = minCandidate;

        while(i.hasNext()) {
            T next = i.next();
            if (comp.compare(next, minCandidate) < 0) {
                minCandidate = next;
            } else if (comp.compare(next, maxCandidate) > 0) {
                maxCandidate = next;
            }
        }

        return new ImmutablePair(minCandidate, maxCandidate);
    }
}
