package com.d2c.logger.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.dao.MessageMapper;
import com.d2c.logger.model.Message;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageSearcher;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.service.MessageSearcherService;
import com.d2c.logger.support.PushBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("messageService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MessageServiceImpl extends ListServiceImpl<Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Reference
    private MessageSearcherService messageSearcherService;
    @Autowired
    private MessageDefService messageDefService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Message insert(Message message) {
        message = this.save(message);
        if (message != null && message.getId() != null) {
            SearcherMessage sm = new SearcherMessage();
            BeanUtils.copyProperties(message, sm);
            messageSearcherService.insert(sm);
        }
        return message;
    }

    @Override
    public int doBatchInsert(Message message, Long[] memberIds) {
        List<Long> list = messageMapper.findIdsByRecId(memberIds, message.getDefId());
        for (Long memberId : memberIds) {
            if (!list.contains(memberId)) {
                message.setRecId(memberId);
                messageMapper.doReplaceInto(message);
                if (message != null && message.getId() != null) {
                    SearcherMessage sm = new SearcherMessage();
                    BeanUtils.copyProperties(message, sm);
                    messageSearcherService.insert(sm);
                    String content = message.getContent();
                    PushBean pushBean = new PushBean(memberId, content, message.getType());
                    pushBean.setAppUrl(message.getUrl());
                    msgUniteService.sendPush(pushBean, null);
                }
            }
        }
        return 1;
    }

    public Message findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<Long> findByDate(Date modifyDate, PageModel page) {
        PageResult<Long> pager = new PageResult<Long>(page);
        int totalCount = messageMapper.countByDate(modifyDate);
        if (totalCount > 0) {
            List<Long> list = messageMapper.findByDate(modifyDate, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    public PageResult<Message> findByRecId(Long recId, Integer status, PageModel page) {
        PageResult<Message> pager = new PageResult<Message>(page);
        int totalCount = messageMapper.countByRecId(recId, status);
        if (totalCount > 0) {
            List<Message> list = messageMapper.findByRecId(recId, status, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public int countByRecId(Long recId, Integer status) {
        return messageMapper.countByRecId(recId, status);
    }

    public PageResult<Message> findBySearch(PageModel page, MessageSearcher searcher) {
        PageResult<Message> pager = new PageResult<Message>(page);
        int totalCount = messageMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Message> list = messageMapper.findBySearch(page, searcher);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int result = messageMapper.delete(id);
        if (result > 0) {
            messageSearcherService.remove(id);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteByIdAndMemberId(Long id, Long memberInfoId) {
        int result = messageMapper.deleteByIdAndMemberId(id, memberInfoId);
        if (result > 0) {
            messageSearcherService.remove(id);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Long memberInfoId, Integer status) {
        int result = messageMapper.updateStatus(id, status);
        if (result > 0) {
            messageSearcherService.updateStatus(id, status);
        }
        return result;
    }

    @Override
    public int sendMemberMsg(MessageDef def, Long[] memberIds, String creator, List<String> openIds) {
        long interval = 1;
        if (def.getSendDate() != null && def.getTiming() == 1) {
            interval = (def.getSendDate().getTime() - new Date().getTime()) / 1000 + 1;
        }
        Long timestamp = System.currentTimeMillis();
        messageDefService.updateDelayTime(def.getId(), timestamp);
        this.timingPushMQ(def, memberIds, creator, interval, timestamp, openIds);
        return 1;
    }

    @Override
    public int sendGlobalMsg(MessageDef def, String creator, List<String> openIds) {
        long interval = 1;
        if (def.getSendDate() != null && def.getTiming() == 1) {
            interval = (def.getSendDate().getTime() - new Date().getTime()) / 1000 + 1;
        }
        Long timestamp = System.currentTimeMillis();
        messageDefService.updateDelayTime(def.getId(), timestamp);
        this.timingPushMQ(def, new Long[]{}, creator, interval, timestamp, openIds);
        return 1;
    }

    private void timingPushMQ(MessageDef def, Long[] memberIds, String creator, long interval, Long timestamp,
                              List<String> openIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("defId", def.getId());
        map.put("idStr", StringUtil.longArrayToStr(memberIds));
        map.put("creator", creator);
        map.put("timestamp", timestamp);
        if (openIds != null && openIds.size() > 0) {
            map.put("openIds", StringUtil.toString(openIds, ","));
        }
        MqEnum.TIMING_PUSH.send(map, interval);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doReadMajor(Long memberId, Integer majorType) {
        int success = messageMapper.doReadMajor(memberId, majorType);
        if (success > 0) {
            messageSearcherService.doReadByMajor(memberId, majorType);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doDeleteExpire(Date date) {
        int success = messageMapper.doDeleteExpire(date);
        if (success > 0) {
            messageSearcherService.doDeleteExpire(date);
        }
        return success;
    }

}
