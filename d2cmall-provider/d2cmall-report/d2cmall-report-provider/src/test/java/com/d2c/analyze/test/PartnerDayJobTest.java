package com.d2c.analyze.test;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.test.SuperTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

public class PartnerDayJobTest extends SuperTest {

    private RestTemplate restTemplate;

    @Test
    public void test() throws IOException {
        restTemplate = new RestTemplate();
//		String ipAddr ="118.31.248.79";
        String ipAddr = "192.168.0.139";
        for (int i = 45; i < 126; i++) {
            Date date = DateUt.dayRollBack(i);
            String str = postSaleDayJob(ipAddr, i);
            logger.info("执行买手" + DateUt.date2str(date) + "日统计任务...." + str);
        }
    }

    private String postSaleDayJob(String ipAddr, Integer day) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        return restTemplate.postForObject("http://" + ipAddr + ":21530/partner/saleDayJob?day=" + day, entity, String.class);
    }

}
