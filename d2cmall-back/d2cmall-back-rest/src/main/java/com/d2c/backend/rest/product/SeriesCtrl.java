package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.SeriesDto;
import com.d2c.product.model.Series;
import com.d2c.product.query.SeriesSearcher;
import com.d2c.product.service.SeriesService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/shop/series")
public class SeriesCtrl extends BaseCtrl<SeriesSearcher> {

    @Autowired
    private SeriesService seriesService;

    @Override
    protected List<Map<String, Object>> getRow(SeriesSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SeriesSearcher searcher) {
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        return seriesService.countBySearch(searcher);
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
    protected Response doHelp(SeriesSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SeriesSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto admin = this.getLoginedAdmin();
        this.initSearcherByRole(admin, searcher);
        PageResult<SeriesDto> pager = seriesService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Series serie = seriesService.findById(id);
        result.put("serie", serie);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            Series series = seriesService.findById(id);
            int flag = seriesService.delete(id, series.getDesignerId(), admin.getUsername());
            if (flag == 0) {
                result.setStatus(-1);
            }
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Series series = seriesService.findById(id);
        int flag = seriesService.delete(id, series.getDesignerId(), admin.getUsername());
        if (flag == 0) {
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Series serie = JsonUtil.instance().toObject(data, Series.class);
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        serie.setCreator(admin.getUsername());
        serie = seriesService.insert(serie);
        result.put("serie", serie);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Series serie = JsonUtil.instance().toObject(data, Series.class);
        serie.setLastModifyMan(admin.getUsername());
        seriesService.update(serie);
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 系列名称是否已经使用 0 表示没有使用 1 已经使用
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response check(String name) {
        SuccessResponse result = new SuccessResponse();
        Series serie = seriesService.findByName(name);
        if (serie == null) {
            result.setStatus(0);
        }
        return result;
    }

}
