package com.d2c.content.dao;

import com.d2c.content.model.ShareTaskClick;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xuhua
 * @see 分享点击
 */
public interface ShareTaskClickMapper extends SuperMapper<ShareTaskClick> {

    /**
     * 获取点击数，IP相同，排除
     */
    int countBy(@Param("shareTaskId") Long shareTaskId, @Param("ip") String ip);

    /**
     * 更新为已统计
     */
    int doCounted(Long id);

}
