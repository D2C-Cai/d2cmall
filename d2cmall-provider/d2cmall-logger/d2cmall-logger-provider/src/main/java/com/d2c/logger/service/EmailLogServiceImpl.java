package com.d2c.logger.service;

import com.d2c.logger.dao.EmailLogMapper;
import com.d2c.logger.model.EmailLog;
import com.d2c.logger.model.EmailLog.EmailLogType;
import com.d2c.logger.model.Template;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service("emailLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class EmailLogServiceImpl extends ListServiceImpl<EmailLog> implements EmailLogService {

    @Autowired
    private EmailLogMapper emailLogMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemplateService templateService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void sendEmail(String email, String subject, Long templateId, Map<String, String> repalce, String content,
                          Long sourceId, EmailLogType type) {
        // 邮件模板
        Template template = null;
        if (templateId != null && templateId > 0) {
            template = templateService.findById(templateId);
        }
        if (template != null) {
            subject = template.getSubject();
            content = template.getContent();
            for (Map.Entry<String, String> entry : repalce.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                content = content.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        this.doSendEmail(email, subject, content, sourceId, type, 3);
    }

    private int doSendEmail(String email, String subject, String content, Long sourceId,
                            EmailLog.EmailLogType emailType, int sendMax) {
        emailService.send(email, subject, content);
        EmailLog newEmailLog = new EmailLog();
        newEmailLog.setSubject(subject);
        newEmailLog.setContent(content);
        newEmailLog.setSourceId(sourceId == null ? System.currentTimeMillis() : sourceId);
        newEmailLog.setEmail(email);
        newEmailLog.setType(emailType.toString());
        newEmailLog.setSend(1);
        newEmailLog.setMaxSend(sendMax);
        newEmailLog.setCreateDate(new Date());
        return emailLogMapper.insert(newEmailLog);
    }

    @Override
    public EmailLog findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public EmailLog findBySourceIdAndType(Long sourceId, EmailLogType type) {
        EmailLog log = new EmailLog();
        log.setSourceId(sourceId);
        log.setType(type.toString());
        return emailLogMapper.findBySourceIdAndType(sourceId, type.toString());
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSend(Long id) {
        return emailLogMapper.updateSend(id);
    }

}
