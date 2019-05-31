package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Comment;
import com.d2c.member.model.Comment.CommentSource;
import com.d2c.member.model.CommentReply;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.query.CommentSearchBean;
import com.d2c.member.search.service.CommentReplySearcherService;
import com.d2c.member.search.service.CommentSearcherService;
import com.d2c.member.service.CommentReplyService;
import com.d2c.member.service.CommentService;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.service.ProductSummaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户评价
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentReplyService commentReplyService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Reference
    private CommentReplySearcherService commentReplySearcherService;
    @Autowired
    private ProductSummaryService productSummaryService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UpyunTaskService upyunTaskService;

    /**
     * 我的评价列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult myComment(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        CommentSearchBean searchBean = new CommentSearchBean();
        searchBean.setMemberId(member.getId());
        getComments(page, result, searchBean);
        return result;
    }

    private void getComments(PageModel page, ResponseResult result, CommentSearchBean searchBean) {
        JSONArray commentArray = new JSONArray();
        PageResult<SearcherComment> commentsPager = commentSearcherService.search(searchBean, page);
        CommentSearchBean replyBean = new CommentSearchBean();
        replyBean.setCommentIds(new long[commentsPager.getList().size()]);
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            replyBean.getCommentIds()[i] = comment.getId();
        }
        PageResult<SearcherCommentReply> replyPager = commentReplySearcherService.search(replyBean, page);
        HashMap<Long, List<CommentReply>> replyMap = new HashMap<>();
        for (int j = 0; j < replyPager.getList().size(); j++) {
            SearcherCommentReply replySearcher = replyPager.getList().get(j);
            CommentReply reply = new CommentReply();
            BeanUtils.copyProperties(replySearcher, reply);
            if (replyMap.get(reply.getCommentId()) == null) {
                replyMap.put(reply.getCommentId(), new ArrayList<CommentReply>());
            }
            replyMap.get(reply.getCommentId()).add(reply);
        }
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            CommentDto comDto = new CommentDto();
            BeanUtils.copyProperties(comment, comDto);
            List<CommentReply> commentReplys = replyMap.get(comDto.getId());
            if (commentReplys == null)
                commentReplys = new ArrayList<>();
            comDto.setCommentReplys(commentReplys);
            commentArray.add(comDto.toJson());
        }
        result.putPage("comments", commentsPager, commentArray);
    }

    /**
     * 评价详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult comment(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        try {
            CommentDto commentDto = commentService.findByIdAndMemberId(id, member.getId());
            result.put("comment", commentDto.toJson());
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        return result;
    }

    /**
     * 评价新增
     *
     * @param orderItemId
     * @param comment
     * @param appTerminal
     * @param appVersion
     * @param taskIds
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/item/insert/{orderItemId}", method = RequestMethod.POST)
    public ResponseResult commentInsert(@PathVariable Long orderItemId, Comment comment, String appTerminal,
                                        String appVersion, String taskIds) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        comment.initComment(member);
        // 商品评价小于等于3默认不通过
        if (comment.getProductScore() != null && comment.getProductScore().intValue() <= 3) {
            comment.setVerified(false);
        } else {
            comment.setVerified(true);
        }
        OrderItem orderItem = orderItemService.findById(orderItemId);
        if (StringUtils.isBlank(comment.getPic())) {
            comment.setPic(null);
        }
        comment.setTitle(orderItem.getProductName());
        comment.setSourceId(orderItem.getId());
        comment.setProductId(orderItem.getProductId());
        comment.setProductSkuId(orderItem.getProductSkuId());
        comment.setSkuProperty("颜色:" + orderItem.getSp1Value() + ", 尺码:" + orderItem.getSp2Value());
        comment.setSource(CommentSource.ORDERITEM.toString());
        comment.setDesignerId(orderItem.getDesignerId());
        comment.setProductImg(orderItem.getSp1Img());
        comment.setScore(comment.getScore() == null ? 1 : comment.getScore());
        comment.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
        comment.setAppVersion(appVersion);
        this.checkSensitiveWords(comment.getContent());
        comment = commentService.insert(comment);
        if (StringUtils.isNotBlank(taskIds) && comment.getId() != null) {
            // 短视频转码任务
            processTaskIds(taskIds, comment.getId());
        }
        int success = orderItemService.doComment(orderItem.getId(), comment.getId());
        if (success > 0 && comment.getVerified() == true) {
            productSummaryService.updateCommentsCount(1, comment.getProductId());
        }
        result.put("commentId", comment.getId());
        return result;
    }

    /**
     * 更新评价图片
     *
     * @param id
     * @param pic
     * @return
     */
    @RequestMapping(value = "/item/update/{id}", method = RequestMethod.POST)
    public ResponseResult updatePic(@PathVariable Long id, String pic) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        commentService.updatePic(id, pic);
        return result;
    }

    /**
     * 处理短视频任务
     *
     * @param taskIds
     * @param shareId
     */
    private void processTaskIds(String taskIds, Long shareId) {
        String[] taskIdss = taskIds.split(",");
        for (String taskId : taskIdss) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskId);
            upyunTask.setSourceType(SourceType.COMMENT.toString());
            upyunTask.setStatus(0);
            upyunTask.setSourceId(shareId);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                commentService.updateVideoById(shareId, upyunTask.getVideo());
            }
        }
    }

    /**
     * 追评新增
     *
     * @param commentId
     * @param commentReply
     * @return
     */
    @RequestMapping(value = "/item/additional/{commentId}", method = RequestMethod.POST)
    public ResponseResult itemAdditional(@PathVariable Long commentId, CommentReply commentReply) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        if (StringUtils.isBlank(commentReply.getContent())) {
            throw new BusinessException("追评内容为空！");
        }
        commentReply.setType(CommentReply.ReplyType.CUSTOMER.name());
        commentReply.setLastModifyMan(member.getDisplayName());
        commentReply.setCommentId(commentId);
        this.checkSensitiveWords(commentReply.getContent());
        commentReplyService.insert(commentReply);
        return result;
    }

}
