package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.ShareTaskDefDto;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskDefSearcher;
import com.d2c.content.service.ShareTaskDefService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.serial.SerialNumUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/sharetaskdef")
public class ShareTaskDefCtrl extends BaseCtrl<ShareTaskDefSearcher> {

    @Autowired
    private ShareTaskDefService shareTaskDefService;

    @Override
    protected List<Map<String, Object>> getRow(ShareTaskDefSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(ShareTaskDefSearcher searcher) {
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
    protected Response doHelp(ShareTaskDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<HelpDTO> dtos = shareTaskDefService.findHelpDtosBySearch(searcher, page);
        return new SuccessResponse(dtos);
    }

    @Override
    protected Response doList(ShareTaskDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ShareTaskDefDto> pager = shareTaskDefService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ShareTaskDef shareTaskDef = shareTaskDefService.findById(id);
        result.put("shareTaskDef", shareTaskDef);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        if (ids == null || ids.length <= 0) {
            return new ErrorResponse("请输入正确的id");
        }
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            shareTaskDefService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        if (id == null) {
            return new ErrorResponse("请输入正确的id");
        }
        shareTaskDefService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        ShareTaskDef shareTaskDef = JsonUtil.instance().toObject(data, ShareTaskDef.class);
        BeanUt.trimString(shareTaskDef);
        shareTaskDef.setSn(SerialNumUtil.buildShareTaskSn());
        shareTaskDef = shareTaskDefService.insert(shareTaskDef);
        SuccessResponse response = new SuccessResponse();
        response.put("sharetaskdef", shareTaskDef);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        ShareTaskDef shareTaskDef = JsonUtil.instance().toObject(data, ShareTaskDef.class);
        BeanUt.trimString(shareTaskDef);
        if (shareTaskDef.getId() == null && id == null) {
            return new ErrorResponse("请确认是否带有实体id参数");
        }
        shareTaskDefService.update(shareTaskDef);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 批量发布分享任务定义
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Response doPublish(Long[] ids) {
        SuccessResponse successResponse = new SuccessResponse();
        String msg = "";
        for (Long id : ids) {
            ShareTaskDef shareTaskDef = shareTaskDefService.findById(id);
            if (shareTaskDef == null)
                continue;
            if (shareTaskDef != null && shareTaskDef.getStatus() != -1) {
                shareTaskDefService.doPublish(id);
            } else {
                successResponse.setStatus(-1);
                msg += shareTaskDef.getTitle() + ",";
            }
        }
        if (!StringUtil.isBlank(msg)) {
            msg += " 已归档，启用失败";
            successResponse.setMsg(msg);
        }
        return successResponse;
    }

    /**
     * 批量关闭分享任务定义
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/doClose", method = RequestMethod.POST)
    public Response doClose(Long[] ids) {
        for (Long id : ids) {
            shareTaskDefService.doClose(id);
        }
        return new SuccessResponse();
    }

}
