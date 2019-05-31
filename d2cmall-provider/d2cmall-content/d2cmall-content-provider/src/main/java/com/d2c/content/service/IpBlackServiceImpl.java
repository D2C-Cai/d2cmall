package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.IpBlackMapper;
import com.d2c.content.model.IpBlack;
import com.d2c.content.query.IpBlackSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ipBlackService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class IpBlackServiceImpl extends ListServiceImpl<IpBlack> implements IpBlackService {

    @Autowired
    private IpBlackMapper ipBlackMapper;

    @Override
    public PageResult<IpBlack> findBySearch(IpBlackSearcher searcher, PageModel page) {
        PageResult<IpBlack> pager = new PageResult<IpBlack>(page);
        int count = ipBlackMapper.countBySearcher(searcher);
        if (count > 0) {
            pager.setTotalCount(count);
            pager.setList(ipBlackMapper.findBysearcher(searcher, page));
        }
        return pager;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public IpBlack insert(IpBlack ipBlack) {
        return this.save(ipBlack);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(IpBlack ipBlack) {
        return this.updateNotNull(ipBlack);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        return ipBlackMapper.deleteById(id);
    }

    @Override
    public IpBlack findById(Long id) {
        return this.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String lastModifyMan) {
        return ipBlackMapper.updateStatus(id, status, lastModifyMan);
    }

}
