package com.d2c.logger.service;

import com.d2c.logger.third.email.EmailClient;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Override
    public boolean send(String to, String subject, String content) {
        return EmailClient.sendOut(to, subject, content);
    }

}
