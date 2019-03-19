package com.tenpay.wxwork.approval.presvr.sender.handler;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Created by Sean Lei on 22/11/2016.
 */

/**
 * 标记处理每个request_type指令的注解
 * spring会自动扫描该注解标注的Bean
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RequestTypeHandler {
    /**
     * 处理的bank指令 例：8801
     *
     * @return
     */
    int requestType() default 0;

    /**
     * spring bean的名称 默认为空则为Service类的类名
     * @return
     */
    String value() default "";
}

