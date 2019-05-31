package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.MemberSearchSumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("")
public class MemberSearchController extends BaseController {

    @Reference
    private SearchKeySearcherService searchKeySearcherService;
    @Autowired
    private MemberSearchSumService memberSearchSumService;

    /**
     * 关键字联想查询
     *
     * @param model
     * @param keyword
     * @return
     */
    @RequestMapping(value = {"/membersearch", "/memberSearch"}, method = RequestMethod.GET)
    public String memberSearch(ModelMap model, String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            keyword = keyword.toLowerCase();
            Set<String> keySet = searchKeySearcherService.search(keyword);
            model.put("list", keySet);
        }
        return "";
    }

    /**
     * 关键字查询统计
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/membersearchsum/count", method = RequestMethod.GET)
    public SuccessResponse memberSearchCount(Long id) {
        SuccessResponse result = new SuccessResponse();
        int success = memberSearchSumService.addCountById(id);
        if (success <= 0) {
            throw new BusinessException("关键字查询统计不成功！");
        }
        return result;
    }

}
