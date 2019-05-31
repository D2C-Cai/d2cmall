package com.d2c.logger.third.email.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class EmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
    private static final String SMTP = "smtp.exmail.qq.com";// smtp服务器
    private static final String FROM = "newslettera@d2cmall.com";// 邮件显示名称
    private static final String COPY_TO = "";// 抄送人邮件地址
    private static final String USERNAME = "newslettera@d2cmall.com";// 发件人真实的账户名
    private static final String PASSWORD = "d2c168";// 发件人密码
    private static final String NICKNAME = "D2C-全球好设计";
    private static final String[] EMAIL_ARRAY = new String[]{"newslettera@d2cmall.com", "newsletterb@d2cmall.com",
            "newsletterc@d2cmall.com", "newsletterd@d2cmall.com", "newslettere@d2cmall.com", "newsletterf@d2cmall.com",
            "newsletterg@d2cmall.com", "newsletterh@d2cmall.com", "newsletteri@d2cmall.com",
            "newsletterj@d2cmall.com"};
    private static EmailUtils instance = null;
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private Multipart multipart;

    private EmailUtils() {
    }

    public static EmailUtils getInstance() {
        if (instance == null) {
            instance = new EmailUtils();
        }
        return instance;
    }

    public void setSmtpHost(String hostName) {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", hostName);
    }

    public boolean createMimeMessage() {
        try {
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            logger.error("获取邮件会话错误！" + e);
            return false;
        }
        try {
            mimeMsg = new MimeMessage(session);
            multipart = new MimeMultipart();
            return true;
        } catch (Exception e) {
            logger.error("创建MIME邮件对象不成功！" + e);
            return false;
        }
    }

    /* 定义SMTP是否需要验证 */
    public void setNeedAuth(boolean need) {
        if (props == null)
            props = System.getProperties();
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    /* 定义邮件主题 */
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            logger.error("定义邮件主题发生错误！");
            return false;
        }
    }

    /* 定义邮件正文 */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=utf-8");
            multipart.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            logger.error("定义邮件正文时发生错误！" + e);
            return false;
        }
    }

    /* 设置发信人 */
    public boolean setFrom(String from) {
        try {
            mimeMsg.setFrom(new InternetAddress(MimeUtility.encodeText(NICKNAME) + "<" + from + ">")); // 发信人
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 定义收信人 */
    public boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 定义抄送人 */
    public boolean setCopyTo(String copyto) {
        if (copyto == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 发送邮件模块 */
    public boolean sendOut(String userName) {
        if (StringUtils.isBlank(userName)) {
            return false;
        }
        try {
            mimeMsg.setContent(multipart);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), userName, PASSWORD);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            transport.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /* 调用sendOut方法完成发送 */
    private boolean sendAndCc(String userName, String smtp, String from, String to, String copyto, String subject,
                              String content) {
        setSmtpHost(smtp);
        createMimeMessage();
        setNeedAuth(true); // 验证
        if (!setSubject(subject))
            return false;
        if (!setBody(content))
            return false;
        if (!setTo(to))
            return false;
        if (!setCopyTo(copyto))
            return false;
        if (!setFrom(from))
            return false;
        if (!sendOut(userName))
            return false;
        return true;
    }

    public boolean sendAndCc(String to, String subject, String content) {
        return sendAndCc(USERNAME, SMTP, FROM, to, COPY_TO, subject, content);
    }

    /**
     * 随机使用邮箱
     */
    public boolean sendAndCcRandom(String to, String subject, String content) {
        Random random = new Random();
        int index = random.nextInt(EMAIL_ARRAY.length);
        return sendAndCc(EMAIL_ARRAY[index], SMTP, EMAIL_ARRAY[index], to, COPY_TO, subject, content);
    }

}
