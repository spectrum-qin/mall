package com.spectrum.mall.common;

/**
 * @author oe_qinzuopu
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ServiceException(IExceptionCode code) {
        super(code);
    }

    public ServiceException(IExceptionCode code, Throwable throwable) {
        super(code, throwable);
    }

    protected ServiceException set(DataResponseExceptionCode code, Throwable throwable) {
        return new ServiceException(code, throwable);
    }
}
