package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.O2oSubscribeItemMapper;
import com.d2c.order.model.O2oSubscribeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("o2oSubscribeItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class O2oSubscribeItemServiceImpl extends ListServiceImpl<O2oSubscribeItem> implements O2oSubscribeItemService {

    @Autowired
    private O2oSubscribeItemMapper o2oSubscribeItemMapper;
    @Autowired
    private O2oSubscribeService o2oSubscribeService;

    public O2oSubscribeItem findById(Long id) {
        return o2oSubscribeItemMapper.selectByPrimaryKey(id);
    }

    public List<O2oSubscribeItem> findBySubscribeId(Long subscribeId) {
        return o2oSubscribeItemMapper.findBySubscribeId(subscribeId);
    }

    public int countBySubscribeId(Long subscribeId) {
        return o2oSubscribeItemMapper.countBySubscribeId(subscribeId);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public O2oSubscribeItem insert(O2oSubscribeItem item) {
        item = this.save(item);
        return item;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByIdAndMemberId(Long itemId, Long memberId) {
        O2oSubscribeItem item = this.findById(itemId);
        if (item != null) {
            int itemCount = countBySubscribeId(item.getSubscribeId());
            if (itemCount == 1) {
                return o2oSubscribeService.deleteBy(item.getSubscribeId(), memberId);
            } else {
                return o2oSubscribeItemMapper.deleteByIdAndMemberId(itemId, memberId);
            }
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteBySubscribeIdAndMemberId(Long subscribeId, Long memberInfoId) {
        return o2oSubscribeItemMapper.deleteBySubscribeIdAndMemberId(subscribeId, memberInfoId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByProductIds(List<Long> ids) {
        return o2oSubscribeItemMapper.deleteByProductIds(ids);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return o2oSubscribeItemMapper.doMerge(memberSourceId, memberTargetId);
    }

}
