package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.*;
import com.d2c.member.search.model.SearcherMemberAttention;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.*;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
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

/**
 * 我的兴趣
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/myinterest")
public class MyInterestController extends BaseController {

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberAttentionService memberAttentionService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Autowired
    private MemberCollectionService memberCollectionService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Autowired
    private MemberFollowService memberFollowService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DesignersService designersService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;

    /**
     * 我的粉丝
     *
     * @param memberId
     * @param page
     * @return
     */
    @RequestMapping(value = "/myfans", method = RequestMethod.GET)
    public ResponseResult myFans(Long memberId, PageModel page) {
        ResponseResult result = new ResponseResult();
        Long myMemberId = null;
        try {
            myMemberId = this.getLoginMemberInfo().getId();
        } catch (NotLoginException e) {
        }
        if (memberId == null || memberId <= 0) {
            memberId = myMemberId;
        }
        PageResult<SearcherMemberFollow> pager = memberFollowSearcherService.findByToId(memberId, null, page);
        JSONArray arry = new JSONArray();
        if (memberId.equals(myMemberId)) {
            for (SearcherMemberFollow follow : pager.getList()) {
                JSONObject obj = follow.toFromJson();
                this.findByMemberId(obj, follow.getFromId());
                arry.add(obj);
            }
        } else {
            String[] fromIds = new String[pager.getList().size()];
            for (int i = 0; i < pager.getList().size(); i++) {
                fromIds[i] = myMemberId + "_" + pager.getList().get(i).getFromId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(fromIds);
            for (SearcherMemberFollow follow : pager.getList()) {
                JSONObject obj = follow.toFromJson();
                this.findByMemberId(obj, follow.getFromId());
                if (memberFollows.containsKey(follow.getFromId())) {
                    obj.put("follow", memberFollows.get(follow.getFromId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                arry.add(obj);
            }
        }
        result.putPage("myFans", pager, arry);
        return result;
    }

    /**
     * 我的关注
     *
     * @param memberId
     * @param page
     * @return
     */
    @RequestMapping(value = "/myfollows", method = RequestMethod.GET)
    public ResponseResult myFollows(Long memberId, PageModel page) {
        ResponseResult result = new ResponseResult();
        Long myMemberId = null;
        try {
            myMemberId = this.getLoginMemberInfo().getId();
        } catch (NotLoginException e) {
        }
        if (memberId == null || memberId <= 0) {
            memberId = myMemberId;
        }
        PageResult<SearcherMemberFollow> pager = memberFollowSearcherService.findByFromId(memberId, page);
        JSONArray arry = new JSONArray();
        if (memberId.equals(myMemberId)) {
            for (SearcherMemberFollow follow : pager.getList()) {
                JSONObject jsonObject = follow.toToJson();
                this.findByMemberId(jsonObject, follow.getToId());
                arry.add(jsonObject);
            }
        } else {
            String[] toIds = new String[pager.getList().size()];
            for (int i = 0; i < pager.getList().size(); i++) {
                toIds[i] = myMemberId + "_" + pager.getList().get(i).getToId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(toIds);
            for (SearcherMemberFollow follow : pager.getList()) {
                JSONObject obj = follow.toToJson();
                this.findByMemberId(obj, follow.getToId());
                if (memberFollows.containsKey(follow.getToId())) {
                    obj.put("follow", memberFollows.get(follow.getToId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                arry.add(obj);
            }
        }
        result.putPage("myFollows", pager, arry);
        return result;
    }

    /**
     * 关注用户
     *
     * @param toMemberId
     * @return
     */
    @RequestMapping(value = "/follow/insert", method = RequestMethod.POST)
    public ResponseResult followInsert(Long toMemberId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getId().equals(toMemberId)) {
            throw new BusinessException("不能关注自己！");
        }
        MemberFollow memberFollow = new MemberFollow();
        memberFollow.setFromId(memberInfo.getId());
        memberFollow.setFromHeadPic(memberInfo.getHeadPic());
        memberFollow.setFromNickName(memberInfo.getDisplayName());
        MemberInfo toInfo = memberInfoService.findById(toMemberId);
        if (toInfo != null) {
            memberFollow.setToId(toMemberId);
            memberFollow.setToHeadPic(toInfo.getHeadPic());
            memberFollow.setToNickName(toInfo.getDisplayName());
            int friends = memberFollowService.insert(memberFollow);
            result.put("follow", friends == 1 ? SearcherMemberFollow.FollowType.EACHPOWDER.getCode()
                    : SearcherMemberFollow.FollowType.FOLLOWED.getCode());
            memberFollowService.doSendFollowMsg(toMemberId, memberFollow, null);
        }
        return result;
    }

    /**
     * 取消关注
     *
     * @param toMemberId
     * @return
     */
    @RequestMapping(value = "/follow/delete", method = RequestMethod.POST)
    public ResponseResult followDelete(Long toMemberId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberFollowService.delete(memberInfo.getId(), toMemberId);
        return result;
    }

    /**
     * 我关注的品牌
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/attention/list", method = RequestMethod.POST)
    public ResponseResult myAttentions(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<SearcherMemberAttention> pager = memberAttentionSearcherService.findByMemberId(memberInfo.getId(),
                page);
        JSONArray arry = new JSONArray();
        for (SearcherMemberAttention attention : pager.getList()) {
            JSONObject obj = attention.toJson();
            page.setPageSize(4);
            ProductProSearchQuery searcherBean = new ProductProSearchQuery();
            PageResult<SearcherProduct> productPager = productSearcherQueryService
                    .findSaleProductByDesigner(attention.getDesignerId(), page, searcherBean);
            obj.put("products", productPager.getList());
            arry.add(obj);
        }
        result.putPage("myAttentions", pager, arry);
        return result;
    }

    /**
     * 关注品牌
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/attention/insert/{brandId}", method = RequestMethod.POST)
    public ResponseResult attentionInsert(@PathVariable Long brandId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberAttention memberAttention = new MemberAttention();
        memberAttention.setMemberId(memberInfo.getId());
        memberAttention.setNickName(memberInfo.getDisplayName());
        memberAttention.setHeadPic(memberInfo.getHeadPic());
        Brand brand = brandService.findById(brandId);
        memberAttention.setDesignerId(brandId);
        memberAttention.setDesignerName(brand.getName());
        memberAttention.setDesignerPic(brand.getHeadPic());
        memberAttentionService.insert(memberAttention);
        // 关注品牌自动关注该设计师
        Designers designers = null;
        if ((designers = designersService.findById(brand.getDesignersId())) != null) {
            try {
                followInsert(designers.getMemberId());
            } catch (Exception e) {
            }
        }
        result.put("attentioned", 1);
        return result;
    }

    /**
     * 取消关注品牌
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/attention/delete", method = RequestMethod.POST)
    public ResponseResult attentionDelete(Long brandId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberAttentionService.delete(memberInfo.getId(), brandId);
        result.put("attentioned", 0);
        return result;
    }

    /**
     * 我收藏的商品
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/collection/list", method = RequestMethod.GET)
    public ResponseResult myCollection(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<SearcherMemberCollection> pager = memberCollectionSearcherService.findByMemberId(memberInfo.getId(),
                page);
        List<String> productIds = new ArrayList<>();
        pager.getList().forEach(item -> productIds.add(item.getProductId().toString()));
        Map<Long, SearcherProduct> map = productSearcherQueryService
                .findMapByIds(productIds.toArray(new String[productIds.size()]));
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> {
            SearcherProduct product = map.get(item.getProductId());
            if (product != null) {
                JSONObject obj = item.toJson();
                obj.putAll(product.toCollectionJson());
                obj.put("collectionPrice", item.getProductPrice());
                array.add(obj);
            }
        });
        result.putPage("myCollections", pager, array);
        return result;
    }

    /**
     * 收藏商品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/collection/insert", method = RequestMethod.POST)
    public ResponseResult collectionInsert(Long productId) {
        ResponseResult result = new ResponseResult();
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
        memberCollection.setProductPrice(product.getSalePrice());
        SearcherProduct searcherProduct = productSearcherQueryService.findById(String.valueOf(productId));
        if (searcherProduct != null) {
            memberCollection.setProductPrice(searcherProduct.getCurrentPrice());
        }
        memberCollectionService.insert(memberCollection);
        result.put("collectioned", 1);
        return result;
    }

    /**
     * 取消收藏的商品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/collection/delete", method = RequestMethod.POST)
    public ResponseResult collectionDelete(Long productId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberCollectionService.delete(memberInfo.getId(), productId);
        result.put("collectioned", 0);
        return result;
    }

    /**
     * 查询用户最新一条买家秀
     *
     * @param obj
     * @param memberId
     */
    private void findByMemberId(JSONObject obj, Long memberId) {
        SearcherMemberShare share = memberShareSearcherService.findLastedByMemberId(memberId);
        if (StringUtil.isNotBlank(share.getDescription())) {
            obj.put("memberShare", share.getDescription());
        } else if (StringUtils.isNotBlank(share.getPic())) {
            obj.put("memberShare", "发布了图片");
        } else if (StringUtil.isNotBlank(share.getVideo())) {
            obj.put("memberShare", "发布了视频");
        } else {
            obj.put("memberShare", "暂无动态");
        }
    }

}
