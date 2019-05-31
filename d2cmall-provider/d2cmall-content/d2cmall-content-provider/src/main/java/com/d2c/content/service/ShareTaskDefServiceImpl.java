package com.d2c.content.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.ShareTaskDefMapper;
import com.d2c.content.dto.ShareTaskDefDto;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskDefSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shareTaskDefService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareTaskDefServiceImpl extends ListServiceImpl<ShareTaskDef> implements ShareTaskDefService {

    @Autowired
    private ShareTaskDefMapper shareTaskDefMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return deleteById(id);
    }

    public ShareTaskDef findById(Long id) {
        return shareTaskDefMapper.selectByPrimaryKey(id);
    }

    public PageResult<ShareTaskDefDto> findBySearch(ShareTaskDefSearcher searcher, PageModel page) {
        PageResult<ShareTaskDefDto> pager = new PageResult<ShareTaskDefDto>(page);
        int totalCount = shareTaskDefMapper.countBySearch(searcher);
        List<ShareTaskDef> list = new ArrayList<ShareTaskDef>();
        List<ShareTaskDefDto> dtos = new ArrayList<ShareTaskDefDto>();
        if (totalCount > 0) {
            list = shareTaskDefMapper.findBySearch(searcher, page);
            for (ShareTaskDef def : list) {
                ShareTaskDefDto dto = new ShareTaskDefDto();
                BeanUtils.copyProperties(def, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doClickSharePoint(Long shareTaskDefId) {
        return shareTaskDefMapper.doClickSharePoint(shareTaskDefId);
    }

    /**
     * 新增
     *
     * @param def
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareTaskDef insert(ShareTaskDef def) {
        return save(def);
    }

    /**
     * 修改
     *
     * @param def
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ShareTaskDef def) {
        return this.updateNotNull(def);
    }

    /**
     * 发布
     *
     * @param defId
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPublish(Long id) {
        return shareTaskDefMapper.publish(id);
    }

    /**
     * 获取定义
     *
     * @param status
     * @return
     */
    public List<ShareTaskDefDto> findByStatus(int status) {
        List<ShareTaskDef> list = shareTaskDefMapper.findByStatus(status);
        List<ShareTaskDefDto> dtos = new ArrayList<ShareTaskDefDto>();
        if (list != null && !list.isEmpty()) {
            for (ShareTaskDef def : list) {
                ShareTaskDefDto dto = new ShareTaskDefDto();
                BeanUtils.copyProperties(def, dto);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    /**
     * 状态更新为已核算
     *
     * @param taskDefId
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCalculationStatus(Long id) {
        return shareTaskDefMapper.updateStatus(id, 8);
    }

    /**
     * 总积分完成或任务到期，更新状态为已完成
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCompletedStatus(Long id) {
        return shareTaskDefMapper.updateCompletedStatus(id);
    }

    @Override
    public List<HelpDTO> findHelpDtosBySearch(ShareTaskDefSearcher searcher, PageModel page) {
        int totalCount = shareTaskDefMapper.countBySearch(searcher);
        List<ShareTaskDef> list = new ArrayList<ShareTaskDef>();
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        if (totalCount > 0) {
            list = shareTaskDefMapper.findBySearch(searcher, page);
            for (ShareTaskDef def : list) {
                HelpDTO dto = new HelpDTO();
                dto.setId(def.getId());
                dto.setName(def.getTitle());
                dto.setPic(def.getSmallPic());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doClose(Long id) {
        return shareTaskDefMapper.doClose(id);
    }

}
