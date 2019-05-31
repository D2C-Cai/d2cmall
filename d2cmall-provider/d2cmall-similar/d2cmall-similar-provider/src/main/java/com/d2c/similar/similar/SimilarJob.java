package com.d2c.similar.similar;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.similar.entity.SimilarSchemeDO;
import com.d2c.similar.helper.SimilarHelper;
import com.d2c.similar.mongo.dao.SimilarReportMongoDao;
import com.d2c.similar.mongo.model.SimilarReportDO;
import com.d2c.similar.similar.resolver.SimilarResolver;

import java.util.Date;
import java.util.List;

public class SimilarJob {
//	private static final int REBUILD_DAY = 120;
    private SimilarSchemeDO scheme;
    private SimilarResolver rsv;
    private SimilarReportProxy report;
    private SimilarReportDO lastReport;
    private SimilarHandler handler;
    private ProductSearcherQueryService service;
    private SimilarReportMongoDao similarReportMongoDao;
    private List<? extends Object> tgList;

    public SimilarJob(SimilarHandler handler, ProductSearcherQueryService serv) {
        this.handler = handler;
        this.service = serv;
        similarReportMongoDao = SpringHelper.getBean(SimilarReportMongoDao.class);
    }

    /**
     * 根据方案执行相似度对比
     */
    public SimilarReportDO similar(SimilarSchemeDO scheme) {
        this.scheme = scheme;
        this.rsv = handler.buildResolver(scheme.getId());
        //尝试恢复上一次任务
        SimilarReportDO rp = similarReportMongoDao.findLastUnDoneReport(scheme.getId());
        if (rp != null) {
            report = new SimilarReportProxy(rp);
        } else {
            report = new SimilarReportProxy();
            report.start(scheme);
        }
        //判断方案是否被修改并执行
        if (needRebuild()) {
            similarAll();
            report.similarAllEnd();
            handler.doAfterJob(scheme.getId());
        } else {
            updateSimilar();
        }
        report.end();
        return report.getReport();
    }

    /**
     * 是否需要全部重建
     */
    private boolean needRebuild() {
        //最后一次完成的任务
        lastReport = handler.findLastReport(scheme.getId());
//		Date last = lastReport.getLastRebuildDate();
        // 每隔一段时间重新完全更新一次
//		return last == null || DateUt.fromDays(last) > REBUILD_DAY || !scheme.getHasExec();
        return !scheme.getHasExec();
    }
    // *************************************************

    /**
     * 根据方案执行相似度对比
     */
    private void similarAll() {
        SimilarBucket bucket = new SimilarBucket(service, scheme, null);
        if (report.isRestart()) {
            bucket.setStart(report.getReport().getBeanCount() - 1);
            report.restart(bucket.getCount());
        }
        while (bucket.hasNext()) {
            Object bean = bucket.next();
            try {
                report.addOne(findId(bean), similarOne(bean));
            } catch (Exception e) {
                report.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 单个商品相似度计算 计算商品和所有该类目下的所有商品
     */
    private int similarOne(Object bean) {
        if (tgList == null) {
            tgList = service.findSimilarTargets(scheme.getCategoryId().longValue(), 1, scheme.getMaxSize());
            report.debug("相似度目标列表查询完成..." + scheme.getMaxSize());
        }
        return handler.similarOneImpl(rsv, scheme, bean, tgList, true);
    }
    // *********************** update 更新商品相似度  *************************

    /**
     * 方案未修改, 根据修改的商品重新计算相似度
     */
    private void updateSimilar() {
        Date lastDate;
        if (lastReport != null) {
            lastDate = lastReport.getGmtCreate();
        } else {
            lastDate = DateUt.dayAdd(-7);
        }
        SimilarBucket bucket = new SimilarBucket(service, scheme, null);
        bucket.setLastDate(lastDate);
        report.initUpdate(lastDate);
        while (bucket.hasNext()) {
            SearcherProduct bean = bucket.next();
            try {
                int count = 0;
                if (bean.isNotShow()) {
                    count = deleteSimilarOne(bean);
                } else {
                    count = updateSimilarOne(bean);
                }
                report.updateOne(findId(bean), count);
            } catch (Exception e) {
                report.error(e.getMessage(), e);
            }
        }
    }

    private int updateSimilarOne(Object bean) {
        similarOne(bean);
        return handler.updateSimilarOneImpl(rsv, scheme, bean);
    }

    private int deleteSimilarOne(Object bean) {
        return handler.deleteSimilarByBeanId(findId(bean));
    }
    // *********************** private 更新商品相似度  *************************

    private Object findId(Object bean) {
        return SimilarHelper.findId(bean);
    }

}
