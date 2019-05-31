package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.ProductTagRelationDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductTag;
import com.d2c.product.model.ProductTag.TagType;
import com.d2c.product.model.ProductTagRelation;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductTagRelationService;
import com.d2c.product.service.ProductTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/rest/product/tag4productrelation")
public class ProductTagRelationCtrl extends SuperCtrl {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private ProductTagRelationService productTagRelationService;

    /**
     * 新增关系
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(Long[] productIds, Long[] tagIds) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        // 所有绑定明星风范标签的商品都绑定全部标签
        ProductTag starTag = productTagService.findOneFixByType(TagType.STAR.toString());
        boolean star = false;
        List<ProductTag> tags = productTagService.findByIds(tagIds);
        for (ProductTag tag : tags) {
            if (TagType.STAR.toString().equals(tag.getType())) {
                star = true;
                break;
            }
        }
        for (Long productId : productIds) {
            int updateCount = 0;
            for (Long tagId : tagIds) {
                ProductTagRelation relation = productTagRelationService.findByTagIdAndProductId(productId, tagId);
                if (relation == null) {
                    productTagRelationService.insert(productId, tagId, admin.getUsername());
                    updateCount++;
                }
            }
            if (star) {
                ProductTagRelation allStarRelation = productTagRelationService.findByTagIdAndProductId(productId,
                        starTag.getId());
                if (allStarRelation == null) {
                    productTagRelationService.insert(productId, starTag.getId(), admin.getUsername());
                }
            }
            // 有更改才刷新product表以及更新索引
            if (updateCount > 0) {
                this.updateTags(productId);
            }
        }
        result.setStatus(1);
        result.setMessage("操作成功！");
        return result;
    }

    private void updateTags(Long productId) {
        String tags = null;
        Long[] tagIds = productTagRelationService.findTagIdByProductId(productId);
        if (tagIds != null) {
            for (int i = 0; i < tagIds.length; i++)
                if (i == 0) {
                    tags = tagIds[i] + "";
                } else {
                    tags = tags + "," + tagIds[i] + "";
                }
        }
        productService.updateTags(productId, tags);
    }

    /**
     * 删除关系
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(Long[] id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long relationId : id) {
            ProductTagRelation relation = productTagRelationService.findById(relationId);
            productTagRelationService.delete(relationId, admin.getUsername());
            updateTags(relation.getProductId());
        }
        result.setStatus(1);
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 更新排序
     */
    @RequestMapping(value = "/sort/{id}/{sort}", method = RequestMethod.POST)
    public Response sort(@PathVariable Long id, @PathVariable Integer sort) {
        SuccessResponse result = new SuccessResponse();
        productTagRelationService.updateSort(id, sort);
        result.setStatus(1);
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 标签关联的商品列表
     */
    @RequestMapping(value = "/prodcut/list/{tagId}", method = RequestMethod.POST)
    public Response findProductsByTagId(@PathVariable Long tagId, ProductSearcher productSearcher, PageModel page) {
        PageResult<ProductTagRelationDto> pager = productTagRelationService.findProductsByTagId(page, productSearcher,
                tagId);
        return new SuccessResponse(pager);
    }

    /**
     * 商品关联的标签列表
     */
    @RequestMapping(value = "/tag/list/{productId}", method = RequestMethod.POST)
    public Response findTagsByProductId(@PathVariable Long productId, PageModel page) {
        PageResult<ProductTagRelationDto> pager = productTagRelationService.findTagsByProductId(page, productId);
        return new SuccessResponse(pager);
    }

    /**
     * 导入标签商品表
     *
     * @param request
     * @param tagId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/relation/import", method = RequestMethod.POST)
    public Response importTagRelation(Long tagId, HttpServletRequest request) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        ProductTag productTag = productTagService.findById(tagId);
        if (productTag == null) {
            result.setStatus(-1);
            result.setMessage("标签未找到，请检查！");
            return result;
        }
        List<Map<String, Object>> excelData = this.getExcelData(request);
        String message = "";
        try {
            String errorStr = "";
            int successCount = 0;
            int failCount = 0;
            Set<Long> productIds = new HashSet<>();
            for (Map<String, Object> map : excelData) {
                String inernalSn = String.valueOf(map.get("商品货号"));
                if (StringUtils.isEmpty(inernalSn)) {
                    continue;
                }
                List<Product> products = productService.findProductBySn(inernalSn);
                if (products != null && products.size() > 0) {
                    for (Product product : products) {
                        if (productIds.add(product.getId())) {
                            if (productTagRelationService.findByTagIdAndProductId(product.getId(), tagId) == null) {
                                productTagRelationService.insert(product.getId(), tagId, admin.getUsername());
                                updateTags(product.getId());
                            } else {
                                errorStr += "货号：" + inernalSn + "，错误原因：已关联该标签<br/>";
                                failCount++;
                                continue;
                            }
                        }
                    }
                    successCount++;
                } else {
                    errorStr += "货号：" + inernalSn + "，错误原因：商品未找到<br/>";
                    failCount++;
                    continue;
                }
            }
            if (errorStr.length() > 0) {
                message = "导入成功" + successCount + "条！" + "导入不成功：" + failCount + "条！不成功原因：" + "<br/>" + errorStr;
            } else {
                message = "导入成功" + successCount + "条！";
            }
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    @RequestMapping(value = "/batch/delete", method = RequestMethod.POST)
    public Response deleteByTagIdAndProductId(Long[] productIds, Long[] tagIds) {
        Admin admin = this.getLoginedAdmin();
        for (Long productId : productIds) {
            for (Long tagId : tagIds) {
                productTagRelationService.deleteByTagIdAndProductId(tagId, productId, admin.getUsername());
            }
        }
        return new SuccessResponse();
    }

}
