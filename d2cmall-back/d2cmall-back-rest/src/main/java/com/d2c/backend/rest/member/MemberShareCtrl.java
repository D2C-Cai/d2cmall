package com.d2c.backend.rest.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberShare;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.MemberShareService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductShareRelationService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/membershare")
public class MemberShareCtrl extends BaseCtrl<MemberShareSearcher> {

    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductShareRelationService productShareRelationService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;

    @Override
    protected List<Map<String, Object>> getRow(MemberShareSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(MemberShareSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(MemberShareSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberShare> pager = memberShareService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doList(MemberShareSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (StringUtils.isNotBlank(searcher.getSourceId())) {
            String[] sourceIds = searcher.getSourceId().split(",");
            List<Long> list = new ArrayList<>();
            for (String sourceId : sourceIds) {
                if (StringUtils.isNotBlank(sourceId)) {
                    list.add(Long.parseLong(sourceId));
                }
            }
            searcher.setSourceIds(list);
        }
        PageResult<MemberShare> pager = memberShareService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        return new SuccessResponse(memberShareService.findById(id));
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            memberShareService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        memberShareService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        data.remove("path");
        data.remove("picsToArray");
        MemberShare memberShare = JsonUtil.instance().toObject(data, MemberShare.class);
        BeanUt.trimString(memberShare);
        if (memberShare.getId() == null) {
            return new ErrorResponse("没有带id参数");
        }
        memberShareService.update(memberShare);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 商品选择帮助
     *
     * @param productSearcher
     * @param pager
     * @param model
     * @return
     */
    @RequestMapping(value = "/product/help", method = RequestMethod.POST)
    public Response showProductHelp(Long shareId) {
        List<Product> products = new ArrayList<>();
        if (shareId != null && shareId > 0) {
            // 默认客户端上次9+运营
            List<ProductShareRelation> relations = productShareRelationService.findByShareId(shareId, 40);
            for (ProductShareRelation relation : relations) {
                Product product = productService.findById(relation.getProductId());
                products.add(product);
            }
        }
        SuccessResponse response = new SuccessResponse();
        response.put("product", products);
        return response;
    }

    /**
     * 设计师选择帮助
     *
     * @param model
     * @param page
     * @param searcher
     * @param pageGroup
     * @return
     */
    @RequestMapping(value = "/designer/help", method = RequestMethod.POST)
    public Response showDesignerHelp(Long shareId) {
        Brand brand = null;
        if (shareId != null && shareId > 0) {
            MemberShare memberShare = memberShareService.findById(shareId);
            if (memberShare != null) {
                brand = brandService.findById(memberShare.getDesignerId());
            }
        }
        SuccessResponse response = new SuccessResponse();
        response.put("designer", brand);
        return response;
    }

    @RequestMapping(value = "/verify/{id}", method = RequestMethod.POST)
    public Response verify(@PathVariable("id") Long id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = memberShareService.doVerify(id, admin.getUsername());
        if (success == 1) {
            MemberShare memberShare = memberShareService.findById(id);
            if (memberShare.getCancelDate() == null) {
                memberShareService.doSendShareMsg(memberShare, null);
            }
        }
        return result;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Response verify(Long[] ids, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            int success = memberShareService.doVerify(id, admin.getUsername());
            if (success == 1) {
                MemberShare memberShare = memberShareService.findById(id);
                if (memberShare.getCancelDate() == null) {
                    memberShareService.doSendShareMsg(memberShare, null);
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/cancelVerify", method = RequestMethod.POST)
    public Response cancelVerify(Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            memberShareService.doCancelVerify(id, admin.getUsername());
        }
        return result;
    }

    /**
     * 绑定url到买家秀
     *
     * @param id
     * @param url
     * @return
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public Response bind(Long id, String url) {
        SuccessResponse result = new SuccessResponse();
        memberShareService.updateUrl(id, url);
        return result;
    }

    /**
     * 买家秀删除关联的商品或者设计师
     *
     * @param type product=商品，designer=设计师
     * @param ids
     * @return
     */
    @RequestMapping(value = "/cancelbind", method = RequestMethod.POST)
    public Response cancelBind(String type, Long[] ids) {
        if (type == null || ids == null || ids.length == 0) {
            return new ErrorResponse("参数不正确");
        }
        for (Long id : ids) {
            memberShareService.doCancelBind(id, type);
        }
        return new SuccessResponse();
    }

    /**
     * 买家秀置顶
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/toTop/{id}", method = RequestMethod.POST)
    public Response toTop(@PathVariable("id") Long id) {
        memberShareService.doTop(id);
        return new SuccessResponse();
    }

    /**
     * 买家秀取消置顶
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/toCancelTop/{id}", method = RequestMethod.POST)
    public Response toCancelTop(@PathVariable("id") Long id) {
        memberShareService.doCancelTop(id);
        return new SuccessResponse();
    }

    /**
     * 买家秀关联商品和设计师
     *
     * @param model
     * @param id
     * @param designerId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/relation/bind", method = RequestMethod.POST)
    public Response updateBindStatus(Long[] ids, Long designerId, Long productId) {
        SuccessResponse response = new SuccessResponse();
        for (Long id : ids) {
            memberShareService.updateBindStatus(id, productId, designerId);
        }
        return response;
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    public Response accept(@PathVariable Long id, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = memberShareService.doAccept(id, status, admin.getUsername());
        if (success == 0) {
            result.setStatus(-1);
            result.setMessage("买家秀不存在或不是体检分享。");
        } else {
            memberShareService.doSendAcceptMsg(id);
        }
        return result;
    }

    @RequestMapping(value = "/refuse/{id}", method = RequestMethod.POST)
    public Response refuse(@PathVariable Long id, String refuseReason) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = memberShareService.doRefuse(id, refuseReason, admin.getUsername());
        if (success == 0) {
            result.setStatus(-1);
            result.setMessage("已审核的买家秀无法拒绝。");
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/bindtopic/{id}", method = RequestMethod.POST)
    public Response bindTopic(@PathVariable Long id, Long topicId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        memberShareService.doBindTopic(id, topicId);
        return result;
    }

    @RequestMapping(value = "/bindtopic", method = RequestMethod.POST)
    public Response bindTopic(Long[] ids, Long topicId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        for (Long id : ids) {
            memberShareService.doBindTopic(id, topicId);
        }
        return result;
    }

    @RequestMapping(value = "/batch/product", method = RequestMethod.POST)
    public Response batchBindProduct(Long[] ids, Long[] productIds) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        Product p = productService.findById(productIds[0]);
        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; j < productIds.length; j++) {
                productShareRelationService.doReplace(new ProductShareRelation(productIds[j], ids[i]));
            }
            List<Long> productIdList = productShareRelationService.findProductIdByShareId(ids[i]);
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(ids[i]);
            sms.setProductIds(StringUtil.longListToStr(productIdList));
            memberShareSearcherService.update(sms);
            memberShareService.updateDesignerId(ids[i], p.getDesignerId());
        }
        return result;
    }

    @RequestMapping(value = "/delete/product", method = RequestMethod.POST)
    public Response unBindProduct(Long shareId, Long productId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int success = productShareRelationService.deleteRelation(shareId, productId);
        if (success > 0) {
            List<Long> productIds = productShareRelationService.findProductIdByShareId(shareId);
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(shareId);
            sms.setProductIds(StringUtil.longListToStr(productIds));
            memberShareSearcherService.update(sms);
        }
        return result;
    }

}
