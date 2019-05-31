package com.d2c.backend.rest.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.similar.dto.SimilarDTO;
import com.d2c.similar.query.SimilarMgQuery;
import com.d2c.similar.service.SimilarService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/chest/similar")
public class SimilarCtrl extends BaseCtrl<ProductSearcher> {

    @Reference
    private SimilarService similarService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    protected Response doList(ProductSearcher query, PageModel page) {
        ProductProSearchQuery searcherBean = query.convertSearchQuery();
        return ResultHandler.success(productSearcherQueryService.search(searcherBean, page));
    }

    /**
     * 根据选中数据，获取对应相似度列表
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public Response select(@PathVariable Integer id, SimilarMgQuery query, PageModel page) {
        try {
            List<SimilarDTO> list = similarService.findRebuildByBeanId(id, query, page.getPageNumber(),
                    page.getPageSize());
            long count = similarService.countByBeanId(id, query);
            return ResultHandler.successPage(page, list, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    @Override
    protected int count(ProductSearcher query) {
        return 0;
    }

    @Override
    protected Response findById(Long id) {
        return ResultHandler.success();
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        return ResultHandler.success();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return ResultHandler.success();
    }

    @Override
    protected Response doDelete(Long id) {
        return ResultHandler.success();
    }
    // *************************************************

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
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
