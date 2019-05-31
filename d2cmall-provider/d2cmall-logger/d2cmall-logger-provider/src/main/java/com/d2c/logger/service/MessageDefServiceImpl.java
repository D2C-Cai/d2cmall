package com.d2c.logger.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.MessageDefMapper;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageDefSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("messageDefService")
public class MessageDefServiceImpl extends ListServiceImpl<MessageDef> implements MessageDefService {

    @Autowired
    private MessageDefMapper messageDefMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public MessageDef insert(MessageDef def) {
        return this.save(def);
    }

    @Cacheable(value = "message_def", key = "'message_def_'+#id", unless = "#result == null")
    public MessageDef findById(Long id) {
        return this.findOneById(id);
    }

    public PageResult<MessageDef> findBySearch(PageModel page, MessageDefSearcher searcher) {
        PageResult<MessageDef> pager = new PageResult<MessageDef>(page);
        int totalCount = messageDefMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MessageDef> list = messageDefMapper.findBySearch(page, searcher);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @CacheEvict(value = "message_def", key = "'message_def_'+#def.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(MessageDef def) {
        return this.updateNotNull(def);
    }

    @Override
    public List<HelpDTO> findHelpDtosBySearch(PageModel page, MessageDefSearcher searcher) {
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        int totalCount = messageDefMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MessageDef> list = messageDefMapper.findBySearch(page, searcher);
            for (MessageDef def : list) {
                HelpDTO dto = new HelpDTO();
                dto.setId(def.getId());
                dto.setName(def.getTitle());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    @CacheEvict(value = "message_def", key = "'message_def_'+#id")
    public int updateStatusById(Long id, int status) {
        return messageDefMapper.updateStatusById(id, status);
    }

    @Override
    @CacheEvict(value = "message_def", key = "'message_def_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDelayTime(Long id, Long timestamp) {
        return messageDefMapper.updateDelayTime(id, timestamp);
    }

}
