package com.d2c.member.mongo.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.member.enums.MemberTaskAfterEnum;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.enums.MemberTaskTypeEnum;
import com.d2c.member.model.MemberTask;
import com.d2c.member.mongo.dao.MemberTaskExecMongoDao;
import com.d2c.member.mongo.enums.MemberTaskState;
import com.d2c.member.mongo.model.MemberTaskExecDO;
import com.d2c.member.service.MemberIntegrationService;
import com.d2c.member.service.MemberTaskService;
import com.d2c.member.view.MemberTaskVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户任务执行情况
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class MemberTaskExecServiceImpl extends ListMongoDao<MemberTaskExecDO> implements MemberTaskExecService {

    @Autowired
    private MemberTaskExecMongoDao memberTaskExecMongoDao;
    @Autowired
    private MemberTaskService memberTaskService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;

    /**
     * 完成一次我的任务
     */
    public MemberTaskExecDO taskDone(Long memberId, MemberTaskEnum taskEnum) {
        return taskDone(memberId, taskEnum.name(), null);
    }

    public MemberTaskExecDO taskDone(Long memberId, String codeType, String subCode) {
        AssertUt.notNull(memberId);
        String code = MemberTask.buildCode(codeType, subCode);
        AssertUt.notBlank(code, "任务编码不能为空");
        MemberTask task = memberTaskService.findByCode(code);
        if (task == null) {
            // 任务不存在，请检查
            return null;
        }
        if (!task.getExec()) { // 未启动任务不执行
            return null;
        }
        // 日常任务，直接发放奖励
        if (MemberTaskTypeEnum.isCommonType(task.getType())) {
            doAddAward(memberId, task);
            MemberTaskExecDO item = new MemberTaskExecDO(memberId, task);
            item.add();
            return item;
        }
        MemberTaskExecDO item = memberTaskExecMongoDao.findOne(memberId, code);
        if (item == null) {
            item = new MemberTaskExecDO(memberId, task);
        } else {
            item.syncTask(task);
            if (item.isDone()) {
                return item;
            }
        }
        item.add();
        memberTaskExecMongoDao.save(item);
        return item;
    }

    /**
     * 领取任务奖励
     */
    public String addTaskAward(Long memberId, String code) {
        AssertUt.notNull(memberId);
        AssertUt.notBlank(code, "任务编码不能为空");
        MemberTask task = memberTaskService.findByCode(code);
        AssertUt.notNull(task, "任务不存在");
        MemberTaskExecDO item = memberTaskExecMongoDao.findOne(memberId, code);
        AssertUt.notNull(item, "任务未完成");
        AssertUt.isTrue(item.isDone(), "任务未完成");
        AssertUt.isTrue(!item.isAward(), "已领取奖励");
        doAddAward(memberId, task);
        item.setState(MemberTaskState.DONE_TASK.name());
        memberTaskExecMongoDao.save(item);
        return task.getSubName();
    }

    private void doAddAward(Long memberId, MemberTask task) {
        switch (MemberTaskAfterEnum.getValueOf(task.getDoAfter())) {
            case INTEGRATION:
                memberIntegrationService.addTaskPoint(memberId, task);
                break;
            case LOTTERY_CHANCE:
                break;
            default:
                throw new BusinessException("奖励类型不存在");
        }
    }

    /**
     * 用户每日任务刷新
     */
    public long refreshOnDayTask() {
        return memberTaskExecMongoDao.updateDayTask();
    }

    /**
     * 我的任务列表
     */
    public List<MemberTaskVO> findTaskList(Long memberId) {
        List<MemberTask> list = memberTaskService.findTaskList();
        List<MemberTaskExecDO> mys = memberTaskExecMongoDao.findList(memberId);
        List<MemberTaskVO> vl = new ArrayList<>();
        list.forEach(task -> {
            MemberTaskExecDO item = selectTask(mys, task.getCode());
            vl.add(toView(task, item));
        });
        return vl;
    }

    /**
     * 待领取任务数量
     */
    @CacheMethod(min = 5)
    public long countAwardState(Long memberId) {
        return memberTaskExecMongoDao.countAwardState(memberId);
    }

    private MemberTaskVO toView(MemberTask task, MemberTaskExecDO item) {
        MemberTaskVO vo = new MemberTaskVO();
        return BeanUt.applys(vo, item, task);
    }

    private MemberTaskExecDO selectTask(List<MemberTaskExecDO> list, String code) {
        if (list == null)
            return null;
        for (MemberTaskExecDO bean : list) {
            if (bean.getCode().equals(code)) {
                return bean;
            }
        }
        return null;
    }

}
