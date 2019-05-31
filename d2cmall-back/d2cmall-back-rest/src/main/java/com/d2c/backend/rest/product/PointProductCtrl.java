package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.order.model.CouponDef;
import com.d2c.order.service.CouponDefService;
import com.d2c.product.dto.PointProductDto;
import com.d2c.product.model.PointProduct;
import com.d2c.product.model.PointProduct.PointProductTypeEnum;
import com.d2c.product.query.PointProductSearcher;
import com.d2c.product.service.PointProductService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/pointproduct")
public class PointProductCtrl extends BaseCtrl<PointProductSearcher> {

    @Autowired
    private PointProductService pointProductService;
    @Autowired
    private CouponDefService couponDefService;

    @Override
    protected Response doList(PointProductSearcher searcher, PageModel page) {
        PageResult<PointProductDto> pager = pointProductService.findDto(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        PointProduct pointProduct = JsonUtil.instance().toObject(data, PointProduct.class);
        pointProduct = pointProductService.insert(pointProduct);
        result.put("pointProduct", pointProduct);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        PointProduct product = pointProductService.findById(id);
        if (PointProductTypeEnum.COUPON.name().equals(product.getType())) {
            CouponDef def = couponDefService.findById(product.getProductId());
            result.put("coupon", def);
        }
        result.put("pointProduct", product);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        PointProduct pointProduct = JsonUtil.instance().toObject(data, PointProduct.class);
        int success = pointProductService.update(pointProduct);
        if (success < 1) {
            result.setMessage("更新失败");
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(PointProductSearcher query, PageModel pager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(PointProductSearcher query) {
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
    protected List<Map<String, Object>> getRow(PointProductSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @return
     */
    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public Response doMark(Long id, Integer mark) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = pointProductService.updateMark(id, mark, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作失败");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public Response doSort(Long id, Integer sort) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = pointProductService.updateSort(id, sort, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作失败");
            result.setStatus(-1);
        }
        return result;
    }

}
