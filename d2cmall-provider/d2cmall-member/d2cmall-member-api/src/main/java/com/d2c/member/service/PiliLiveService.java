package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.PiliLiveSearcher;

import java.util.Map;

public interface PiliLiveService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    PiliLive findById(Long id);

    /**
     * 查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PiliLive> findBySearcher(PiliLiveSearcher searcher, PageModel page);

    /**
     * 新增
     *
     * @param live
     * @return
     */
    PiliLive insert(PiliLive live);

    /**
     * 结束
     *
     * @param id
     * @param replayUrl
     * @return
     */
    int doClose(Long id, String replayUrl);

    /**
     * 查询上一次未关闭的
     *
     * @param memberId
     * @return
     */
    PiliLive findLastOne(Long memberId);

    /**
     * 查询直播中实时观看人数
     *
     * @param id
     * @return
     */
    Integer getLiveCount(Long id);

    /**
     * 进入直播
     *
     * @param id
     * @param vrate
     * @param memberId
     * @param headPic
     * @return
     */
    Map<String, Object> doIn(Long id, int vrate, Long memberId, String headPic);

    /**
     * 退出直播
     *
     * @param id
     * @param memberId
     * @param headPic
     * @return
     */
    Map<String, Object> doOut(Long id, Long memberId, String headPic);

    /**
     * 观看录播
     *
     * @param id
     * @return
     */
    int doWatch(Long id);

    /**
     * 删除
     *
     * @param id
     * @param memberId
     * @return
     */
    int deleteByMemberId(Long id, Long memberId);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 查询热门直播
     *
     * @param page
     * @return
     */
    PageResult<PiliLive> findHot(PageModel page);

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @param modifyMan
     * @return
     */
    int updateMark(Long id, Integer mark, String modifyMan);

    /**
     * 置顶
     *
     * @param id
     * @param top
     * @param modifyMan
     * @return
     */
    int updateTop(Long id, Integer top, String modifyMan);

    /**
     * 更改僵尸粉数量
     *
     * @param id
     * @param vfans
     * @param modifyMan
     * @return
     */
    int updateVfans(Long id, Integer vfans, String modifyMan);

    /**
     * 更改实时人数区间
     *
     * @param id
     * @param vrate
     * @param modifyMan
     * @return
     */
    int updateVrate(Long id, Integer vrate, String modifyMan);

    /**
     * 更改红包倍率
     *
     * @param id
     * @param ratio
     * @param modifyMan
     * @return
     */
    int updateRatio(Long id, Integer ratio, String modifyMan);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     * @param nickName
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

}
