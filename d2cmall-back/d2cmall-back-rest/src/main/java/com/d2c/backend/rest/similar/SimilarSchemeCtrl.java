package com.d2c.backend.rest.similar;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.similar.entity.DimenValueDO;
import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.query.SimilarSchemeQuery;
import com.d2c.similar.service.DimenValueService;
import com.d2c.similar.service.SimilarRuleService;
import com.d2c.similar.service.SimilarSchemeService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/chest/similar/scheme")
public class SimilarSchemeCtrl extends BaseCtrl<SimilarSchemeQuery> {

    @Reference
    private SimilarSchemeService similarSchemeService;
    @Reference
    private SimilarRuleService similarRuleService;
    @Reference
    private DimenValueService dimenValueService;

    /**
     * 获取方案内容明细
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Response detail(@PathVariable Integer id) {
        try {
            return ResultHandler.success(similarSchemeService.getSchemeDetail(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    /**
     * 查看相似度规则
     */
    @RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)
    public Response findRule(@PathVariable Integer ruleId) {
        try {
            return ResultHandler.success(similarRuleService.getSimilarRuleOnEdit(ruleId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    /**
     * 修改相似度规则
     */
    @RequestMapping(value = "/rule/update/{id}", method = {RequestMethod.POST})
    public Response update(@PathVariable Long id) {
        SimilarRuleDO bean = getBean(SimilarRuleDO.class);
        similarRuleService.updateNotNull(bean);
        return ResultHandler.successData(bean, "修改成功!");
    }
    // ******************* Dimen *********************

    /**
     * 获得多维度配置数据
     */
    @RequestMapping(value = "/rule/dimen/{keyId}", method = RequestMethod.GET)
    public Response findDimenValue(@PathVariable Integer keyId) {
        try {
            return ResultHandler.success(dimenValueService.findDimenValueByKeyId(keyId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultHandler.error(e.getMessage());
        }
    }

    /**
     * 修改维度数据
     */
    @RequestMapping(value = "/rule/dimen/update/{keyId}", method = {RequestMethod.POST})
    public Response updateDimen(@PathVariable Integer keyId, @RequestBody List<DimenValueDO> list) {
        dimenValueService.updateAll(list);
        return ResultHandler.success(list, "修改成功!");
    }
    // ****************************************

    @Override
    protected Response doList(SimilarSchemeQuery query, PageModel pager) {
        List<SimilarSchemeDO> list = similarSchemeService.findQuery(query, pager);
        return ResultHandler.success(list);
    }

    @Override
    protected int count(SimilarSchemeQuery query) {
        return similarSchemeService.count(query);
    }

    @Override
    protected Response findById(Long id) {
        return ResultHandler.success(similarSchemeService.findOneById(id));
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SimilarSchemeDO bean = (SimilarSchemeDO) JsonUtil.instance().toObject(data, SimilarSchemeDO.class);
        similarSchemeService.updateNotNull(bean);
        return ResultHandler.successData(bean);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SimilarSchemeDO bean = (SimilarSchemeDO) JsonUtil.instance().toObject(data, SimilarSchemeDO.class);
        similarSchemeService.createScheme(bean);
        return ResultHandler.successData(bean);
    }

    @Override
    protected Response doDelete(Long id) {
        similarSchemeService.deleteById(id);
        return ResultHandler.success();
    }
    // *************************************************

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(SimilarSchemeQuery query, PageModel page) {
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
    protected Response doHelp(SimilarSchemeQuery query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
