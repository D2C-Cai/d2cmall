package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.SettingMapper;
import com.d2c.order.model.Setting;
import com.d2c.order.query.SettingSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("settingService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SettingServiceImpl extends ListServiceImpl<Setting> implements SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Override
    @Cacheable(value = "setting", key = "'setting_code_'+#code", unless = "#result == null")
    public Setting findByCode(String code) {
        return settingMapper.findByCode(code);
    }

    @Override
    public PageResult<Setting> findBySearch(PageModel page, SettingSearcher searcher) {
        PageResult<Setting> pager = new PageResult<>(page);
        int totalCount = settingMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Setting> list = settingMapper.findBySearch(page, searcher);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Setting insert(Setting setting) throws Exception {
        return this.save(setting);
    }

    @Override
    @CacheEvict(value = "setting", key = "'setting_code_'+#code")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateValueById(String value, Long id, String code) {
        int success = settingMapper.updateValueById(value, id);
        if (code.equals(Setting.SMSCHOICE)) {
            MqEnum.SMS_CHOICE.send(value.trim());
        }
        return success;
    }

    @Override
    @CacheEvict(value = "setting", key = "'setting_code_'+#code")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, int status, String code) {
        settingMapper.updateStatusById(id, status);
        int success = settingMapper.updateStatusByBelongto(id, status);
        return success;
    }

    @Override
    @CacheEvict(value = "setting", key = "'setting_code_'+#code")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id, String code) {
        this.deleteById(id);
        int success = settingMapper.deleteByBelongto(id);
        return success;
    }

    @Override
    public Setting findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @CacheEvict(value = "setting", key = "'setting_code_'+#setting.code")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Setting setting) throws Exception {
        int success = this.updateNotNull(setting);
        if (setting.getCode().equals(Setting.SMSCHOICE)) {
            MqEnum.SMS_CHOICE.send(setting.getValue().trim());
        }
        return success;
    }

}
