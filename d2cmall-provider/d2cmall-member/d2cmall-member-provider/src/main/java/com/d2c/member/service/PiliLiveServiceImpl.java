package com.d2c.member.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.PiliLiveMapper;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("piliLiveService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PiliLiveServiceImpl extends ListServiceImpl<PiliLive> implements PiliLiveService {

    @Autowired
    private PiliLiveMapper piliLiveMapper;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;
    @Autowired
    private RedisTemplate<String, JSONObject> redisTemplate;

    @Override
    @Cacheable(value = "live", key = "'live_'+#id", unless = "#result == null")
    public PiliLive findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<PiliLive> findBySearcher(PiliLiveSearcher searcher, PageModel page) {
        PageResult<PiliLive> pager = new PageResult<PiliLive>(page);
        int totalCount = piliLiveMapper.countBySearcher(searcher);
        List<PiliLive> list = new ArrayList<PiliLive>();
        if (totalCount > 0) {
            list = piliLiveMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public PiliLive insert(PiliLive live) {
        return this.save(live);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doClose(Long id, String replayUrl) {
        return piliLiveMapper.doClose(id, replayUrl);
    }

    @Override
    public PiliLive findLastOne(Long memberId) {
        return piliLiveMapper.findLastOne(memberId);
    }

    @Override
    public Integer getLiveCount(Long id) {
        Integer realtime = redisHandler.get("live_count_" + id);
        if (realtime == null) {
            realtime = 0;
        }
        return realtime;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String, Object> doIn(Long id, int vrate, Long memberId, String headPic) {
        Map<String, Object> result = new HashMap<>();
        // 正在观看直播的人数
        int count = 1;
        if (vrate > 1) {
            count = new Random().nextInt(vrate) + 1;
        }
        piliLiveMapper.doIn(id, count);
        Integer realtime = redisHandler.get("live_count_" + id);
        if (realtime == null) {
            realtime = 0;
        }
        redisHandler.set("live_count_" + id, realtime + count);
        PiliLive live = this.findById(id);
        result.put("count", realtime + count + live.getVfans());
        // 正在观看观众的头像列表
        String key = "live_headpic_" + id;
        JSONObject obj = new JSONObject();
        obj.put("memberId", memberId);
        obj.put("headPic", headPic == null ? "" : headPic);
        redisTemplate.opsForList().remove(key, 1, obj);
        redisTemplate.opsForList().leftPush(key, obj);
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > 10) {
            redisTemplate.opsForList().rightPop(key);
        }
        result.put("headers", redisTemplate.opsForList().range(key, 0, -1));
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String, Object> doOut(Long id, Long memberId, String headPic) {
        Map<String, Object> result = new HashMap<>();
        // 正在观看直播的人数
        Integer realtime = redisHandler.get("live_count_" + id);
        if (realtime == null) {
            realtime = 0;
        }
        redisHandler.set("live_count_" + id, realtime - 1);
        PiliLive live = this.findById(id);
        result.put("count", realtime == 0 ? realtime + live.getVfans() : realtime - 1 + live.getVfans());
        // 正在观看观众的头像列表
        String key = "live_headpic_" + id;
        JSONObject obj = new JSONObject();
        obj.put("memberId", memberId);
        obj.put("headPic", headPic == null ? "" : headPic);
        redisTemplate.opsForList().remove(key, 1, obj);
        result.put("headers", redisTemplate.opsForList().range(key, 0, -1));
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doWatch(Long id) {
        return piliLiveMapper.doIn(id, 1);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int deleteByMemberId(Long id, Long memberId) {
        return piliLiveMapper.deleteByMemberId(id, memberId);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int deleteById(Long id) {
        return piliLiveMapper.deleteById(id);
    }

    @Override
    public PageResult<PiliLive> findHot(PageModel page) {
        PageResult<PiliLive> pager = new PageResult<>(page);
        Integer totalCount = piliLiveMapper.countHot();
        List<PiliLive> list = new ArrayList<>();
        if (totalCount > 0) {
            list = piliLiveMapper.findHot(page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateMark(Long id, Integer mark, String modifyMan) {
        return piliLiveMapper.updateMark(id, mark, modifyMan);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateTop(Long id, Integer top, String modifyMan) {
        return piliLiveMapper.updateTop(id, top, modifyMan);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateVfans(Long id, Integer vfans, String modifyMan) {
        return piliLiveMapper.updateVfans(id, vfans, modifyMan);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateVrate(Long id, Integer vrate, String modifyMan) {
        return piliLiveMapper.updateVrate(id, vrate, modifyMan);
    }

    @Override
    @CacheEvict(value = "live", key = "'live_'+#id")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateRatio(Long id, Integer ratio, String modifyMan) {
        return piliLiveMapper.updateRatio(id, ratio, modifyMan);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        piliLiveMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
    }

}
