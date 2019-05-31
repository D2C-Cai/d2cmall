package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.SmsLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.query.SmsLogSearcher;

/**
 * 短信发送日志（SmsLog）
 */
public interface SmsLogService {

    /**
     * 发送短信验证码
     *
     * @param nationCode 国家代码
     * @param loginCode  电话号码
     * @param smsContent 短信内容
     * @return
     */
    int doSendSms(String nationCode, String loginCode, String smsContent);

    /**
     * 发送语音验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    int doSendVms(String mobile, String code, String tempId, JSONObject params);

    /**
     * 被动发送验证（获取验证码，忘记密码，绑定手机账户）
     *
     * @param nationCode
     * @param loginCode
     * @param code
     * @param content
     * @param type
     * @param source
     * @param ip
     * @param method
     * @return
     */
    void sendSms(String nationCode, String loginCode, String code, String content, SmsLogType type, String source,
                 String ip, String method);

    /**
     * 生成短信日志，插入数据库
     *
     * @param type    短信类型
     * @param source  业务
     * @param mobile  手机号码
     * @param ip      最后一次IP地址
     * @param code    短信验证码
     * @param content 短信内容
     * @return
     * @throws Exception
     */
    int insertLog(SmsLogType type, String source, String mobile, String ip, String code, String content);

    /**
     * 根据业务及类型获取短信日志
     *
     * @param source 业务
     * @param type   类型
     * @return
     */
    SmsLog findBySourceAndType(String source, SmsLogType type);

    /**
     * 根据最后一次IP地址及类型获取短信日志，根据创建时间进行了排序。
     *
     * @param ip   最后一次IP地址
     * @param type 类型
     * @return
     */
    SmsLog findOneByIp(String ip, SmsLogType type);

    /**
     * 删除1200秒前的日志信息。
     *
     * @return
     */
    int deleteExpireLog();

    /**
     * 短信验证码检查
     *
     * @param type   短信类型
     * @param source 业务
     * @param code   短信验证码
     * @return
     * @throws Exception
     */
    SmsLog validate(SmsLogType type, String source, String code);

    /**
     * 保存短信日志
     *
     * @param entity
     * @return
     */
    SmsLog insert(SmsLog entity);

    /**
     * 根据id获取短信日志
     *
     * @param id
     * @return
     */
    SmsLog findById(Long id);

    /**
     * 根据SmsLogSearcher获取短信日志列表
     *
     * @param searcher
     * @return
     */
    PageResult<SmsLog> findBySearcher(SmsLogSearcher searcher, PageModel page);

}
