package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.RequisitionLogMapper;
import com.d2c.logger.model.RequisitionLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("requisitionLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RequisitionLogServiceImpl extends ListServiceImpl<RequisitionLog> implements RequisitionLogService {

    @Autowired
    private RequisitionLogMapper requisitionLogMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RequisitionLog insert(RequisitionLog entity) {
        return this.save(entity);
    }

    @Override
    public RequisitionLog findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public PageResult<RequisitionLog> findByRequisitionId(Long requisitionId, PageModel page) {
        PageResult<RequisitionLog> pager = new PageResult<RequisitionLog>(page);
        Integer count = requisitionLogMapper.countByRequisitionId(requisitionId);
        List<RequisitionLog> list = new ArrayList<RequisitionLog>();
        if (count > 0) {
            list = requisitionLogMapper.findByRequisitionId(requisitionId, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

    @Override
    public PageResult<RequisitionLog> findByRequisitionItemId(Long requisitionItemId, Long requisitionId,
                                                              PageModel page) {
        PageResult<RequisitionLog> pager = new PageResult<RequisitionLog>(page);
        Integer count = requisitionLogMapper.countByRequisitionItemId(requisitionItemId, requisitionId);
        List<RequisitionLog> list = new ArrayList<RequisitionLog>();
        if (count > 0) {
            list = requisitionLogMapper.findByRequisitionItemId(requisitionItemId, requisitionId, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateSnByItemIds(List<Long> ids, String requisitionSn) {
        return requisitionLogMapper.updateSnByItemIds(ids, requisitionSn);
    }

}
