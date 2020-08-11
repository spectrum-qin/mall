package com.spectrum.mall.common;

/**
 * @author oe_qinzuopu
 */
public class DataResponseExceptionCode implements IExceptionCode {
    private String value;
    private String message;

    public DataResponseExceptionCode(String value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
