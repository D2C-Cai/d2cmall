package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.dto.AdminDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.query.BrandCategorySearcher;
import com.d2c.product.service.BrandCategoryService;
import com.d2c.product.service.BrandService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/rest/sys/basicdata")
public class BrandCategoryCtrl extends BaseCtrl<BrandCategorySearcher> {

    @Autowired
    private BrandCategoryService basicDataService;
    @Autowired
    private BrandService brandService;

    @Override
    protected List<Map<String, Object>> getRow(BrandCategorySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BrandCategorySearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
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
    protected Response doHelp(BrandCategorySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(BrandCategorySearcher searcher, PageModel page) {
        if (StringUtils.isBlank(searcher.getType())) {
            searcher.setType("country");
        }
        // BeanUt.trimString(searcher);
        PageResult<BrandCategory> pager = basicDataService.findBySearch(searcher, page);
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
        SuccessResponse result = new SuccessResponse();
        BrandCategory basicData = (BrandCategory) JsonUtil.instance().toObject(data, BrandCategory.class);
        basicData.setCode("1");
        basicData.setOrderList(1);
        try {
            basicData = basicDataService.insert(basicData);
            result.put("basicData", basicData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        BrandCategory basicData = (BrandCategory) JsonUtil.instance().toObject(data, BrandCategory.class);
        try {
            basicDataService.update(basicData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/sort/{id}/{orderList}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable(value = "id") Long id,
                               @PathVariable(value = "orderList") Integer orderList) {
        SuccessResponse result = new SuccessResponse();
        try {
            basicDataService.updateSort(id, orderList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/status/{status}/{id}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable(value = "status") Integer status, @PathVariable(value = "id") Long id) {
        SuccessResponse result = new SuccessResponse();
        try {
            basicDataService.updateStatus(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public Response myOperator() {
        SuccessResponse result = new SuccessResponse();
        AdminDto admin = this.getLoginedAdmin();
        List<JSONObject> list = new ArrayList<>();
        if (admin.getDesignerId() != null) {
            List<Brand> brands = brandService.findByDesignersId(admin.getDesignerId(), new Integer[]{1});
            if (brands != null && brands.size() > 0) {
                Map<String, JSONObject> operationMap = new HashMap<>();
                brands.forEach(b -> {
                    JSONObject obj = null;
                    if ((obj = operationMap.get(b.getOperation())) == null) {
                        BrandCategory brandCategory = basicDataService.findByNameAndType(b.getOperation(),
                                BrandCategory.OPERATION);
                        obj = JSON.parseObject(brandCategory.getInfo());
                        operationMap.put(b.getOperation(), obj);
                    }
                    obj.put("brand", b.getName());
                    list.add(obj);
                });
            }
        }
        result.put("list", list);
        return result;
    }

}
