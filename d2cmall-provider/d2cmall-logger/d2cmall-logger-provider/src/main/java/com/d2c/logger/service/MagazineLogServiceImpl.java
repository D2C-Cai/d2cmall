package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.MagazineLogMapper;
import com.d2c.logger.model.MagazineLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("magazineLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MagazineLogServiceImpl extends ListServiceImpl<MagazineLog> implements MagazineLogService {

    @Autowired
    private MagazineLogMapper magazineLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int insert(MagazineLog log) {
        return magazineLogMapper.doInsert(log);
    }

    @Override
    public PageResult<MagazineLog> findByMemberId(Long memberId, PageModel page) {
        PageResult<MagazineLog> pager = new PageResult<MagazineLog>(page);
        Integer count = magazineLogMapper.countByMemberId(memberId);
        List<MagazineLog> list = new ArrayList<MagazineLog>();
        if (count > 0) {
            list = magazineLogMapper.findByMemberId(memberId, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

}
