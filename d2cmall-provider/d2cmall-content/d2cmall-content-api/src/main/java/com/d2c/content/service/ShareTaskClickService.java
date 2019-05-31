package com.d2c.content.service;

import com.d2c.content.model.ShareTaskClick;

public interface ShareTaskClickService {

    /**
     * 添加一个分享任务的点击事件 1.添加一个分享任务的点击实体 2.通过ip，判断任务是否没有被分享过，增加分享任务的积分
     * 3.标记分享任务事件已经被统计
     *
     * @param click
     * @return
     */
    int doClick(ShareTaskClick click);

}
