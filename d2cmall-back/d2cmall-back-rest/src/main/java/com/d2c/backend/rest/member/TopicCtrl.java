package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.TopicDto;
import com.d2c.member.model.Topic;
import com.d2c.member.query.TopicSearcher;
import com.d2c.member.service.TopicService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 话题
 *
 * @author Lain
 */
@RestController
@RequestMapping("/rest/society/topic")
public class TopicCtrl extends BaseCtrl<TopicSearcher> {

    @Autowired
    private TopicService topicService;

    @Override
    protected Response doList(TopicSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<TopicDto> pager = topicService.findDtoBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(TopicSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(TopicSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(TopicSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Topic> pager = topicService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Topic topic = topicService.findById(id);
        result.put("topic", topic);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Topic topic = JsonUtil.instance().toObject(data, Topic.class);
        SuccessResponse result = new SuccessResponse();
        topicService.update(topic);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Topic topic = JsonUtil.instance().toObject(data, Topic.class);
        topic.setSort(0);
        topic.setDeleted(0);
        topic.setStatus(0);
        topic.setTiming(0);
        topic.setTop(0);
        topic = topicService.insert(topic);
        result.put("topic", topic);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        topicService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response updateMark(Long[] ids, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            topicService.updateStatus(id, status);
        }
        return result;
    }

    @RequestMapping(value = "/sort/{sort}", method = RequestMethod.POST)
    public Response updateSort(Long id, @PathVariable Integer sort) {
        SuccessResponse result = new SuccessResponse();
        topicService.updateSort(id, sort);
        return result;
    }

    @RequestMapping(value = "/top/{top}", method = RequestMethod.POST)
    public Response top(Long id, @PathVariable Integer top) {
        SuccessResponse result = new SuccessResponse();
        topicService.updateTop(id, top);
        return result;
    }

}
