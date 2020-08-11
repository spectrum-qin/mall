package com.spectrum.mall.common.user;

import com.spectrum.mall.common.IExceptionCode;

/**
 * @author oe_qinzuopu
 */

public enum UserExceptionCode implements IExceptionCode {

    /**
     * 用户不允许修改
     */
    USER_UPDATE("201", "用户不允许修改"),;

    private final String value;

    private final String message;

    private UserExceptionCode(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public static UserExceptionCode getValue(String value) {
        if (null == value) {
            return null;
        }
        for (UserExceptionCode item : UserExceptionCode.values()) {
            if (value.equals(item.value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMessage() {

        return message;
    }
}
