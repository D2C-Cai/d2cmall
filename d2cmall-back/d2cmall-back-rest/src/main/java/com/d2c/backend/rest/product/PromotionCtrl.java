package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.dto.PromotionDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Promotion;
import com.d2c.product.model.Promotion.PromotionScope;
import com.d2c.product.model.Promotion.PromotionType;
import com.d2c.product.query.PromotionSearcher;
import com.d2c.product.service.PromotionService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/promotion")
public class PromotionCtrl extends BaseCtrl<PromotionSearcher> {

    @Autowired
    private PromotionService promotionService;

    @Override
    protected List<Map<String, Object>> getRow(PromotionSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(PromotionSearcher searcher) {
        return 0;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response doHelp(PromotionSearcher searcher, PageModel page) {
        if (searcher == null) {
            searcher = new PromotionSearcher();
            searcher.setWhole(false);
            searcher.setBeginEndTime(new Date());
        }
        BeanUt.trimString(searcher);
        PageResult<Promotion> pager = promotionService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doList(PromotionSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PromotionDto> pager = promotionService.findDtoBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Promotion promotion = promotionService.findById(id);
        PromotionDto dto = new PromotionDto();
        BeanUtils.copyProperties(promotion, dto);
        if (promotion.getBrandId() != null) {
            Brand brand = brandService.findById(promotion.getBrandId());
            if (brand != null) {
                dto.setBrandName(brand.getName());
            }
        }
        result.put("promotion", dto);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (int i = 0; i < ids.length; i++) {
            promotionService.delete(ids[i]);
        }
        result.setMessage("删除成功");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        promotionService.delete(id);
        result.setMessage("删除成功");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Promotion promotion = JsonUtil.instance().toObject(data, Promotion.class);
        if (promotion.getStartTime().getTime() >= promotion.getEndTime().getTime()) {
            return new ErrorResponse("开始时间小于结束时间，请填写正确时间的关系");
        }
        if (promotion.getPromotionType().equals(PromotionType.APRICE.getCode())) {
            promotion.setSolution(null);
        }
        String error = checkPromoitionIllegal(promotion);
        if (error != null) {
            result.setStatus(-1);
            result.setMessage(error);
            return result;
        }
        promotion = promotionService.insert(promotion);
        result.put("promotion", promotion);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Promotion promotion = JsonUtil.instance().toObject(data, Promotion.class);
        SuccessResponse result = new SuccessResponse();
        if (promotion.getStartTime().getTime() >= promotion.getEndTime().getTime()) {
            return new ErrorResponse("开始时间小于结束时间，请填写正确时间的关系");
        }
        String error = checkPromoitionIllegal(promotion);
        if (error != null) {
            result.setStatus(-1);
            result.setMessage(error);
            return result;
        }
        promotionService.update(promotion);
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    private String checkPromoitionIllegal(Promotion promotion) {
        if (promotion.getPromotionScope() == null) {
            promotion.setPromotionScope(PromotionScope.GOODS.getCode());
        }
        int promotionType = promotion.getPromotionType();
        if (promotionType == PromotionType.X_OFF_Y_STEP.getCode()
                || promotionType == PromotionType.X_OFF_Y_UNLIMITED.getCode()) {
            if (promotion.getSolution() == null) {
                return "请输入正确的促销方式具体规则";
            }
            if (!promotion.getSolution().contains("-")) {
                return "请输入正确的促销方式具体规则";
            }
            String[] solutions = promotion.getSolution().split(",");
            for (String solution : solutions) {
                String[] ss = solution.split("-");
                if (ss.length != 2) {
                    return "请输入正确的促销方式具体规则";
                }
                if (Integer.parseInt(ss[0]) < Integer.parseInt(ss[1])) {
                    return "请输入正确的促销方式具体规则";
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/updateEnable/{flag}", method = RequestMethod.POST)
    public Response doMark(@PathVariable Long flag, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        boolean enable = false;
        if (flag == 1) {
            enable = true;
        }
        for (Long promotionId : ids) {
            try {
                promotionService.doMark(enable, promotionId);
            } catch (Exception e) {
                e.printStackTrace();
                result.setMsg(e.getMessage());
            }
        }
        result.setMessage(enable ? "启用成功" : "禁用成功");
        return result;
    }

    @RequestMapping(value = "/timingMark", method = RequestMethod.POST)
    public Response timingMark(Long promotionId, int mark) {
        SuccessResponse result = new SuccessResponse();
        promotionService.doTiming(promotionId, mark);
        return result;
    }

}
