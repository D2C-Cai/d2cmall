package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.PromotionSearcher;
import com.d2c.product.service.PromotionService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredPromotionTask extends BaseTask {

    @Autowired
    private PromotionService promotionService;

    @Scheduled(fixedDelay = 60 * 1000 * 60)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.autoDownPromotion();
        this.autoUpPromotion();
    }

    /**
     * 到期活动自动上线
     */
    private void autoUpPromotion() {
        PromotionSearcher searcher = new PromotionSearcher();
        searcher.setEnable(false);
        searcher.setTiming(1);
        Date date = new Date();
        searcher.setBeginStartTime(date);
        searcher.setEndEndTime(date);
        try {
            this.processPager(100, new EachPage<Promotion>() {
                @Override
                public int count() {
                    return promotionService.countBySearcher(searcher);
                }

                @Override
                public PageResult<Promotion> search(PageModel page) {
                    return promotionService.findBySearcher(searcher, page);
                }

                @Override
                public boolean each(Promotion object) {
                    int result = promotionService.doMark(true, object.getId());
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 过期活动自动下线
     */
    private void autoDownPromotion() {
        PromotionSearcher searcher = new PromotionSearcher();
        searcher.setEndEndTime(new Date());
        searcher.setEnable(true);
        try {
            this.processPager(100, new EachPage<Promotion>() {
                @Override
                public int count() {
                    return promotionService.countBySearcher(searcher);
                }

                @Override
                public PageResult<Promotion> search(PageModel page) {
                    return promotionService.findBySearcher(searcher, page);
                }

                @Override
                public boolean each(Promotion object) {
                    int result = promotionService.doMark(false, object.getId());
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
