package com.d2c.flame.controller.content;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.model.AdResource;
import com.d2c.content.service.AdResourceService;
import com.d2c.flame.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告资源位
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/ad")
public class AdResourceController extends BaseController {

    @Autowired
    private AdResourceService adResourceService;

    /**
     * 根据频道和类型，获取广告资源
     *
     * @param appChannel
     * @param type
     * @return
     */
    @RequestMapping(value = "/{appChannel}/{type}", method = RequestMethod.GET)
    public ResponseResult getResource(@PathVariable String appChannel, @PathVariable String type) {
        ResponseResult result = new ResponseResult();
        AdResource adResource = adResourceService.findByAppChannelAndType(appChannel, type);
        if (adResource == null) {
            throw new BusinessException("广告不存在或已下架！");
        }
        result.put("adResource", adResource.toJson());
        return result;
    }

}
