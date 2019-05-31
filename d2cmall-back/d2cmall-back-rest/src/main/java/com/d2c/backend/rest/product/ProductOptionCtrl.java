package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.dto.AdminDto;
import com.d2c.product.model.ProductOption;
import com.d2c.product.model.ProductSkuOption;
import com.d2c.product.query.ProductOptionSearcher;
import com.d2c.product.service.ProductOptionService;
import com.d2c.product.service.ProductSkuOptionService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productoption")
public class ProductOptionCtrl extends BaseCtrl<ProductOptionSearcher> {

    @Autowired
    private ProductOptionService productOptionService;
    @Autowired
    private ProductSkuOptionService productSkuOptionService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    protected Response doList(ProductOptionSearcher searcher, PageModel page) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        PageResult<ProductOption> pageResult = productOptionService.findBySearch(searcher, page);
        SuccessResponse successResponse = new SuccessResponse(pageResult);
        return successResponse;
    }

    @Override
    protected int count(ProductOptionSearcher searcher) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        return productOptionService.countBySearch(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductOptionSearcher searcher, PageModel page) {
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
    protected Response doHelp(ProductOptionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductOption productOption = productOptionService.findById(id);
        result.put("product", productOption);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        // 商品SKU
        JSONArray jaProductSKUSet = JSON.parseArray(data.getString("productSKUSet"));
        data.remove("productSKUSet");
        // 商品图片
        JSONArray jaProductPictures = JSON.parseArray(data.getString("productPictures"));
        data.remove("productPictures");
        // 商品短视频
        String taskIds = data.getString("task_ids");
        data.remove("task_ids");
        // 预计发货时间
        String estimateDate = data.getString("estimateDate");
        if ("".equals(estimateDate)) {
            data.remove("estimateDate");
        }
        data.remove("sizetable");
        data.remove("productOtherJson");
        ProductOption productDto = JsonUtil.instance().toObject(data, ProductOption.class);
        if (data.getString("sizeJson") != null) {
            productDto.setSizeJson(data.getString("sizeJson"));
        }
        // 商品SKU数据设置
        List<ProductSkuOption> productSKUSet = new ArrayList<>();
        List<String> productSKUs = new ArrayList<>();
        for (int i = 0; i < jaProductSKUSet.size(); i++) {
            JSONObject jo = jaProductSKUSet.getJSONObject(i);
            String sp1 = jo.get("sp1").toString();
            String sp2 = jo.get("sp2").toString();
            ProductSkuOption sku = JsonUtil.instance().toObject(jo, ProductSkuOption.class);
            sku.setSp1(sp1);
            sku.setSp2(sp2);
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
            // ProductSku oldSku = productSkuService.findByBarCode(sku.getSn());
            // if (oldSku != null && oldSku.getStatus() != -1 &&
            // !oldSku.getId().equals(sku.getId())) {
            // result.setStatus(-1);
            // result.setMessage("sku条码：" + sku.getSn() + "已存在！");
            // return result;
            // }
            productSKUSet.add(sku);
            productSKUs.add(sku.getSn());
        }
        // 商品图片数据设置
        String introPic = "";
        for (int i = 0; i < jaProductPictures.size(); i++) {
            introPic += jaProductPictures.getString(i).toString() + ",";
        }
        if (introPic.length() > 0) {
            introPic = introPic.substring(0, introPic.length() - 1);
        }
        productDto.setIntroPic(introPic);
        // 商品总体更新
        productOptionService.update(productDto, productSKUSet);
        // 商品短视频设置
        if (productDto.getVideo() == null) {
            productOptionService.updateVideoById(productDto.getId(), null);
        } else if (StringUtils.isNotBlank(taskIds)) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskIds);
            upyunTask.setSourceType(SourceType.PRODUCT.toString());
            upyunTask.setSourceId(productDto.getId());
            upyunTask.setStatus(0);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                productOptionService.updateVideoById(productDto.getId(), upyunTask.getVideo());
            }
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        // 商品SKU
        JSONArray jaProductSKUSet = JSON.parseArray(data.getString("productSKUSet"));
        data.remove("productSKUSet");
        // 商品图片
        JSONArray jaProductPictures = JSON.parseArray(data.getString("productPictures"));
        data.remove("productPictures");
        // 商品短视频
        String taskIds = data.getString("task_ids");
        data.remove("task_ids");
        // 预计发货时间
        String estimateDate = data.getString("estimateDate");
        if ("".equals(estimateDate)) {
            data.remove("estimateDate");
        }
        data.remove("sizetable");
        data.remove("productOtherJson");
        ProductOption product = JsonUtil.instance().toObject(data, ProductOption.class);
        if (data.getString("sizeJson") != null) {
            product.setSizeJson(data.getString("sizeJson"));
        }
        product.setOnlineProductId(product.getId());
        product.setId(null);
        // 商品SKU数据设置
        List<ProductSkuOption> productSKUSet = new ArrayList<>();
        List<String> productSKUs = new ArrayList<>();
        for (int i = 0; i < jaProductSKUSet.size(); i++) {
            JSONObject jo = jaProductSKUSet.getJSONObject(i);
            String sp1 = jo.get("sp1").toString();
            String sp2 = jo.get("sp2").toString();
            ProductSkuOption sku = JsonUtil.instance().toObject(jo, ProductSkuOption.class);
            sku.setSp1(sp1);
            sku.setSp2(sp2);
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
            // ProductSku oldSku = productSkuService.findByBarCode(sku.getSn());
            // if (oldSku != null && oldSku.getStatus() != -1) {
            // result.setStatus(-1);
            // result.setMessage("sku条码：" + sku.getSn() + "已存在！");
            // return result;
            // }
            sku.setId(null);
            productSKUSet.add(sku);
            productSKUs.add(sku.getSn());
        }
        // product.setProductSKUSet(productSKUSet);
        // 商品图片数据设置
        String introPic = "";
        for (int i = 0; i < jaProductPictures.size(); i++) {
            introPic += jaProductPictures.getString(i).toString() + ",";
        }
        if (introPic.length() > 0) {
            introPic = introPic.substring(0, introPic.length() - 1);
        }
        product.setIntroPic(introPic);
        // 商品总体新增
        product.setMark(0);
        product = productOptionService.insert(product, productSKUSet);
        result.put("product", product);
        // 商品短视频设置
        if (StringUtils.isNotBlank(taskIds)) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskIds);
            upyunTask.setSourceType(SourceType.PRODUCT.toString());
            upyunTask.setSourceId(product.getId());
            upyunTask.setStatus(0);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                productOptionService.updateVideoById(product.getId(), upyunTask.getVideo());
            }
        }
        return result;
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

    @RequestMapping(value = "/submit/{id}", method = RequestMethod.POST)
    public Response submit(@PathVariable Long id) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        productOptionService.doSubmit(id);
        return result;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Response submits(Long[] ids) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            productOptionService.doSubmit(id);
        }
        return result;
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public Response refuse(Long[] ids, String refuseReason) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            productOptionService.doRefuse(id, refuseReason);
        }
        return result;
    }

    @RequestMapping(value = "/sku/list", method = RequestMethod.POST)
    public Response sku(Long productId) {
        this.getLoginedAdmin();
        List<ProductSkuOption> list = productSkuOptionService.findByProductId(productId);
        return new SuccessResponse(list);
    }

    @RequestMapping(value = "/delete/sku", method = RequestMethod.POST)
    public Response deleteSku(Long id) {
        this.getLoginedAdmin();
        ProductSkuOption deleteSku = productSkuOptionService.findById(id);
        if (deleteSku != null) {
            ProductOption product = productOptionService.findById(deleteSku.getProductId());
            if (product != null && product.getMark() != 0) {
                throw new BusinessException("该商品已经提交审核，不可删除sku");
            }
        }
        productSkuOptionService.delete(id);
        return new SuccessResponse();
    }

}
