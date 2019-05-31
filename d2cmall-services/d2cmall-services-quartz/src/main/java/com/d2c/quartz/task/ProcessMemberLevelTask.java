package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberLevel;
import com.d2c.member.service.MemberDetailService;
import com.d2c.member.service.MemberLevelService;
import com.d2c.order.service.OrderItemService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Component
public class ProcessMemberLevelTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberLevelService memberLevelService;
    @Autowired
    private MemberDetailService memberDetailSevice;

    @Scheduled(cron = "0 30 3 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doUpLevel();
        this.doDownLevel();
    }

    /**
     * 升级
     */
    private void doUpLevel() {
        PageModel page = new PageModel();
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int pagerNumber = 1;
        do {
            page.setPageNumber(pagerNumber);
            pager = orderItemService.findDailyFinishAmount(page);
            for (Map<String, Object> map : pager.getList()) {
                try {
                    MemberDetail memberDetail = memberDetailSevice
                            .findByMemberInfoId(Long.parseLong(map.get("memberId").toString()));
                    if (map.get("amount") == null) {
                        continue;
                    }
                    Integer payAmount = new BigDecimal(map.get("amount").toString()).intValue();
                    if (memberDetail != null) {
                        memberDetail.setAdditionalAmount(memberDetail.getAdditionalAmount() + payAmount);
                        // 查找满足当前金额的最高级
                        MemberLevel memberLevel = memberLevelService.findVaildLevel(memberDetail.getAdditionalAmount());
                        // 如果最高级比目前的级别高就更新
                        if (memberLevel != null && memberDetail.getLevel() < memberLevel.getLevel()) {
                            memberDetailSevice.updateLevel(memberDetail.getAdditionalAmount(), memberLevel.getLevel(),
                                    new Date(), memberDetail.getMemberInfoId());
                        } else {
                            memberDetailSevice.updateLevel(memberDetail.getAdditionalAmount(), memberDetail.getLevel(),
                                    memberDetail.getUpgradeDate(), memberDetail.getMemberInfoId());
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount());
    }

    /**
     * 满365天，重新计算等级
     */
    private void doDownLevel() {
        try {
            this.processPager(500, new EachPage<MemberDetail>() {
                @Override
                public int count() {
                    return memberDetailSevice.countExpireMember();
                }

                @Override
                public PageResult<MemberDetail> search(PageModel page) {
                    return memberDetailSevice.findExpireMember(page);
                }

                @Override
                public boolean each(MemberDetail object) {
                    MemberLevel memberLevel = memberLevelService.findByLevel(object.getLevel());
                    object.setAdditionalAmount(
                            object.getAdditionalAmount().intValue() - memberLevel.getDeduction().intValue());
                    MemberLevel newMemberLevel = memberLevelService.findVaildLevel(object.getAdditionalAmount());
                    int result = memberDetailSevice.updateLevel(object.getAdditionalAmount(), newMemberLevel.getLevel(),
                            new Date(), object.getMemberInfoId());
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
