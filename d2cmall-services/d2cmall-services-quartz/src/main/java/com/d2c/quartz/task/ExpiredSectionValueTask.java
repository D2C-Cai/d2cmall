package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.service.SectionValueService;
import com.d2c.content.service.SubModuleService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class ExpiredSectionValueTask extends BaseTask {

    private static final long FIVE_MINUTES = 5 * 60 * 1000;
    @Autowired
    private SectionValueService sectionValueService;
    @Autowired
    private SubModuleService subModuleService;

    @Scheduled(fixedDelay = 60 * 1000 * 5)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        Date beginDate = new Date(new Date().getTime() - FIVE_MINUTES);
        Date endDate = new Date();
        Set<Long> defId1 = processTimingUp(beginDate, endDate);
        Set<Long> defId2 = processTimingDown(beginDate, endDate);
        defId1.addAll(defId2);
        processPublic(defId1);
    }

    private Set<Long> processTimingUp(Date beginDate, Date endDate) {
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setBeginStartTime(beginDate);
        searcher.setEndStartTime(endDate);
        searcher.setStatus(0);
        searcher.setTiming(1);
        Set<Long> defId = new HashSet<>();
        this.processPager(100, new EachPage<SectionValue>() {
            @Override
            public int count() {
                return sectionValueService.countBySearcher(searcher);
            }

            @Override
            public PageResult<SectionValue> search(PageModel page) {
                return sectionValueService.findBySearcher(searcher, page);
            }

            @Override
            public boolean each(SectionValue object) {
                int result = sectionValueService.updateStatus(object.getId(), 1);
                defId.add(object.getSectionDefId());
                return result > 0 ? true : false;
            }
        });
        return defId;
    }

    private Set<Long> processTimingDown(Date beginDate, Date endDate) {
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setBeginEndTime(beginDate);
        searcher.setEndEndTime(endDate);
        searcher.setStatus(1);
        searcher.setTiming(1);
        Set<Long> defId = new HashSet<>();
        this.processPager(100, new EachPage<SectionValue>() {
            @Override
            public int count() {
                return sectionValueService.countBySearcher(searcher);
            }

            @Override
            public PageResult<SectionValue> search(PageModel page) {
                return sectionValueService.findBySearcher(searcher, page);
            }

            @Override
            public boolean each(SectionValue object) {
                int result = sectionValueService.updateStatus(object.getId(), 0);
                defId.add(object.getSectionDefId());
                return result > 0 ? true : false;
            }
        });
        return defId;
    }

    private void processPublic(Set<Long> defIds) {
        for (Long sectionId : defIds) {
            subModuleService.doPublishSection(sectionId);
        }
    }

}
