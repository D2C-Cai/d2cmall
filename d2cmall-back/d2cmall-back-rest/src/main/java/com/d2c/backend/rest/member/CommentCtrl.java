package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.service.SensitiveWordsService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Comment;
import com.d2c.member.model.Comment.CommentSource;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberShare;
import com.d2c.member.model.MemberShare.ResourceType;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.service.CommentService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberShareService;
import com.d2c.member.service.MemberShareTagRelationService;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductSummaryService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/society/comment")
public class CommentCtrl extends BaseCtrl<CommentSearcher> {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSummaryService productSummaryService;
    @Autowired
    private SensitiveWordsService sensitiveWordsService;
    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberShareTagRelationService memberShareTagRelationService;

    @Override
    protected List<Map<String, Object>> getRow(CommentSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        searcher.setSource(CommentSource.ORDERITEM.name());
        PageResult<CommentDto> pager = commentService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (CommentDto comment : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("创建日期", sdf.format(comment.getCreateDate()));
            cellsMap.put("用户名", comment.getName());
            cellsMap.put("评论内容", comment.getContent());
            cellsMap.put("商品", comment.getTitle());
            cellsMap.put("商品评分", comment.getProductScore());
            cellsMap.put("送货速度评分", comment.getDeliverySpeedScore());
            cellsMap.put("配送服务评分", comment.getDeliveryServiceScore());
            cellsMap.put("商品包装评分", comment.getPackageScore());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(CommentSearcher searcher) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        searcher.setSource(CommentSource.ORDERITEM.name());
        return commentService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "商品评论表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"创建日期", "用户名", "评论内容", "商品", "商品评分", "送货速度评分", "配送服务评分", "商品包装评分"};
    }

    @Override
    protected Response doHelp(CommentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CommentSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<CommentDto> pager = commentService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        Map<String, Object> count = commentService.countGroupByStatus();
        result.put("count", count);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            Comment comment = commentService.findById(id);
            commentService.delete(comment.getProductId(), id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        Comment comment = commentService.findById(id);
        commentService.delete(comment.getProductId(), id);
        productSummaryService.updateCommentsCount(-1, comment.getProductId());
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Comment comment = (Comment) JsonUtil.instance().toObject(data, Comment.class);
        if (comment.getCreateDate() != null && comment.getCreateDate().after(new Date(System.currentTimeMillis()))) {
            result.setStatus(-1);
            result.setMessage("评论时间不能大于当前时间！");
            return result;
        }
        comment.setSource(CommentSource.PRODUCTSKU.toString());
        ProductSku productSKU = productSkuService.findById(comment.getProductSkuId());
        comment.setSkuProperty("颜色:" + productSKU.getColorValue() + ", 尺码:" + productSKU.getSizeValue());
        comment.setVerified(true);
        Product product = productService.findById(comment.getProductId());
        comment.setTitle(product.getName());
        comment.setDesignerId(product.getDesignerId());
        if (StringUtils.isBlank(comment.getPic())) {
            comment.setPic(null);
        }
        try {
            comment = commentService.insert4Back(comment);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
            return result;
        }
        result.put("comment", comment);
        productSummaryService.updateCommentsCount(1, comment.getProductId());
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Comment comment = (Comment) JsonUtil.instance().toObject(data, Comment.class);
        commentService.update(comment);
        result.put("data", comment);
        return result;
    }

    @Override
    protected String getExportFileType() {
        return "Comment";
    }

    @RequestMapping(value = "/audit/{status}", method = RequestMethod.POST)
    public Response updateStatus(Long[] ids, @PathVariable Integer status) {
        for (Long id : ids) {
            Comment comment = commentService.findById(id);
            int result = commentService.updateStatus(id, comment.getProductId(), status);
            if (result > 0) {
                if (status == 1) {
                    productSummaryService.updateCommentsCount(1, comment.getProductId());
                }
                if (status == 0) {
                    productSummaryService.updateCommentsCount(-1, comment.getProductId());
                }
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/top/{top}", method = RequestMethod.POST)
    public Response updateTop(Long[] ids, @PathVariable Integer top) {
        SuccessResponse successResponse = new SuccessResponse();
        commentService.updateTop(ids, top);
        return successResponse;
    }

    /**
     * 把评论转为买家秀
     *
     * @param ids
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/toshare", method = RequestMethod.POST)
    public Response toShare(Long[] ids) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse successResponse = new SuccessResponse();
        for (Long id : ids) {
            Comment comment = commentService.findById(id);
            if (StringUtils.isNotBlank(comment.getPic())) {
                MemberShare share = new MemberShare();
                share.setMemberId(comment.getMemberId());
                MemberInfo memberInfo = memberInfoService.findById(comment.getMemberId());
                if (memberInfo.getDesignerId() != null && memberInfo.getDesignerId() > 0) {
                    // 设置为设计师
                    share.setRole(1);
                }
                share.setNickname(comment.getNickName());
                share.setHeadPic(comment.getHeadPic());
                share.setPic(comment.getPic());
                share.setDescription(comment.getContent());
                share.setStatus(1);
                share.setVerifyDate(new Date());
                share.setVerifyMan(admin.getUsername());
                share.setName(comment.getName());
                share.setMobile(comment.getTel());
                share.setEmail(comment.getEmail());
                share.setProductId(comment.getProductId());
                share.setComments(0);
                share.setType(2);
                share.setResourceType(ResourceType.pic.toString());
                if (comment.getProductId() != null) {
                    share.setUrl("http://www.d2cmall.com/product/" + comment.getProductId());
                }
                share = memberShareService.insert(share);
                if (share.getId() != null) {
                    commentService.updateShareId(id, share.getId());
                }
                if (memberInfo.getDesignerId() != null) {
                    memberShareTagRelationService.insert(share.getId(), new Long[]{7L});
                }
                // 发送推送消息
                memberShareService.doSendShareMsg(share, null);
            } else {
                successResponse.setStatus(-1);
                successResponse.setMessage("只有带图片的评论才可以转为买家秀。");
            }
        }
        return successResponse;
    }

    /**
     * 导入评论表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response importComment(HttpServletRequest request) {
        this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String commentDate = String.valueOf(map.get("评论时间"));
                String productSn = String.valueOf(map.get("货号"));
                String barCode = String.valueOf(map.get("SKU"));
                String content = String.valueOf(map.get("评论语"));
                String loginCode = String.valueOf(map.get("D2C账号"));
                Date cDate = DateUtil.str2second(commentDate);
                if (cDate.after(new Date())) {
                    errorMsg.append("评论时间：必须小于等于当前时间<br/>");
                    return false;
                }
                Product product = productService.findByProductSn(productSn);
                if (product == null) {
                    errorMsg.append("货号：" + productSn + "不存在<br/>");
                    return false;
                }
                ProductSku productSku = productSkuService.findByBarCode(barCode);
                if (productSku == null) {
                    errorMsg.append("条码：" + barCode + "不存在<br/>");
                    return false;
                }
                if (sensitiveWordsService.findBySensitiveWords(content)) {
                    errorMsg.append("评论内容中含有敏感词<br/>");
                    return false;
                }
                Comment comment = new Comment();
                comment.setCreateDate(cDate);
                comment.setSource(CommentSource.PRODUCTSKU.name());
                comment.setName(loginCode);
                comment.setContent(content);
                comment.setVerified(true);
                comment.setTitle(product.getInernalSn());
                comment.setProductImg(product.getProductImageCover());
                comment.setProductScore(5);
                comment.setPackageScore(5);
                comment.setDeliverySpeedScore(5);
                comment.setDeliveryServiceScore(5);
                comment.setProductId(product.getId());
                comment.setProductSkuId(productSku.getId());
                comment.setSkuProperty("颜色:" + productSku.getColorValue() + ", 尺码:" + productSku.getSizeValue());
                comment.setDesignerId(product.getDesignerId());
                commentService.insert4Back(comment);
                return true;
            }
        });
    }

}
