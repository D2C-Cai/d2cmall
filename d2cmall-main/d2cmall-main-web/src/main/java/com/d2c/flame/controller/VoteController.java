package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.dto.VoteDefDto;
import com.d2c.content.model.VoteDef;
import com.d2c.content.model.VoteItem;
import com.d2c.content.model.VoteSelection;
import com.d2c.content.service.VoteDefService;
import com.d2c.content.service.VoteItemService;
import com.d2c.content.service.VoteSelectionService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController {

    @Autowired
    private VoteDefService voteDefService;
    @Autowired
    private VoteSelectionService voteSelectionService;
    @Autowired
    private VoteItemService voteItemService;

    /**
     * 投票详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        VoteDefDto voteDefDto = voteDefService.findDtoById(id);
        MemberInfo memberInfo = null;
        try {
            memberInfo = this.getLoginMemberInfo();
        } catch (NotLoginException e) {
        }
        if (memberInfo != null) {
            List<VoteItem> alreadyList = voteItemService.findByDefIdAndMemberId(id, memberInfo.getId());
            model.put("alreadyList", alreadyList);
        } else {
            model.put("alreadyList", null);
        }
        if (voteDefDto == null) {
            throw new BusinessException("投票不存在！");
        }
        if (voteDefDto.getEndDate() != null && voteDefDto.getEndDate().before(new Date())
                && voteDefDto.getStatus() != 2) {
            voteDefDto.setStatus(2);
            voteDefService.updateStatus(id, 2);
        }
        model.put("voteDef", voteDefDto);
        return "";
    }

    /**
     * 投票
     *
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public String insert(Long[] ids, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        List<VoteSelection> list = voteSelectionService.findByIds(ids);
        if (list != null && list.size() > 0) {
            List<VoteItem> alreadyList = voteItemService.findByDefIdAndMemberId(list.get(0).getDefId(),
                    memberInfo.getId());
            if (alreadyList != null && alreadyList.size() > 0) {
                throw new BusinessException("你已经投票过了！");
            }
        }
        VoteDef voteDef = voteDefService.findById(list.get(0).getDefId());
        if (voteDef.getBeginDate() != null && voteDef.getBeginDate().after(new Date())) {
            throw new BusinessException("投票尚未开始！");
        }
        if (voteDef.getEndDate() != null && voteDef.getEndDate().before(new Date())) {
            throw new BusinessException("投票已经结束！");
        }
        for (VoteSelection item : list) {
            VoteItem voteItem = new VoteItem(item);
            voteItem.setMemberId(memberInfo.getId());
            voteItem = voteItemService.insert(voteItem);
        }
        return "";
    }

}
