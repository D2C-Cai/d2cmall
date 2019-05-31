package com.d2c.backend.rest.logger;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.CaomeiOrderErrorLog;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.model.KaolaOrderErrorLog;
import com.d2c.logger.query.ErrorLogSearcher;
import com.d2c.logger.service.CaomeiOrderErrorLogService;
import com.d2c.logger.service.ErrorLogService;
import com.d2c.logger.service.KaolaOrderErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/sys/errorLog")
public class ErrorLogCtrl extends BaseCtrl<ErrorLogSearcher> {

    @Autowired
    private ErrorLogService errorLogService;
    @Autowired
    private KaolaOrderErrorLogService kaolaOrderErrorLogService;
    @Autowired
    private CaomeiOrderErrorLogService caomeiOrderErrorLogService;

    @Override
    protected Response doList(ErrorLogSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ErrorLog> pager = errorLogService.findBySearcher(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(ErrorLogSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ErrorLogSearcher searcher, PageModel page) {
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
    protected Response doHelp(ErrorLogSearcher searcher, PageModel page) {
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
        SuccessResponse result = new SuccessResponse();
        int count = 0;
        for (Long id : ids) {
            int success = errorLogService.delete(id);
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("成功删除" + count + "条");
        return result;
    }

    /**
     * 考拉异常推单
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/kaola/orders", method = RequestMethod.POST)
    public Response kaolaOrders(PageModel page) {
        PageResult<KaolaOrderErrorLog> pager = kaolaOrderErrorLogService.findBySearcher(page);
        return new SuccessResponse(pager);
    }

    /**
     * 草莓异常推单
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/caomei/orders", method = RequestMethod.POST)
    public Response caomeiOrders(PageModel page) {
        PageResult<CaomeiOrderErrorLog> pager = caomeiOrderErrorLogService.findBySearcher(page);
        return new SuccessResponse(pager);
    }

}
