package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.PopMessageMapper;
import com.d2c.logger.model.PopMessage;
import com.d2c.logger.query.PopMessageSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("popMessageService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PopMessageServiceImpl extends ListServiceImpl<PopMessage> implements PopMessageService {

    @Autowired
    private PopMessageMapper popMessageMapper;
    @Autowired
    private MsgPushService msgPushService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PopMessage insert(PopMessage popMessage) {
        return this.save(popMessage);
    }

    @Override
    public PageResult<PopMessage> findBySearcher(PopMessageSearcher searcher, PageModel page) {
        PageResult<PopMessage> pager = new PageResult<PopMessage>();
        Integer totalCount = popMessageMapper.countBySearcher(searcher);
        List<PopMessage> list = new ArrayList<>();
        if (totalCount > 0) {
            list = popMessageMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPush(Long id, String sendMan) {
        PopMessage popMessage = popMessageMapper.selectByPrimaryKey(id);
        JSONObject msgContent = new JSONObject();
        msgContent.put("url", popMessage.getUrl());
        msgContent.put("title", popMessage.getTitle());
        // msgContent.put("subTitle", "活动提醒");
        msgContent.put("pic", popMessage.getPic());
        msgPushService.doPushTransmissionMsgToApp(msgContent, 2, 0, false, "POP", "");
        return popMessageMapper.doPush(id, sendMan);
    }

    @Override
    public PopMessage findById(Long id) {
        return popMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PopMessage popMessage) {
        return this.updateNotNull(popMessage);
    }

}
