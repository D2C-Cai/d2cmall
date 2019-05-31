package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.dto.PromotionTagRelationDto;
import com.d2c.product.model.PromotionTag;
import com.d2c.product.model.PromotionTagRelation;
import com.d2c.product.query.PromotionTagSearcher;
import com.d2c.product.service.PromotionTagRelationService;
import com.d2c.product.service.PromotionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/promotion/tag4promotionrelation")
public class PromotionTagRelationCtrl extends SuperCtrl {

    @Autowired
    private PromotionTagService promotionTagService;
    @Autowired
    private PromotionTagRelationService promotionTagRelationService;

    /**
     * 新增关系
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(Long promotionId, Long[] tagIds) {
        SuccessResponse result = new SuccessResponse();
        promotionTagRelationService.deleteByPromotionId(promotionId);
        for (Long tagId : tagIds) {
            PromotionTagRelation relation = new PromotionTagRelation(promotionId, tagId);
            promotionTagRelationService.insert(relation);
        }
        return result;
    }

    /**
     * 删除关系
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(Long tagId, Long[] promotionIds) {
        SuccessResponse result = new SuccessResponse();
        for (Long promotionId : promotionIds) {
            promotionTagRelationService.deleteByPromotionIdAndTagId(promotionId, tagId);
        }
        return result;
    }

    /**
     * 标签关联的活动列表
     */
    @RequestMapping(value = "/promotion/list/{tagId}", method = RequestMethod.POST)
    public Response findPromotionsByTagId(@PathVariable Long tagId, PageModel page) {
        PageResult<PromotionTagRelationDto> pager = promotionTagRelationService.findByTagId(tagId, page);
        return new SuccessResponse(pager);
    }

    /**
     * 活动关联的标签列表
     */
    @RequestMapping(value = "/tag/list/{promotionId}", method = RequestMethod.POST)
    public Response findTagsByPromotionId(@PathVariable long promotionId) {
        SuccessResponse result = new SuccessResponse();
        PageResult<PromotionTag> pager = promotionTagService.findBySearch(new PromotionTagSearcher(),
                new PageModel(1, 500));
        List<PromotionTagRelation> alreadyBind = promotionTagRelationService.findByPromotionId(promotionId);
        result.put("tagList", pager.getList());
        result.put("alreadyBind", alreadyBind);
        return result;
    }

    /**
     * 排序
     *
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        promotionTagRelationService.updateSort(id, sort);
        return result;
    }

}
