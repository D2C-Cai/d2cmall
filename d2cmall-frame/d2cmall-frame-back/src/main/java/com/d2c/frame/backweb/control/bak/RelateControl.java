package com.d2c.frame.backweb.control.bak;

import com.d2c.common.api.dto.DtoHandler;
import com.d2c.common.api.dto.RelateDTO;
import com.d2c.common.api.dto.ResDTO;
import com.d2c.common.api.service.RelateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 多对多关联控制器
 *
 * @author wull
 */
public abstract class RelateControl<T, E, F> extends EditControl<T> {

    private static final Logger logger = LoggerFactory.getLogger(RelateControl.class);
    private static final String RELATE_URL = "relate";
    protected RelateService<T, E, F> relateService;

    @PostConstruct
    private void init() {
        relateService = getService();
    }

    protected abstract RelateService<T, E, F> getService();
    // ******************************* @Override ***********************
    // ******************************* Method ******************************

    /**
     * 查询关联对象列表
     */
    @ResponseBody
    @RequestMapping(value = RELATE_URL + "/{id}", method = RequestMethod.GET)
    public ResDTO getSlaveList(@PathVariable Integer id) {
        logger.debug("查询关联对象列表，根据主关联 id:" + id);
        List<F> list = relateService.getSlaveListByMasterId(id);
        return DtoHandler.success(list);
    }

    /**
     * 新增对象
     */
    @ResponseBody
    @RequestMapping(value = RELATE_URL, method = POST)
    public ResDTO saveRelate(@RequestBody RelateDTO<T> relate) {
        logger.debug("批量提交对象保存:");
        relateService.saveRelate(relate);
        return DtoHandler.success();
    }

}
