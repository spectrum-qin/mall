package com.spectrum.mall.logback.filter;

import com.spectrum.mall.logback.constants.Constance;
import com.spectrum.mall.utils.text.StringUtils;
import com.spectrum.mall.utils.text.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author oe_qinzuopu
 */
@Slf4j
@Component
public class LogTraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取traceId
        String traceId = request.getHeader(Constance.HTTP_HEADER_TRACE_ID);
        // 不存在就生成一个
        if (!StringUtils.isEmpty(traceId)) {
            MDC.put("TraceId", traceId);
        } else {
            MDC.put("TraceId", UuidUtils.getUuid());
        }
        return true;
    }
}
