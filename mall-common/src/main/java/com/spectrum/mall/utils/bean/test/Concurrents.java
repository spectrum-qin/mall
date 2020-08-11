package com.spectrum.mall.utils.bean.test;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Concurrents {
    public Concurrents() {
    }

    public static long timedWait(Object obj, long timeout) throws InterruptedException {
        return timedWait(obj, timeout, TimeUnit.MILLISECONDS);
    }

    public static long timedWait(Object obj, long timeout, TimeUnit unit) throws InterruptedException {
        if (obj == null) {
            throw new IllegalArgumentException("invalid parameter obj");
        } else if (unit == null) {
            throw new IllegalArgumentException("invalid parameter unit");
        } else if (timeout <= 0L) {
            return 0L;
        } else {
            long now = System.nanoTime();
            synchronized(obj) {
                unit.timedWait(obj, timeout);
            }

            long elapsedTime = System.nanoTime() - now;
            return substract(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
        }
    }

    public static long timedJoin(Thread thread, long timeout) throws InterruptedException {
        return timedJoin(thread, timeout, TimeUnit.MILLISECONDS);
    }

    public static long timedJoin(Thread thread, long timeout, TimeUnit unit) throws InterruptedException {
        if (thread == null) {
            throw new IllegalArgumentException("invalid parameter thread");
        } else if (unit == null) {
            throw new IllegalArgumentException("invalid parameter unit");
        } else if (timeout <= 0L) {
            return 0L;
        } else {
            long now = System.nanoTime();
            unit.timedJoin(thread, timeout);
            long elapsedTime = System.nanoTime() - now;
            return substract(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
        }
    }

    public static long delay(long timeout) throws InterruptedException {
        return delay(timeout, TimeUnit.MILLISECONDS);
    }

    public static long delay(Date date) throws InterruptedException {
        if (date == null) {
            throw new IllegalArgumentException("invalid parameter date");
        } else {
            return delay(date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public static long delay(long timeout, TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new IllegalArgumentException("invalid parameter unit");
        } else if (timeout <= 0L) {
            return 0L;
        } else {
            Object object = new Object();
            long now = System.nanoTime();
            synchronized(object) {
                unit.timedWait(object, timeout);
            }

            long elapsedTime = System.nanoTime() - now;
            return substract(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
        }
    }

    public static long shutdownAndAwaitTermination(ExecutorService exec, long timeout) throws InterruptedException {
        return shutdownAndAwaitTermination(exec, timeout, TimeUnit.MILLISECONDS);
    }

    public static long shutdownAndAwaitTermination(ExecutorService exec, long timeout, TimeUnit unit) throws InterruptedException {
        if (exec == null) {
            throw new IllegalArgumentException("invalid parameter exec");
        } else if (unit == null) {
            throw new IllegalArgumentException("invalid parameter unit");
        } else if (timeout <= 0L) {
            return 0L;
        } else {
            long now = System.nanoTime();
            if (!exec.isShutdown()) {
                exec.shutdown();
            }

            exec.awaitTermination(timeout, unit);
            long elapsedTime = System.nanoTime() - now;
            return substract(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
        }
    }

    public static <K, V> V getOrCreate(ConcurrentHashMap<K, V> m, K k, BeanCreator<V> creator) {
        V v = m.get(k);
        if (v != null) {
            return v;
        } else {
            v = creator.create();
            V r = m.putIfAbsent(k, v);
            return r != null ? r : v;
        }
    }

    public static <K, V> V putIfAbsent(ConcurrentMap<K, V> map, K key, V value) {
        V result = map.putIfAbsent(key, value);
        return result != null ? result : value;
    }

    private static long substract(long l1, long l2) {
        long r = (l1 < 0L ? 0L : l1) - (l2 < 0L ? 0L : l2);
        return r < 0L ? 0L : r;
    }
}
