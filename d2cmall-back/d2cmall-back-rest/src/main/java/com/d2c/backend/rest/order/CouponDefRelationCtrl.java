package com.d2c.backend.rest.order;

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
import com.d2c.order.model.CouponDefRelation;
import com.d2c.order.model.CouponDefRelation.CouponRelationType;
import com.d2c.order.query.CouponDefRelationSearcher;
import com.d2c.order.service.CouponDefRelationService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/coupondefrelation")
public class CouponDefRelationCtrl extends BaseCtrl<CouponDefRelationSearcher> {

    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    @Override
    protected List<Map<String, Object>> getRow(CouponDefRelationSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(CouponDefRelationSearcher searcher) {
        BeanUt.trimString(searcher);
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
    protected Response doHelp(CouponDefRelationSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CouponDefRelationSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (searcher.getRelationType() == null) {
            return new ErrorResponse("请确认优惠券的关联关系");
        }
        SuccessResponse result = null;
        if (searcher.getRelationType().equals(CouponDefRelation.CouponRelationType.DESIGNER.toString())) {
            PageResult<Brand> pager = brandService.findByCouponDefId(searcher.getCouponDefId(), page);
            result = new SuccessResponse(pager);
            result.put("defineId", searcher.getCouponDefId());
            return result;
        } else if (searcher.getRelationType().equals(CouponDefRelation.CouponRelationType.PRODUCT.toString())) {
            PageResult<Product> pager = productService.findByCouponDefId(searcher.getCouponDefId(), page);
            result = new SuccessResponse(pager);
            result.put("defineId", searcher.getCouponDefId());
            return result;
        }
        return new ErrorResponse("没有该关联关系, " + searcher.getRelationType());
    }

    @Override
    protected Response findById(Long id) {
        return new ErrorResponse("没有实现该方法");
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return new ErrorResponse("没有实现该方法");
    }

    @Override
    protected Response doDelete(Long id) {
        return new ErrorResponse("没有实现该方法");
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return new ErrorResponse("没有实现该方法");
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        return new ErrorResponse("没有实现该方法");
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/list/{defineId}", method = RequestMethod.POST)
    public Response findProductsBySearcher(ProductSearcher searcher, @PathVariable Long defineId, PageModel page) {
        PageResult<Product> pager = couponDefRelationService.findProductsBySearcher(searcher, defineId, page);
        SuccessResponse successResponse = new SuccessResponse(pager);
        return successResponse;
    }

    @RequestMapping(value = "/{type}/list", method = RequestMethod.POST)
    public Response list(Long defineId, @PathVariable String type, Boolean exclude, Integer p) {
        SuccessResponse result = null;
        if (type.equals(CouponRelationType.PRODUCT.toString())) {
            PageModel page = new PageModel();
            if (p != null) {
                page.setP(p);
            }
            PageResult<Product> pager = productService.findByCouponDefId(defineId, page);
            result = new SuccessResponse(pager);
            result.put("defineId", defineId);
            return result;
        } else if (type.equals(CouponRelationType.DESIGNER.toString())) {
            PageModel page = new PageModel();
            if (p != null) {
                page.setP(p);
            }
            PageResult<Brand> pager = brandService.findByCouponDefId(defineId, page);
            result = new SuccessResponse(pager);
            result.put("defineId", defineId);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/insert/{type}", method = RequestMethod.POST)
    public Response insert(@PathVariable String type, String targetIds, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        String[] tids = targetIds.split(",");
        Long[] targetId = new Long[tids.length];
        for (int i = 0; i < tids.length; i++) {
            targetId[i] = (tids[i] == null ? null : Long.parseLong(tids[i]));
        }
        for (int n = 0; n < ids.length; n++) {
            CouponDefRelation cdr = new CouponDefRelation();
            if (type.equals(CouponRelationType.DESIGNER.toString())) {
                cdr.setType(CouponRelationType.DESIGNER.name());
            } else if (type.equals(CouponRelationType.PRODUCT.toString())) {
                cdr.setType(CouponRelationType.PRODUCT.name());
            }
            cdr.setCouponDefId(ids[n]);
            for (int m = 0; m < targetId.length; m++) {
                cdr.setTargetId(targetId[m]);
                cdr.setCreator(admin.getUsername());
                couponDefRelationService.insert(cdr);
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/saveOne/{type}", method = RequestMethod.POST)
    public Response saveOne(@PathVariable String type, Long targetId, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        String targettype = null;
        // 单个商品或设计师关联时，先删除该商品或者设计师下所有的优惠券再保存关联关系。
        if (type.equals(CouponRelationType.DESIGNER.toString())) {
            targettype = CouponRelationType.DESIGNER.name();
            couponDefRelationService.deleteByTargetId(targetId, CouponRelationType.DESIGNER.toString());
        } else if (type.equals(CouponRelationType.PRODUCT.toString())) {
            targettype = CouponRelationType.PRODUCT.name();
            couponDefRelationService.deleteByTargetId(targetId, CouponRelationType.PRODUCT.toString());
        }
        // 保存关联关系
        for (int n = 0; n < ids.length; n++) {
            CouponDefRelation cdr = new CouponDefRelation();
            cdr.setType(targettype);
            cdr.setCouponDefId(ids[n]);
            cdr.setTargetId(targetId);
            cdr.setCreator(admin.getUsername());
            couponDefRelationService.insert(cdr);
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/delete/{type}/{defineId}", method = RequestMethod.POST)
    public Response delete(@PathVariable String type, @PathVariable Long defineId, Long ids[])
            throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        if (type.equals(CouponRelationType.PRODUCT.toString())) {
            for (int i = 0; i < ids.length; i++) {
                couponDefRelationService.deleteByCidAndTid(ids[i], defineId, CouponRelationType.PRODUCT.toString(),
                        admin.getUsername());
            }
        } else if (type.equals(CouponRelationType.DESIGNER.toString())) {
            for (int i = 0; i < ids.length; i++) {
                couponDefRelationService.deleteByCidAndTid(ids[i], defineId, CouponRelationType.DESIGNER.toString(),
                        admin.getUsername());
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/list/{type}/{defineId}", method = RequestMethod.POST)
    public Response deleteBatch(@PathVariable String type, @PathVariable Long defineId, Long ids[])
            throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        if (type.equals(CouponRelationType.PRODUCT.toString())) {
            for (int i = 0; i < ids.length; i++) {
                couponDefRelationService.deleteByCidAndTid(ids[i], defineId, CouponRelationType.PRODUCT.toString(),
                        admin.getUsername());
            }
        } else if (type.equals(CouponRelationType.DESIGNER.toString())) {
            for (int i = 0; i < ids.length; i++) {
                couponDefRelationService.deleteByCidAndTid(ids[i], defineId, CouponRelationType.DESIGNER.toString(),
                        admin.getUsername());
            }
        }
        return new SuccessResponse();
    }

    /**
     * 导入关联表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import/{type}", method = RequestMethod.POST)
    public Response importRelation(HttpServletRequest request, @PathVariable String type, Long couponDefId) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                if (CouponRelationType.DESIGNER.name().equals(type)) {
                    return processImportBrandRelation(couponDefId, map, row, errorMsg, admin.getUsername());
                } else if (CouponRelationType.PRODUCT.name().equals(type)) {
                    return processImportProductRelation(couponDefId, map, row, errorMsg, admin.getUsername());
                }
                return false;
            }
        });
    }

    private boolean processImportBrandRelation(Long couponDefId, Map<String, Object> map, Integer row,
                                               StringBuilder errorMsg, String username) {
        if (map.get("品牌ID") == null) {
            errorMsg.append("第" + row + "行，品牌ID不能为空<br/>");
            return false;
        }
        Long brandId = new BigDecimal(String.valueOf(map.get("品牌ID"))).longValue();
        Brand brand = brandService.findById(brandId);
        if (brand == null) {
            errorMsg.append("第" + row + "行，品牌ID不存在<br/>");
            return false;
        }
        CouponDefRelation cdr = new CouponDefRelation();
        cdr.setType(CouponRelationType.DESIGNER.name());
        cdr.setCouponDefId(couponDefId);
        cdr.setTargetId(brandId);
        cdr.setCreator(username);
        try {
            couponDefRelationService.insert(cdr);
            return true;
        } catch (Exception e) {
            errorMsg.append("第" + row + "行，" + e.getMessage() + "<br/>");
        }
        return false;
    }

    private boolean processImportProductRelation(Long couponDefId, Map<String, Object> map, Integer row,
                                                 StringBuilder errorMsg, String username) {
        if (map.get("D2C货号") == null) {
            errorMsg.append("第" + row + "行，D2C货号不能为空<br/>");
            return false;
        }
        String productSn = String.valueOf(map.get("D2C货号"));
        List<Product> products = productService.findProductBySn(productSn);
        if (products == null || products.size() == 0) {
            errorMsg.append("第" + row + "行，D2C货号不存在<br/>");
            return false;
        }
        try {
            for (Product product : products) {
                CouponDefRelation cdr = new CouponDefRelation();
                cdr.setType(CouponRelationType.PRODUCT.name());
                cdr.setCouponDefId(couponDefId);
                cdr.setTargetId(product.getId());
                cdr.setCreator(username);
                couponDefRelationService.insert(cdr);
            }
            return true;
        } catch (Exception e) {
            errorMsg.append("第" + row + "行，" + e.getMessage() + "<br/>");
        }
        return false;
    }

}
