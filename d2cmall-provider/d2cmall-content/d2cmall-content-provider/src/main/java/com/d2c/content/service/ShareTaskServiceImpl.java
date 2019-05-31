package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.ShareTaskMapper;
import com.d2c.content.dto.ShareTaskDto;
import com.d2c.content.model.ShareTask;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shareTaskService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareTaskServiceImpl extends ListServiceImpl<ShareTask> implements ShareTaskService {

    @Autowired
    private ShareTaskMapper shareTaskMapper;
    @Autowired
    private ShareTaskDefService shareTaskDefService;

    public PageResult<ShareTaskDto> findBySearch(ShareTaskSearcher searcher, PageModel page) {
        PageResult<ShareTaskDto> pager = new PageResult<ShareTaskDto>(page);
        int totalCount = shareTaskMapper.countBySearch(searcher);
        List<ShareTask> list = new ArrayList<ShareTask>();
        List<ShareTaskDto> dtos = new ArrayList<ShareTaskDto>();
        if (totalCount > 0) {
            list = shareTaskMapper.findBySearch(searcher, page);
            for (ShareTask task : list) {
                ShareTaskDto dto = new ShareTaskDto();
                BeanUtils.copyProperties(task, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    public int countBySearch(ShareTaskSearcher searcher) {
        return shareTaskMapper.countBySearch(searcher);
    }

    public ShareTask findById(Long id) {
        return shareTaskMapper.selectByPrimaryKey(id);
    }

    public ShareTask findByMemberIdAndTaskDef(Long memberInfoId, Long defId) {
        return shareTaskMapper.findByMemberIdAndTaskDef(memberInfoId, defId);
    }

    /**
     * @param taskId
     * @param clicks 点击用一次，执行一次
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doClickSharePoint(Long taskDefId, Long taskId) {
        int result = shareTaskMapper.doClickSharePoint(taskId);
        if (result > 0) {
            result = shareTaskDefService.doClickSharePoint(taskDefId);
            shareTaskDefService.updateCompletedStatus(taskDefId);
        }
        return result;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateClick(Long memberInfoId, Long taskDefId) {
        return shareTaskMapper.updateClick(memberInfoId, taskDefId);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateShare(Long memberInfoId, Long taskDefId) {
        return shareTaskMapper.updateShare(memberInfoId, taskDefId);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doShare(Long taskDefId, Long shareTaskId) {
        ShareTaskDef def = shareTaskDefService.findById(taskDefId);
        String luckyNum = null;
        if (!def.isOver()) {
            luckyNum = SerialNumUtil.getRandomNumber(def.getLuckNumDigit());
        }
        return shareTaskMapper.doShare(shareTaskId, luckyNum);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ShareTask insert(ShareTask shareTask) {
        return this.save(shareTask);
    }

}
