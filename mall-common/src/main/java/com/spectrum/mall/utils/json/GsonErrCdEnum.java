package com.spectrum.mall.utils.json;

import com.spectrum.mall.common.AbstractEnum;

public enum GsonErrCdEnum implements AbstractEnum<String> {
    SUCCESS("000000", "成功"),
    FAIL("000001", "失败"),
    KEYI("000002", "可疑"),
    TIMEOUT("000003", "超时");

    private String value;
    private String displayName;

    private GsonErrCdEnum(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.displayName;
    }
}

