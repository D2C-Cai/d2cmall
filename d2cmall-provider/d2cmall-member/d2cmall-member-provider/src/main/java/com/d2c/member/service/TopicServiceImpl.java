package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.TopicMapper;
import com.d2c.member.dto.TopicDto;
import com.d2c.member.model.Topic;
import com.d2c.member.query.TopicSearcher;
import com.d2c.member.search.model.SearcherTopic;
import com.d2c.member.search.service.TopicSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("topicService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class TopicServiceImpl extends ListServiceImpl<Topic> implements TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private MemberShareService memberShareService;
    @Reference
    private TopicSearcherService topicSearcherService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public Topic insert(Topic topic) {
        topic = this.save(topic);
        if (topic != null) {
            SearcherTopic searcherTopic = new SearcherTopic();
            BeanUtils.copyProperties(topic, searcherTopic);
            topicSearcherService.insert(searcherTopic);
        }
        return topic;
    }

    @Override
    public Topic findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Topic topic) {
        int success = this.updateNotNull(topic);
        if (success > 0) {
            SearcherTopic searcherTopic = new SearcherTopic();
            BeanUtils.copyProperties(topic, searcherTopic);
            topicSearcherService.insert(searcherTopic);
            topicSearcherService.update(searcherTopic);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int delete(Long id) {
        int success = topicMapper.delete(id);
        if (success > 0) {
            topicSearcherService.remove(id);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateStatus(Long id, Integer status) {
        int success = topicMapper.updateStatus(id, status);
        if (success > 0) {
            SearcherTopic searcherTopic = new SearcherTopic();
            searcherTopic.setId(id);
            searcherTopic.setStatus(status);
            topicSearcherService.update(searcherTopic);
        }
        return success;
    }

    @Override
    public PageResult<Topic> findBySearcher(TopicSearcher searcher, PageModel page) {
        PageResult<Topic> pager = new PageResult<Topic>(page);
        Integer totalCount = topicMapper.countBySearcher(searcher);
        List<Topic> list = new ArrayList<Topic>();
        if (totalCount > 0) {
            list = topicMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateTop(Long id, Integer top) {
        int success = topicMapper.updateTop(id, top);
        if (success > 0) {
            SearcherTopic searcherTopic = new SearcherTopic();
            searcherTopic.setId(id);
            searcherTopic.setTop(top);
            topicSearcherService.update(searcherTopic);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSort(Long id, Integer sort) {
        int success = topicMapper.updateSort(id, sort);
        if (success > 0) {
            SearcherTopic searcherTopic = new SearcherTopic();
            searcherTopic.setId(id);
            searcherTopic.setSort(sort);
            topicSearcherService.update(searcherTopic);
        }
        return success;
    }

    @Override
    public PageResult<TopicDto> findDtoBySearcher(TopicSearcher searcher, PageModel page) {
        PageResult<TopicDto> pager = new PageResult<TopicDto>(page);
        Integer totalCount = topicMapper.countBySearcher(searcher);
        List<TopicDto> dtoList = new ArrayList<TopicDto>();
        if (totalCount > 0) {
            List<Topic> list = topicMapper.findBySearcher(searcher, page);
            for (Topic topic : list) {
                TopicDto dto = new TopicDto();
                BeanUtils.copyProperties(topic, dto);
                int shareCount = memberShareService.countByTopic(topic.getId());
                dto.setShareCount(shareCount);
                dtoList.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtoList);
        return pager;
    }

}
