package com.d2c.common.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * AOP继承类
 *
 * @author wull
 */
public class BaseAop {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<?> getClass(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass();
    }

    protected Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    protected <T extends Annotation> T getAnn(JoinPoint joinPoint, Class<T> annClazz) {
        return getMethod(joinPoint).getAnnotation(annClazz);
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    //***************** DEMO *******************
	
	/*
	@Pointcut("@annotation(com.d2c.frame.web.annotation.CacheMethod)")
	public void pointcut(){}
	
    @Before("pointcut()")  
    public void before(JoinPoint joinPoint){  
    	logger.info("执行方法前跑的...");
    }

    @After("pointcut()")  
    public void after(JoinPoint joinPoint){  
    	logger.info("执行之后跑的...");
    }
    
    @AfterReturning(value = "pointcut()", returning = "res")
    public void afterReturn(JoinPoint joinPoint, String res){  
    	logger.info("执行数据返回后..." + res);
    }
    
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e){  
    	logger.error("执行返回异常...", e);
    }
	
    @Around("pointcut()")  
    public Object around(ProceedingJoinPoint point){  
    	Object res = null;
		try {
    		logger.info("执行方法前跑的...");
        	res = point.proceed(point.getArgs());
        	logger.info("执行数据返回后..." + res);
		} catch (Exception e) {
    		logger.error("执行返回异常...", e);
    	}
    	return res;
    }
    
    */
}
