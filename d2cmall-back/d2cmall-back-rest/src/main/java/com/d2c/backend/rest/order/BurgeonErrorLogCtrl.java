package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.BurgeonErrorLog;
import com.d2c.logger.query.BurgeonErrorLogSearcher;
import com.d2c.logger.service.BurgeonErrorLogService;
import com.d2c.member.model.Admin;
import com.d2c.order.service.RequisitionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/burgeonlog")
public class BurgeonErrorLogCtrl extends BaseCtrl<BurgeonErrorLogSearcher> {

    @Autowired
    private BurgeonErrorLogService burgeonErrorLogService;
    @Autowired
    private RequisitionItemService requisitionItemService;

    @Override
    protected Response doList(BurgeonErrorLogSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<BurgeonErrorLog> pager = burgeonErrorLogService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(BurgeonErrorLogSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(BurgeonErrorLogSearcher searcher, PageModel page) {
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
    protected Response doHelp(BurgeonErrorLogSearcher searcher, PageModel page) {
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
     * 手工做单标记为已处理
     *
     * @param id
     * @param handleContent
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public Response doHandle(Long id, String handleContent) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        burgeonErrorLogService.doHandle(id, admin.getUsername(), handleContent);
        return new SuccessResponse();
    }

    /**
     * 系统重新做单
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/process/{id}", method = RequestMethod.POST)
    public Response doReProcess(@PathVariable Long id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int sucess = requisitionItemService.doReProcess(id);
        if (sucess == 0) {
            result.setStatus(-1);
            result.setMessage("重新做单失败，刷新页面查看失败原因");
            return result;
        }
        burgeonErrorLogService.doReProcessSucess(id, admin.getUsername());
        return result;
    }

}
