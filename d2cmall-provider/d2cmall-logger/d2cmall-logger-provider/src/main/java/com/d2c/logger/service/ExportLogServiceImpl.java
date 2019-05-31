package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.ExportLogMapper;
import com.d2c.logger.model.ExportLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("exportLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ExportLogServiceImpl extends ListServiceImpl<ExportLog> implements ExportLogService {

    @Autowired
    private ExportLogMapper exportLogMapper;

    @Override
    public List<ExportLog> findByCreatorAndType(String creator, String log_type) {
        List<ExportLog> list = new ArrayList<ExportLog>();
        list = exportLogMapper.findByCreatorAndType(creator, log_type);
        return list;
    }

    @Override
    public PageResult<ExportLog> findForPage(String creator, String[] log_type, PageModel page) {
        PageResult<ExportLog> pager = new PageResult<ExportLog>(page);
        Integer count = exportLogMapper.countNum(creator, log_type);
        List<ExportLog> list = new ArrayList<ExportLog>();
        if (count > 0) {
            list = exportLogMapper.findForPage(creator, log_type, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

    @Override
    public ExportLog insert(ExportLog entity) {
        return this.save(entity);
    }

    @Override
    public int deleteById(Long id, String creator) {
        return exportLogMapper.deleteById(id, creator);
    }

}
