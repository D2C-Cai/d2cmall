package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.dao.ConsultMapper;
import com.d2c.member.dto.ConsultDto;
import com.d2c.member.model.Consult;
import com.d2c.member.query.ConsultSearcher;
import com.d2c.member.search.model.SearcherConsult;
import com.d2c.member.search.service.ConsultSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("consultService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ConsultServiceImpl extends ListServiceImpl<Consult> implements ConsultService {

    @Autowired
    private ConsultMapper consultMapper;
    @Reference
    private ConsultSearcherService consultSearcherService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public Consult findById(Long id) {
        Consult consult = this.findOneById(id);
        return consult;
    }

    @Override
    public PageResult<ConsultDto> findBySearcher(ConsultSearcher searcher, PageModel page) {
        PageResult<ConsultDto> pager = new PageResult<>(page);
        int totalCount = consultMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Consult> list = consultMapper.findBySearcher(searcher, page);
            List<ConsultDto> dtos = new ArrayList<>();
            for (Consult c : list) {
                ConsultDto dto = new ConsultDto();
                BeanUtils.copyProperties(c, dto);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(ConsultSearcher searcher) {
        return consultMapper.countBySearcher(searcher);
    }

    @Override
    public int countByProduct(Long productId, Integer status) {
        return consultMapper.countByProductId(productId, status);
    }

    @Override
    public Map<String, Object> countGroupByStatus() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> countList = consultMapper.countGroupByStatus();
        // 获取各状态下的数量
        for (Map<String, Object> count : countList) {
            String status = count.get("status").toString();
            switch (status) {
                case "-1":
                    map.put("delete", count.get("count").toString());
                    break;
                case "0":
                    map.put("noAudit", count.get("count").toString());
                    break;
                case "1":
                    map.put("noReply", count.get("count").toString());
                    break;
                case "2":
                    map.put("reply", count.get("count").toString());
                    break;
            }
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Consult insert(Consult consult) {
        consult.setRecomend(0);
        consult.setRecomendDate(new Date());
        consult = this.save(consult);
        if (consult != null) {
            SearcherConsult searcherConsult = new SearcherConsult();
            BeanUtils.copyProperties(consult, searcherConsult);
            consultSearcherService.insert(searcherConsult);
        }
        return consult;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public int updateRecommand(Long[] ids, Integer recommand) {
        int success = consultMapper.updateRecomendByRecomendStatus(ids, recommand);
        if (success > 0) {
            for (Long id : ids) {
                SearcherConsult searcherConsult = new SearcherConsult();
                searcherConsult.setId(id);
                searcherConsult.setRecomend(recommand);
                consultSearcherService.update(searcherConsult);
            }
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doReply(Long[] ids, String reply) {
        int result = 0;
        for (Long id : ids) {
            Consult consult = this.findById(id);
            result = consultMapper.reply(id, reply);
            if (result > 0 && consult.getMemberId() != null && consult.getMemberId() != 0) {
                SearcherConsult searcherConsult = new SearcherConsult();
                searcherConsult.setReply(reply);
                searcherConsult.setId(id);
                searcherConsult.setReplyDate(new Date());
                searcherConsult.setStatus(2);
                consultSearcherService.update(searcherConsult);
                if (consult.getStatus() != 2) {
                    this.productSummaryMQ(consult.getProductId(), 1);
                }
                String subject = "咨询回复";
                String content = "您发表的购买咨询已收到回复，请查看";
                PushBean pushBean = new PushBean(consult.getMemberId(), content, 25);
                pushBean.setAppUrl("/detail/consult/" + consult.getId());
                MsgBean msgBean = new MsgBean(consult.getMemberId(), 25, subject, content);
                msgBean.setAppUrl("/detail/consult/" + consult.getId());
                msgBean.setPic(consult.getProductPic());
                msgUniteService.sendPush(pushBean, msgBean);
            }
        }
        return result;
    }

    private void productSummaryMQ(Long productId, int count) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", productId);
        map.put("count", count);
        map.put("type", "collection");
        MqEnum.PRODUCT_SUMMARY.send(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = consultMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            consultSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteByIds(Long[] ids) {
        int result = 0;
        for (long id : ids) {
            Consult consult = this.findById(id);
            result = consultMapper.deleteById(id);
            if (result > 0) {
                consultSearcherService.updateStatusByIds(new Long[]{id}, -1);
                if (consult.getStatus() == 2) {
                    this.productSummaryMQ(consult.getProductId(), -1);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int cancelDelete(Long[] ids) {
        int result = consultMapper.cancelDelete(ids);
        if (result > 0) {
            consultSearcherService.updateStatusByIds(ids, 1);
        }
        return result;
    }

}
