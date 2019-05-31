package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberAttention;
import com.d2c.member.model.MemberCollection;
import com.d2c.member.model.MemberFollow;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.member.service.MemberAttentionService;
import com.d2c.member.service.MemberCollectionService;
import com.d2c.member.service.MemberFollowService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member/interest")
public class MyInterestController extends BaseController {

    @Autowired
    private MemberAttentionService memberAttentionService;
    @Autowired
    private MemberCollectionService memberCollectionService;
    @Autowired
    private MemberFollowService memberFollowService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 我关注的设计师
     *
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/attention/list", method = RequestMethod.GET)
    public String attentionList(PageModel page, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<MemberAttention> pager = memberAttentionService.findByMemberId(memberInfo.getId(), page);
        model.put("pager", pager);
        return "member/attention_designer_list";
    }

    /**
     * 关注设计师
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/attention/insert/{designerId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String attentionInsert(@PathVariable Long designerId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberAttention memberAttention = new MemberAttention();
        memberAttention.setMemberId(memberInfo.getId());
        memberAttention.setNickName(memberInfo.getDisplayName());
        memberAttention.setHeadPic(memberInfo.getHeadPic());
        Brand brand = brandService.findById(designerId);
        memberAttention.setDesignerId(designerId);
        memberAttention.setDesignerName(brand.getName());
        memberAttention.setDesignerPic(brand.getHeadPic());
        try {
            memberAttentionService.insert(memberAttention);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 取消关注设计师
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/attention/delete/{designerId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String attentionDelete(@PathVariable Long designerId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.setMsg("取消关注成功！");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberAttentionService.delete(memberInfo.getId(), designerId);
        return "";
    }

    /**
     * 检验关注设计师
     *
     * @param designerId
     * @param model
     * @return
     */
    @RequestMapping(value = "/attention/check/{designerId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String attentionCheck(@PathVariable Long designerId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberAttention memberAttention = memberAttentionService.findByMemberAndDesignerId(memberInfo.getId(),
                designerId);
        if (memberAttention == null) {
            throw new BusinessException();
        }
        return "";
    }

    /**
     * 我收藏的商品
     *
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/collection/list", method = RequestMethod.GET)
    public String collectionList(PageModel page, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<MemberCollection> pager = memberCollectionService.findByMemberId(memberInfo.getId(), page);
        model.put("pager", pager);
        return "member/collect_product_list";
    }

    /**
     * 收藏商品
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping(value = "/collection/insert/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String collectionInsert(@PathVariable Long productId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        result.setMessage("收藏成功");
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberCollection memberCollection = new MemberCollection();
        memberCollection.setMemberId(memberInfo.getId());
        memberCollection.setNickName(memberInfo.getDisplayName());
        memberCollection.setHeadPic(memberInfo.getHeadPic());
        Product product = productService.findById(productId);
        memberCollection.setProductId(productId);
        memberCollection.setProductName(product.getName());
        memberCollection.setProductInernalSN(product.getInernalSn());
        memberCollection.setProductPic(product.getProductImageListFirst());
        memberCollection.setDesigners(product.getDesignerName());
        memberCollection.setProductPrice(product.getOriginalPrice());
        SearcherProduct searcherProduct = productSearcherQueryService.findById(String.valueOf(productId));
        if (searcherProduct != null) {
            memberCollection.setProductPrice(searcherProduct.getCurrentPrice());
        }
        try {
            memberCollectionService.insert(memberCollection);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 取消收藏商品
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping(value = "/collection/delete/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String collectionDelete(@PathVariable Long productId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.setMsg("取消收藏成功！");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberCollectionService.delete(memberInfo.getId(), productId);
        return "";
    }

    /**
     * 检验收藏商品
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping(value = "/collection/check/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String collectionCheck(@PathVariable Long productId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberCollection memberCollection = memberCollectionService.findByMemberAndProductId(memberInfo.getId(),
                productId);
        if (memberCollection == null) {
            throw new BusinessException();
        }
        return "";
    }

    /**
     * 关注用户
     *
     * @param memberId
     * @param model
     * @return
     */
    @RequestMapping(value = "/follow/insert/{memberId}", method = RequestMethod.POST)
    public String followInsert(@PathVariable Long memberId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.setMsg("关注成功！");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getId().equals(memberId)) {
            throw new BusinessException("不能关注自己！");
        }
        MemberFollow memberFollow = new MemberFollow();
        memberFollow.setFromId(memberInfo.getId());
        memberFollow.setFromHeadPic(memberInfo.getHeadPic());
        memberFollow.setFromNickName(memberInfo.getDisplayName());
        MemberInfo toInfo = memberInfoService.findById(memberId);
        if (toInfo != null) {
            try {
                memberFollow.setToId(memberId);
                memberFollow.setToHeadPic(toInfo.getHeadPic());
                memberFollow.setToNickName(toInfo.getDisplayName());
                int friends = memberFollowService.insert(memberFollow);
                result.put("friends", friends);
                memberFollowService.doSendFollowMsg(memberId, memberFollow, null);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
        return "";
    }

    /**
     * 取消关注用户
     *
     * @param memberId
     * @param model
     * @return
     */
    @RequestMapping(value = "/follow/delete/{memberId}", method = RequestMethod.POST)
    public String followDelete(@PathVariable Long memberId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.setMsg("取消关注成功！");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberFollowService.delete(memberInfo.getId(), memberId);
        return "";
    }

    /**
     * 我的兴趣数量
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/counts", method = RequestMethod.GET)
    public String itemsCount(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        int collectionCount = memberCollectionSearcherService.countByMemberId(memberInfo.getId());
        int attentionCount = memberAttentionSearcherService.countByMemberId(memberInfo.getId());
        result.put("collectionCount", collectionCount);
        result.put("attentionCount", attentionCount);
        model.put("result", result);
        return "";
    }

}
