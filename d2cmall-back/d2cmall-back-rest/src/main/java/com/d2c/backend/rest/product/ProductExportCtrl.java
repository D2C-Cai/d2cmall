package com.d2c.backend.rest.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductModuleSearchService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.support.UploadProductBean;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import com.d2c.util.file.ExcelUtil;
import com.d2c.util.file.ZipUtil;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rest/product/export")
public class ProductExportCtrl extends SuperCtrl {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ExportLogService exportLogService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;

    protected void saveExportLog(String fileName, String filePath, long fileSize, String type)
            throws NotLoginException {
        ExportLog exportLog = new ExportLog();
        exportLog.setFileName(fileName);
        exportLog.setFilePath(filePath);
        exportLog.setFileSize(fileSize);
        exportLog.setLogType(type);
        exportLog.setCreator(this.getLoginedAdmin().getUsername());
        exportLogService.insert(exportLog);
    }

    /**
     * 商品二维码导出，根据搜索条件
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/qrcode/bysearch", method = RequestMethod.POST)
    public Response qrcodeBySearch(ProductSearcher productSearcher, HttpServletRequest request,
                                   HttpServletResponse response, PageModel page) throws NotLoginException {
        productSearcher.setSaleCategory(SaleCategory.NORMALPRODUCT.name());
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        PageResult<ProductDto> pager = null;
        List<String> inernalSNs = null;
        List<BufferedImage> imgs = null;
        Admin admin = this.getLoginedAdmin();
        String fileName = admin.getUsername() + "_" + "商品二维码表";
        try {
            ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
            QRcodeEntity qrcodeEntity = this.getQRcodeEntity(request);
            int i = 1;
            int rowNum = 0;
            do {
                page.setPageNumber(i++);
                pager = productService.findBySearch(productSearcher, page);
                inernalSNs = new ArrayList<>();
                imgs = new ArrayList<>();
                for (Product product : pager.getList()) {
                    inernalSNs.add(product.getInernalSn());
                    qrcodeEntity.setContent("http://www.d2cmall.com/product/sn/" + product.getInernalSn());
                    qrcodeEntity.setLowerContent(product.getInernalSn());
                    imgs.add(QRcodeUtil.createQRCode(qrcodeEntity));
                }
                excelUtil.exportQRcodeExcel(inernalSNs, imgs, rowNum);
                rowNum = inernalSNs.size();
            } while (i <= pager.getPageCount());
            saveExportLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                    excelUtil.getExportFileBean().getFileSize(), "qrcode");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new SuccessResponse();
    }

    /**
     * 商品二维码导出，根据货号，分隔符是逗号
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/qrcode/bysn", method = RequestMethod.POST)
    public Response qrcodeBySn(String sn, HttpServletRequest request, HttpServletResponse response)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isBlank(sn)) {
            result.setStatus(-1);
            result.setMessage("提交信息不完整");
            return result;
        }
        String[] inernalSNs = null;
        List<BufferedImage> imgs = null;
        String fileName = this.getLoginedAdmin().getUsername() + "_" + "商品二维码表";
        Admin admin = this.getLoginedAdmin();
        try {
            ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
            QRcodeEntity qrcodeEntity = this.getQRcodeEntity(request);
            inernalSNs = sn.split(",");
            if (inernalSNs.length < 500) {
                imgs = new ArrayList<>();
                List<String> inernalSns = new ArrayList<>();
                for (String inernalSn : inernalSNs) {
                    if (StringUtils.isBlank(inernalSn)) {
                        continue;
                    }
                    inernalSn = inernalSn.trim();
                    inernalSns.add(inernalSn);
                    qrcodeEntity.setContent("http://www.d2cmall.com/product/sn/" + inernalSn);
                    qrcodeEntity.setLowerContent(inernalSn);
                    imgs.add(QRcodeUtil.createQRCode(qrcodeEntity));
                }
                excelUtil.exportQRcodeExcel(inernalSns, imgs, 0);
            }
            saveExportLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                    excelUtil.getExportFileBean().getFileSize(), "qrcode");
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 商品二维码，导出为图片压缩包，根据货号，分隔符是回车
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/qrcode/bysn/zip", method = RequestMethod.POST)
    public Response qrcodeZipBySn(String sn, HttpServletRequest request) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isBlank(sn)) {
            result.setStatus(-1);
            result.setMessage("提交信息不完整");
            return result;
        }
        String logName = this.getLoginedAdmin().getUsername() + "_商品二维码表_";
        String[] inernalSNs = null;
        if (sn.indexOf("\r\n") > 0) {
            inernalSNs = sn.split("\n");
        } else if (sn.indexOf(",") > 0) {
            inernalSNs = sn.split(",");
        } else if (sn.indexOf("\n") > 0) {
            inernalSNs = sn.split("\n");
        } else {
            inernalSNs = new String[]{sn};
        }
        try {
            Date date = new Date();
            int year = DateUtil.getYearOfDate(date);
            int month = DateUtil.getMonthOfYear(date);
            int day = DateUtil.getDayOfMonth(date);
            String baseName = String.valueOf(date.getTime());
            String folderName = "/mnt/www/download/qrcode/" + year + "/" + month + "/" + day + "/" + baseName;
            File dir = new File(folderName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            QRcodeEntity qrcodeEntity = this.getQRcodeEntity(request);
            if (inernalSNs.length > 1000) {
                result.setStatus(-1);
                result.setMessage("数据过大");
                return result;
            }
            for (String inernalSn : inernalSNs) {
                if (StringUtils.isBlank(inernalSn)) {
                    continue;
                }
                inernalSn = inernalSn.trim();
                qrcodeEntity.setContent("http://www.d2cmall.com/product/sn/" + inernalSn);
                qrcodeEntity.setLowerContent(inernalSn);
                ImageIO.write(QRcodeUtil.createQRCode(qrcodeEntity), "jpg",
                        new File(dir.getAbsolutePath() + "/" + inernalSn + "_1.jpg"));
            }
            String zipName = dir.getAbsolutePath() + ".zip";
            ZipUtil zc = new ZipUtil(zipName);
            zc.compress(dir.getAbsolutePath());
            String fileName = folderName.substring(8, folderName.length()) + ".zip";
            long size = 0;
            File f = new File(zipName);
            if (f.exists() && f.isFile()) {
                size = f.length();
            }
            this.saveExportLog(logName + baseName + ".zip", fileName, size, "qrcode_zip");
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private QRcodeEntity getQRcodeEntity(HttpServletRequest request) {
        QRcodeEntity zxing = new QRcodeEntity();
        zxing.setCharacterSet("UTF-8");
        zxing.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
        zxing.setMargin(4);
        zxing.setWidth(468);
        zxing.setHeight(468);
        zxing.setLogoPath(request.getSession().getServletContext().getRealPath("/") + "/static/img/logo.png");
        return zxing;
    }

    /**
     * 商品SKU导出
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/productsku", method = RequestMethod.POST)
    public Response productSKUExcelExport(ProductSearcher searcher, HttpServletRequest request, PageModel page)
            throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        SuccessResponse result = new SuccessResponse();
        String fileName = this.getLoginedAdmin().getUsername() + "_" + "商品库存表";
        String[] titleNames = getProductSKUTitleNames();
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageResult<ProductDto> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        int totalCount = productService.countBySearch(searcher);
        pager.setTotalCount(totalCount);
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber++);
            pager = productService.findBySearch(searcher, page);
            exportSuccess = csvUtil.writeRowToFile(getProductSKURowList(pager.getList()));
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        if (exportSuccess) {
            saveExportLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), "ProductSku");
        }
        return result;
    }

    private List<Map<String, Object>> getProductSKURowList(List<ProductDto> productList) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (Product product : productList) {
            List<ProductSku> ProductSKUList = productSkuService.findByProductId(product.getId());
            if (ProductSKUList == null) {
                continue;
            }
            for (ProductSku sku : ProductSKUList) {
                cellsMap = new HashMap<>();
                cellsMap.put("D2C货号", sku.getInernalSn());
                cellsMap.put("第三方货号", product.getExternalSn());
                cellsMap.put("条码", sku.getBarCode());
                cellsMap.put("颜色", sku.getColorValue());
                cellsMap.put("尺寸", sku.getSizeValue());
                cellsMap.put("自营库存", String.valueOf(sku.getStore()));
                cellsMap.put("POP库存", String.valueOf(sku.getPopStore()));
                String saleTypeName = "";
                String sellTypeName = "";
                if (product.getProductSaleType() != null) {
                    switch (product.getProductSaleType()) {
                        case "BUYOUT":
                            saleTypeName = "买断";
                            break;
                        case "CONSIGN":
                            saleTypeName = "代销";
                            break;
                        case "SELF":
                            saleTypeName = "自产";
                            break;
                        case "COOPERATIVE":
                            saleTypeName = "合作款";
                            break;
                    }
                }
                if (product.getProductSellType() != null) {
                    switch (product.getProductSellType()) {
                        case "SPOT":
                            sellTypeName = "现货";
                            break;
                        case "PRESELL":
                            sellTypeName = "预售";
                            break;
                        case "CUSTOM":
                            sellTypeName = "定制";
                            break;
                    }
                }
                cellsMap.put("生产类型", saleTypeName);
                cellsMap.put("销售类型", sellTypeName);
                cellsMap.put("设计师品牌", product.getDesignerName());
                String topCategory = "";
                if (StringUtils.isNotBlank(product.getTopCategory())) {
                    topCategory = JSONObject.parseObject(product.getTopCategory()).getString("name");
                }
                cellsMap.put("品类一级分类", topCategory);
                String secondCategory = "";
                String thirdCategory = "";
                if (StringUtils.isNotBlank(product.getProductCategory())) {
                    JSONArray array = JSONArray.parseArray(product.getProductCategory());
                    secondCategory = array.getJSONObject(0).getString("name");
                    if (array.size() == 2) {
                        thirdCategory = array.getJSONObject(1).getString("name");
                    }
                }
                cellsMap.put("品类二级分类", secondCategory);
                cellsMap.put("品类三级分类", thirdCategory);
                cellsMap.put("吊牌价", product.getOriginalPrice());
                rowList.add(cellsMap);
            }
        }
        return rowList;
    }

    private String[] getProductSKUTitleNames() {
        return new String[]{"D2C货号", "第三方货号", "生产类型", "销售类型", "设计师品牌", "品类一级分类", "品类二级分类", "品类三级分类", "条码", "颜色", "尺寸",
                "吊牌价", "自营库存", "POP库存"};
    }

    /**
     * 导入商品排序表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/product/sort", method = RequestMethod.POST)
    public Response importProductSort(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        String message = "";
        try {
            for (Map<String, Object> map : excelData) {
                productService.updateSort(Long.parseLong(String.valueOf(map.get("商品ID"))),
                        Integer.parseInt(String.valueOf(map.get("优先级"))), admin.getUsername());
            }
            result.setMessage("导入成功");
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    /**
     * 导入商品库存表
     *
     * @param request
     * @param initial
     * @param designerCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/productsku/stock", method = RequestMethod.POST)
    public Response importProductSkuStock(HttpServletRequest request, boolean initial, String designerCode) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        String message = "";
        try {
            String errorStr = "";
            int successCount = 0;
            int failCount = 0;
            Brand brand = brandService.findByBrandCode(designerCode);
            if (brand == null) {
                throw new RuntimeException("该品牌不存在！");
            }
            if (initial) {
                productSkuService.doInitPopStore(brand.getId());
            }
            Set<Long> ignoreProductId = new HashSet<>();
            for (Map<String, Object> map : excelData) {
                UploadProductBean bean = new UploadProductBean();
                String barCode = null;
                try {
                    barCode = String.valueOf(map.get("商品SKU编码"));
                    if (StringUtils.isBlank(barCode)) {
                        errorStr += "设计师：" + brand.getName() + "条码：" + barCode + "，错误原因：商品条码不能为空<br/>";
                        failCount++;
                        continue;
                    }
                    Float store = Float.parseFloat(String.valueOf(map.get("库存"))); // Integer.parseInt(String.valueOf(map.get("库存"));
                    if (store < 0) {
                        errorStr += "设计师：" + brand.getName() + "条码：" + barCode + "，错误原因：库存不能为负<br/>";
                        failCount++;
                        continue;
                    }
                    bean.setBarCode(barCode);
                    bean.setPopStore(store.intValue());
                } catch (Exception e) {
                    result.setStatus(-1);
                    result.setMessage("导入格式不正确！请检查数据是否正确且不为空！");
                    return result;
                }
                List<Long> productIds = productService.findByDesignerIdAndBarCode(brand.getId(), barCode, null);
                if (productIds != null && productIds.size() > 0) {
                    productSkuService.updatePopStore(bean, admin.getUsername());
                    successCount++;
                    for (Long productId : productIds) {
                        ignoreProductId.add(productId);
                    }
                } else {
                    errorStr += "设计师：" + brand.getName() + "条码：" + barCode + "，错误原因：商品条码未找到<br/>";
                    failCount++;
                    continue;
                }
            }
            rebuildPopStore(brand.getDesignersId(), ignoreProductId);
            if (errorStr.length() > 0) {
                message = "导入成功" + successCount + "条！" + "导入不成功：" + failCount + "条！不成功原因：" + "<br/>" + errorStr;
            } else {
                message = "导入成功" + successCount + "条！";
            }
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    /**
     * 导入外部编码表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/productsku/externalSn", method = RequestMethod.POST)
    public Response importProductSkuExternalSn(HttpServletRequest request) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        String message = "";
        try {
            String errorStr = "";
            int successCount = 0;
            int failCount = 0;
            for (Map<String, Object> map : excelData) {
                String barCode = String.valueOf(map.get("商品SKU编码"));
                String externalSn = String.valueOf(map.get("设计师货号"));
                if (StringUtils.isBlank(barCode) || StringUtils.isBlank(externalSn)) {
                    continue;
                }
                ProductSku sku = productSkuService.findByBarCode(barCode);
                if (sku != null) {
                    productSkuService.updateExternalSnBySkuSn(barCode, externalSn);
                    successCount++;
                } else {
                    errorStr += "商品条码：" + barCode + "，错误原因：商品条码未找到<br/>";
                    failCount++;
                    continue;
                }
            }
            if (errorStr.length() > 0) {
                message = "导入成功" + successCount + "条！" + "导入不成功：" + failCount + "条！不成功原因：" + "<br/>" + errorStr;
            } else {
                message = "导入成功" + successCount + "条！";
            }
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    private void rebuildPopStore(final Long designerId, final Set<Long> ignoreProductId) {
        // 重建有库存商品pop被改成0后的情况
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                PageResult<SearcherProduct> pager = new PageResult<>();
                ProductProSearchQuery searcher = new ProductProSearchQuery();
                searcher.setDesignerId(designerId);
                searcher.setStore(1);
                PageModel page = new PageModel();
                do {
                    pager = productSearcherQueryService.search(searcher, page);
                    for (SearcherProduct p : pager.getList()) {
                        if (ignoreProductId.add(p.getProductId())) {
                            productModuleSearchService.rebuild(p.getProductId());
                        }
                    }
                    page.setP(page.getP() + 1);
                } while (pager.getTotalCount() == page.getPageSize());
            }
        });
        executor.shutdown();
    }

    /**
     * 导入商品一口价
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/productsku/aprice", method = RequestMethod.POST)
    public Response importProductSkuAPrice(HttpServletRequest request) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        excelData = processImportAprice(excelData);
        String message = "";
        try {
            int successCount = 0;
            for (Map<String, Object> map : excelData) {
                Long skuId = (Long) map.get("skuId");
                BigDecimal aPrice = new BigDecimal(String.valueOf(map.get("aPrice")));
                productSkuService.updateAPrice(skuId, aPrice, admin.getUsername());
                successCount++;
            }
            message = "导入成功" + successCount + "条！";
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    private List<Map<String, Object>> processImportAprice(List<Map<String, Object>> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        BigDecimal zero = new BigDecimal(0);
        int number = 1;
        for (Map<String, Object> map : list) {
            String barCode = String.valueOf(map.get("商品SKU"));
            BigDecimal aPrice = new BigDecimal(String.valueOf(map.get("一口价")));
            if (aPrice.compareTo(zero) < 0) {
                throw new BusinessException("第" + number + "行：任一价格不能为负数，导入不成功");
            }
            ProductSku sku = productSkuService.findByBarCode(barCode);
            if (sku == null) {
                throw new BusinessException("第" + number + "行：sku不存在，导入不成功");
            }
            if (sku.getOriginalCost().compareTo(aPrice) < 0 || sku.getPrice().compareTo(aPrice) < 0) {
                throw new BusinessException("第" + number + "行：销售价不能小于一口价，导入不成功");
            }
            Map<String, Object> item = new HashMap<>();
            item.put("skuId", sku.getId());
            item.put("aPrice", aPrice);
            result.add(item);
            number++;
        }
        return result;
    }

    /**
     * 导入商品限时购价格
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/productsku/flashprice", method = RequestMethod.POST)
    public Response importProductSkuFlashPrice(HttpServletRequest request) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        excelData = processImportFlashPrice(excelData);
        String message = "";
        try {
            int successCount = 0;
            for (Map<String, Object> map : excelData) {
                Long productId = (Long) map.get("productId");
                Long skuId = (Long) map.get("skuId");
                BigDecimal flashPrice = new BigDecimal(String.valueOf(map.get("flashPrice")));
                Integer flashStore = (Integer) map.get("flashStore");
                productSkuService.updateFlashPrice(productId, skuId, flashPrice, flashStore);
                successCount++;
            }
            message = "导入成功" + successCount + "条！";
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

    private List<Map<String, Object>> processImportFlashPrice(List<Map<String, Object>> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        BigDecimal zero = new BigDecimal(0);
        int number = 1;
        for (Map<String, Object> map : list) {
            String barCode = String.valueOf(map.get("商品SKU"));
            BigDecimal flashPrice = new BigDecimal(String.valueOf(map.get("限时购价")));
            Integer flashStore = new Integer(String.valueOf(map.get("活动库存")));
            if (flashPrice.compareTo(zero) < 0) {
                throw new BusinessException("第" + number + "行：任一价格不能为负数，导入不成功");
            }
            if (flashStore < 0) {
                throw new BusinessException("第" + number + "行：活动库存不能为负数，导入不成功");
            }
            ProductSku sku = productSkuService.findByBarCode(barCode);
            if (sku == null) {
                throw new BusinessException("第" + number + "行：sku不存在，导入不成功");
            }
            Map<String, Object> item = new HashMap<>();
            item.put("productId", sku.getProductId());
            item.put("skuId", sku.getId());
            item.put("flashPrice", flashPrice);
            item.put("flashStore", flashStore);
            result.add(item);
            number++;
        }
        return result;
    }

}
