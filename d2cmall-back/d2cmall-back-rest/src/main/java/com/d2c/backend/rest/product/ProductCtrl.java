package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.ProductLogService;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.model.*;
import com.d2c.product.model.Product.ProductSellType;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.*;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ExcelSizeReader;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rest/product/product")
public class ProductCtrl extends BaseCtrl<ProductSearcher> {

    @Autowired
    private ProductService productService;
    @Autowired
    private TopCategoryService topCategoryService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductLogService productLogService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductOptionService productOptionService;
    @Autowired
    private ProductSkuOptionService productSkuOptionService;
    @Autowired
    private ProductThirdService productThirdService;
    @Autowired
    private BrandDetailService brandDetailService;

    @Override
    protected List<Map<String, Object>> getRow(ProductSearcher searcher, PageModel page) {
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (Product product : pager.getList()) {
            List<ProductSku> ProductSKUList = productSkuService.findByProductId(product.getId());
            if (ProductSKUList == null) {
                continue;
            }
            for (ProductSku sku : ProductSKUList) {
                cellsMap = new HashMap<>();
                cellsMap.put("商品货号", product.getInernalSn());
                cellsMap.put("设计师款号", product.getExternalSn());
                cellsMap.put("sku", sku.getBarCode());
                cellsMap.put("颜色", sku.getColorValue());
                cellsMap.put("尺码", sku.getSizeValue());
                cellsMap.put("吊牌价", sku.getOriginalCost());
                cellsMap.put("销售价", sku.getPrice());
                cellsMap.put("一口价", sku.getaPrice());
                cellsMap.put("限时购价", sku.getFlashPrice());
                String sellTypeName = "";
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
                cellsMap.put("商品类型", sellTypeName);
                cellsMap.put("预售时间（天）", product.getEstimateDay() != null ? product.getEstimateDay() : "");
                cellsMap.put("预售时间（日期）",
                        product.getEstimateDate() != null ? DateUtil.second2str(product.getEstimateDate()) : "");
                rowList.add(cellsMap);
            }
        }
        return rowList;
    }

