package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.RemindMapper;
import com.d2c.logger.model.EmailLog.EmailLogType;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.model.Template;
import com.d2c.logger.query.RemindSearcher;
import com.d2c.logger.support.EmailBean;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.mybatis.service.ListServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("remindService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RemindServiceImpl extends ListServiceImpl<Remind> implements RemindService {

    @Autowired
    private RemindMapper remindMapper;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private TemplateService templateService;

    public Remind findById(Long id) {
        Remind remind = remindMapper.selectByPrimaryKey(id);
        return remind;
    }

    public Remind findByMemberId(Long memberId) {
        Remind remind = remindMapper.findByMemberId(memberId);
        return remind;
    }

    public PageResult<Remind> findBySearcher(RemindSearcher searcher, PageModel page) {
        PageResult<Remind> pager = new PageResult<Remind>(page);
        int totalCount = remindMapper.countBySearcher(searcher);
        List<Remind> remindList = new ArrayList<Remind>();
        if (totalCount > 0) {
            remindList = remindMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(remindList);
        return pager;
    }

    @Override
    public int countBySearcher(RemindSearcher searcher) {
        return remindMapper.countBySearcher(searcher);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public Remind insert(Remind remind) {
        RemindSearcher searcher = new RemindSearcher();
        searcher.remindForSearcher(remind);
        int count = remindMapper.countBySearcher(searcher);
        if (count > 0) {
            return null;
        } else {
            remind = save(remind);
            return remind;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Remind remind) {
        return this.updateNotNull(remind);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int delete(Long id) {
        return deleteById(id);
    }

    @Override
    public int doSendMsg(Long[] remindIds) {
        Remind remind = null;
        int result = 0;
        for (Long remindId : remindIds) {
            remind = this.findById(remindId);
            if (remind == null) {
                continue;
            }
            if (remind.getSmsSend() != null && remind.getSmsSend() == true) {
                continue;
            }
            if (StringUtils.isBlank(remind.getMobile())) {
                continue;
            }
            String phone = remind.getMobile();
            String subject = remind.getRemindTypeName();
            String content = remind.getContent();
            PushBean pushBean = new PushBean(remind.getMemberId(), content, 29);
            MsgBean msgBean = new MsgBean(remind.getMemberId(), 29, subject, content);
            SmsBean smsBean = new SmsBean(null, phone, SmsLogType.REMIND, content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
            remind.setSmsSend(true);
            remind.setSmsSendDate(new Date());
            result = this.update(remind);
        }
        return result;
    }

    @Override
    public int doSendEmail(Long[] remindIds) {
        Remind remind = null;
        int result = 0;
        Template template = templateService.findById(9L);// 会员产品提醒模板
        if (template == null) {
            return 0;
        }
        for (Long remindId : remindIds) {
            remind = this.findById(remindId);
            if (remind == null) {
                continue;
            }
            if (remind.getType() == null || RemindType.CUSTOM.toString().equals(remind.getType())) {
                continue;
            }
            if (remind.isSended() == true) {
                continue;
            }
            if (StringUtils.isBlank(remind.getMail())) {
                continue;
            }
            String content = template.getContent();
            content = content.replace("{name}", "尊敬的D2C会员").replace("{content}", remind.getContent());
            Map<String, String> mailC = new HashMap<String, String>();
            mailC.put("name", "尊敬的D2C会员");
            mailC.put("content", remind.getContent());
            String subject = remind.getRemindTypeName();
            EmailBean emailBean = new EmailBean(remind.getMail(), 9L, EmailLogType.REMIND, subject, remind.getContent(),
                    mailC);
            msgUniteService.sendMsg(null, null, null, emailBean);
            remind.setSended(true);
            remind.setSendDate(new Date());
            result = this.update(remind);
        }
        return result;
    }

    @Override
    public List<Long> findSourceIdByType(String type, Integer smsSend) {
        return remindMapper.findSourceIdByType(type, smsSend);
    }

}
