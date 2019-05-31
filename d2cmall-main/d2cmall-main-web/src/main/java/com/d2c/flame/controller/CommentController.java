package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Comment;
import com.d2c.member.model.Comment.CommentSource;
import com.d2c.member.model.CommentReply;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.query.CommentSearchBean;
import com.d2c.member.search.service.CommentReplySearcherService;
import com.d2c.member.search.service.CommentSearcherService;
import com.d2c.member.service.CommentReplyService;
import com.d2c.member.service.CommentService;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.O2oSubscribeService;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.service.ProductSummaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/comment")
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
    private OrderItemService orderItemService;
    @Autowired
    private O2oSubscribeService o2oSubscribeService;
    @Autowired
    private ProductSummaryService productSummaryService;

    /**
     * 商品评论列表
     *
     * @param model
     * @param page
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String productComment(ModelMap model, PageModel page, @PathVariable Long productId) {
        CommentSearchBean searchBean = new CommentSearchBean();
        searchBean.setProductId(productId);
        searchBean.setVerified(true);
        try {
            searchBean.setMemberId(this.getLoginMemberInfo().getId());
            searchBean.setAllList(true);
        } catch (NotLoginException e) {
        }
        PageResult<CommentDto> pager = getComments(page, searchBean);
        model.put("searcher", searchBean);
        model.put("pager", pager);
        return "product/comment";
    }

    /**
     * 我的评论列表
     *
     * @param searcher
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String myComment(CommentSearcher searcher, PageModel page, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CommentSearchBean searchBean = new CommentSearchBean();
        BeanUtils.copyProperties(searcher, searchBean);
        searchBean.setMemberId(memberInfo.getId());
        PageResult<CommentDto> pager = getComments(page, searchBean);
        model.put("pager", pager);
        model.put("searcher", searchBean);
        return "member/my_comment";
    }

    private PageResult<CommentDto> getComments(PageModel page, CommentSearchBean searchBean) {
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
        PageResult<CommentDto> pager = new PageResult<>();
        BeanUtils.copyProperties(commentsPager, pager, "list");
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            CommentDto comDto = new CommentDto();
            BeanUtils.copyProperties(comment, comDto);
            List<CommentReply> commentReplys = replyMap.get(comDto.getId());
            if (commentReplys == null)
                commentReplys = new ArrayList<>();
            comDto.setCommentReplys(commentReplys);
            pager.getList().add(comDto);
        }
        return pager;
    }

    /**
     * 订单明细评价编辑
     *
     * @param model
     * @param orderItemId
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/item/edit", method = RequestMethod.GET)
    public String itemEdit(ModelMap model, Long orderItemId, Long commentId) {
        Long memberInfoId = this.getLoginMemberInfo().getId();
        Comment comment = null;
        if (commentId != null) {
            comment = commentService.findByIdAndMemberId(commentId, memberInfoId);
            model.put("action", "/comment/item/update");
        } else {
            comment = new Comment();
            // 新ui界面 评论页面需显示商品信息
            OrderItem orderItem = orderItemService.findById(orderItemId);
            model.put("orderItem", orderItem);
            model.put("action", "/comment/item/insert");
        }
        model.put("comment", comment);
        model.put("orderItemId", orderItemId);
        return "order/orderitem_comment";
    }

    /**
     * 订单明细评价新增
     *
     * @param model
     * @param orderItemId
     * @param comment
     * @param path
     * @param anonymous
     * @return
     */
    @RequestMapping(value = "/item/insert", method = RequestMethod.POST)
    public String itemInsert(ModelMap model, Long orderItemId, Comment comment, String[] path, Integer anonymous) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        comment.initComment(memberInfo);
        if (anonymous != null && anonymous.intValue() == 1) {
            comment.setNickName("匿名");
        }
        // 商品评价小于等于3默认不通过
        if (comment.getProductScore() != null && comment.getProductScore().intValue() <= 3) {
            comment.setVerified(false);
        } else {
            comment.setVerified(true);
        }
        OrderItem orderItem = orderItemService.findById(orderItemId);
        comment.setTitle(orderItem.getProductName());
        comment.setSourceId(orderItem.getId());
        comment.setProductId(orderItem.getProductId());
        comment.setProductSkuId(orderItem.getProductSkuId());
        comment.setSkuProperty("颜色:" + orderItem.getSp1Value() + ", 尺码:" + orderItem.getSp2Value());
        comment.setSource(CommentSource.ORDERITEM.toString());
        comment.setDesignerId(orderItem.getDesignerId());
        comment.setPic(StringUtils.join(path, ","));
        comment.setProductImg(orderItem.getSp1Img());
        comment.setScore(comment.getScore() == null ? 1 : comment.getScore());
        if (isNormalDevice()) {
            comment.setDevice(DeviceTypeEnum.PC.toString());
        } else {
            comment.setDevice(DeviceTypeEnum.MOBILE.toString());
        }
        comment.setAppVersion(VERSION);
        this.checkSensitiveWords(comment.getContent());
        comment = commentService.insert(comment);
        int success = orderItemService.doComment(orderItem.getId(), comment.getId());
        if (success > 0 && comment.getVerified() == true) {
            productSummaryService.updateCommentsCount(1, comment.getProductId());
        }
        return "";
    }

    /**
     * 订单明细评价更新
     *
     * @param model
     * @param comment
     * @return
     */
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    public String itemUpdate(ModelMap model, Comment comment) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        this.getLoginMemberInfo();
        commentService.update(comment);
        return "";
    }

    /**
     * 预约单评价编辑
     *
     * @param model
     * @param o2oSubscribeId
     * @param productImg
     * @param skuProperty
     * @return
     */
    @RequestMapping(value = "/o2o/edit", method = RequestMethod.GET)
    public String o2oEdit(ModelMap model, Long o2oSubscribeId, String productImg, String skuProperty) {
        Comment comment = new Comment();
        comment.setProductId(0L);
        comment.setProductImg(productImg);
        comment.setSkuProperty(skuProperty);
        model.put("comment", comment);
        model.put("o2oSubscribeId", o2oSubscribeId);
        return "/o2o/subscribe_comment";
    }

    /**
     * 预约单评价新增
     *
     * @param model
     * @param o2oSubscribeId
     * @param comment
     * @param cusCost
     * @return
     */
    @RequestMapping(value = "/o2o/insert", method = RequestMethod.POST)
    public String o2oInsert(ModelMap model, Long o2oSubscribeId, Comment comment, BigDecimal cusCost) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        comment.initComment(memberInfo);
        comment.setSourceId(o2oSubscribeId);
        comment.setSource(CommentSource.O2OSUBSCRIBE.toString());
        comment.setScore(comment.getScore() == null ? 1 : comment.getScore());
        if (isNormalDevice()) {
            comment.setDevice(DeviceTypeEnum.PC.toString());
        } else {
            comment.setDevice(DeviceTypeEnum.MOBILE.toString());
        }
        comment.setAppVersion(VERSION);
        this.checkSensitiveWords(comment.getContent());
        comment = commentService.insert(comment);
        int success = o2oSubscribeService.updateCusCostById(o2oSubscribeId, 6, comment.getId(), cusCost);
        if (success > 0) {
            productSummaryService.updateCommentsCount(1, comment.getProductId());
        }
        return "";
    }

    /**
     * 追评编辑
     *
     * @param model
     * @param commentId
     * @param replyId
     * @return
     */
    @RequestMapping(value = "/reply/edit", method = RequestMethod.GET)
    public String replyEdit(ModelMap model, Long commentId, Long replyId) {
        this.getLoginMemberInfo();
        CommentReply commentReply = commentReplyService.findById(replyId);
        model.put("commentReply", commentReply);
        model.put("commentId", commentId);
        model.put("replyId", replyId);
        return "member/comment_reply";
    }

    /**
     * 追评保存
     *
     * @param model
     * @param commentReply
     * @return
     */
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public String reply(ModelMap model, CommentReply commentReply) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        commentReply.setType(CommentReply.ReplyType.CUSTOMER.name());
        commentReply.setCreator(memberInfo.getLoginCode());
        if (!commentReply.getContent().isEmpty() && commentReply.getCommentId() != null
                && commentReply.getReplyId() != null) {
            this.checkSensitiveWords(commentReply.getContent());
            commentReply = commentReplyService.insert(commentReply);
            if (commentReply == null || commentReply.getId() == null) {
                throw new BusinessException("追评不成功！");
            }
        }
        model.put("result", result);
        return "";
    }

}
