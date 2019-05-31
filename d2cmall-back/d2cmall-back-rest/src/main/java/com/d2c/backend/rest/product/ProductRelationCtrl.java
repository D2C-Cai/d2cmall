package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductRelation;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.service.ProductRelationService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/product/productrelation")
public class ProductRelationCtrl extends SuperCtrl {

    @Autowired
    private ProductRelationService productRelationService;
    @Autowired
    private ProductService productService;

    /**
     * 批量新增
     *
     * @param type
     * @param sourceIds
     * @param relationIds
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/insert/{type}", method = RequestMethod.POST)
    public Response insert(@PathVariable String type, Long[] sourceIds, Long[] relationIds) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        RelationType relationType = RelationType.geTypeByEnumName(type);
        if (relationType == null) {
            return new ErrorResponse("没有该类型，" + type);
        }
        for (Long sid : sourceIds) {
            List<ProductRelation> relations = new ArrayList<ProductRelation>();
            for (Long rid : relationIds) {
                if (RelationType.PRODUCT.name().equals(type) && rid.equals(sid)) {
                    // 商品不能关联自身
                    continue;
                }
                ProductRelation relation = new ProductRelation();
                relation.setType(type);
                relation.setSourceId(sid);
                relation.setRelationId(rid);
                relation.setCreator(admin.getUsername());
                relations.add(relation);
            }
            productRelationService.insert(relations, sid);
        }
        return new SuccessResponse();
    }

    /**
     * 批量删除
     *
     * @param type
     * @param sourceId
     * @param relationId
     * @return
     */
    @RequestMapping(value = "/delete/{type}/{sourceId}", method = RequestMethod.POST)
    public Response delete(@PathVariable String type, @PathVariable Long sourceId, Long[] relationIds) {
        RelationType relationType = RelationType.geTypeByEnumName(type);
        if (relationType == null) {
            return new ErrorResponse("没有该类型，" + type);
        }
        SuccessResponse result = new SuccessResponse();
        if (relationIds != null && relationIds.length > 0) {
            for (Long rid : relationIds) {
                productRelationService.deleteOne(type.toString(), rid, sourceId);
            }
            result.setMessage("删除成功");
        } else {
            result.setStatus(-1);
            result.setMessage("删除不成功");
        }
        return result;
    }

    /**
     * 推荐搭配
     *
     * @param sourceId
     * @param page
     * @return
     */
    @RequestMapping(value = "/PRODUCT/{sourceId}", method = {RequestMethod.GET, RequestMethod.POST})
    public Response list(@PathVariable Long sourceId, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        PageResult<Product> pager = productService.findBySourceId(sourceId, RelationType.PRODUCT, page, null);
        result.put("list", pager.getList());
        return result;
    }

}
