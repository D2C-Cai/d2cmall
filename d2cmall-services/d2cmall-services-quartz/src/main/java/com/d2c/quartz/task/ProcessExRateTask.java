package com.d2c.quartz.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import com.d2c.quartz.task.base.BaseTask;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessExRateTask extends BaseTask {

    @Autowired
    private SettingService settingService;

    /**
     * 请求数据
     *
     * @return
     */
    private static JSONArray processHttp() {
        String response = HttpUtils.get("http://www.chinamoney.com.cn/r/cms/www/chinamoney/data/fx/ccpr.json");
        System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        JSONArray records = obj.getJSONArray("records");
        return records;
    }

    public static void main(String[] args) {
        processHttp();
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        JSONArray records = processHttp();
        this.processHKDExRate(records);
    }

    /**
     * 港币换人民币
     *
     * @param records
     */
    private void processHKDExRate(JSONArray records) {
        JSONObject HKDObj = null;
        if ("HKD".equals(records.getJSONObject(3).getString("foreignCName"))) {
            HKDObj = records.getJSONObject(3);
        } else {
            for (int i = 0; i < records.size(); i++) {
                if ("HKD".equals(records.getJSONObject(i).getString("foreignCName"))) {
                    HKDObj = records.getJSONObject(i);
                    break;
                }
            }
        }
        if (HKDObj != null) {
            Setting setting = settingService.findByCode(Setting.HKDEXRATE);
            String exRate = HKDObj.getString("price");
            settingService.updateValueById(exRate, setting.getId(), Setting.HKDEXRATE);
        }
    }

}
