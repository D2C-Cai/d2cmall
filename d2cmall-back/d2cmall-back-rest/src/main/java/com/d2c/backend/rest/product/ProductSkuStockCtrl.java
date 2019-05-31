package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.order.model.Store;
import com.d2c.order.service.StoreService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.dto.ProductSkuStockDto;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.service.*;
import com.d2c.util.file.CSVUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rest/product/productskustock")
public class ProductSkuStockCtrl extends BaseCtrl<ProductSkuStockSearcher> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private StoreService storeService;

    @Override
    protected List<Map<String, Object>> getRow(ProductSkuStockSearcher searcher, PageModel page) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<ProductDto> pager = productService.findStockBySearch(page, searcher);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (ProductDto product : pager.getList()) {
            for (ProductSkuStockSummary sku : product.getStockList()) {
                cellsMap = new HashMap<>();
                cellsMap.put("款号", product.getInernalSn());
                cellsMap.put("名称", product.getName());
                cellsMap.put("设计师", product.getDesignerName());
                cellsMap.put("吊牌价", product.getOriginalPrice());
                cellsMap.put("SKU", sku.getBarCode());
                cellsMap.put("顺丰库存", sku.getSfStock());
                cellsMap.put("门店库存", sku.getStStock());
                rowList.add(cellsMap);
            }
        }
        return rowList;
    }

    @Override
    protected int count(ProductSkuStockSearcher searcher) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        return productService.countStockBySearch(searcher);
    }

    protected int storeCount(ProductSkuStockSearcher searcher) {
        return productSkuStockSummaryService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "商品库存表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"款号", "名称", "设计师", "吊牌价", "SKU", "顺丰库存", "门店库存"};
    }

    protected String[] getStoreExportTitles() {
        return new String[]{"D2C货号", "设计师货号", "品牌", "SKU", "颜色", "尺码", "门店", "库存数量"};
    }

    @RequestMapping(value = "/store/export", method = RequestMethod.POST)
    public Response skuExport(ProductSkuStockSearcher searcher, PageModel pageModel) throws NotLoginException {
        try {
            AdminDto admin = this.getLoginedAdmin();
            this.initSearcherByRole(admin, searcher);
            SuccessResponse result = new SuccessResponse();
            result.setMsg("导出成功！");
            String fileName = admin.getUsername() + "_" + getFileName();
            String[] titleNames = getStoreExportTitles();
            CSVUtil csvUtil = new CSVUtil();
            csvUtil.setFileName(fileName);
            csvUtil.writeTitleToFile(titleNames);
            // 一次导出500条，如果有的话
            pageModel.setPageSize(1000);
            PageResult<Object> pageResult = new PageResult<>(pageModel);
            int pagerNumber = 1;
            int totalCount = storeCount(searcher);
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                exportSuccess = csvUtil.writeRowToFile(getStoreRow(searcher, pageModel));
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
            // 保存导出记录
            if (exportSuccess) {
                saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "storeStock");
            }
            return result;
        } catch (NotLoginException e) {
            logger.error(e.getMessage(), e);
            return new ErrorResponse(e.getMessage());
        }
    }

    private List<Map<String, Object>> getStoreRow(ProductSkuStockSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductSkuStockDto> pager = productSkuStockSummaryService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (ProductSkuStockDto dto : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("D2C货号", dto.getInernalSn());
            cellsMap.put("设计师货号", dto.getExternalSn());
            cellsMap.put("品牌", dto.getBrandName());
            cellsMap.put("SKU", dto.getBarCode());
            cellsMap.put("颜色", dto.getColorValue());
            cellsMap.put("尺码", dto.getSizeValue());
            cellsMap.put("门店", dto.getName() == null ? "" : dto.getName());
            cellsMap.put("库存数量", dto.getStock());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected Response doHelp(ProductSkuStockSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ProductSkuStockSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        if ("store".equalsIgnoreCase(searcher.getType()) && dto.getStoreId() != null) {
            Store store = storeService.findById(dto.getStoreId());
            if (store == null) {
                return new ErrorResponse("店铺不存在！");
            }
        }
        PageResult<ProductDto> pager = productService.findStockBySearch(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        List<String> seasonList = seriesService.findSeason();
        Set<String> years = new HashSet<>();
        Set<String> seasons = new HashSet<>();
        for (String str : seasonList) {
            if (!StringUtils.isEmpty(str) && str.length() > 4) {
                years.add(str.substring(0, 4));
                seasons.add(str.substring(4, str.length()));
            }
        }
        result.put("years", years);
        result.put("seasons", seasons);
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
        return "SkuStock";
    }

    /**
     * 根据sku查询门店或者顺丰库存
     *
     * @param barCode
     * @param storeId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public Response stock(String barCode, Long storeId) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Store store = storeService.findById(storeId);
        ProductSkuStock stStock = productSkuStockService.findOne(store.getCode(), barCode);
        ProductSkuStock sfStock = productSkuStockService.findOne("0000", barCode);
        result.put("stStock", stStock != null ? (stStock.getStock() - stStock.getOccupyStock()) : 0);
        result.put("sfStock", sfStock != null ? sfStock.getStock() : 0);
        return result;
    }

    /**
     * 根据sku查询门店或者顺丰库存列表
     *
     * @param barCode
     * @param primary
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/store/stock", method = RequestMethod.POST)
    public Response storeStock(String barCode, Integer primary) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> stockList = productSkuService.findStockByStore(barCode, primary);
        result.put("stockList", stockList);
        return result;
    }

}
