package com.d2c.content.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.FeedBackMapper;
import com.d2c.content.dto.FeedBackDto;
import com.d2c.content.model.FeedBack;
import com.d2c.content.query.FeedBackSearcher;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("feedBackService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FeedBackServiceImpl extends ListServiceImpl<FeedBack> implements FeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private MsgUniteService msgUniteService;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public FeedBack insert(FeedBack feedBack) {
        return this.save(feedBack);
    }

    @Override
    public FeedBack findById(Long id) {
        return this.findOneById(id);
    }

    public PageResult<FeedBackDto> findBySearcher(FeedBackSearcher searcher, PageModel page) {
        PageResult<FeedBackDto> pager = new PageResult<FeedBackDto>(page);
        Integer count = feedBackMapper.countBySearcher(searcher);
        List<FeedBack> list = new ArrayList<FeedBack>();
        List<FeedBackDto> dtos = new ArrayList<FeedBackDto>();
        if (count != null && count > 0) {
            list = feedBackMapper.findBySearcher(searcher, page);
            for (FeedBack fb : list) {
                FeedBackDto dto = new FeedBackDto();
                BeanUtils.copyProperties(fb, dto);
                dtos.add(dto);
            }
        }
        pager.setList(dtos);
        pager.setTotalCount(count);
        return pager;
    }

    @Override
    public Integer countBySearcher(FeedBackSearcher searcher) {
        return feedBackMapper.countBySearcher(searcher);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByType(FeedBackSearcher searcher) {
        return feedBackMapper.findCountGroupByType(searcher);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return deleteByIds(new Long[]{id});
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByIds(Long[] ids) {
        return feedBackMapper.deleteByIds(ids);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return feedBackMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReply(Long id, String reply, String replyMan) {
        int result = feedBackMapper.doReply(id, reply, replyMan);
        if (result > 0) {
            FeedBack feedback = this.findOneById(id);
            if (feedback.getMemberId() != null && feedback.getMemberId() != 0) {
                JSONObject obj = new JSONObject();
                obj.put("nickName", feedback.getNickName());
                obj.put("headPic", feedback.getHeadPic());
                obj.put("content", feedback.getContent());
                obj.put("reply", reply);
                String subject = "您的反馈&投诉有一条回复。";
                String content = "回复:" + reply;
                PushBean pushBean = new PushBean(feedback.getMemberId(), content, 26);
                pushBean.setAppUrl("/feedback/" + feedback.getId());
                MsgBean msgBean = new MsgBean(feedback.getMemberId(), 26, subject, content);
                msgBean.setAppUrl("/feedback/" + feedback.getId());
                msgBean.setOther(obj.toJSONString());
                msgUniteService.sendPush(pushBean, msgBean);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doClose(Long id) {
        return feedBackMapper.doClose(id);
    }

    @Override
    public List<String> findVersions() {
        return feedBackMapper.findVersions();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String meno) {
        return feedBackMapper.updateStatus(id, status, meno);
    }

}
