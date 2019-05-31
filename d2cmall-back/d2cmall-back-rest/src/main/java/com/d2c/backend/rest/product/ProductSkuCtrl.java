package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.model.Store;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.StoreService;
import com.d2c.order.third.caomei.CaomeiClient;
import com.d2c.order.third.caomei.CaomeiProduct;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.query.ProductSkuSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductSkuStockSummaryService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productsku")
public class ProductSkuCtrl extends BaseCtrl<ProductSkuSearcher> {

    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 店铺sku列表
     *
     * @param storeCode
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/store/list", method = RequestMethod.POST)
    public Response storeList(ProductSkuStockSearcher searcher, PageModel page) {
        AdminDto admin = this.getLoginedAdmin();
        Store store = storeService.findById(admin.getStoreId());
        if (store == null) {
            return new ErrorResponse("此账号不是店铺账号！");
        }
        PageResult<ProductSkuDto> pager = productSkuService.findByStore(store.getCode(), searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductSkuSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductSkuSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        return "商品SKU资料表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"货号", "条码", "颜色", "尺寸", "库存", "POP库存"};
    }

    @Override
    protected Response doHelp(ProductSkuSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductSkuDto> pager = productSkuService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response doList(ProductSkuSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductSkuDto> pager = productSkuService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (orderItemService.countByProductSkuId(id) > 0) {
            return new ErrorResponse("该sku已经产生订单，不可删除");
        }
        productSkuService.delete(id, admin.getUsername());
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        ProductSkuDto productSkuDto = (ProductSkuDto) JsonUtil.instance().toObject(data, ProductSkuDto.class);
        String json_sp1 = data.getString("sp1");
        String json_sp2 = data.getString("sp2");
        String json_sp3 = data.getString("sp3");
        if (json_sp1 != null && !("null").equals(json_sp1.toString())) {
            productSkuDto.setSp1(json_sp1.toString());
        }
        if (json_sp2 != null && !("null").equals(json_sp2.toString())) {
            productSkuDto.setSp2(json_sp2.toString());
        }
        if (json_sp3 != null && !("null").equals(json_sp3.toString())) {
            productSkuDto.setSp3(json_sp3.toString());
        }
        Product product = this.productService.findById(productSkuDto.getProductId());
        Admin admin = this.getLoginedAdmin();
        if (product != null) {
            productSkuService.update(productSkuDto, true, product.getStatus(), admin.getUsername());
            productService.updatePriceBySku(product.getId());
        }
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return "ProductSku";
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public Response skuList(@PathVariable Long productId) {
        SuccessResponse result = new SuccessResponse();
        List<ProductSku> skus = productSkuService.findByProductId(productId);
        result.put("skus", skus);
        List<ProductSkuStockSummary> stocks = productSkuStockSummaryService.findByProductId(productId);
        result.put("stocks", stocks);
        return result;
    }

    @RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
    public Response batchUpdate(HttpServletRequest request) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String reqBody = sb.toString();
        String json = URLDecoder.decode(reqBody, "utf-8");
        JSONObject jsonObj = JSONObject.parseObject(json, Feature.OrderedField);
        JSONArray jaProductSKUSet = jsonObj.getJSONArray("data");// 商品sku的集合
        Product product = null;
        for (int i = 0; i < jaProductSKUSet.size(); i++) {
            JSONObject jo = jaProductSKUSet.getJSONObject(i);
            ProductSkuDto productSkuDto = (ProductSkuDto) JsonUtil.instance().toObject(jo, ProductSkuDto.class);
            String json_sp1 = jo.getString("sp1");
            String json_sp2 = jo.getString("sp2");
            String json_sp3 = jo.getString("sp3");
            if (json_sp1 != null && !("null").equals(json_sp1.toString())) {
                productSkuDto.setSp1(json_sp1.toString());
            }
            if (json_sp2 != null && !("null").equals(json_sp2.toString())) {
                productSkuDto.setSp2(json_sp2.toString());
            }
            if (json_sp3 != null && !("null").equals(json_sp3.toString())) {
                productSkuDto.setSp3(json_sp3.toString());
            }
            product = this.productService.findById(productSkuDto.getProductId());
            Admin admin = this.getLoginedAdmin();
            if (product != null) {
                productSkuService.update(productSkuDto, true, product.getStatus(), admin.getUsername());
                productService.updatePriceBySku(product.getId());
            }
        }
        if (product != null) {
            productService.updatePriceBySku(product.getId());
        }
        return new SuccessResponse();
    }

    /**
     * 考拉查询goodsId下面所有的skuId
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/kaola/goods", method = RequestMethod.GET)
    public Response kaolaSkuIds(String goodsId) {
        SuccessResponse result = new SuccessResponse();
        try {
            List<String> list = new ArrayList<String>();
            list.add(goodsId);
            JSONObject obj = KaolaClient.getInstance().querySkuIdsByGoodsIds(list);
            result.put("kaolaSkuIds", obj);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**
     * 考拉单个sku查询
     *
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/kaola/{skuId}", method = RequestMethod.GET)
    public Response kaolaGoodsInfo(@PathVariable String skuId) {
        SuccessResponse result = new SuccessResponse();
        try {
            JSONObject obj = KaolaClient.getInstance().queryGoodsInfoById(skuId, null);
            result.put("kaolaProduct", obj);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**
     * 草莓单个sku查询
     *
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/caomei/{prodId}", method = RequestMethod.GET)
    public Response caomeiGoodsInfo(@PathVariable String prodId) {
        SuccessResponse result = new SuccessResponse();
        try {
            CaomeiProduct obj = CaomeiClient.getInstance().queryProd(prodId);
            result.put("caomeiProduct", obj);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

}