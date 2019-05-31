package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.content.dao.SplashScreenMapper;
import com.d2c.content.model.SplashScreen;
import com.d2c.content.query.SplashScreenSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("splashScreenService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SplashScreenServiceImpl extends ListServiceImpl<SplashScreen> implements SplashScreenService {

    @Autowired
    private SplashScreenMapper splashScreenMapper;

    @Override
    @CacheEvict(value = "splash_screen", key = "'splash_screen_now'")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public SplashScreen insert(SplashScreen splashScreen) {
        splashScreen = this.save(splashScreen);
        timingScreenMQ(splashScreen);
        return splashScreen;
    }

    @Override
    public PageResult<SplashScreen> findBySearcher(PageModel page, SplashScreenSearcher searcher) {
        PageResult<SplashScreen> pager = new PageResult<SplashScreen>(page);
        Integer totalCount = splashScreenMapper.countBySearcher(searcher);
        List<SplashScreen> list = new ArrayList<SplashScreen>();
        if (totalCount > 0) {
            list = splashScreenMapper.findBySearcher(page, searcher);
            pager.setTotalCount(totalCount);
        }
        pager.setList(list);
        return pager;
    }

    @Override
    @Cacheable(value = "splash_screen", key = "'splash_screen_now'", unless = "#result == null")
    public SplashScreen findCurrentVersion() {
        return splashScreenMapper.findCurrentVersion();
    }

    @Override
    @CacheEvict(value = "splash_screen", key = "'splash_screen_now'")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SplashScreen splashScreen) {
        int success = this.updateNotNull(splashScreen);
        this.timingScreenMQ(splashScreen);
        return success;
    }

    private void timingScreenMQ(SplashScreen splashScreen) {
        if (splashScreen.getTiming() != null && splashScreen.getTiming() == 1) {
            Date now = new Date();
            Map<String, Object> map = new HashMap<String, Object>();
            long interval = 0;
            if (splashScreen.getBeginDate().after(now)) {
                interval = (splashScreen.getBeginDate().getTime() - now.getTime()) / 1000 + 1;
                map.put("mark", 1);
            }
            if (splashScreen.getEndDate().after(now) && splashScreen.getEndDate().after(splashScreen.getBeginDate())) {
                interval = (splashScreen.getEndDate().getTime() - now.getTime()) / 1000 + 1;
                map.put("mark", 0);
            }
            map.put("id", splashScreen.getId());
            map.put("date", splashScreen.getBeginDate().getTime());
            MqEnum.TIMING_SCREEN.send(map, interval);
        }
    }

    @Override
    @CacheEvict(value = "splash_screen", key = "'splash_screen_now'")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String lastModifyMan) {
        if (status.intValue() == 1) {
            splashScreenMapper.doDownAll();
        }
        return splashScreenMapper.updateStatus(id, status, lastModifyMan);
    }

    @Override
    public SplashScreen findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doTiming(Long id, Integer timing) {
        return splashScreenMapper.doTiming(id, timing);
    }

}
