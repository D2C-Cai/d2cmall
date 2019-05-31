package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Partner;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.PartnerService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessPartnerLevelTask extends BaseTask {

    @Autowired
    private PartnerService partnerService;

    @Scheduled(cron = "0 30 4 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doUpLevel2to1();
        this.doUpLevel1to0();
    }

    private void doUpLevel2to1() {
        // 1.销售30个开店礼包
        try {
            PartnerSearcher searcher = new PartnerSearcher();
            searcher.setLevel(2);
            searcher.setStatus(1);
            this.processPager(500, new EachPage<Partner>() {
                @Override
                public int count() {
                    return partnerService.countBySearcher(searcher);
                }

                @Override
                public PageResult<Partner> search(PageModel page) {
                    return partnerService.findBySearcher(searcher, page);
                }

                @Override
                public boolean each(Partner object) {
                    if (object.getGiftCount() >= 30 && object.getUpgrade() == 1) {
                        int result = partnerService.updateLevel(object.getId(), 1, "定时器");
                        return result > 0 ? true : false;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doUpLevel1to0() {
        // 1.销售750个开店礼包 2.邀请的DM大于等于2个
        try {
            PartnerSearcher searcher = new PartnerSearcher();
            searcher.setLevel(1);
            searcher.setStatus(1);
            this.processPager(500, new EachPage<Partner>() {
                @Override
                public int count() {
                    return partnerService.countBySearcher(searcher);
                }

                @Override
                public PageResult<Partner> search(PageModel page) {
                    return partnerService.findBySearcher(searcher, page);
                }

                @Override
                public boolean each(Partner object) {
                    PartnerSearcher searcher = new PartnerSearcher();
                    searcher.setLevel(1);
                    searcher.setStatus(1);
                    searcher.setParentId(object.getId());
                    if (object.getGiftCount() >= 750 && partnerService.countBySearcher(searcher) >= 2
                            && object.getUpgrade() == 1) {
                        int result = partnerService.updateLevel(object.getId(), 0, "定时器");
                        return result > 0 ? true : false;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    // private void doDownLevel() {
    // // 1.销售30个开店礼包 2.累计成交金额满50000元
    // try {
    // PartnerSearcher searcher = new PartnerSearcher();
    // searcher.setLevel(1);
    // Date deadline = DateUtil.getIntervalDay(new Date(), -30);
    // searcher.setCreateDateEnd(deadline);
    // this.processPager(500, new EachPage<Partner>() {
    //
    // @Override
    // public int count() {
    // return partnerService.countBySearcher(searcher);
    // }
    //
    // @Override
    // public PageResult<Partner> search(PageModel page) {
    // return partnerService.findBySearcher(searcher, page);
    // }
    //
    // @Override
    // public boolean each(Partner object) {
    // if (object.getTotalOrderAmount().compareTo(new BigDecimal(50000)) < 0
    // && object.getGiftCount() < 30) {
    // int result = partnerService.updateLevel(object.getId(), 2, "定时器");
    // return result > 0 ? true : false;
    // }
    // return false;
    // }
    // });
    // } catch (Exception e) {
    // logger.error(e.getMessage(), e);
    // }
    // }
}
