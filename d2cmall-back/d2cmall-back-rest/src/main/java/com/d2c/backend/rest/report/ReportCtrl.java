package com.d2c.backend.rest.report;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.logger.model.ExportLog;
import com.d2c.logger.service.ExportLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 报表控制器
 */
@RestController
@RequestMapping("/rest/report/report")
public class ReportCtrl extends SuperCtrl {

    @Autowired
    private ExportLogService exportLogService;

    /**
     * 下载中心
     *
     * @param type
     * @param name
     * @param pageModel
     * @return
     */
    @RequestMapping(value = "/exportList", method = RequestMethod.POST)
    public Response list(String type, String name, PageModel pageModel) {
        Response result = null;
        if (type.contains("'")) {
            type = type.replace("'", "");
        }
        String[] types = type.split(",");
        pageModel = new PageModel();
        try {
            PageResult<ExportLog> pager = exportLogService.findForPage(this.getLoginedAdmin().getUsername(), types,
                    pageModel);
            result = new SuccessResponse(pager);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = new ErrorResponse(e.getMessage());
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/file/{id}", method = RequestMethod.POST)
    public Response deleteFile(HttpServletRequest request, @PathVariable Long id) {
        Response result = new SuccessResponse();
        if (id == null) {
            result = new ErrorResponse("-1", "没有任何行被选中，无法进行删除操作！");
        }
        try {
            int success = exportLogService.deleteById(id, this.getLoginedAdmin().getUsername());
            if (success < 1) {
                result.setMessage("删除不成功！");
                result.setStatus(-1);
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
