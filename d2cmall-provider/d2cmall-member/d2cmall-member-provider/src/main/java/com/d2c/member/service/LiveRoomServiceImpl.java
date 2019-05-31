package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.LiveRoomMapper;
import com.d2c.member.model.LiveRoom;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.LiveRoomSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("liveRoomService")
@Transactional(rollbackFor = Exception.class, readOnly = true, propagation = Propagation.SUPPORTS)
public class LiveRoomServiceImpl extends ListServiceImpl<LiveRoom> implements LiveRoomService {

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private LiveRoomMapper liveRoomMapper;

    @Override
    public LiveRoom findByMemberId(Long memberId) {
        return liveRoomMapper.findByMemberId(memberId);
    }

    @Override
    public PageResult<LiveRoom> findBySearcher(LiveRoomSearcher searcher, PageModel page) {
        PageResult<LiveRoom> pager = new PageResult<>(page);
        int totalCount = liveRoomMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<LiveRoom> list = liveRoomMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public int doCreateLiveRoom(Long memberId) {
        // 先删除原来的房间
        this.doDeleteLiveRoom(memberId);
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        if (memberInfo.getDesignerId() != null || memberInfo.getType() == 3) {
            LiveRoom liveRoom = new LiveRoom(memberInfo.getId(), memberInfo.getLoginCode(),
                    memberInfo.getType() == 3 ? 0 : memberInfo.getDesignerId());
            liveRoom = this.save(liveRoom);
            if (liveRoom.getId() != null) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public int doDeleteLiveRoom(Long memberId) {
        LiveRoom liveRoom = this.findByMemberId(memberId);
        if (liveRoom != null) {
            return liveRoomMapper.doDeleteLiveRoom(liveRoom.getId());
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public int doBindCoupons(Long id, Long couponId, String operator, Long couponGroupId) {
        return liveRoomMapper.doBindCoupons(id, couponId, operator, couponGroupId);
    }

}
