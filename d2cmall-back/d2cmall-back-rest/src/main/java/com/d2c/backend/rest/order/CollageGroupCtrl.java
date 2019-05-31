package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.query.CollageGroupSearcher;
import com.d2c.order.service.CollageGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 拼团团信息
 *
 * @author baicai
 */
@RestController
@RequestMapping(value = "/rest/order/collagegroup")
public class CollageGroupCtrl extends BaseCtrl<CollageGroupSearcher> {

    @Autowired
    private CollageGroupService collageGroupService;

    @Override
    protected Response doList(CollageGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CollageGroup> pager = collageGroupService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(CollageGroupSearcher searcher) {
        return collageGroupService.countBySearch(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CollageGroupSearcher searcher, PageModel page) {
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
    protected Response doHelp(CollageGroupSearcher searcher, PageModel page) {
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

}
