package com.spectrum.mall.common;

/**
 * @author oe_qinzuopu
 */
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = 6626868446456726497L;
    private final IExceptionCode code;
    private final String codeMsg;

    public BaseException(IExceptionCode code) {
        super((String)code.getValue() + "###" + code.getMessage());
        this.code = code;
        this.codeMsg = code.getMessage();
    }

    public BaseException(IExceptionCode code, String message) {
        super((String)code.getValue() + "###" + code.getMessage());
        this.code = code;
        this.codeMsg = message;
    }

    public BaseException(IExceptionCode code, Throwable throwable) {
        super((String)code.getValue() + "###" + code.getMessage(), throwable);
        this.code = code;
        if (null == throwable && null != code) {
            this.codeMsg = code.getMessage();
        } else if (null != throwable) {
            this.codeMsg = throwable.getMessage();
        } else {
            this.codeMsg = null;
        }

    }

    public IExceptionCode getCode() {
        return this.code;
    }

    public String getMessage() {
        return (String)this.code.getValue() + "###" + this.code.getMessage();
    }

    public String getCodeMsg() {
        return this.codeMsg;
    }
}
