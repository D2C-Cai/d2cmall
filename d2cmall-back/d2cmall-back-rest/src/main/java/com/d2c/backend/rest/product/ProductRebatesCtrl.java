package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productrebates")
public class ProductRebatesCtrl extends BaseCtrl<ProductSearcher> {

    @Autowired
    private ProductService productService;

    @Override
    protected Response doList(ProductSearcher searcher, PageModel page) {
        if (searcher.getMark() == null) {
            searcher.setMarks(new Integer[]{1});
        }
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(ProductSearcher searcher) {
        return productService.countBySearch(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "productRebates";
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductSearcher searcher, PageModel page) {
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductDto dto : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("商品名称", dto.getName());
            map.put("品牌", dto.getDesignerName());
            map.put("销售价", dto.getSalePrice());
            map.put("间接返利", dto.getFirstRatio());
            map.put("直接返利", dto.getSecondRatio());
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "商品返利表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"商品名称", "品牌", "销售价", "间接返利", "直接返利"};
    }

    @Override
    protected Response doHelp(ProductSearcher searcher, PageModel page) {
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

    /**
     * 品牌或者系列设置返利
     *
     * @param brandId
     * @param seriesId
     * @param firstRatio
     * @param secondRatio
     * @param grossRatio
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/quick/rebate", method = RequestMethod.POST)
    public Response doQuickRebate(Long brandId, Long seriesId, BigDecimal firstRatio, BigDecimal secondRatio,
                                  BigDecimal grossRatio) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        int successCount = 0;
        // BigDecimal min = new BigDecimal(0);
        // BigDecimal max = new BigDecimal(1);
        Admin admin = this.getLoginedAdmin();
        // if (firstRatio.compareTo(max) >= 0 || secondRatio.compareTo(max) >= 0
        // || firstRatio.compareTo(min) < 0
        // || secondRatio.compareTo(min) < 0) {
        // result.setStatus(-1);
        // result.setMessage("返利设置需小于1大于等于0");
        // return result;
        // }
        if (brandId == null && seriesId == null) {
            result.setStatus(-1);
            result.setMessage("必须选择一个品牌或者系列");
            return result;
        }
        PageModel page = new PageModel();
        int numberPage = 1;
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<ProductDto> pager = new PageResult<>();
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.setDesignerId(brandId);
        productSearcher.setSeriesId(seriesId);
        do {
            pager = productService.findBySearch(productSearcher, page);
            for (Product product : pager.getList()) {
                int success = productService.updateRebate(product.getId(), firstRatio, secondRatio, grossRatio,
                        admin.getUsername());
                if (success > 0) {
                    successCount++;
                }
            }
            page.setPageNumber(page.getPageNumber() + 1);
        } while (pager.isNext());
        result.setMessage("成功设置" + successCount + "条！");
        return result;
    }

    /**
     * 批量设置返利
     *
     * @param ids
     * @param firstRadio
     * @param secondRatio
     * @param grossRatio
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/batch/rebate", method = RequestMethod.POST)
    public Response doBatchRebate(Long[] ids, BigDecimal firstRatio, BigDecimal secondRatio, BigDecimal grossRatio)
            throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        int successCount = 0;
        // BigDecimal min = new BigDecimal(0);
        // BigDecimal max = new BigDecimal(1);
        Admin admin = this.getLoginedAdmin();
        // if (firstRatio.compareTo(max) >= 0 || secondRatio.compareTo(max) >= 0
        // || firstRatio.compareTo(min) < 0
        // || secondRatio.compareTo(min) < 0) {
        // result.setStatus(-1);
        // result.setMessage("返利设置需小于1大于等于0");
        // return result;
        // }
        for (Long id : ids) {
            int success = productService.updateRebate(id, firstRatio, secondRatio, grossRatio, admin.getUsername());
            if (success > 0) {
                successCount++;
            }
        }
        result.setMessage("成功设置" + successCount + "条，失败" + (ids.length - successCount) + "条");
        return result;
    }

    /**
     * 批量导入返利
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/rebates", method = RequestMethod.POST)
    public Response importRebates(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        BigDecimal min = new BigDecimal(0);
        BigDecimal max = new BigDecimal(1);
        List<Map<String, Object>> execelData = this.getExcelData(request);
        List<Product> beans = new ArrayList<>();
        for (Map<String, Object> map : execelData) {
            Product product = new Product();
            if (map.get("间接返利") == null || map.get("直接返利") == null) {
                return new ErrorResponse("间接返利和直接返利不能为空，没有返利的设置0");
            }
            if (map.get("款号") == null) {
                return new ErrorResponse("款号不能为空");
            }
            product.setFirstRatio(new BigDecimal(map.get("间接返利").toString()));
            product.setSecondRatio(new BigDecimal(map.get("直接返利").toString()));
            product.setInernalSn(map.get("款号").toString());
            beans.add(product);
        }
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                Product bean = (Product) object;
                if (bean.getFirstRatio().compareTo(max) >= 0 || bean.getFirstRatio().compareTo(min) < 0) {
                    errorMsg.append("第" + row + "行，编号：" + bean.getInernalSn() + "的商品间接返利设置不正确，返利值应大于等于0，小于1" + "<br/>");
                    return false;
                }
                if (bean.getSecondRatio().compareTo(max) >= 0 || bean.getSecondRatio().compareTo(min) < 0) {
                    errorMsg.append("第" + row + "行，编号：" + bean.getInernalSn() + "的商品直接利设置不正确，返利值应大于等于0，小于1" + "<br/>");
                    return false;
                }
                Product product = productService.findOneBySn(bean.getInernalSn());
                if (product == null) {
                    errorMsg.append("第" + row + "行，编号：" + bean.getInernalSn() + "的商品不存在或下架状态" + "<br/>");
                    return false;
                }
                productService.updateRebate(product.getId(), bean.getFirstRatio(), bean.getSecondRatio(),
                        bean.getGrossRatio(), admin.getUsername());
                return true;
            }
        });
    }

}
