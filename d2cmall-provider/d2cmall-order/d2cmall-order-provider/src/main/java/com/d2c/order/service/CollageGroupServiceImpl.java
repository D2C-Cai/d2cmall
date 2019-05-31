package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CollageGroupMapper;
import com.d2c.order.dto.CollageGroupDto;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.query.CollageGroupSearcher;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.service.CollagePromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value = "collageGroupService")
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CollageGroupServiceImpl extends ListServiceImpl<CollageGroup> implements CollageGroupService {

    @Autowired
    private CollageGroupMapper collageGroupMapper;
    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RequisitionItemService requisitionItemService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollageGroup insert(CollageGroup collageGroup) {
        return this.save(collageGroup);
    }

    @Override
    public CollageGroup findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public CollageGroupDto findDtoById(Long id) {
        CollageGroupDto dto = new CollageGroupDto();
        CollageGroup group = this.findById(id);
        BeanUtils.copyProperties(group, dto);
        List<CollageOrder> list = collageOrderService.findByGroupId(id);
        dto.setCollageOrders(list);
        return dto;
    }

    @Override
    public PageResult<CollageGroup> findVaildByPromotionId(Long promotionId, PageModel page) {
        PageResult<CollageGroup> pager = new PageResult<>(page);
        int totalCount = collageGroupMapper.countVaildByPromotionId(promotionId);
        if (totalCount > 0) {
            List<CollageGroup> list = collageGroupMapper.findVaildByPromotionId(promotionId, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<CollageGroup> findBySearch(CollageGroupSearcher searcher, PageModel page) {
        PageResult<CollageGroup> pager = new PageResult<>(page);
        int totalCount = collageGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CollageGroup> list = collageGroupMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(CollageGroupSearcher searcher) {
        return collageGroupMapper.countBySearch(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateCurrentMemberCount(Long groupId, int num) {
        return collageGroupMapper.updateCurrentMemberCount(groupId, num);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePayCount(Long groupId, int num) {
        return collageGroupMapper.updatePayCount(groupId, num);
    }

    @Override
    public PageResult<CollageGroup> findExpireGroup(Date deadline, PageModel page) {
        PageResult<CollageGroup> pager = new PageResult<>(page);
        int totalCount = collageGroupMapper.countExpireGroup(deadline);
        if (totalCount > 0) {
            List<CollageGroup> list = collageGroupMapper.findExpiredGroup(deadline, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countExpireGroup(Date deadline) {
        return collageGroupMapper.countExpireGroup(deadline);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doFailGroup(Long id) {
        return collageGroupMapper.doFailGroup(id);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSuccess(CollageGroup group) {
        int result = collageGroupMapper.doSuccess(group.getId());
        if (result > 0) {
            CollagePromotion promotion = collagePromotionService.findById(group.getPromotionId());
            List<CollageOrder> list = collageOrderService.findByGroupId(group.getId());
            Map<Long, String> members = new HashMap<>();
            List<String> orderSn = new ArrayList<String>();
            for (CollageOrder order : list) {
                collageOrderService.doSuccess(order.getId());
                orderSn.add(order.getSn());
                members.put(order.getMemberId(), order.getLoginCode());
            }
            orderItemService.updateCollageStatus(orderSn, 8);
            for (Long memberId : members.keySet()) {
                // 发送消息
                String content = "恭喜您！您参与的拼团活动 '" + promotion.getName() + "' 已拼团成功，可到D2C全球好设计小程序或D2C APP中查看~ ";
                MsgBean msgBean = new MsgBean(memberId, 61, "恭喜您！拼团成功", content);
                msgBean.setAppUrl("/mycollage/list");
                msgBean.setPic(promotion.getProductImage());
                SmsBean smsBean = new SmsBean(null, members.get(memberId), SmsLogType.REMIND, content);
                msgUniteService.sendMsg(smsBean, null, msgBean, null);
            }
            requisitionItemService.doSyncDesignerDelivery(orderSn);
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateInitiator(Long id, Long memberId, String memberName, String headPic) {
        return collageGroupMapper.updateInitiator(id, memberId, memberName, headPic);
    }

    @Override
    public int countGroupByPromotionId(Long promotionId) {
        return collageGroupMapper.countGroupByPromotionId(promotionId);
    }

}
