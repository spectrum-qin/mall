package com.spectrum.mall.utils.math;

import com.spectrum.mall.utils.text.StringUtils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public BigDecimalUtils() {
    }

    public static BigDecimal add(String a1, String a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = new BigDecimal(a1);
            BigDecimal b2 = new BigDecimal(a2);
            return b1.add(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal add(Integer a1, Integer a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((long)a1);
            BigDecimal b2 = BigDecimal.valueOf((long)a2);
            return b1.add(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal add(Long a1, Long a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.add(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal add(Float a1, Float a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((double)a1);
            BigDecimal b2 = BigDecimal.valueOf((double)a2);
            return b1.add(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal add(Double a1, Double a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.add(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal subtract(String a1, String a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = new BigDecimal(a1);
            BigDecimal b2 = new BigDecimal(a2);
            return b1.subtract(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal subtract(Integer a1, Integer a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((long)a1);
            BigDecimal b2 = BigDecimal.valueOf((long)a2);
            return b1.subtract(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal subtract(Long a1, Long a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.subtract(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal subtract(Float a1, Float a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((double)a1);
            BigDecimal b2 = BigDecimal.valueOf((double)a2);
            return b1.subtract(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal subtract(Double a1, Double a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.subtract(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal multiply(String a1, String a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = new BigDecimal(a1);
            BigDecimal b2 = new BigDecimal(a2);
            return b1.multiply(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal multiply(Integer a1, Integer a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((long)a1);
            BigDecimal b2 = BigDecimal.valueOf((long)a2);
            return b1.multiply(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal multiply(Long a1, Long a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.multiply(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal multiply(Float a1, Float a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((double)a1);
            BigDecimal b2 = BigDecimal.valueOf((double)a2);
            return b1.multiply(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal multiply(Double a1, Double a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.multiply(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal divide(Integer a1, Integer a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((long)a1);
            BigDecimal b2 = BigDecimal.valueOf((long)a2);
            return b1.divide(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal divide(Long a1, Long a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.divide(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal divide(Float a1, Float a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf((double)a1);
            BigDecimal b2 = BigDecimal.valueOf((double)a2);
            return b1.divide(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static BigDecimal divide(Double a1, Double a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            BigDecimal b1 = BigDecimal.valueOf(a1);
            BigDecimal b2 = BigDecimal.valueOf(a2);
            return b1.divide(b2);
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static boolean equals(BigDecimal a1, BigDecimal a2) {
        if (!StringUtils.isEmpty(a1) && !StringUtils.isEmpty(a1)) {
            return a1.compareTo(a2) == 0;
        } else {
            throw new NullPointerException("输入参数为空");
        }
    }

    public static void main(String[] args) {
        BigDecimal amount1 = new BigDecimal("0.06");
        BigDecimal amount2 = new BigDecimal(0.06D);
        BigDecimal amount3 = new BigDecimal("0.06");
        System.out.println(amount1);
        System.out.println(amount2);
        System.out.println(amount1.equals(amount3));
        System.out.println(amount1.compareTo(amount3));
    }
}
