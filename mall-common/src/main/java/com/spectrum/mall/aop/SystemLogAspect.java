package com.spectrum.mall.aop;

import com.spectrum.mall.annotation.SystemControllerLog;
import com.spectrum.mall.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author oe_qinzuopu
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    /**
     * Service层切点
     */
/*	@Pointcut("@annotation(com.myron.ims.annotation.SystemServiceLog)")
	public void serviceAspect(){}*/

    /**
     * Controller层切点 注解拦截
     */
//    @Pointcut("@annotation(com.spectrum.mall.annotation.SystemControllerLog)")
//    public void controllerAspect(){}

    /**
     * 方法规则拦截
     */
    @Pointcut("execution(* com.spectrum.mall.controller.*.*(..))")
    public void controllerPointerCut(){}
    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     */
    @Before("controllerPointerCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        String title="";
        //请求的IP
        String remoteAddr = request.getRemoteAddr();
        //请求端口
        Integer remotePort = request.getRemotePort();
        //请求的Uri
        String requestUri = request.getRequestURI();
        log.info("请求路径：" + remoteAddr + ":" + remotePort + requestUri);
        //请求提交的参数
        Object[] arguments = joinPoint.getArgs();
//        if (arguments.length == 0) {
//            return;
//        }
//        try {
//            title=getControllerMethodDescription2(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        log.info("接口【{}】请求报文：" + JsonUtils.objectToJson(arguments), requestUri);

    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
//    @SuppressWarnings("unchecked")
//    @After("controllerAspect()")
//    public void doAfter(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String title="";
//        //日志类型(info:入库,error:错误)
//        String type="info";
//        //请求的IP
//        String remoteAddr = request.getRemoteAddr();
//        //请求的Uri
//        String requestUri = request.getRequestURI();
//        logger.info("请求路径：" + remoteAddr + requestUri);
//        //请求提交的参数
//        Object[] arguments = joinPoint.getArgs();
//        if (arguments.length == 0) {
//            return;
//        }
//        try {
//            title=getControllerMethodDescription2(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.info(title + "接口请求报文：" + JsonUtils.objectToJson(arguments));
//    }

    @AfterReturning(returning = "response", pointcut = "controllerPointerCut()")
    public void doAfterReturning(Object response) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //请求的Uri
        String requestUri = request.getRequestURI();
        // 处理完请求，返回内容
        log.info("接口【{}】响应报文: " + JsonUtils.objectToJson(response), requestUri);
    }

    /**
     *  异常通知
     * @param e
     */
    @AfterThrowing(pointcut = "controllerPointerCut()", throwing = "e")
    public  void doAfterThrowing(Throwable e) {
        log.info("异常堆栈信息打印：" + e);
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method
                .getAnnotation(SystemControllerLog.class);
        String discription = controllerLog.description();
        return discription;
    }
}