    @Override
    protected int count(ProductSearcher searcher) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        return productService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "商品信息表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"商品货号", "设计师款号", "sku", "颜色", "尺码", "吊牌价", "销售价", "一口价", "限时购价", "商品类型", "预售时间（天）",
                "预售时间（日期）"};
    }

    @Override
    protected Response doHelp(ProductSearcher searcher, PageModel page) {
        page.setPageSize(5);
        BeanUt.trimString(searcher);
        if (searcher.getMark() == null) {
            searcher.setMarks(new Integer[]{0, 1});
        }
        if (searcher.getFlashPromotionId() != null) {
            searcher.setOrderByStr("fp_sort DESC");
        }
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response doList(ProductSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        if (searcher.getMark() == null) {
            searcher.setMarks(new Integer[]{0, 1});
        }
        if (searcher.getExpired() != null && searcher.getExpired() == 1) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 2);
            searcher.setExpiredDate(c.getTime());
        }
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Product product = productService.findById(id);
        ProductDetail productDetail = productDetailService.findByProductId(id);
        ProductDto dto = new ProductDto(product, productDetail);
        result.put("product", dto);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            productService.delete(id, -1, admin.getUsername());
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        productService.delete(id, -1, admin.getUsername());
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        // 商品SKU
        JSONArray jaProductSKUSet = JSON.parseArray(data.getString("productSKUSet"));
        data.remove("productSKUSet");
        // 商品图片
        JSONArray jaProductPictures = JSON.parseArray(data.getString("productPictures"));
        data.remove("productPictures");
        // 商品尺码表
        String sizeJson = data.getString("sizeJson");
        data.remove("sizeJson");
        // 商品试穿报告
        String tryOnReportJson = data.getString("tryOnReportJson");
        data.remove("tryOnReportJson");
        // 商品短视频
        String taskIds = data.getString("task_ids");
        data.remove("task_ids");
        // 预计发货时间
        String estimateDate = data.getString("estimateDate");
        if ("".equals(estimateDate)) {
            data.remove("estimateDate");
        }
        ProductDto productDto = JsonUtil.instance().toObject(data, ProductDto.class);
        // 商品SKU数据设置
        List<ProductSkuDto> productSKUSet = new ArrayList<>();
        List<String> productSKUs = new ArrayList<>();
        for (int i = 0; i < jaProductSKUSet.size(); i++) {
            JSONObject jo = jaProductSKUSet.getJSONObject(i);
            String sp1 = jo.get("sp1").toString();
            String sp2 = (jo.get("sp2") == null ? null : jo.get("sp2").toString());
            ProductSkuDto sku = JsonUtil.instance().toObject(jo, ProductSkuDto.class);
            sku.setSp1(sp1);
            sku.setSp2(sp2);
            if (sku.getStore() == null || sku.getPopStore() == null) {
                result.setMessage("库存异常，请输入正整数！");
                result.setStatus(-1);
                return result;
            }
            if (StringUtils.isEmpty(sku.getSn())) {
                result.setStatus(-1);
                result.setMessage("sku条码存在异常！");
                return result;
            }
            if (productSKUs.size() > 0 && productSKUs.contains(sku.getSn())) {
                result.setStatus(-1);
                result.setMessage("sku条码存在重复！");
                return result;
            }
            ProductSku oldSku = productSkuService.findByBarCode(sku.getSn());
            if (oldSku != null && oldSku.getStatus() != -1) {
                result.setStatus(-1);
                result.setMessage("sku条码：" + sku.getSn() + "已存在！");
                return result;
            }
            productSKUSet.add(sku);
            productSKUs.add(sku.getSn());
        }
        productDto.setProductSKUSet(productSKUSet);
        // 商品图片数据设置
        String introPic = "";
        for (int i = 0; i < jaProductPictures.size(); i++) {
            introPic += jaProductPictures.getString(i).toString() + ",";
        }
        if (introPic.length() > 0) {
            introPic = introPic.substring(0, introPic.length() - 1);
        }
        productDto.setIntroPic(introPic);
        // 商品尺码表和试穿报告数据设置
        productDto.setSizeJson(sizeJson);
        productDto.setTryOnReportJson(tryOnReportJson);
        // 商品店铺排序置顶
        int maxSort = productService.getMaxSort(productDto.getDesignerId()) + 1;
        productDto.setSort(maxSort);
        // 商品总体新增
        Product product = productService.insert(productDto, admin.getUsername());
        result.put("product", product);
        // 考拉商品图片处理
        if (productDto.getSource().equals(ProductSource.KAOLA.name())
                || productDto.getSource().equals(ProductSource.CAOMEI.name())
                || productDto.getSource().equals(ProductSource.MILAN.name())) {
            if (StringUtils.isNoneBlank(productDto.getIntroPic()) && productDto.getIntroPic().contains("http")) {
                productDto.setId(product.getId());
                this.processKaolaPic(productDto, admin.getUsername());
            }
        }
        // 商品短视频设置
        if (StringUtils.isNotBlank(taskIds)) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskIds);
            upyunTask.setSourceType(SourceType.PRODUCT.toString());
            upyunTask.setSourceId(product.getId());
            upyunTask.setStatus(0);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                productService.updateVideoById(product.getId(), upyunTask.getVideo());
            }
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        // 商品SKU
        JSONArray jaProductSKUSet = JSON.parseArray(data.getString("productSKUSet"));
        data.remove("productSKUSet");
        // 商品图片
        JSONArray jaProductPictures = JSON.parseArray(data.getString("productPictures"));
        data.remove("productPictures");
        // 商品尺码表
        String sizeJson = data.getString("sizeJson");
        data.remove("sizeJson");
        // 商品试穿报告
        String tryOnReportJson = data.getString("tryOnReportJson");
        data.remove("tryOnReportJson");
        // 商品短视频
        String taskIds = data.getString("task_ids");
        data.remove("task_ids");
        // 预计发货时间
        String estimateDate = data.getString("estimateDate");
        if ("".equals(estimateDate)) {
            data.remove("estimateDate");
        }
        ProductDto productDto = JsonUtil.instance().toObject(data, ProductDto.class);
        // 商品SKU数据设置
        List<ProductSkuDto> productSKUSet = new ArrayList<>();
        List<String> productSKUs = new ArrayList<>();
        for (int i = 0; i < jaProductSKUSet.size(); i++) {
            JSONObject jo = jaProductSKUSet.getJSONObject(i);
            String sp1 = jo.get("sp1").toString();
            String sp2 = (jo.get("sp2") == null ? null : jo.get("sp2").toString());
            ProductSkuDto sku = JsonUtil.instance().toObject(jo, ProductSkuDto.class);
            sku.setSp1(sp1);
            sku.setSp2(sp2);
            if (sku.getStore() == null || sku.getPopStore() == null) {
                result.setMessage("库存异常，请输入正整数！");
                result.setStatus(-1);
                return result;
            }
            if (StringUtils.isEmpty(sku.getSn())) {
                result.setStatus(-1);
                result.setMessage("sku条码存在异常！");
                return result;
            }
            if (productSKUs.size() > 0 && productSKUs.contains(sku.getSn())) {
                result.setStatus(-1);
                result.setMessage("sku条码存在重复！");
                return result;
            }
            ProductSku oldSku = productSkuService.findByBarCode(sku.getSn());
            if (oldSku != null && oldSku.getStatus() != -1 && !oldSku.getId().equals(sku.getId())) {
                result.setStatus(-1);
                result.setMessage("sku条码：" + sku.getSn() + "已存在！");
                return result;
            }
            productSKUSet.add(sku);
            productSKUs.add(sku.getSn());
        }
        productDto.setProductSKUSet(productSKUSet);
        // 商品图片数据设置
        String introPic = "";
        for (int i = 0; i < jaProductPictures.size(); i++) {
            introPic += jaProductPictures.getString(i).toString() + ",";
        }
        if (introPic.length() > 0) {
            introPic = introPic.substring(0, introPic.length() - 1);
        }
        productDto.setIntroPic(introPic);
        // 商品尺码表和试穿报告数据设置
        productDto.setSizeJson(sizeJson);
        productDto.setTryOnReportJson(tryOnReportJson);
        // 商品总体更新
        productService.update(productDto, admin.getUsername());
        // 考拉商品图片处理
        if (productDto.getSource().equals(ProductSource.KAOLA.name())
                || productDto.getSource().equals(ProductSource.CAOMEI.name())
                || productDto.getSource().equals(ProductSource.MILAN.name())) {
            if (StringUtils.isNoneBlank(productDto.getIntroPic()) && productDto.getIntroPic().contains("http")) {
                this.processKaolaPic(productDto, admin.getUsername());
            }
        }
        // 商品短视频设置
        if (productDto.getVideo() == null) {
            productService.updateVideoById(productDto.getId(), null);
        } else if (StringUtils.isNotBlank(taskIds)) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskIds);
            upyunTask.setSourceType(SourceType.PRODUCT.toString());
            upyunTask.setSourceId(productDto.getId());
            upyunTask.setStatus(0);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                productService.updateVideoById(productDto.getId(), upyunTask.getVideo());
            }
        }
        return result;
    }

    // 处理考拉图片
    private void processKaolaPic(ProductDto productDto, String operator) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String[] pics = productDto.getIntroPic().split(",");
                String[] taskIds = productThirdService.pullNetworkImage(pics);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                List<String> list = upyunTaskService.findPicsByTaskIds(taskIds);
                String introPic = StringUtils.join(list.toArray(), ",");
                productDto.setIntroPic(introPic);
                List<ProductSkuDto> productSKUSet = productDto.getProductSKUSet();
                for (int i = 0; i < list.size() && i < productSKUSet.size(); i++) {
                    String sp1 = productSKUSet.get(i).getSp1();
                    if (StringUtil.isNotBlank(sp1)) {
                        JSONObject sp1Json = JSONObject.parseObject(sp1);
                        sp1Json.put("img", list.get(i));
                        productSKUSet.get(i).setSp1(sp1Json.toString());
                        ProductSku sku = productSkuService.findByBarCode(productSKUSet.get(i).getBarCode());
                        productSKUSet.get(i).setId(sku.getId());
                    }
                }
                productService.doRepairKaolaPic(productDto);
            }
        });
        executor.shutdown();
    }

    @Override
    protected String getExportFileType() {
        return "ProductSale";
    }

    @RequestMapping(value = "/index/{status}", method = RequestMethod.POST)
    public Response index(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateIndex(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long[] ids) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            if (1 == status) {
                this.processKaolaGoodsMark(id);
            }
            productService.updateMark(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    /**
     * 上架的时候才去判断考拉商品的状态
     *
     * @param productId
     * @param admin
     * @return
     * @throws Exception
     */
    private void processKaolaGoodsMark(Long productId) {
        Product product = productService.findById(productId);
        if (ProductSource.KAOLA.name().equals(product.getSource())) {
            List<ProductSku> skus = productSkuService.findByProductId(productId);
            List<String> kalaoSkuId = new ArrayList<>();
            skus.forEach(sku -> kalaoSkuId.add(sku.getBarCode()));
            JSONArray array = new JSONArray();
            try {
                array = KaolaClient.getInstance().queryGoodsInfoByIds(kalaoSkuId, null, 1);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            for (int i = 0; i < array.size(); i++) {
                if (200 != array.getJSONObject(i).getInteger("recCode")) {
                    throw new BusinessException(array.getJSONObject(i).getString("recMsg"));
                }
                if (0 == array.getJSONObject(i).getJSONObject("goodsInfo").getInteger("onlineStatus")) {
                    throw new BusinessException("商品货号：" + product.getInernalSn() + "在考拉平台为下架状态，无法上架！");
                }
            }
        }
    }

    @RequestMapping(value = "/top/{status}", method = RequestMethod.POST)
    public Response top(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateTopical(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/ad/{status}", method = RequestMethod.POST)
    public Response ad(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateAd(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/cod/{status}", method = RequestMethod.POST)
    public Response cod(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            try {
                if (status == 1) {
                    Product product = productService.findById(id);
                    if (product == null) {
                        continue;
                    }
                    if (product.getDesignerId() != null) {
                        Brand brand = brandService.findById(product.getDesignerId());
                        if (brand != null && brand.getCod() == 0) {
                            result.setStatus(-1);
                            result.setMessage("操作不成功，该商品的设计师不支持货到付款！");
                            return result;
                        }
                    }
                }
                productService.updateCod(id, status, admin.getUsername());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        return result;
    }

    @RequestMapping(value = "/cart/{status}", method = RequestMethod.POST)
    public Response cart(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            try {
                if (status == 1) {
                    Product product = productService.findById(id);
                    if (product.getStatus() == 5) {
                        result.setStatus(-1);
                        result.setMessage("操作不成功，秒杀商品不支持加入购物车功能！");
                        return result;
                    } else {
                        productService.updateCart(id, status, admin.getUsername());
                    }
                } else {
                    productService.updateCart(id, status, admin.getUsername());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        result.setMsg("操作成功");
        return result;
    }

    @RequestMapping(value = "/sub/{status}", method = RequestMethod.POST)
    public Response sub(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            try {
                productService.updateSubscribe(id, status, admin.getUsername());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/after/{status}", method = RequestMethod.POST)
    public Response after(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            try {
                if (status == 1) {
                    Product product = productService.findById(id);
                    if (product != null && product.getDesignerId() != null) {
                        Brand brand = brandService.findById(product.getDesignerId());
                        if (brand != null && brand.getAfter() == 0) {
                            result.setStatus(-1);
                            result.setMessage("操作不成功，该商品的设计师不支持售后！");
                            return result;
                        }
                    }
                }
                productService.updateAfter(id, status, admin.getUsername());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        result.setMsg("操作成功");
        return result;
    }

    @RequestMapping(value = "/marketdate/{mark}", method = RequestMethod.POST)
    public Response marketdate(@PathVariable Integer mark, Date marketDate, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateMarketDate(id, mark, marketDate, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/sort/{productId}", method = RequestMethod.POST)
    public Response sort(@PathVariable Long productId, Integer sort) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        productService.updateSort(productId, sort, admin.getUsername());
        return new SuccessResponse();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Response addEdit() {
        SuccessResponse result = new SuccessResponse();
        List<TopCategory> list = topCategoryService.findAll(1);
        result.put("topList", list);
        return result;
    }

    @RequestMapping(value = "/insert/edit", method = RequestMethod.POST)
    public Response insertEdit(Long categoryId) {
        SuccessResponse result = new SuccessResponse();
        ProductDto product = new ProductDto();
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        TopCategory topCategory = topCategoryService.findById(productCategory.getTopId());
        product.setProductCategory(JSONArray.toJSONString(this.productCategoryToCollection(productCategory)));
        product.setProductCategoryId(productCategory.getId());
        product.setSp1GroupId(productCategory.getSp1GroupId());
        product.setSp2GroupId(productCategory.getSp2GroupId());
        product.setAttributeGroupId(productCategory.getAttributeGroupId());
        product.setTopCategory(JSONObject.toJSONString(this.topCategoryToCollection(topCategory)));
        product.setTopCategoryId(topCategory.getId());
        result.put("product", product);
        return result;
    }

    private List<Map<String, Object>> productCategoryToCollection(ProductCategory productCategory) {
        List<ProductCategory> list = productCategoryService.findInSet(productCategory.getPath());
        List<Map<String, Object>> pcateMapList = new ArrayList<>();
        for (ProductCategory item : list) {
            Map<String, Object> pcateMap = new HashMap<>();
            pcateMap.put("id", item.getId());
            pcateMap.put("name", item.getName());
            pcateMap.put("attrgroupid", item.getAttributeGroupId() == null ? "" : item.getAttributeGroupId());
            pcateMap.put("sp1GroupId", item.getSp1GroupId() == null ? "" : item.getSp1GroupId());
            pcateMap.put("sp2GroupId", item.getSp2GroupId() == null ? "" : item.getSp2GroupId());
            pcateMapList.add(pcateMap);
        }
        return pcateMapList;
    }

    private Map<String, Object> topCategoryToCollection(TopCategory topCategory) {
        Map<String, Object> tcateMap = new HashMap<>();
        tcateMap.put("id", topCategory.getId());
        tcateMap.put("name", topCategory.getName());
        return tcateMap;
    }

    @RequestMapping(value = "/check/sku", method = RequestMethod.POST)
    public Response checkSKU(String sn, Long sp1GroupId, Long sp2GroupId, Long skuId) {
        SuccessResponse result = new SuccessResponse();
        if (!StringUtils.isEmpty(sn)) {
            sn = sn.toUpperCase().trim();
            SkuCodeBean bean = productService.getSKUCodeParser(sp1GroupId, sp2GroupId, sn);
            if (bean == null) {
                result.setStatus(-1);
                result.setMessage("输入的SKU码解析错误, 请检查颜色/尺码是否存在！");
                return result;
            }
            result.put("bean", bean);
        }
        return result;
    }

    @RequestMapping(value = "/exist/sku", method = RequestMethod.POST)
    public Response exist(String sn, Long skuId) {
        SuccessResponse result = new SuccessResponse();
        sn = sn.toUpperCase().trim();
        ProductSku oldSku = productSkuService.findByBarCode(sn);
        if (oldSku != null && !oldSku.getId().equals(skuId)) {
            result.setStatus(-1);
            result.setMessage("sku：" + sn + "已存在");
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/size/upload", method = RequestMethod.POST)
    public Response sizeUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response,
                               HttpServletRequest request, ModelMap model) throws Exception {
        SuccessResponse result = new SuccessResponse();
        JSONObject excel;
        ExcelSizeReader reader = new ExcelSizeReader();
        if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1)))
            excel = reader.poiExcel2007(file.getInputStream(), 0);
        else
            excel = reader.poiExcel2003(file.getInputStream(), 0);
        result.put("excel", excel);
        return result;
    }

    @RequestMapping(value = "/tryreport/upload", method = RequestMethod.POST)
    public Response tryOnReportUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response,
                                      HttpServletRequest request, ModelMap model) throws Exception {
        SuccessResponse result = new SuccessResponse();
        JSONObject excel;
        ExcelSizeReader reader = new ExcelSizeReader();
        if ("xlsx".equals(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1)))
            excel = reader.poiExcel2007(file.getInputStream(), 0);
        else
            excel = reader.poiExcel2003(file.getInputStream(), 0);
        result.put("excel", excel);
        return result;
    }

    @RequestMapping(value = "/update/category", method = RequestMethod.POST)
    public Response updateCategory(Long categoryId, Long[] productIds) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        ProductCategory productCategory = productCategoryService.findById(categoryId);
        TopCategory topCategory = topCategoryService.findById(productCategory.getTopId());
        List<Map<String, Object>> pcateMapList = this.productCategoryToCollection(productCategory);
        Map<String, Object> tcateMap = this.topCategoryToCollection(topCategory);
        for (Long id : productIds) {
            productService.updateCategory(id, productCategory, JSONArray.toJSONString(pcateMapList),
                    JSONObject.toJSONString(tcateMap), admin.getUsername());
        }
        return result;
    }

    @RequestMapping(value = "/update/estimatedate", method = RequestMethod.POST)
    public Response updateEstimateDate(String date, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        int count = 0;
        SuccessResponse result = new SuccessResponse();
        if (DateUtil.getEndOfDay(new Date()).after(DateUtil.str2second(date))) {
            result.setStatus(-1);
            result.setMessage("预计发货时间不能早于今天！");
            return result;
        }
        for (Long id : ids) {
            int success = productService.updateEstimateDate(id, date, admin.getUsername());
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("操作成功" + count + "条，失败" + (ids.length - count) + "条");
        return result;
    }

    @RequestMapping(value = "/noStore", method = RequestMethod.POST)
    public Response noStoreList(ProductSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductDto> pager = productService.findNoStore(page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @RequestMapping(value = "/list/designer/{designerId}", method = RequestMethod.GET)
    public Response listByDesigner(@PathVariable Long designerId, PageModel page) {
        ProductSearcher searcher = new ProductSearcher();
        searcher.setDesignerId(designerId);
        searcher.setMarks(new Integer[]{0, 1});
        searcher.setOrderByStr("sort desc");
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @RequestMapping(value = "/group/count", method = RequestMethod.GET)
    public Response groupCount(PageModel page, ProductSearcher searcher) {
        SuccessResponse result = new SuccessResponse();
        Map<String, Object> count = productService.countGroupByMark();
        ProductSearcher warnStoreSearcher = new ProductSearcher();
        warnStoreSearcher.setWarnStore(1);
        warnStoreSearcher.setMarks(new Integer[]{1});
        int warnStoreCount = productService.countBySearch(warnStoreSearcher);
        count.put("warnStoreCount", warnStoreCount);
        if (searcher.getExpired() != null && searcher.getExpired() == 1) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 2);
            searcher.setWarnStore(null);
            searcher.setExpiredDate(c.getTime());
            searcher.setMarks(new Integer[]{0, 1});
            int expiredCount = productService.countBySearch(searcher);
            count.put("expiredCount", expiredCount);
            result.put("count", count);
        }
        return result;
    }

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response log(@PathVariable Long id, PageModel page) {
        PageResult<ProductLog> pager = productLogService.findByProductId(id, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    /**
     * 批量修改备注
     *
     * @param ids
     * @param remark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    public Response batchRemark(Long[] ids, String remark) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateRemark(id, remark, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/brand/remark", method = RequestMethod.POST)
    public Response updateRemarkByBrand(Long brandId, Long seriesId, String remark) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (brandId == null) {
            result.setStatus(-1);
            result.setMessage("请选择品牌！");
            return result;
        }
        // 查找该品牌的商品
        PageModel page = new PageModel();
        PageResult<Long> productIds = new PageResult<>();
        do {
            productIds = productService.findByBrandAndSeries(brandId, seriesId, page);
            for (Long id : productIds.getList()) {
                productService.updateRemark(id, remark, admin.getUsername());
            }
            page.setP(page.getP() + 1);
        } while (productIds.isNext());
        return result;
    }

    /**
     * 批量修改售后备注
     *
     * @param ids
     * @param afterMemo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/batch/aftermemo", method = RequestMethod.POST)
    public Response batchAfterMemo(Long[] ids, String afterMemo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateAfterMemo(id, afterMemo, admin.getUsername());
        }
        return result;
    }

    /**
     * 批量修改销售类型
     *
     * @param ids
     * @param sellType
     * @param estimateDate
     * @param estimateDay
     * @param remark
     * @return
     */
    @RequestMapping(value = "/batch/sellType", method = RequestMethod.POST)
    public Response batchSellType(Long[] ids, String sellType, Date estimateDate, Integer estimateDay, String remark) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        ProductSellType type = ProductSellType.valueOf(sellType);
        if (type == null) {
            return new ErrorResponse("不存在该类型的生产类型");
        }
        if (ProductSellType.PRESELL.name().equals(sellType) && estimateDate == null) {
            return new ErrorResponse("预售商品，预计发货时间不能为空");
        }
        if (ProductSellType.CUSTOM.name().equals(sellType) && estimateDay == null) {
            return new ErrorResponse("定制商品，预计发货时间不能为空");
        }
        int count = 0;
        for (Long id : ids) {
            int success = productService.updateSellType(id, sellType, estimateDate, estimateDay, remark);
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("操作成功" + count + "条，失败" + (ids.length - count) + "条");
        return result;
    }

    /**
     * 备选商品通过审核
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/option/success", method = RequestMethod.POST)
    public Response optionSuccess(Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            ProductOption productOption = productOptionService.findById(id);
            if (productOption.getMark() == 8) {
                result.setStatus(-1);
                result.setMessage("商品款号：" + productOption.getInernalSn() + "，该商品已经审核通过了");
                return result;
            }
            Product product = new Product();
            BeanUtils.copyProperties(productOption, product);
            product.setMark(1);
            JSONObject productJson = (JSONObject) JSON.toJSON(product);
            JSONArray jaProductPictures = new JSONArray();
            if (productOption.getIntroPic() != null) {
                jaProductPictures = JSONArray.parseArray(JSON.toJSONString(productOption.getIntroPic().split(",")));
            }
            productJson.put("productPictures", jaProductPictures);
            productJson.putAll(productOption.getProductOtherJson());
            // 更新到线上商品
            if (productOption.getOnlineProductId() != null) {
                productJson.put("id", productOption.getOnlineProductId());
                // 合并sku
                List<ProductSku> onlieSkus = productSkuService.findByProductId(productOption.getOnlineProductId());
                List<ProductSkuOption> optionSkus = productSkuOptionService.findByProductId(id);
                JSONArray array = new JSONArray();
                Map<String, ProductSku> onlieSkuMap = new HashMap<>();
                for (ProductSku productSku : onlieSkus) {
                    array.add(productSku);
                    onlieSkuMap.put(productSku.getSn(), productSku);
                }
                for (ProductSkuOption optionSku : optionSkus) {
                    if (onlieSkuMap.containsKey(optionSku.getSn())) {
                        ProductSku onlieSku = onlieSkuMap.get(optionSku.getSn());
                        optionSku.setId(onlieSku.getId());
                        optionSku.setStatus(onlieSku.getStatus());
                        optionSku.setPopStore(onlieSku.getPopStore());
                        BeanUtils.copyProperties(optionSku, onlieSku);
                        array.remove(onlieSkuMap.get(optionSku.getSn()));
                        array.add(onlieSku);
                    } else {
                        optionSku.setId(null);
                        array.add(optionSku);
                    }
                }
                productJson.put("productSKUSet", array);
                // 避免影响线上
                productJson.put("sort", null);
                productJson.put("syncStamp", null);
                productJson.put("popStore", null);
                Response updateResult = this.doUpdate(productOption.getOnlineProductId(), productJson);
                if (updateResult.getStatus() == 1) {
                    productOptionService.doMarkSuccess(productOption.getId());
                    // 添加操作日志
                    JSONObject info = new JSONObject();
                    info.put("操作", "审核通关设计师提交的商品修改");
                    productLogService.insert(new ProductLog(admin.getUsername(), info.toJSONString(),
                            ProductLogType.Update, product.getId(), null));
                } else {
                    updateResult.setMessage("商品款号：" + productOption.getInernalSn() + "," + updateResult.getMessage());
                    return updateResult;
                }
            } else {
                productJson.put("id", null);
                JSONArray array = new JSONArray();
                List<ProductSkuOption> optionSkus = productSkuOptionService.findByProductId(id);
                for (ProductSkuOption optionSku : optionSkus) {
                    optionSku.setId(null);
                    array.add(optionSku);
                }
                productJson.put("productSKUSet", array);
                // 新增商品设置返利比
                BrandDetail brandDetail = brandDetailService.findByBrandId(productOption.getDesignerId());
                BigDecimal firstRatio = null;
                BigDecimal secondRatio = null;
                BigDecimal grossRatio = null;
                if (brandDetail != null) {
                    String type = SaleCategory.POPPRODUCT.name().equals(productOption.getSaleCategory())
                            ? SaleCategory.POPPRODUCT.name() : productOption.getProductSaleType();// 通过商品销售类型获取返利
                    if (brandDetail.getPartnerRatioJson().keySet().contains(type)) {
                        JSONObject ratio = brandDetail.getPartnerRatioJson().getJSONObject(type);
                        JSONObject defaultRatio = new BrandDetail().getPartnerRatioJson().getJSONObject(type);// 跟默认值比较，如果是默认值，要求它必填
                        if (ratio.getBigDecimal("firstRatio").compareTo(defaultRatio.getBigDecimal("firstRatio")) != 0
                                && ratio.getBigDecimal("secondRatio")
                                .compareTo(defaultRatio.getBigDecimal("secondRatio")) != 0) {
                            firstRatio = ratio.getBigDecimal("firstRatio");
                            secondRatio = ratio.getBigDecimal("secondRatio");
                            grossRatio = ratio.getBigDecimal("grossRatio");
                        }
                    }
                }
                if (firstRatio == null || secondRatio == null || grossRatio == null) {
                    result.setStatus(-1);
                    result.setMessage("商品款号：" + productOption.getInernalSn() + ",请先设置该商品品牌的返利比");
                    return result;
                }
                productJson.put("firstRatio", firstRatio);
                productJson.put("secondRatio", secondRatio);
                productJson.put("grossRatio", grossRatio);
                Response insertResult = this.doInsert(productJson);
                if (insertResult.getStatus() == 1) {
                    productOptionService.doMarkSuccess(productOption.getId());
                    // 添加操作日志
                    JSONObject info = new JSONObject();
                    info.put("操作", "审核通关设计师提交的商品新增");
                    productLogService.insert(new ProductLog(admin.getUsername(), info.toJSONString(),
                            ProductLogType.Insert, product.getId(), null));
                } else {
                    insertResult.setMessage("商品款号：" + productOption.getInernalSn() + "," + insertResult.getMessage());
                    return insertResult;
                }
            }
        }
        return result;
    }

    /**
     * 更新活动的排序
     *
     * @param productId
     * @param gpSort
     * @param opSort
     * @param fpSort
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Long productId, Integer gpSort, Integer opSort, Integer fpSort)
            throws NotLoginException {
        this.getLoginedAdmin();
        productService.updatePromotionSort(productId, gpSort, opSort, fpSort);
        return new SuccessResponse();
    }

    /**
     * 上传以图搜图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.GET)
    public Response uploadPic(@PathVariable("id") Long id) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int success = productService.uploadPic(id);
        if (success < 0) {
            result.setMessage("上传图片不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 是否支持优惠券
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/coupon/{coupon}", method = RequestMethod.POST)
    public Response coupon(@PathVariable Integer coupon, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            productService.updateCoupon(id, coupon, admin.getUsername());
        }
        return result;
    }

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/ids", method = RequestMethod.POST)
    public Response findByIds(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        Map<Long, Product> map = productService.findByIds(ids);
        List<Product> list = new ArrayList<>();
        for (Long id : ids) {
            list.add(map.get(id));
        }
        result.put("products", list);
        return result;
    }

}