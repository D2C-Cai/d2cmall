package com.d2c.util.date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期插入到数据库中查找
 *
 * @author Lain
 */
public class HolidayUtil {

    private static final String key = "e7b8ee14119177cda33f3703c5db7090";
    private static RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.136:3306/d2cmall_log");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 这里也可以使用构造方法
        jdbcTemplate.setDataSource(dataSource);
        int year = 2019;
        int month = 1;
        for (; month < 13; ) {
            JSONObject json = restTemplate.getForObject(
                    "http://v.juhe.cn/calendar/month?year-month=" + year + "-" + month + "&key=" + key,
                    JSONObject.class);
            if ("Success".equals(json.getString("reason"))) {
                String holidayStr = json.getJSONObject("result").getJSONObject("data").getString("holiday");
                JSONArray array = new JSONArray();
                if ("{".equals(holidayStr.substring(0, 1))) {
                    array.add(JSON.parseObject(holidayStr));
                } else {
                    array = JSON.parseArray(holidayStr);
                }
                for (int i = 0; i < array.size(); i++) {
                    JSONObject holidayInfo = array.getJSONObject(i);
                    JSONArray holidayItems = holidayInfo.getJSONArray("list");
                    for (int j = 0; j < holidayItems.size(); j++) {
                        // jdbcTemplate.insert("INSERT INTO sys_holiday (`date`)
                        // VALUES(\""
                        // + holidayItems.getJSONObject(j).getString("date") +
                        // "\")");
                        jdbcTemplate.execute("REPLACE INTO sys_holiday (`date`) VALUES(\""
                                + holidayItems.getJSONObject(j).getString("date") + "\")");
                    }
                }
            }
            month++;
        }
        List<Date> dates = getWeekDayList();
        for (Date date : dates) {
            jdbcTemplate.execute("REPLACE INTO sys_holiday (`date`) VALUES(\"" + DateUtil.second2str(date) + "\")");
        }
    }
    // 这个月以后5个月的所有周末

    private static List<Date> getWeekDayList() {
        List<Date> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 0);
        for (; calendar.get(Calendar.YEAR) < 2019; ) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                result.add(calendar.getTime());
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

}

class Holiday2 {

    /**
     * 节日
     */
    private String name;
    /**
     * 日期
     */
    private String date;
    /**
     * 1节假日 2是双休
     */
    private Integer status;

    public Holiday2(JSONObject json) {
        this.date = json.getString("date");
        this.status = Integer.parseInt(json.getString("status"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}