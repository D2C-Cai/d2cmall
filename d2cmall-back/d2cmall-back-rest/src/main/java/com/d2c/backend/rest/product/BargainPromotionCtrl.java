package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.order.mongo.model.BargainHelpDO;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.service.BargainHelpService;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.product.dto.BargainPromotionDto;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.BargainRule;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.BargainPromotionSearcher;
import com.d2c.product.service.BargainPromotionService;
import com.d2c.product.service.BargainRuleService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/bargainpromotion")
public class BargainPromotionCtrl extends BaseCtrl<BargainPromotionSearcher> {

    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Autowired
    private BargainRuleService bargainRuleService;
    @Autowired
    private BargainPriceService bargainPriceService;
    @Autowired
    private BargainHelpService bargainHelpService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    protected Response doList(BargainPromotionSearcher searcher, PageModel page) {
        PageResult<BargainPromotionDto> pager = bargainPromotionService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(BargainPromotionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(BargainPromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
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
    protected Response doHelp(BargainPromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        BargainPromotion promotion = bargainPromotionService.findById(id);
        BargainPromotionDto dto = new BargainPromotionDto();
        BeanUtils.copyProperties(promotion, dto);
        Product product = productService.findById(promotion.getProductId());
        dto.setProduct(product);
        result.put("bargainPromotion", dto);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        BargainPromotion bargainPromotion = JsonUtil.instance().toObject(data, BargainPromotion.class);
        bargainPromotion.setLastModifyMan(admin.getUsername());
        int success = bargainPromotionService.update(bargainPromotion, admin.getUsername());
        if (success > 0) {
            bargainPriceService.updateBargainPromotion(bargainPromotion);
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        BargainPromotion bargainPromotion = JsonUtil.instance().toObject(data, BargainPromotion.class);
        bargainPromotionService.insert(bargainPromotion);
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = bargainPromotionService.delete(id, admin.getUsername());
        if (success < 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/mark", method = RequestMethod.POST)
    public Response updateMark(Long id, Integer mark) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = bargainPromotionService.updateMark(id, mark, admin.getUsername());
        if (success > 0) {
            BargainPromotion bargainPromotion = bargainPromotionService.findById(id);
            bargainPriceService.updateBargainPromotion(bargainPromotion);
        }
        return result;
    }

    /**
     * 排序
     *
     * @param id
     * @param sort
     * @return
     */
    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        bargainPromotionService.updateSort(id, sort);
        return result;
    }

    /**
     * 规则列表
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/rule/list", method = RequestMethod.GET)
    public Response ruleList(Long promotionId) {
        SuccessResponse result = new SuccessResponse();
        List<BargainRule> rules = bargainRuleService.findByPromotionId(promotionId);
        result.put("rules", rules);
        return result;
    }

    /**
     * 插入规则
     *
     * @param rule
     * @return
     */
    @RequestMapping(value = "/insert/rule", method = RequestMethod.POST)
    public Response doInsertRule(BargainRule rule) {
        SuccessResponse result = new SuccessResponse();
        try {
            bargainRuleService.insert(rule);
        } catch (BusinessException e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 删除规则
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/delete/rule", method = RequestMethod.POST)
    public Response doDeleteRule(Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int success = bargainRuleService.delete(id);
        if (success < 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 更新规则
     *
     * @param rule
     * @return
     */
    @RequestMapping(value = "/update/rule", method = RequestMethod.POST)
    public Response doUpdateRule(BargainRule rule) {
        SuccessResponse result = new SuccessResponse();
        int success = bargainRuleService.update(rule);
        if (success < 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 发起砍价的列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/memberBargain/list", method = RequestMethod.GET)
    public Response memberBargain(BargainPriceSearcher searcher, PageModel page) {
        PageResult<BargainPriceDO> pager = bargainPriceService.findBySearcher(page, searcher);
        return new SuccessResponse(pager);
    }

    /**
     * 帮助砍价的列表
     *
     * @param bargainId
     * @param page
     * @return
     */
    @RequestMapping(value = "/helper/list", method = RequestMethod.GET)
    public Response memberBargain(String bargainId, PageModel page) {
        PageResult<BargainHelpDO> pager = bargainHelpService.findByBargainId(bargainId, page);
        return new SuccessResponse(pager);
    }

    /**
     * 更新活动库存
     *
     * @param skuIds
     * @param flashStock
     * @return
     */
    @RequestMapping(value = "/update/stock", method = RequestMethod.POST)
    public Response updateStock(Long[] skuIds, Integer[] flashStock) {
        SuccessResponse result = new SuccessResponse();
        if (skuIds == null || skuIds.length <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
            return result;
        }
        ProductSku sku = productSkuService.findById(skuIds[0]);
        productSkuService.updateFlashStore(sku.getProductId(), skuIds, flashStock, null, null);
        return result;
    }

}
