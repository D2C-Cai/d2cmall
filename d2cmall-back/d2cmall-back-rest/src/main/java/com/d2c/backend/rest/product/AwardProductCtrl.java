package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.service.CouponDefService;
import com.d2c.product.dto.AwardProductDto;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.model.AwardProduct.AwardType;
import com.d2c.product.query.AwardProductSearcher;
import com.d2c.product.service.AwardProductService;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/award/product")
public class AwardProductCtrl extends BaseCtrl<AwardProductSearcher> {

    @Autowired
    private AwardProductService awardProductService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private ProductService productServicie;

    @Override
    protected Response doList(AwardProductSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AwardProductDto> pager = awardProductService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(AwardProductSearcher searcher) {
        return awardProductService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AwardProductSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return new String[0];
    }

    @Override
    protected Response doHelp(AwardProductSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        AwardProduct awardProduct = awardProductService.findById(id);
        return new SuccessResponse(awardProduct);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        AwardProduct awardProduct = JsonUtil.instance().toObject(data, AwardProduct.class);
        SuccessResponse response = new SuccessResponse();
        awardProductService.update(awardProduct);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AwardProduct awardProduct = JsonUtil.instance().toObject(data, AwardProduct.class);
        if (AwardType.RED.equals(awardProduct.getType())) {
            BigDecimal red = new BigDecimal(awardProduct.getParam());
            if (red.compareTo(new BigDecimal(0)) <= 0) {
                result.setStatus(-1);
                result.setMessage("红包金额不能小于等于0");
                return result;
            }
        } else if (AwardType.COUPON.equals(awardProduct.getType())) {
            String[] params = awardProduct.getParam().split("-");
            if (couponDefService.findById(Long.parseLong(params[0])) == null) {
                result.setStatus(-1);
                result.setMessage("优惠券不存在！");
                return result;
            }
            if (params.length == 2 && StringUtils.isNotBlank(params[1])) {
                if (productServicie.findById(Long.parseLong(params[1])) == null) {
                    result.setStatus(-1);
                    result.setMessage("商品不存在！");
                    return result;
                }
            }
        }
        awardProduct.setStatus(0);
        awardProduct = awardProductService.insert(awardProduct);
        result.put("awardProduct", awardProduct);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    /**
     * 批量上下架
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/updateEnable/{markStatus}", method = RequestMethod.POST)
    public Response doMark(@PathVariable Integer markStatus, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            awardProductService.doMark(markStatus, id);
        }
        result.setMessage(markStatus == 1 ? "上架成功!" : "下架成功!");
        return result;
    }

}
