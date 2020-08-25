package com.spectrum.mall.annotation;

import java.lang.annotation.*;

/**
 * @author oe_qinzuopu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SystemServiceLog {

    /**
     * 描述业务操作 例:Xxx管理-执行Xxx操作
     * @return
     */
    String description()  default "";
}
