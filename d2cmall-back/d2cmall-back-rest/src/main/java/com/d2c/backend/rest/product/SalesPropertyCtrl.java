package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.dto.SalesPropertyDto;
import com.d2c.product.model.SalesProperty;
import com.d2c.product.model.SalesPropertyGroup;
import com.d2c.product.query.SalesPropertySearcher;
import com.d2c.product.service.SalesPropertyGroupService;
import com.d2c.product.service.SalesPropertyService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/salesproperty")
public class SalesPropertyCtrl extends BaseCtrl<SalesPropertySearcher> {

    @Autowired
    private SalesPropertyService salesPropertyService;
    @Autowired
    private SalesPropertyGroupService salesPropertyGroupService;

    @Override
    protected List<Map<String, Object>> getRow(SalesPropertySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SalesPropertySearcher searcher) {
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
    protected Response doHelp(SalesPropertySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SalesPropertySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SalesPropertyDto> pager = salesPropertyService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        SalesProperty salesProperty = salesPropertyService.findById(id);
        result.put("salesProperty", salesProperty);
        return result;
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
        SuccessResponse result = new SuccessResponse();
        SalesProperty salesProperty = (SalesProperty) JsonUtil.instance().toObject(data, SalesProperty.class);
        if (salesProperty.getGroupId() == null) {
            result.setStatus(-1);
            result.setMessage("规格必须选择一个规格组！");
            return result;
        }
        try {
            SalesPropertyGroup group = salesPropertyGroupService.findById(salesProperty.getGroupId());
            salesProperty.setName(group.getName());
            salesProperty.setType(group.getType());
            salesProperty = salesPropertyService.insert(salesProperty);
            SalesPropertyDto dto = new SalesPropertyDto();
            BeanUtils.copyProperties(salesProperty, dto);
            dto.setGroupName(group.getTitle());
            result.put("salesProperty", dto);
        } catch (Exception ex) {
            Response result1 = new ErrorResponse(ex.getMessage());
            if (result1.getMessage().indexOf("DuplicateKeyException") > 0) {
                result.setMessage("该规格组下已经存在相同的编码，请确认！");
                result.setStatus(-1);
            }
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SalesProperty salesProperty = (SalesProperty) JsonUtil.instance().toObject(data, SalesProperty.class);
        try {
            SalesPropertyGroup group = salesPropertyGroupService.findById(salesProperty.getGroupId());
            salesProperty.setName(group.getName());
            salesProperty.setType(group.getType());
            salesPropertyService.update(salesProperty);
        } catch (Exception ex) {
            Response result1 = new ErrorResponse(ex.getMessage());
            if (result1.getMessage().indexOf("DuplicateKeyException") > 0) {
                result.setMessage("该规格组下已经存在相同的编码，请确认！");
                result.setStatus(-1);
            }
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

}
