package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.order.model.Store;
import com.d2c.order.model.Store.BusTypeEnum;
import com.d2c.order.query.StoreSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.StoreService;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report/dplus")
public class DplusController extends BaseControl {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private StoreService storeService;

    /**
     * 总览数据
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/total", method = RequestMethod.POST)
    public ResponseResult total(Date startDate, Date endDate) {
        ResponseResult result = new ResponseResult();
        List<Map<String, Object>> list = orderItemService.findDplusTotalAmount(startDate, endDate);
        result.put("list", list);
        return result;
    }

    /**
     * 单店数据
     *
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public ResponseResult store(Long storeId, Date startDate, Date endDate) {
        ResponseResult result = new ResponseResult();
        List<Map<String, Object>> list = orderItemService.findDplusAmountByShop(storeId, startDate, endDate);
        result.put("list", list);
        return result;
    }

    /**
     * D+店列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/store/list", method = RequestMethod.GET)
    public ResponseResult storeList(PageModel page) {
        ResponseResult result = new ResponseResult();
        StoreSearcher searcher = new StoreSearcher();
        searcher.setBusType(BusTypeEnum.DPLUS.name());
        PageResult<Store> pager = storeService.findBySearch(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

    /**
     * 单店库存
     *
     * @param storeCode
     * @param page
     * @return
     */
    @RequestMapping(value = "/store/stock", method = RequestMethod.GET)
    public ResponseResult storeStock(String storeCode, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<ProductSkuDto> pager = productSkuService.findByStore(storeCode, new ProductSkuStockSearcher(), page);
        result.putPage("pager", pager);
        return result;
    }

}
