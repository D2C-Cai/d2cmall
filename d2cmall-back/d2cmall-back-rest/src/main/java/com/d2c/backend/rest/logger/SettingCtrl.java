package com.d2c.backend.rest.logger;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Template;
import com.d2c.logger.service.TemplateService;
import com.d2c.order.model.Setting;
import com.d2c.order.model.Setting.MemberType;
import com.d2c.order.query.SettingSearcher;
import com.d2c.order.service.SettingService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统参数设置
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/rest/sys/setting")
public class SettingCtrl extends BaseCtrl<SettingSearcher> {

    @Autowired
    private SettingService settingService;
    @Autowired
    private TemplateService templateService;

    @Override
    protected List<Map<String, Object>> getRow(SettingSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SettingSearcher searcher) {
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
    protected Response doHelp(SettingSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SettingSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Setting> pager = settingService.findBySearch(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        try {
            Setting setting = settingService.findById(id);
            settingService.delete(id, setting.getCode());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Setting setting = JsonUtil.instance().toObject(data, Setting.class);
        try {
            String joValue = data.getString("value");// 系统参数值
            setting.setValue(joValue.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        BeanUt.trimString(setting);
        setting.setMemberId(0L);
        setting.setMemberType(MemberType.MALL.name());
        Setting dto = new Setting();
        try {
            setting = settingService.insert(setting);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.indexOf("DuplicateKeyException") > 0) {
                result.setMessage("保存不成功！本账户已经设置（分类+参数编号+参数服务对象）相同的参数！");
                result.setStatus(-1);
                return result;
            } else {
                logger.error(e.getMessage());
            }
        }
        BeanUtils.copyProperties(setting, dto);
        Template temple = templateService.findById(setting.getTemplateId());
        if (temple != null) {
            // dto.setSubject(temple.getSubject());
        }
        result.put("setting", dto);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Setting setting = JsonUtil.instance().toObject(data, Setting.class);
        JSONObject joValue = null;
        try {
            joValue = data.getJSONObject("value");// 系统参数值
            setting.setValue(joValue.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            settingService.update(setting);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 更新参数值
     *
     * @param value
     * @param id
     * @return
     */
    @RequestMapping(value = "/update/value", method = RequestMethod.POST)
    public Response updateValue(String value, Long id, String code) {
        SuccessResponse result = new SuccessResponse();
        settingService.updateValueById(value, id, code);
        return result;
    }

    /**
     * 批量更改参数状态
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public Response updateStatus(Long[] ids, Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            try {
                Setting setting = settingService.findById(id);
                settingService.updateStatus(id, status, setting.getCode());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        result.setMessage(status == 1 ? "启用成功!" : "停用成功!");
        return result;
    }

}
