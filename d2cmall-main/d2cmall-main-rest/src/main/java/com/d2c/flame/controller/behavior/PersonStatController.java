package com.d2c.flame.controller.behavior;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.mongo.dto.PersonSessionDTO;
import com.d2c.behavior.services.PersonStatService;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.flame.controller.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户统计数据
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/behavior/personstat")
public class PersonStatController extends BaseController {

    @Reference
    private PersonStatService personStatService;

    /**
     * 会员用户登录统计
     * <p>
     * API: /v3/api/behavior/personstat/persons
     */
    @ResponseBody
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Response findPersonSessionList() {
        List<PersonSessionDTO> bean = personStatService.findPersonSessionList();
        return ResultHandler.success(bean);
    }

    /**
     * 访客用户登录统计
     * <p>
     * API: /v3/api/behavior/personstat/vistors
     */
    @ResponseBody
    @RequestMapping(value = "/vistors", method = RequestMethod.GET)
    public Response findVistorSessionList() {
        List<PersonSessionDTO> bean = personStatService.findVistorSessionList();
        return ResultHandler.success(bean);
    }

}
