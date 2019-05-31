package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.dao.SmsLogMapper;
import com.d2c.logger.model.SmsLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.query.SmsLogSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("smsLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SmsLogServiceImpl extends ListServiceImpl<SmsLog> implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;
    @Autowired
    private SmsService smsService;

    @Override
    public int doSendSms(String nationCode, String loginCode, String smsContent) {
        if (StringUtil.isBlank(nationCode)) {
            nationCode = "86";
        }
        int index = nationCode.indexOf("+");
        if (index >= 0) {
            nationCode = nationCode.substring(index);
        }
        if (nationCode.equals("86")) {
            return smsService.sendSMS(loginCode, smsContent);
        } else {
            return smsService.sendGlobalSMS("00" + nationCode + loginCode, smsContent);
        }
    }

    @Override
    public int doSendVms(String mobile, String code, String tempId, JSONObject params) {
        return smsService.doSendVms(mobile, code, tempId, params);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void sendSms(String nationCode, String loginCode, String code, String content, SmsLogType type,
                        String source, String ip, String method) {
        if (StringUtil.hasBlack(new Object[]{loginCode, type})) {
            throw new BusinessException("参数异常");
        }
        // 语音验证码
        if ("audio".equalsIgnoreCase(method)) {
            this.doSendVms(loginCode, code, null, null);
        } else {
            this.doSendSms(nationCode, loginCode, content);
        }
        SmsLog smsLog = new SmsLog();
        smsLog.setMobile(loginCode);
        smsLog.setCode(code);
        smsLog.setContent(content);
        smsLog.setType(type.name());
        smsLog.setSource(source);
        smsLog.setIp(ip);
        smsLog.setMaxSend(1);
        smsLog.setCreateDate(new Date());
        this.save(smsLog);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertLog(SmsLogType type, String source, String mobile, String ip, String code, String content) {
        SmsLog smsLog = new SmsLog();
        smsLog.setType(type.name());
        smsLog.setMaxSend(1);
        smsLog.setSource(source);
        smsLog.setMobile(mobile);
        smsLog.setContent(content);
        smsLog.setCode(code);
        smsLog.setIp(ip);
        smsLog.setCreateDate(new Date());
        int n = smsLogMapper.insert(smsLog);
        return n;
    }

    @Override
    public SmsLog findBySourceAndType(String source, SmsLogType type) {
        return smsLogMapper.findBySourceAndType(source, type);
    }

    @Override
    public SmsLog findOneByIp(String ip, SmsLogType type) {
        return smsLogMapper.findOneByIp(ip, type.toString());
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteExpireLog() {
        return smsLogMapper.deleteExpireLog();
    }

    @Override
    public SmsLog validate(SmsLogType type, String source, String code) {
        SmsLog smsLog = smsLogMapper.findBySourceAndType(source, type);
        if (smsLog == null || StringUtils.isBlank(smsLog.getCode()) || !smsLog.getCode().equals(code)) {
            throw new BusinessException("短信校验码错误");
        }
        return smsLog;
    }

    @Override
    public SmsLog insert(SmsLog entity) {
        return this.save(entity);
    }

    @Override
    public SmsLog findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public PageResult<SmsLog> findBySearcher(SmsLogSearcher searcher, PageModel page) {
        PageResult<SmsLog> pager = new PageResult<>(page);
        Integer totalCount = smsLogMapper.countBySearcher(searcher);
        List<SmsLog> list = new ArrayList<>();
        if (totalCount > 0) {
            list = smsLogMapper.findBySearcher(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

}
