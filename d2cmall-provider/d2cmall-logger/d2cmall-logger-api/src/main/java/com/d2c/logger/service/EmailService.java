package com.d2c.logger.service;

public interface EmailService {

    boolean send(String to, String subject, String content);

}
