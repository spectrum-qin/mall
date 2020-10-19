package com.spectrum.mall.logback.aspect;


import com.spectrum.mall.logback.filter.LogTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author oe_qinzuopu
 */
@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LogTraceInterceptor logTraceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logTraceInterceptor).addPathPatterns("/**");
    }
}
