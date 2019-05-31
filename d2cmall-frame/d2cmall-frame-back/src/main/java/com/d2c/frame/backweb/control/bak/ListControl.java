package com.d2c.frame.backweb.control.bak;

import com.d2c.common.api.dto.DtoHandler;
import com.d2c.common.api.dto.ResDTO;
import com.d2c.common.api.query.QueryItem;
import com.d2c.common.api.query.QueryPage;
import com.d2c.common.api.service.BaseService;
import com.d2c.common.api.service.ListService;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.excel.ListExcel;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 基础控制器
 *
 * @author wull
 */
public abstract class ListControl<T> extends EditControl<T> {

    private static final Logger logger = LoggerFactory.getLogger(ListControl.class);
    @Autowired(required = false)
    protected ListExcel<T> listExcel;
    protected ListService<T> listService;

    @PostConstruct
    private void init() {
        listService = getListService();
    }

    protected abstract ListService<T> getListService();

    protected BaseService<T> getService() {
        return getListService();
    }
    // ******************************* @Override *******************************************

    /**
     * 分页数据总数
     */
    public int countPage(List<QueryItem> searchList) {
        return listService.count(searchList);
    }

    /**
     * 分页数据列表
     */
    public List<T> getPageList(List<QueryItem> searchList, Integer page, Integer limit) {
        return listService.findQuery(searchList, page, limit);
    }

    /**
     * 导出数据列表
     */
    public List<T> getExcelList() {
        return listService.findPage(1, 5000);
    }
    // ******************************* Method *******************************************

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = POST)
    public ResDTO getPageRest(@RequestBody(required = false) QueryPage searchParams) {
        logger.debug("查询列表分页 SearchParams: " + searchParams);
        List<QueryItem> searchList = searchParams.getQuerys();
        Integer count = countPage(searchList);
        List<T> list = getPageList(searchList, searchParams.getPage(), searchParams.getLimit());
        return DtoHandler.success(count, list);
    }

    /**
     * 查询列表 GET 方法(走JSON解析效率较低，不建议)
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = GET)
    public ResDTO getPageRest(@RequestParam(required = false) String searchList,
                              @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        List<QueryItem> list = null;
        if (searchList != null) {
            list = jsonToBean(searchList, new TypeReference<List<QueryItem>>() {
            });
        }
        return getPageRest(new QueryPage(list, page, limit));
    }
    // ********************************** Excel ************************************************

    /**
     * 导出Excel
     */
    @ResponseBody
    @RequestMapping(value = "/excel", method = GET)
    public ResDTO makeExcel() {
        AssertUt.notNull(listExcel);
        listExcel.excel(getExcelList());
        return DtoHandler.success();
    }

    /**
     * 常用Json转对象
     */
    public <E> E jsonToBean(String json, TypeReference<E> tr) {
        if (StringUtils.isNotEmpty(json)) {
            return JsonUt.deserialize(json.replaceAll("&quot;", "\""), tr);
        }
        return null;
    }

}
