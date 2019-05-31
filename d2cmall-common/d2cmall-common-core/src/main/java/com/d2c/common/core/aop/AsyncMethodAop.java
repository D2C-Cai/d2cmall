package com.d2c.common.core.aop;

import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.ThreadUt;
import com.d2c.common.core.annotation.AsyncMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 异步调用AOP切片处理类
 *
 * @author wull
 */
@Aspect
@Component
public class AsyncMethodAop extends BaseAop {

    /**
     * 异步调用AsyncMethod
     *
     * @see AsyncMethod
     */
    @Around("@annotation(com.d2c.common.core.annotation.AsyncMethod)")
    public void asyncMethod(ProceedingJoinPoint point) {
        AsyncMethod ann = this.getAnn(point, AsyncMethod.class);
        long msec = ann.delay();
        MyExecutors.cachePool().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (msec > 0) {
                        ThreadUt.sleep(msec);
                    }
                    point.proceed(point.getArgs());
                } catch (Throwable e) {
                    throw new BaseException("异步调用异常:" + e.getMessage(), e);
                }
            }
        });
    }

}
