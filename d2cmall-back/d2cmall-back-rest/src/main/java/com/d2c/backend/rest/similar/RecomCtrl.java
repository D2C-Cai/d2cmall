package com.d2c.backend.rest.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.ProductService;
import com.d2c.similar.dto.RecomDTO;
import com.d2c.similar.service.RecomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/chest/recom")
public class RecomCtrl extends BaseCtrl<ProductSearcher> {

    @Autowired
    protected ProductService productService;
    @Reference
    private RecomService recomService;
    @Reference
    private BargainPriceService bargainPriceService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    protected Response doList(ProductSearcher query, PageModel page) {
        ProductProSearchQuery searcherBean = query.convertSearchQuery();
        return ResultHandler.success(productSearcherQueryService.search(searcherBean, page));
    }

    /**
     * 根据商品ID获取推荐得分
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public Response productRecom(@PathVariable Long id) {
        try {
            RecomDTO bean = recomService.findBuildRecomByProductId(id);
            return ResultHandler.success(bean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    /**
     * 推荐值重建
     */
    @RequestMapping(value = "/rebuild", method = RequestMethod.GET)
    public Response rebuild(@PathVariable Long id) {
        try {
            recomService.buildAllRecomInJob();
            return ResultHandler.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    /**
     * 商品运营推荐打开和关闭
     */
    @RequestMapping(value = "/operator/recom/{id}", method = RequestMethod.POST)
    public Response operRecom(@PathVariable Long id, @RequestParam(defaultValue = "false") Boolean operRecom) {
        try {
            RecomDTO bean = recomService.buildRecomByProductId(id, operRecom);
            return ResultHandler.success(bean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    @Override
    protected int count(ProductSearcher query) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductSearcher query, PageModel page) {
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
    protected Response doHelp(ProductSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
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

}
