package com.d2c.backend.rest.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.query.convert.QueryConvert;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.similar.entity.RecomRuleDO;
import com.d2c.similar.query.RecomRuleQuery;
import com.d2c.similar.service.RecomRuleService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/chest/recom/rule")
public class RecomRuleCtrl extends BaseCtrl<RecomRuleQuery> {

    @Reference
    private RecomRuleService recomRuleService;

    @Override
    protected Response doList(RecomRuleQuery query, PageModel pager) {
        List<RecomRuleDO> list = recomRuleService.findQuery(query, pager);
        return ResultHandler.success(list);
    }

    @Override
    protected int count(RecomRuleQuery query) {
        return recomRuleService.count(QueryConvert.convert(query));
    }

    @Override
    protected Response findById(Long id) {
        return ResultHandler.success(recomRuleService.findOneById(id));
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        RecomRuleDO bean = (RecomRuleDO) JsonUtil.instance().toObject(data, RecomRuleDO.class);
        recomRuleService.updateNotNull(bean);
        return ResultHandler.successData(bean);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        RecomRuleDO bean = (RecomRuleDO) JsonUtil.instance().toObject(data, RecomRuleDO.class);
        recomRuleService.save(bean);
        return ResultHandler.successData(bean);
    }

    @Override
    protected Response doDelete(Long id) {
        recomRuleService.deleteById(id);
        return ResultHandler.success();
    }
    // *************************************************

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(RecomRuleQuery query, PageModel page) {
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
    protected Response doHelp(RecomRuleQuery query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
