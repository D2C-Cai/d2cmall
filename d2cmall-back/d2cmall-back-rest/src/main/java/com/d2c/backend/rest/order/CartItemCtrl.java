package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.model.CartItem;
import com.d2c.order.query.CartItemSearcher;
import com.d2c.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/cartitem")
public class CartItemCtrl extends BaseCtrl<CartItemSearcher> {

    @Autowired
    private CartService cartService;

    @Override
    protected List<Map<String, Object>> getRow(CartItemSearcher searcher, PageModel page) {
        List<CartItem> list = cartService.findBySearcher(searcher, page);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        for (CartItem item : list) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("购买人", item.getBuyMemberId());
            cellsMap.put("账号", item.getBuyMemberLoginCode());
            cellsMap.put("手机", item.getBuyMemberMobile());
            cellsMap.put("商品", item.getProductName());
            if (item.getSp1() != null) {
                JSONObject object = JSONObject.parseObject(item.getSp1());
                cellsMap.put("颜色", object.get("value"));
            }
            if (item.getSp2() != null) {
                JSONObject object = JSONObject.parseObject(item.getSp2());
                cellsMap.put("尺码", object.get("value"));
            }
            cellsMap.put("数量", item.getQuantity());
            cellsMap.put("设计师品牌", item.getDesignerName());
            cellsMap.put("商品货号", item.getProductSn());
            cellsMap.put("商品sku", item.getProductSkuSn());
            cellsMap.put("创建时间", sdf.format(item.getCreateDate()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(CartItemSearcher searcher) {
        return cartService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "购物车信息表";
    }

    @Override
    protected String[] getExportTitles() {
        String[] titleNames = {"购买人", "账号", "手机", "商品", "颜色", "尺码", "数量", "设计师品牌", "商品货号", "商品sku", "创建时间"};
        return titleNames;
    }

    @Override
    protected Response doHelp(CartItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CartItemSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CartItem> pager = cartService.findPageBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "CartItem";
    }

}
