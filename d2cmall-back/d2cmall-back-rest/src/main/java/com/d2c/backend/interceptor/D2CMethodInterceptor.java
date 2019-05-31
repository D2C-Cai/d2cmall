package com.d2c.backend.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

public class D2CMethodInterceptor implements MethodInterceptor {

    // 哪些方法是不过滤的
    private String[] excludeMethod = new String[]{"invication"};

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (Arrays.asList(excludeMethod).contains(invocation.getMethod().getName()))
            return invocation.proceed();
        // TODO 拦截逻辑
        return invocation.proceed();
    }

}
