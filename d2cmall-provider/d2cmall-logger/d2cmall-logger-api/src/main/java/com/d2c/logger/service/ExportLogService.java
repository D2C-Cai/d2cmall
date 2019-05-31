package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ExportLog;

import java.util.List;

public interface ExportLogService {

    /**
     * 根据创建者及类型查找导出的excel列表
     *
     * @param creator
     * @param log_type
     * @return
     */
    List<ExportLog> findByCreatorAndType(String creator, String log_type);

    /**
     * 根据创建者及类型查找导出的excel列表,以分页形式返回。
     *
     * @param creator
     * @param log_type
     * @param page
     * @return
     */
    PageResult<ExportLog> findForPage(String creator, String[] log_type, PageModel page);

    /**
     * 保存导出日志
     *
     * @param entity
     * @return
     */
    ExportLog insert(ExportLog entity);

    /**
     * 删除记录
     *
     * @param id
     * @param creator
     * @return
     */
    int deleteById(Long id, String creator);

}
