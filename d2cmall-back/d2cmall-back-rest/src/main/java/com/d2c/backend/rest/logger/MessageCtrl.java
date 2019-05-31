package com.d2c.backend.rest.logger;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Message;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.model.MessageDef.MessageChannel;
import com.d2c.logger.query.MessageSearcher;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.service.MessageSearcherService;
import com.d2c.logger.service.MessageDefService;
import com.d2c.logger.service.MessageService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.service.MemberTagRelationService;
import com.d2c.member.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/msg/message")
public class MessageCtrl extends BaseCtrl<MessageSearcher> {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageDefService messageDefService;
    @Autowired
    private MemberTagRelationService memberTagRelationService;
    @Reference
    private MessageSearcherService messageSearcherService;
    @Autowired
    private PartnerService partnerService;

    @Override
    protected List<Map<String, Object>> getRow(MessageSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(MessageSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
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
    protected Response doHelp(MessageSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(MessageSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Message> pager = messageService.findBySearch(page, searcher);
        SuccessResponse response = new SuccessResponse(pager);
        return response;
    }

    @Override
    protected Response findById(Long id) {
        Message message = messageService.findById(id);
        return new SuccessResponse(message);
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                messageService.delete(ids[i]);
            }
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        messageService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 发送全站消息或发送用户消息
     *
     * @param defId
     * @param tagId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/diff_insert", method = RequestMethod.POST)
    public Response send(Long defId, Long tagId) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse successResponse = new SuccessResponse();
        MessageDef def = messageDefService.findById(defId);
        if (def == null) {
            successResponse.setStatus(-1);
            successResponse.setMsg("该消息定义不存在");
            return successResponse;
        }
        if (def.getStatus() == 1) {
            successResponse.setStatus(-1);
            successResponse.setMsg("该消息已经发送");
            return successResponse;
        }
        List<String> openids = null;
        int success = 0;
        if (tagId != null && tagId > 0) {
            // 根据用户标签发送消息
            List<Long> list = memberTagRelationService.findByTagId(tagId);
            Long[] memberIds = (Long[]) list.toArray(new Long[list.size()]);
            if (MessageChannel.ALL.equals(def.getChannel())
                    || MessageChannel.WEIXINTOPARTNER.equals(def.getChannel())) {
                openids = partnerService.findOpenIdByMemberId(Arrays.asList(memberIds));
            }
            success = messageService.sendMemberMsg(def, memberIds, admin.getName(), openids);
        } else {
            // 发送全站消息
            if (MessageChannel.ALL.equals(def.getChannel())
                    || MessageChannel.WEIXINTOPARTNER.equals(def.getChannel())) {
                openids = partnerService.findOpenIdByMemberId(null);
            }
            success = messageService.sendGlobalMsg(def, admin.getName(), openids);
        }
        if (success <= 0) {
            successResponse.setStatus(-1);
            successResponse.setMsg("发送失败");
            return successResponse;
        }
        return successResponse;
    }

    /**
     * 我的消息
     *
     * @param searcher
     * @param page
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/mine/list", method = RequestMethod.POST)
    public Response mine(MessageSearcher searcher, PageModel page) throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        PageResult<SearcherMessage> pager = messageSearcherService.searchByType(dto.getMemberId(),
                new long[]{13L, 14L}, page);
        SuccessResponse response = new SuccessResponse(pager);
        return response;
    }

    /**
     * 我的消息数量
     *
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/mine/count", method = RequestMethod.GET)
    public Response count() throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int unreadCount = messageSearcherService.countUnReadByType(dto.getMemberId(), new long[]{13L, 14L});
        result.put("unreadCount", unreadCount);
        return result;
    }

    /**
     * 标记为已读
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public Response read(@PathVariable Long id) throws NotLoginException {
        AdminDto dto = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        messageService.updateStatus(id, dto.getMemberId(), 1);
        return result;
    }

}
