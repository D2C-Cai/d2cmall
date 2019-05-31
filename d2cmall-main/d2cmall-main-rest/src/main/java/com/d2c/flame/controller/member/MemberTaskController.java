package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.ListUt;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberTask;
import com.d2c.member.mongo.model.MemberTaskExecDO;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.service.MemberTaskService;
import com.d2c.member.view.MemberTaskVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户任务
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/member/task")
public class MemberTaskController extends BaseController {

    @Reference
    private MemberTaskExecService memberTaskExecService;
    @Reference
    private MemberTaskService memberTaskService;

    /**
     * 根据code查用户任务
     * <p> API: v3/api/member/task/find/{codeType}
     */
    @RequestMapping(value = "/find/{codeType}", method = RequestMethod.GET)
    public Response findTaskByCode(@PathVariable String codeType) {
        MemberTask bean = memberTaskService.findByCode(codeType);
        return ResultHandler.success(bean);
    }

    /**
     * 红点:待领取任务数量
     * <p> API: v3/api/member/task/count/award
     */
    @RequestMapping(value = "/count/award", method = RequestMethod.GET)
    public Response countTask() {
        MemberInfo member = this.getLoginMemberInfo();
        long count = memberTaskExecService.countAwardState(member.getId());
        return ResultHandler.success(count);
    }

    /**
     * 执行任务一次
     * <p> API: v3/api/member/task/done/{codeType}
     */
    @RequestMapping(value = "/done/{codeType}", method = RequestMethod.GET)
    public Response taskDone(@PathVariable String codeType, String subCode) {
        MemberInfo member = this.getLoginMemberInfo();
        MemberTaskExecDO bean = memberTaskExecService.taskDone(member.getId(), codeType, subCode);
        return ResultHandler.success(bean);
    }

    /**
     * 任务完成领取奖励
     * <p> API: v3/api/member/task/award/{code}
     */
    @RequestMapping(value = "/award/{code}", method = RequestMethod.GET)
    public Response addTaskAward(@PathVariable String code) {
        MemberInfo member = this.getLoginMemberInfo();
        String res = memberTaskExecService.addTaskAward(member.getId(), code);
        return ResultHandler.success(res);
    }

    /**
     * 我的任务
     * <p> API: v3/api/member/task/list
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response taskList() {
        MemberInfo member = this.getLoginMemberInfo();
        List<MemberTaskVO> list = memberTaskExecService.findTaskList(member.getId());
        return ResultHandler.success(ListUt.groupToMap(list, "type"));
    }

    /**
     * 测试每日更新
     * <p> API: v3/api/member/task/refresh/day
     */
    @RequestMapping(value = "/refresh/day", method = RequestMethod.GET)
    public Response refreshDay() {
        long res = memberTaskExecService.refreshOnDayTask();
        return ResultHandler.success(res);
    }

}
