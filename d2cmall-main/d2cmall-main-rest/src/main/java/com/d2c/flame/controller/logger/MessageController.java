package com.d2c.flame.controller.logger;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.service.MessageSearcherService;
import com.d2c.logger.search.support.MessageReadTimeHelp;
import com.d2c.logger.search.support.MessageSearchHelp;
import com.d2c.logger.service.MessageService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.services.MemberTaskExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息通知
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/message")
public class MessageController extends BaseController {

    @Reference
    private MessageSearcherService messageSearcherService;
    @Autowired
    private MessageService messageService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    /**
     * 消息大类列表
     *
     * @return
     */
    @RequestMapping(value = "/major/list", method = RequestMethod.GET)
    public ResponseResult major(MessageReadTimeHelp readTime) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberinfo = this.getLoginMemberInfo();
        List<MessageSearchHelp> list = messageSearcherService.searchGroupByMajorType(memberinfo.getId(), readTime);
        JSONArray array = new JSONArray();
        list.forEach(item -> array.add(item.toJson()));
        result.put("messages", array);
        return result;
    }

    /**
     * 消息列表
     *
     * @param page      分页{@link PageModel}
     * @param majorType 消息大类值
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page, Long majorType, MessageReadTimeHelp readTime) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<SearcherMessage> pager = messageSearcherService.search(memberInfo.getId(), majorType, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("messages", pager, array);
        int unreadCount = messageSearcherService.count(memberInfo.getId(), 0, readTime);
        result.put("unreadCount", unreadCount);
        return result;
    }

    /**
     * 标记为已读
     *
     * @param id 消息ID
     * @return
     */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public ResponseResult read(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        messageService.updateStatus(id, memberInfo.getId(), 1);
        return result;
    }

    /**
     * 删除
     *
     * @param id 消息ID
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseResult delete(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        messageService.deleteByIdAndMemberId(id, memberInfo.getId());
        return result;
    }

    /**
     * 批量删除
     *
     * @param ids 消息ID数组
     * @return
     */
    @RequestMapping(value = "/batch/delete", method = RequestMethod.GET)
    public ResponseResult deleteIds(Long[] ids) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        for (Long id : ids) {
            messageService.deleteByIdAndMemberId(id, memberInfo.getId());
        }
        return result;
    }

    /**
     * 读取一个大类的消息
     *
     * @param majorType 大类值
     * @return
     */
    @RequestMapping(value = "/read/major/{majorType}", method = RequestMethod.GET)
    public ResponseResult readMajor(@PathVariable Integer majorType) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        messageService.doReadMajor(memberInfo.getId(), majorType);
        return result;
    }

}
