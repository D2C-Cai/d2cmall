package com.d2c.flame.controller;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.model.AdResource;
import com.d2c.content.service.AdResourceService;
import com.d2c.flame.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/adresource")
public class AdResourceController extends BaseController {

    @Autowired
    private AdResourceService adResourceService;

    /**
     * 获取广告资源
     *
     * @param index APP的频道
     * @param type  广告位 晒单有礼SHARE_1，商品报告PRODUCTREPORT_1，品牌分类BRANDCATEGORY_1
     * @return
     */
    @RequestMapping(value = "/{appChannel}/{type}", method = RequestMethod.GET)
    public String getType(@PathVariable String appChannel, @PathVariable String type, ModelMap model) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        AdResource adResource = adResourceService.findByAppChannelAndType(appChannel, type);
        if (adResource == null) {
            throw new BusinessException("广告不存在或已下架！");
        }
        model.put("adResource", adResource);
        return "";
    }

}
