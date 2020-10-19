package com.spectrum.mall.logback.aspect;


import com.spectrum.mall.logback.constants.Constance;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

/**
 * @author oe_qinzuopu
 */
public class FeignInterceptor implements RequestInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(Constance.HTTP_HEADER_TRACE_ID, MDC.get(Constance.LOG_TRACE_ID));
    }
}
