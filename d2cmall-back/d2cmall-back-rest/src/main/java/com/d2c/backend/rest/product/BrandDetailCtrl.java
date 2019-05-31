package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.BrandDetailDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.BrandContract;
import com.d2c.product.model.BrandContract.SaleType;
import com.d2c.product.model.BrandDetail;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.query.BrandDetailSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.BrandDetailService;
import com.d2c.product.service.ProductService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/branddetail")
public class BrandDetailCtrl extends BaseCtrl<BrandDetailSearcher> {

    @Autowired
    private BrandDetailService brandDetailService;
    @Autowired
    private ProductService productService;

    @Override
    protected Response doList(BrandDetailSearcher searcher, PageModel page) {
        PageResult<BrandDetailDto> pager = brandDetailService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(BrandDetailSearcher searcher) {
        return brandDetailService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "BrandDetail";
    }

    @Override
    protected List<Map<String, Object>> getRow(BrandDetailSearcher searcher, PageModel page) {
        searcher.setContractStatus(1);
        PageResult<BrandDetailDto> pager = brandDetailService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (BrandDetailDto brandDetail : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("品牌名称", brandDetail.getBrandName());
            cellsMap.put("设计师名称", brandDetail.getDesigner());
            cellsMap.put("联系人", brandDetail.getLinker());
            cellsMap.put("联系方式", brandDetail.getContacts());
            cellsMap.put("邮箱", brandDetail.getEmail());
            cellsMap.put("打款账号", brandDetail.getAccount());
            cellsMap.put("公司税号", brandDetail.getTaxNumber());
            cellsMap.put("账单接收邮箱", brandDetail.getBillReceiveEmail());
            List<BrandContract> list = brandDetail.getContracts();
            for (int i = 0; i < list.size(); i++) {
                BrandContract brandContract = list.get(i);
                cellsMap.put("合同" + i, SaleType.valueOf(brandContract.getType()).getName());
                cellsMap.put("合同开始时间" + i, DateUtil.second2str(brandContract.getStartDate()));
                cellsMap.put("合同到期时间" + i, DateUtil.second2str(brandContract.getEndDate()));
                cellsMap.put("分成" + i,
                        "平台：" + brandContract.getPlatformRatio() + ",品牌：" + brandContract.getBrandRatio());
            }
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "品牌档案";
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return new String[]{"品牌名称", "设计师名称", "联系人", "联系方式", "邮箱", "打款账号", "公司税号", "账单接收邮箱", "合同1", "合同开始时间1",
                "合同到期时间1", "分成1", "合同2", "合同开始时间2", "合同到期时间2", "分成2", "合同3", "合同开始时间3", "合同到期时间3", "分成3", "合同4",
                "合同开始时间4", "合同到期时间4", "分成4"};
    }

    @Override
    protected Response doHelp(BrandDetailSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        BrandDetailDto brandDetail = brandDetailService.findById(id);
        result.put("brandDetail", brandDetail);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        String brandContractListKey = "contracts";
        Admin admin = this.getLoginedAdmin();
        JSONArray array = JSON.parseArray(data.getString(brandContractListKey));
        data.remove(brandContractListKey);
        data.remove("partnerRatio");
        data.remove("partnerRatioJson");
        BrandDetail brandDetail = (BrandDetail) JsonUtil.instance().toObject(data, BrandDetail.class);
        brandDetail.setStatus(1);
        brandDetail.setMerchantsMan(admin.getUsername());
        BrandDetailDto dto = new BrandDetailDto();
        BeanUtils.copyProperties(brandDetail, dto);
        List<BrandContract> brandContracts = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            BrandContract brandContract = JsonUtil.instance().toObject(array.getJSONObject(i), BrandContract.class);
            brandContract.setStatus(1);
            brandContract.setBrandId(brandDetail.getBrandId());
            brandContracts.add(brandContract);
        }
        dto.setContracts(brandContracts);
        brandDetailService.update(dto);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // String brandContractListKey = "brandContractList";
        // Admin admin = this.getLoginedAdmin();
        // JSONArray array =
        // JSON.parseArray(data.getString(brandContractListKey));
        // data.remove(brandContractListKey);
        // BrandDetail brandDetail = (BrandDetail)
        // JsonUtil.instance().toObject(data, BrandDetail.class);
        // brandDetail.setStatus(1);
        // brandDetail.setMerchantsMan(admin.getUsername());
        // BrandDetailDto dto = new BrandDetailDto();
        // BeanUtils.copyProperties(brandDetail, dto);
        // List<BrandContract> brandContracts = new ArrayList<>();
        // for (int i = 0; i < array.size(); i++) {
        // BrandContract brandContract =
        // JsonUtil.instance().toObject(array.getJSONObject(i),
        // BrandContract.class);
        // brandContract.setBrandId(brandDetail.getBrandId());
        // brandContracts.add(brandContract);
        // }
        // dto.setContracts(brandContracts);
        // brandDetailService.insert(dto);
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

    /**
     * 终止合同
     *
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "/contractstatus/{status}", method = RequestMethod.POST)
    public Response stopContract(@PathVariable Integer status, Long id) {
        SuccessResponse result = new SuccessResponse();
        if (!(status == 1 || status == 0) || id <= 0) {
            result.setStatus(-1);
            result.setMessage("参数错误！");
            return result;
        }
        brandDetailService.updateContractStatus(id, status);
        return result;
    }

    /**
     * 按商品类型批量下架商品
     *
     * @param brandId
     * @param saleType
     * @param saleCategory
     * @return
     */
    @RequestMapping(value = "/mark/type/{brandId}", method = RequestMethod.POST)
    public Response markProduct(@PathVariable Long brandId, String saleType) {
        Admin admin = this.getLoginedAdmin();
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.setDesignerId(brandId);
        productSearcher.setMark(1);
        if (SaleCategory.POPPRODUCT.name().equals(saleType)) {
            productSearcher.setSaleCategory(SaleCategory.POPPRODUCT.name());
        } else {
            productSearcher.setProductSaleType(saleType);
            productSearcher.setSaleCategory(SaleCategory.NORMALPRODUCT.name());
        }
        PageModel page = new PageModel();
        PageResult<ProductDto> pager = null;
        do {
            pager = productService.findBySearch(productSearcher, page);
            for (ProductDto p : pager.getList()) {
                productService.updateMark(p.getId(), 0, admin.getUsername());
            }
            pager.setPageNumber(pager.getPageNumber() + 1);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 修改分销分成
     *
     * @param id
     * @param firstRatio
     * @param secondRatio
     * @param grossRatio
     * @return
     */
    @RequestMapping(value = "/ratio/{id}", method = RequestMethod.POST)
    public Response processRatio(@PathVariable Long id, String partnerRatio) {
        brandDetailService.updateRatioById(id, partnerRatio);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/ratio/default", method = RequestMethod.GET)
    public Response findRatioByProduct(Long designerId, String saleCategory, String saleType) {
        SuccessResponse result = new SuccessResponse();
        result.put("firstRatio", null);
        result.put("secondRatio", null);
        result.put("grossRatio", null);
        // 查找品牌合同，设置分销比
        BrandDetail brandDetail = brandDetailService.findByBrandId(designerId);
        if (brandDetail != null) {
            String type = SaleCategory.POPPRODUCT.name().equals(saleCategory) ? SaleCategory.POPPRODUCT.name()
                    : saleType;// 通过商品销售类型获取返利
            if (brandDetail.getPartnerRatioJson().keySet().contains(type)) {
                JSONObject ratio = brandDetail.getPartnerRatioJson().getJSONObject(type);
                JSONObject defaultRatio = new BrandDetail().getPartnerRatioJson().getJSONObject(type);// 跟默认值比较，如果是默认值，要求它必填
                if (ratio.getBigDecimal("firstRatio").compareTo(defaultRatio.getBigDecimal("firstRatio")) != 0 && ratio
                        .getBigDecimal("secondRatio").compareTo(defaultRatio.getBigDecimal("secondRatio")) != 0) {
                    result.put("firstRatio", ratio.getBigDecimal("firstRatio"));
                    result.put("secondRatio", ratio.getBigDecimal("secondRatio"));
                    result.put("grossRatio", ratio.getBigDecimal("grossRatio"));
                }
            }
        }
        return result;
    }

}
