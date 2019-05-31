package com.d2c.common.base.utils;

import com.d2c.common.base.exception.ThreadException;

/**
 * Thead线程工具类
 *
 * @author wull
 */
public class ThreadUt {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new ThreadException(e);
        }
    }

}
