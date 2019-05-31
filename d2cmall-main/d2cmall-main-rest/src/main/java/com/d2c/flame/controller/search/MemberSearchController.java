package com.d2c.flame.controller.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.MemberSearchSumService;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

/**
 * 关键字搜索查询
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/searchsum")
public class MemberSearchController extends BaseController {

    @Autowired
    private MemberSearchSumService memberSearchSumService;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;

    /**
     * 关键字列表
     *
     * @param subModuleCategoryId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(Long subModuleCategoryId) {
        PageModel page = new PageModel();
        page.setPageSize(13);
        ResponseResult result = new ResponseResult();
        String key = CacheKey.SEARCHHOTKEY + (subModuleCategoryId == null ? "" : subModuleCategoryId);
        List<MemberSearchSum> list = CacheTimerHandler.getAndSetCacheValue(key, 60,
                new CacheCallback<List<MemberSearchSum>>() {
                    @Override
                    public List<MemberSearchSum> doExecute() {
                        MemberSearchSumSearcher searcher = new MemberSearchSumSearcher();
                        searcher.setSubModuleCategoryId(subModuleCategoryId);
                        List<MemberSearchSum> searchHot = memberSearchSumService
                                .findBySearcher(searcher, new PageModel(1, 11)).getList();
                        return searchHot;
                    }
                });
        JSONArray array = new JSONArray();
        list.forEach(item -> array.add(item.toJson()));
        result.put("memberSearchSumList", array);
        return result;
    }

    /**
     * 关键字联想
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/membersearch", method = RequestMethod.GET)
    public ResponseResult memberSearch(String keyword) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isNotBlank(keyword)) {
            keyword = keyword.replace(' ', '+');
            String paramData = "";
            try {
                paramData = new String(BASE64.decode(keyword), "utf-8");
                paramData = paramData.toLowerCase();
            } catch (UnsupportedEncodingException e) {
                throw new BusinessException("参数异常！");
            }
            Set<String> keySet = searchKeySearcherService.search(paramData);
            result.put("list", keySet);
        }
        return result;
    }

    /**
     * 关键字查询统计
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseResult memberSearchCount(Long id) {
        ResponseResult result = new ResponseResult();
        int success = memberSearchSumService.addCountById(id);
        if (success <= 0) {
            throw new BusinessException("关键字查询统计不成功！");
        }
        return result;
    }

}
