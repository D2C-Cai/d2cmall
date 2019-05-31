package com.d2c.frame.backweb.control.bak;

import com.d2c.common.api.dto.DtoHandler;
import com.d2c.common.api.dto.ResDTO;
import com.d2c.common.api.service.BaseService;
import com.d2c.common.base.utils.AssertUt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 基础控制器
 *
 * @author wull
 */
public abstract class EditControl<T> extends SuperBackControl {

    private static final Logger logger = LoggerFactory.getLogger(EditControl.class);
    protected BaseService<T> baseService;

    @PostConstruct
    private void init() {
        baseService = getService();
    }

    protected abstract BaseService<T> getService();
    // ******************************* @Override *******************************************

    /**
     * 查询所有列表
     */
    public List<T> getAllList(Integer page, Integer limit) {
        return baseService.findPage(page, limit);
    }

    /**
     * 根据ID查询
     */
    public T getDataById(Integer id) {
        return baseService.findOneById(id);
    }

    /**
     * 新增数据
     */
    public T saveData(T entity) {
        return baseService.save(entity);
    }

    /**
     * 修改数据
     */
    public void updateData(T entity) {
        baseService.updateNotNull(entity);
    }

    /**
     * 删除数据
     */
    public void deleteData(Integer id) {
        baseService.deleteById(id);
    }
    // ******************************* Method *******************************************

    /**
     * 查询列表
     */
    @ResponseBody
    @RequestMapping(value = "", method = GET)
    public ResDTO getAllRest(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        logger.debug("查询全文列表");
        List<T> list = getAllList(page, limit);
        return DtoHandler.success(list);
    }

    /**
     * 查询某对象
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = GET)
    public ResDTO getRestById(@PathVariable Integer id) {
        logger.debug("查询对象 id:" + id);
        T entity = getDataById(id);
        return DtoHandler.success(entity);
    }

    /**
     * 新增对象
     */
    @ResponseBody
    @RequestMapping(value = "", method = POST)
    public ResDTO saveRest(@RequestBody T entity) {
        logger.debug("新增对象:" + entity);
        AssertUt.notNull(entity, "新增对象不能为空");
        return DtoHandler.success(saveData(entity));
    }

    /**
     * 修改对象
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = POST)
    public ResDTO updateRest(@PathVariable Integer id, @RequestBody T entity) {
        logger.debug("修改对象:" + entity);
        AssertUt.notNull(entity, "修改对象不能为空");
        updateData(entity);
        return DtoHandler.success();
    }

    /**
     * 删除对象
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = GET)
    public ResDTO deleteRest(@PathVariable Integer id) {
        logger.debug("删除对象id:" + id);
        deleteData(id);
        return DtoHandler.success();
    }

}
