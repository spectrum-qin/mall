package com.spectrum.mall.logback.config;

import com.spectrum.mall.logback.aspect.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author oe_qinzuopu
 */
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignInterceptor();
    }
}
