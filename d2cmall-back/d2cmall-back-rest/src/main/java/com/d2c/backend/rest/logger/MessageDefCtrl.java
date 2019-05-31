package com.d2c.backend.rest.logger;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageDefSearcher;
import com.d2c.logger.service.MessageDefService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/msg/messagedef")
public class MessageDefCtrl extends BaseCtrl<MessageDefSearcher> {

    @Autowired
    private MessageDefService messageDefService;

    @Override
    protected List<Map<String, Object>> getRow(MessageDefSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(MessageDefSearcher searcher) {
        return 0;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response doHelp(MessageDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<HelpDTO> dtos = messageDefService.findHelpDtosBySearch(page, searcher);
        SuccessResponse response = new SuccessResponse(dtos);
        return response;
    }

    @Override
    protected Response doList(MessageDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MessageDef> pager = messageDefService.findBySearch(page, searcher);
        SuccessResponse response = new SuccessResponse(pager);
        return response;
    }

    @Override
    protected Response findById(Long id) {
        MessageDef md = messageDefService.findById(id);
        SuccessResponse response = new SuccessResponse();
        response.put("messageDef", md);
        return response;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        BeanUt.trimString(data);
        MessageDef def = JsonUtil.instance().toObject(data, MessageDef.class);
        Admin admin = getLoginedAdmin();
        def.setCreator(admin.getName());
        def = messageDefService.insert(def);
        SuccessResponse response = new SuccessResponse();
        response.put("messageDef", def);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        BeanUt.trimString(data);
        MessageDef def = JsonUtil.instance().toObject(data, MessageDef.class);
        messageDefService.update(def);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Response getDef(Long[] id) throws NotLoginException {
        String ids = "";
        for (Long memberId : id) {
            ids += memberId + ",";
        }
        MessageDefSearcher searcher = new MessageDefSearcher();
        searcher.setGlobal(0);
        searcher.setOverTime(false);
        PageModel page = new PageModel();
        PageResult<MessageDef> pager = messageDefService.findBySearch(page, searcher);
        SuccessResponse response = new SuccessResponse();
        response.put("ids", ids.substring(0, ids.length() - 1));
        response.put("defs", pager.getList());
        return response;
    }

}
