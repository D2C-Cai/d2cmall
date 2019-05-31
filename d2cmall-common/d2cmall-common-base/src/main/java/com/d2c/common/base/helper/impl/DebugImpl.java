package com.d2c.common.base.helper.impl;

import com.d2c.common.base.utils.DateUt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class DebugImpl {

    private static final int DEFAUT_ROUND = 100;
    private static Date last;
    private Logger logger;
    private String key;
    private int round = DEFAUT_ROUND;
    private int cnt = 0;
    private long keep = 0;

    public DebugImpl(String key) {
        this(null, key);
    }

    public DebugImpl(Object serv, String key) {
        this.key = key;
        if (serv == null) serv = this;
        logger = LoggerFactory.getLogger(serv.getClass());
    }

    public void log() {
        if (last == null) last = new Date();
        if (cnt == 0) {
            logger.info("开始累计" + key + "执行消耗时间... 输出间隔: " + round);
        }
        cnt++;
        Date d = new Date();
        keep = keep + DateUt.between(last, d);
        last = d;
        if (cnt % round == 0) {
            logger.info(cnt + "条, " + key + " Time:" + DateUt.duration(keep));
        }
    }

}
