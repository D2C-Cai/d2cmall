package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.BrandTagRelationDto;
import com.d2c.product.query.BrandTagRelationSearcher;
import com.d2c.product.service.BrandTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/shop/tag4designerrelation")
public class BrandTagRelationCtrl extends SuperCtrl {

    @Autowired
    private BrandTagRelationService brandTagRelationService;

    /**
     * 新增关系
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(Long[] designerIds, Long[] tagIds) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long designerId : designerIds) {
            brandTagRelationService.insert(designerId, tagIds, admin.getUsername());
        }
        return new SuccessResponse();
    }

    /**
     * 删除关系
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(Long[] designerIds, Long[] tagIds) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long designerId : designerIds) {
            for (Long tagId : tagIds) {
                brandTagRelationService.deleteByTagIdAndDesignerId(tagId, designerId, admin.getUsername());
            }
        }
        return new SuccessResponse();
    }

    /**
     * 更新排序
     */
    @RequestMapping(value = "/sort/{id}/{sort}", method = RequestMethod.POST)
    public Response sort(@PathVariable Long id, @PathVariable Integer sort) {
        brandTagRelationService.updateSort(id, sort);
        return new SuccessResponse();
    }

    /**
     * 标签关联的品牌列表
     */
    @RequestMapping(value = "/designer/list", method = RequestMethod.POST)
    public Response findDesignersByTagId(Long tagId, PageModel page, BrandTagRelationSearcher searcher) {
        searcher.setTagId(tagId);
        PageResult<BrandTagRelationDto> pager = brandTagRelationService.findDesignersByTagId(searcher, page);
        return new SuccessResponse(pager);
    }

    /**
     * 品牌关联的标签列表
     */
    @RequestMapping(value = "/tag/list/{designerId}", method = RequestMethod.POST)
    public Response findDesignersByTagId(@PathVariable Long designerId, PageModel page) {
        PageResult<BrandTagRelationDto> pager = brandTagRelationService.findTagsByDesignerId(designerId, page);
        return new SuccessResponse(pager);
    }

}
