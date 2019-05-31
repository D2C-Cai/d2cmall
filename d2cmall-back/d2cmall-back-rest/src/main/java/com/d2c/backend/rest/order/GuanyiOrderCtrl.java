package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.GuanyiOrderDto;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.query.GuanyiOrderItemSearcher;
import com.d2c.order.query.GuanyiOrderSearcher;
import com.d2c.order.service.GuanyiOrderItemService;
import com.d2c.order.service.GuanyiOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/guanyi")
public class GuanyiOrderCtrl extends BaseCtrl<GuanyiOrderSearcher> {

    @Autowired
    private GuanyiOrderService guanyiOrderService;
    @Autowired
    private GuanyiOrderItemService guanyiOrderItemService;

    @Override
    protected Response doList(GuanyiOrderSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(GuanyiOrderSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(GuanyiOrderSearcher searcher, PageModel page) {
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
    protected Response doHelp(GuanyiOrderSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/error/burgeon/list", method = RequestMethod.GET)
    public Response toBurgeonError(GuanyiOrderSearcher search, PageModel page) {
        search.setBurgeonFall(1);
        PageResult<GuanyiOrderDto> pager = guanyiOrderService.findBySearch(search, page);
        return new SuccessResponse(pager);
    }

    @RequestMapping(value = "/error/express/list", method = RequestMethod.GET)
    public Response toExpressError(GuanyiOrderItemSearcher search, PageModel page) {
        search.setExpressFall(1);
        PageResult<GuanyiOrderItem> pager = guanyiOrderItemService.findBySearcher(search, page);
        return new SuccessResponse(pager);
    }

    @RequestMapping(value = "/reprocess/burgeon/{id}", method = RequestMethod.POST)
    public Response reProcessBurgeon(@PathVariable("id") Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        GuanyiOrder guanyiOrder = guanyiOrderService.findById(id);
        GuanyiOrderDto guanyiOrderDto = new GuanyiOrderDto();
        BeanUtils.copyProperties(guanyiOrder, guanyiOrderDto);
        guanyiOrderDto.setItems(guanyiOrderItemService.findByOrderId(guanyiOrder.getId()));
        Integer success = guanyiOrderService.processBurgeon(guanyiOrderDto, true, admin.getUsername());
        if (success == 0) {
            result.setStatus(-1);
            result.setMessage("重新做单失败，请刷新查看错误信息.");
        }
        return result;
    }

    @RequestMapping(value = "/reprocess/express/{id}", method = RequestMethod.POST)
    public Response reProcess(@PathVariable("id") Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        GuanyiOrderItem guanyiOrderItem = guanyiOrderItemService.findById(id);
        try {
            guanyiOrderItemService.processExpress(guanyiOrderItem, false, admin.getUsername());
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage("重新做单失败，请刷新查看错误信息.");
            guanyiOrderItem.setExpressFall(1);
            guanyiOrderItem.setExpressError(e.getMessage() == null ? "空指针异常" : e.getMessage());
            guanyiOrderItemService.update(guanyiOrderItem);
        }
        return result;
    }

    @RequestMapping(value = "/handle/burgeon/{id}", method = RequestMethod.POST)
    public Response handleBurgeon(@PathVariable("id") Long id, String handleContent) {
        Admin admin = this.getLoginedAdmin();
        guanyiOrderService.doHandle(id, 2, admin.getUsername(), handleContent);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/handle/express/{id}", method = RequestMethod.POST)
    public Response handleExpress(@PathVariable("id") Long id, String handleContent) {
        Admin admin = this.getLoginedAdmin();
        guanyiOrderItemService.doHandle(id, admin.getUsername(), handleContent);
        return new SuccessResponse();
    }

}
