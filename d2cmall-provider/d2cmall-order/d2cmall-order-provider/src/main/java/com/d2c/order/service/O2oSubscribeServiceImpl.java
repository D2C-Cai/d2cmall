package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.O2oSubscribeMapper;
import com.d2c.order.dto.O2oSubscribeDto;
import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.model.O2oSubscribeItem;
import com.d2c.order.model.Store;
import com.d2c.order.query.O2oSubscribeSearcher;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("o2oSubscribeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class O2oSubscribeServiceImpl extends ListServiceImpl<O2oSubscribe> implements O2oSubscribeService {

    @Autowired
    private O2oSubscribeMapper o2oSubscribeMapper;
    @Autowired
    private O2oSubscribeItemService o2oSubscribeItemService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public O2oSubscribeDto findById(Long id) {
        O2oSubscribe o2o = o2oSubscribeMapper.selectByPrimaryKey(id);
        O2oSubscribeDto dto = new O2oSubscribeDto();
        if (o2o != null) {
            BeanUtils.copyProperties(o2o, dto);
            dto.setItems(o2oSubscribeItemService.findBySubscribeId(o2o.getId()));
        }
        return dto;
    }

    @Override
    public O2oSubscribe findByIdAndStoreId(Long id, Long storeId) {
        return o2oSubscribeMapper.findByIdAndStoreId(id, storeId);
    }

    @Override
    public O2oSubscribeDto findUnSubmit(Long memberId) {
        O2oSubscribe o2oSubscribe = o2oSubscribeMapper.findUnSubmit(memberId);
        if (o2oSubscribe == null) {
            return null;
        }
        O2oSubscribeDto dto = new O2oSubscribeDto();
        BeanUtils.copyProperties(o2oSubscribe, dto);
        dto.setItems(o2oSubscribeItemService.findBySubscribeId(o2oSubscribe.getId()));
        return dto;
    }

    @Override
    public O2oSubscribe findLastO2oSubscribe(Long memberId) {
        return o2oSubscribeMapper.findLastO2oSubscribe(memberId);
    }

    @Override
    public PageResult<O2oSubscribeDto> findBySearch(O2oSubscribeSearcher searcher, PageModel page) {
        int totalCount = o2oSubscribeMapper.countBySearch(searcher);
        List<O2oSubscribeDto> dtos = new ArrayList<>();
        PageResult<O2oSubscribeDto> pager = new PageResult<>(page);
        if (totalCount > 0) {
            List<O2oSubscribe> list = o2oSubscribeMapper.findBySearch(searcher, page);
            for (O2oSubscribe o2o : list) {
                O2oSubscribeDto dto = new O2oSubscribeDto();
                BeanUtils.copyProperties(o2o, dto);
                List<O2oSubscribeItem> items = o2oSubscribeItemService.findBySubscribeId(o2o.getId());
                dto.setItems(items);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public Integer countBySearch(O2oSubscribeSearcher searcher) {
        return o2oSubscribeMapper.countBySearch(searcher);
    }

    @Override
    public Map<String, Object> findAnalysisBySearch(O2oSubscribeSearcher searcher) {
        return o2oSubscribeMapper.findAnalysisBySearch(searcher);
    }

    @Override
    public Map<String, Integer> countGroupByStatus(Long storeId) {
        Map<String, Integer> result = new HashMap<>();
        List<Map<Integer, Integer>> list = o2oSubscribeMapper.countGroupByStatus(storeId);
        for (Map<Integer, Integer> item : list) {
            Integer status = item.get("status");
            switch (status) {
                case 2:
                    result.put("WaitingForRec", item.get("count"));
                    break;
                case 3:
                    result.put("WaitingForServer", item.get("count"));
                    break;
                case 4:
                    result.put("FinishForServer", item.get("count"));
                    break;
            }
        }
        return result;
    }

    @Override
    public int countSubscribeTimes(O2oSubscribe subscribe) {
        return o2oSubscribeMapper.countSubscribeTimes(subscribe);
    }

    @Override
    public int findPendingCount() {
        O2oSubscribeSearcher searcher = new O2oSubscribeSearcher();
        searcher.setStatus(new Integer[]{1});
        return o2oSubscribeMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public O2oSubscribe insert(O2oSubscribe noSubmit, O2oSubscribeItem item) {
        if (noSubmit.getStoreId() != null) {
            Store store = storeService.findById(noSubmit.getStoreId());
            noSubmit.setStoreName(store.getName());
            noSubmit.setStoreAddress(store.getAddress());
            noSubmit.setStoreTel(store.getTel());
        }
        noSubmit = this.save(noSubmit);
        item.setSubscribeId(noSubmit.getId());
        o2oSubscribeItemService.insert(item);
        return noSubmit;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(O2oSubscribe o2oSubscribe) {
        O2oSubscribe old = o2oSubscribeMapper.selectByPrimaryKey(o2oSubscribe.getId());
        o2oSubscribe.setMemberId(old.getMemberId());
        if (!o2oSubscribe.getStoreId().equals(old.getStoreId())) {
            Store store = storeService.findById(o2oSubscribe.getStoreId());
            o2oSubscribe.setStoreName(store.getName());
            o2oSubscribe.setStoreTel(store.getTel());
            o2oSubscribe.setStoreAddress(store.getAddress());
        }
        if (o2oSubscribe.getStoreService() == null) {
            o2oSubscribe.setStoreService("");
        }
        return this.updateNotNull(o2oSubscribe);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateVisitById(Long id, String visit) {
        return o2oSubscribeMapper.updateVisitById(id, visit);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRemarkById(Long id, String remark) {
        return o2oSubscribeMapper.updateRemarkById(id, remark);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCusCostById(Long o2oSubscribeId, int status, Long commentId, BigDecimal cusCost) {
        return o2oSubscribeMapper.updateCusCostById(o2oSubscribeId, status, commentId, cusCost);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doNotice(Long id, String noticeMan) {
        return o2oSubscribeMapper.notice(id, noticeMan);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancel(Long id, String cancelMan, String cancelReason) {
        return o2oSubscribeMapper.cancel(id, cancelMan, cancelReason);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReceive(Long id, String operator) {
        int result = o2oSubscribeMapper.receive(id, operator);
        if (result > 0) {
            O2oSubscribeDto dto = this.findById(id);
            Calendar c = Calendar.getInstance();
            c.setTime(dto.getEstimateDate());
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            String subject = "您的试衣申请已成功，小D会尽快为您准备美衣，期待您的光临~~\r\n" + "点击进入-我的预约";
            String content = "亲爱的" + (StringUtil.isNotBlank(dto.getName()) ? dto.getName() : "顾客") + "，您预约的" + month
                    + "月" + day + "号" + dto.getStartHour() + "点" + dto.getStartMinute() + "分到" + dto.getEndHour() + "点"
                    + dto.getEndMinute() + "分于" + dto.getStoreAddress() + "试衣申请已成功，小D会尽快为您准备美衣，期待您的光临~~";
            PushBean pushBean = new PushBean(dto.getMemberId(), content, 27);
            pushBean.setAppUrl("/o2oSubscribe/my/list");
            MsgBean msgBean = new MsgBean(dto.getMemberId(), 27, subject, content);
            msgBean.setAppUrl("/o2oSubscribe/my/list");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReady(Long id) {
        int result = o2oSubscribeMapper.ready(id);
        if (result > 0) {
            O2oSubscribeDto dto = this.findById(id);
            Calendar c = Calendar.getInstance();
            c.setTime(dto.getEstimateDate());
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            String subject = "您的试衣申请小D已为您准备完毕，期待您的光临~~\r\n" + "点击进入-我的预约";
            String content = "亲爱的 " + (StringUtil.isNotBlank(dto.getName()) ? dto.getName() : "顾客") + "，您预约的" + month
                    + "月" + day + "号" + dto.getStartHour() + "点" + dto.getStartMinute() + "分到" + dto.getEndHour() + "点"
                    + dto.getEndMinute() + "分于" + dto.getStoreAddress() + "试衣，小D已为您准备完毕，期待您的光临~~";
            PushBean pushBean = new PushBean(dto.getMemberId(), content, 27);
            pushBean.setAppUrl("/o2oSubscribe/my/list");
            MsgBean msgBean = new MsgBean(dto.getMemberId(), 27, subject, content);
            msgBean.setAppUrl("/o2oSubscribe/my/list");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doComplete(Long id, String completeMan, String feedback, BigDecimal payAmount, Integer payStatus,
                          Integer actualNumbers, String retailSn) {
        return o2oSubscribeMapper.complete(id, completeMan, feedback, payAmount, payStatus, actualNumbers, retailSn);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancelByMemberId(Long id, Long memberId) {
        return o2oSubscribeMapper.doCancelByMemberId(id, memberId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return o2oSubscribeMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteBy(Long id, Long memberId) {
        int result = o2oSubscribeMapper.deleteByIdAndMemberId(id, memberId);
        if (result > 0) {
            o2oSubscribeItemService.deleteBySubscribeIdAndMemberId(id, memberId);
        }
        return result;
    }

}
