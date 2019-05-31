package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.dto.AdminDto;
import com.d2c.order.model.RequisitionError;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.query.RequisitionErrorSearcher;
import com.d2c.order.service.RequisitionErrorService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/requisitionerror")
public class RequisitionErrorCtrl extends BaseCtrl<RequisitionErrorSearcher> {

    @Autowired
    private RequisitionErrorService requisitionErrorService;
    @Autowired
    private RequisitionItemService requisitionItemService;

    @Override
    protected Response doList(RequisitionErrorSearcher query, PageModel page) {
        PageResult<RequisitionError> pager = requisitionErrorService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AdminDto admin = this.getLoginedAdmin();
        RequisitionError requisitionError = JsonUtil.instance().toObject(data, RequisitionError.class);
        RequisitionItem requisitionItem = requisitionItemService
                .findByRequisitionSn(requisitionError.getRequisitionSn());
        if (requisitionItem != null) {
            requisitionError.buildProductByRequisitionItem(requisitionItem);
        }
        requisitionError.setCreator(admin.getUsername());
        requisitionError.setStatus(0);
        requisitionError = requisitionErrorService.insert(requisitionError);
        result.put("requisitionError", requisitionError);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        RequisitionError requisitionError = requisitionErrorService.findById(id);
        result.put("requisitionError", requisitionError);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
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

    @Override
    protected Response doHelp(RequisitionErrorSearcher query, PageModel pager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(RequisitionErrorSearcher query) {
        return requisitionErrorService.countBySearcher(query);
    }

    @Override
    protected String getFileName() {
        return "异常单据";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"异常单号", "关联单号", "异常类型", "SKU", "数量", "货号", "颜色", "尺码", "快递公司", "快递单号", "情况说明"};
    }

    @Override
    protected List<Map<String, Object>> getRow(RequisitionErrorSearcher query, PageModel page) {
        PageResult<RequisitionError> pager = requisitionErrorService.findBySearcher(query, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        for (RequisitionError requisitionError : pager.getList()) {
            Map<String, Object> cellsMap = new HashMap<String, Object>();
            cellsMap.put("异常单号", requisitionError.getRequisitionSn());
            cellsMap.put("关联单号", requisitionError.getRelationSn());
            cellsMap.put("异常类型", requisitionError.getType());
            cellsMap.put("SKU", requisitionError.getBarcode());
            cellsMap.put("数量", requisitionError.getQuantity());
            cellsMap.put("货号", requisitionError.getProductSn());
            if (StringUtils.isNotBlank(requisitionError.getSp1())) {
                cellsMap.put("颜色", JSON.parseObject(requisitionError.getSp1()).getString("value"));
            }
            if (StringUtils.isNotBlank(requisitionError.getSp2())) {
                cellsMap.put("尺码", JSON.parseObject(requisitionError.getSp2()).getString("value"));
            }
            cellsMap.put("快递公司", requisitionError.getDeliveryCorp());
            cellsMap.put("快递单号", requisitionError.getDeliverySn());
            cellsMap.put("情况说明", requisitionError.getRemark());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getExportFileType() {
        return "RequisitionError";
    }

    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    public Response doReship(Long id, String remark) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        requisitionErrorService.doRemark(id, remark, admin.getUsername());
        return new SuccessResponse();
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public Response doProcess(Long id) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        requisitionErrorService.doProcess(id, admin.getUsername());
        return new SuccessResponse();
    }

}
