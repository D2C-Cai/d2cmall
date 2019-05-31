package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.query.RemindSearcher;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RemindService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoticeProductArrivalTask extends BaseTask {

    @Autowired
    private RemindService remindService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Scheduled(cron = "0 0 11 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.processRemind();
    }

    private void processRemind() {
        List<Long> list = remindService.findSourceIdByType(RemindType.ARRIVAL.name(), 0);
        if (list != null && list.size() > 0) {
            List<String> productIds = list.stream().map(id -> String.valueOf(id)).collect(Collectors.toList());
            ProductProSearchQuery searcher = new ProductProSearchQuery();
            searcher.setStore(1);
            searcher.setProductIds(productIds);
            PageResult<SearcherProduct> products = productSearcherQueryService.search(searcher,
                    new PageModel(1, productIds.size()));
            for (SearcherProduct product : products.getList()) {
                RemindSearcher remindSearch = new RemindSearcher();
                remindSearch.setSourceId(product.getId());
                remindSearch.setType(RemindType.ARRIVAL.name());
                remindSearch.setSmsSend(false);
                try {
                    this.processPager(100, new EachPage<Remind>() {
                        @Override
                        public int count() {
                            return remindService.countBySearcher(remindSearch);
                        }

                        @Override
                        public PageResult<Remind> search(PageModel page) {
                            return remindService.findBySearcher(remindSearch, page);
                        }

                        @Override
                        public boolean each(Remind object) {
                            if (object.getMemberId() != null && object.getMemberId() >= 0) {
                                String subject = "到货提醒";
                                String content = "您好，" + product.getName() + "已经到货，快来抢购！";
                                PushBean pushBean = new PushBean(object.getMemberId(), content, 52);
                                pushBean.setAppUrl("/detail/product/" + product.getId());
                                MsgBean msgBean = new MsgBean(object.getMemberId(), 52, subject, content);
                                msgBean.setPic(product.getProductImageCover());
                                msgBean.setAppUrl("/detail/product/" + product.getId());
                                msgUniteService.sendMsg(null, pushBean, msgBean, null);
                                object.setSmsSend(true);
                                object.setSmsSendDate(new Date());
                                int result = remindService.update(object);
                                return result > 0 ? true : false;
                            }
                            return false;
                        }
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

}
