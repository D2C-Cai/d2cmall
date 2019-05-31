package com.d2c.flame.freemarker;

import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RenderStaticTimeStamp implements TemplateMethodModelEx {

    protected static final Log logger = LogFactory.getLog(RenderStaticTimeStamp.class);
    @Autowired
    private SettingService settingService;

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
        String key = CacheKey.TIMESTAMPKEY;
        return CacheTimerHandler.getAndSetCacheValue(key, 10, new CacheCallback<String>() {
            String defaultValue = "20180101";

            public String doExecute() {
                Setting setting = settingService.findByCode(Setting.TIMESTAMP);
                defaultValue = Setting.defaultValue(setting, "20180101").toString();
                return defaultValue;
            }
        });
    }

}
