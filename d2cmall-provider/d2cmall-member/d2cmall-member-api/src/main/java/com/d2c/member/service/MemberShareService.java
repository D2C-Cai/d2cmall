package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberShare;
import com.d2c.member.query.MemberShareSearcher;

public interface MemberShareService {

    /**
     * 通过买家秀id，查找出对应的买家秀实体类
     *
     * @param memberShareId
     * @return
     */
    MemberShare findById(Long memberShareId);

    /**
     * 根据标签id查询
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<MemberShare> findByTagId(Long tagId, PageModel page);

    /**
     * 查询我关注的买家秀
     *
     * @param id
     * @param picTag
     * @return
     */
    PageResult<MemberShare> findMyFollows(Long memberInfoId, Integer status, PageModel page);

    /**
     * 获取到没有审核通过的买家秀数量
     *
     * @return
     */
    int findUnSubmitCount();

    /**
     * 通过查询条件和分页条件，得到符合条件的买家秀分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberShare> findBySearch(MemberShareSearcher searcher, PageModel page);

    /**
     * 通过商品id和买家秀审核状态，得到对应买家秀的数量
     *
     * @param productId 商品id
     * @param status    状态
     * @return
     */
    int countByProductId(Long productId, Integer status);

    /**
     * 通过用户id和买家秀审核状态，得到对应买家秀的数量
     *
     * @param productId 商品id
     * @param status    状态
     * @return
     */
    int countByMemberId(Long memberId, Integer status);

    /**
     * 添加一条买家秀实体类数据
     *
     * @param ms
     * @return
     */
    MemberShare insert(MemberShare ms);

    /**
     * 更新买家秀实体数据，并且重建搜索数据
     *
     * @param ms
     * @return
     */
    int update(MemberShare ms);

    /**
     * 更新一条买家秀的url的路径，并且重建搜索数据
     *
     * @param memberShareId 买家秀id
     * @param url           路径
     * @return
     */
    int updateUrl(Long memberShareId, String url);

    /**
     * 更新买家秀图片
     *
     * @param id
     * @param picTag
     * @return
     */
    int updatePicById(Long id, String pic);

    /**
     * 更新短视频链接
     *
     * @param taskIds
     * @param video
     * @return
     */
    int updateVideoById(Long id, String video);

    /**
     * 更新买家秀图片标签
     *
     * @param id
     * @param picTag
     * @return
     */
    int updatePicTag(Long id, String picTag);

    /**
     * 更新买家秀喜欢的数量
     *
     * @param shareId 买家秀id
     * @param count   喜欢的数量
     * @return
     */
    int updateLikesCount(Long shareId, int count);

    /**
     * 更新买家秀评论的数量
     *
     * @param shareId 买家秀id
     * @param count   喜欢的数量
     * @return
     */
    int updateCommentsCount(Long shareId, int count);

    /**
     * 更新绑定买家秀与商品，设计师的id，并且更新搜索数据
     *
     * @param memberShareId 买家秀id
     * @param productId     商品id
     * @param designerId    设计师id
     * @return
     */
    int updateBindStatus(Long memberShareId, Long productId, Long designerId);

    /**
     * 设计师上下架修改买家秀设计师id
     *
     * @param id
     * @param deisgnerId
     * @return
     */
    int updateDesignerId(Long id, Long designerId);

    /**
     * 物理删除一条买家秀的记录，并且移除搜索数据
     *
     * @param memberShareId 买家秀id
     * @return
     */
    int delete(Long memberShareId);

    /**
     * 增加查看次数
     *
     * @param shareId
     * @return
     */
    int doWatch(Long shareId);

    /**
     * 买家秀拒绝
     *
     * @param shareId
     * @param refuseReason
     * @return
     */
    int doRefuse(Long shareId, String refuseReason, String lastModifyMan);

    /**
     * 对一条买家秀的数据，审核通过操作,重建搜索数据
     *
     * @param memberShareId 买家秀id
     * @param verifyMan     审核人员
     * @return
     */
    int doVerify(Long memberShareId, String verifyMan);

    /**
     * 对一条买家秀取消审核，并且重建搜索数据
     *
     * @param memberShareId 买家秀id
     * @param verifyMan     审核人员
     * @return
     */
    int doCancelVerify(Long memberShareId, String verifyMan);

    /**
     * 对一条买家秀数据至顶操作，并且更新搜索数据
     *
     * @param memberShareId 买家秀id
     * @return
     */
    int doTop(Long memberShareId);

    /**
     * 对一条买家秀数据取消至顶操作，并且更新搜索数据
     *
     * @param memberShareId
     * @return
     */
    int doCancelTop(Long memberShareId);

    /**
     * 取消绑定设计师，并更新买家秀数据,并且更新搜索数据
     *
     * @param memberShareId 买家秀
     * @return
     */
    int doUnBindDesigner(Long memberShareId);

    /**
     * 根据类型对指定的买家秀进行相应的取消绑定操作，包括商品和设计师
     *
     * @param memberShareId 买家秀
     * @param type          类型 商品:product, 设计师:designer
     * @return
     */
    int doCancelBind(Long memberShareId, String type);

    /**
     * 采纳
     *
     * @return
     */
    int doAccept(Long id, Integer status, String lastModifyMan);

    /**
     * 关注用户发布买家秀提醒
     *
     * @param memberShare
     * @param ip
     */
    void doSendAcceptMsg(Long memberShareId);

    /**
     * 关注用户发布买家秀提醒
     *
     * @param memberShare
     * @param ip
     */
    void doSendShareMsg(MemberShare memberShare, String ip);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 合并数据
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 绑定话题
     *
     * @param id
     * @param topicId
     * @return
     */
    int doBindTopic(Long id, Long topicId);

    /**
     * 绑定该话题的数量
     *
     * @param topicId
     * @return
     */
    int countByTopic(Long topicId);

    /**
     * 更新买家秀用户标示
     *
     * @param id
     * @param role
     * @return
     */
    int updateRole(Long id, Integer role);

    /**
     * 更新买家秀标示
     *
     * @param id
     * @param role
     * @return
     */
    int updateRoleByMemberId(Long id, Integer role);

    /**
     * 更新下载数
     *
     * @param id
     * @return
     */
    int doAddDownCount(Long id);

    /**
     * 更新分享数
     *
     * @param id
     * @return
     */
    int doAddShareCount(Long id);

}
