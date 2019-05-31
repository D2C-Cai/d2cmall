package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ShareTaskDefDto;
import com.d2c.content.model.ShareTask;
import com.d2c.content.model.ShareTaskClick;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskDefSearcher;
import com.d2c.content.query.ShareTaskSearcher;
import com.d2c.content.service.ShareTaskClickService;
import com.d2c.content.service.ShareTaskDefService;
import com.d2c.content.service.ShareTaskService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/shareTask")
public class ShareTaskController extends BaseController {

    @Autowired
    private ShareTaskDefService shareTaskDefService;
    @Autowired
    private ShareTaskService shareTaskService;
    @Autowired
    private ShareTaskClickService shareTaskClickService;

    /**
     * 我的任务列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model, PageModel page) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ShareTaskDefSearcher searcher = new ShareTaskDefSearcher();
        searcher.setOrderByStr("p.start_time DESC,p.create_date DESC");
        searcher.setGtStatus(0);
        searcher.setLtStartTime(new Date());
        PageResult<ShareTaskDefDto> pager = shareTaskDefService.findBySearch(searcher, page);
        ShareTaskSearcher shareTaskSearcher = new ShareTaskSearcher();
        shareTaskSearcher.setMemberId(memberInfo.getId());
        // 检查会员已经领取过的任务
        for (ShareTaskDefDto def : pager.getList()) {
            shareTaskSearcher.setTaskDefId(def.getId());
            if (shareTaskService.findByMemberIdAndTaskDef(memberInfo.getId(), def.getId()) != null) {
                def.setClaimed(true);
            }
        }
        model.put("pager", pager);
        return "/member/my_share_list";
    }

    /**
     * 任务详情
     *
     * @param model
     * @param defId
     * @return
     */
    @RequestMapping(value = "/detail/{defId}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable Long defId) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ShareTaskDef shareTaskDef = shareTaskDefService.findById(defId);
        if (shareTaskDef != null) {
            if (shareTaskService.updateClick(memberInfo.getId(), defId) <= 0) {
                ShareTask shareTask = new ShareTask();
                shareTask.setTaskDefId(shareTaskDef.getId());
                shareTask.setSn(shareTaskDef.getSn());
                shareTask.setTitle(shareTaskDef.getTitle());
                shareTask.setMemberId(memberInfo.getId());
                shareTask.setMemberName(memberInfo.getLoginCode());
                shareTask.setMaxPoint(shareTaskDef.getMemberPoint());
                shareTask.setClickPoint(shareTaskDef.getClickPoint());
                shareTaskService.insert(shareTask);
            }
            ShareTask shareTask = shareTaskService.findByMemberIdAndTaskDef(memberInfo.getId(), defId);
            model.put("shareTaskDef", shareTaskDef);
            model.put("shareTask", shareTask);
        }
        return "/member/my_share_detail";
    }

    /**
     * 任务分享出去后，用户点击的回调页面
     */
    @RequestMapping(value = "/view/{defId}", method = RequestMethod.GET)
    public String clickDef(@PathVariable("defId") Long defId, ModelMap model) {
        return doClickTask(defId, null, model);
    }

    /**
     * 任务分享出去后，用户点击的回调页面
     */
    @RequestMapping(value = "/view/{defId}/{parentTaskId}", method = RequestMethod.GET)
    public String clickDef(@PathVariable("defId") Long defId, @PathVariable("parentTaskId") Long parentTaskId,
                           ModelMap model) {
        return doClickTask(defId, parentTaskId, model);
    }

    private String doClickTask(Long defId, Long parentTaskId, ModelMap model) {
        ShareTaskDef shareTaskDef = shareTaskDefService.findById(defId);
        if (shareTaskDef == null) {
            return "return:/";
        }
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ShareTask shareTask = null;
        if (shareTaskService.updateClick(memberInfo.getId(), defId) <= 0) {
            shareTask = new ShareTask();
            shareTask.setTaskDefId(shareTaskDef.getId());
            shareTask.setSn(shareTaskDef.getSn());
            shareTask.setUrl(shareTaskDef.getUrl());
            shareTask.setTitle(shareTaskDef.getTitle());
            shareTask.setParentTaskId(parentTaskId);
            shareTask.setMemberId(memberInfo.getId());
            shareTask.setMemberName(memberInfo.getLoginCode());
            shareTask.setMaxPoint(shareTaskDef.getMemberPoint());
            shareTask.setClickPoint(shareTaskDef.getClickPoint());
            shareTask = shareTaskService.insert(shareTask);
        } else {
            shareTask = shareTaskService.findByMemberIdAndTaskDef(memberInfo.getId(), defId);
            shareTask.setUrl(shareTaskDef.getUrl());
        }
        ShareTaskClick click = new ShareTaskClick();
        click.setIp(getLoginIp());
        if (isNormalDevice()) {
            click.setDevice(DeviceTypeEnum.PC.toString());
        } else if (isTabletDevice()) {
            click.setDevice(DeviceTypeEnum.TABLET.toString());
        } else {
            click.setDevice(DeviceTypeEnum.MOBILE.toString());
        }
        click.setRefer(getRequest().getHeader("Referer"));
        shareTaskClickService.doClick(click);
        if (StringUtils.isEmpty(shareTask.getUrl())) {
            model.put("task", shareTask);
            return "page/share_click";
        } else {
            return "redirect:" + shareTask.getUrl() + "/" + defId + "/" + shareTask.getId();
        }
    }

    /**
     * 任务分享出去后，用户点击的回调页面
     */
    @RequestMapping(value = "/shared/{defId}/{shareTaskId}", method = RequestMethod.GET)
    public String click(@PathVariable("defId") Long defId, @PathVariable("shareTaskId") Long shareTaskId,
                        ModelMap model) {
        this.shareTaskService.doShare(defId, shareTaskId);
        ShareTaskDef shareTaskDef = shareTaskDefService.findById(defId);
        ShareTask shareTask = shareTaskService.findByMemberIdAndTaskDef(this.getLoginMemberInfo().getId(), defId);
        shareTask.setUrl(shareTaskDef.getUrl());
        if (StringUtils.isEmpty(shareTask.getUrl())) {
            model.put("task", shareTask);
            return "page/share_click";
        } else {
            return "redirect:" + shareTask.getUrl() + "/" + defId + "/" + shareTask.getId();
        }
    }

}
