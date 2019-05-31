package com.d2c.common.mongodb.report;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.common.mongodb.enums.ReportStatus;
import com.d2c.common.mongodb.model.ReportMongoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

/**
 * 任务执行报告代理
 *
 * @author wull
 */
public abstract class ReportProxy<T extends ReportMongoDO> {

    private static final int DEFAULT_SAVE_LIMIT = 1000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected T report;
    protected MongoTemplate mongoTemplate;
    protected int i = 0;

    public ReportProxy(T report) {
        this.report = report;
        mongoTemplate = SpringHelper.getBean(MongoTemplate.class);
    }

    /**
     * 获取报告名称
     */
    protected abstract String getName();

    /**
     * 任务开始执行时操作
     */
    protected void doStart() {
    }

    ;

    protected void doEnd() {
    }

    ;
    //*******************************************

    /**
     * 任务开始
     */
    public synchronized void start() {
        doStart();
        debug("任务开始...");
    }

    /**
     * 添加数据
     *
     * @return 是否已保存
     */
    public synchronized boolean addOne() {
        report.add();
        if (isSave()) {
            debug(report.toString());
            return true;
        }
        return false;
    }

    /**
     * 任务执行结束
     */
    public synchronized void end() {
        doEnd();
        report.setStatus(ReportStatus.END.name());
        debug("任务执行结束...");
    }

    /**
     * 数据保存
     */
    public synchronized void save() {
        Date date = new Date();
        report.setGmtModified(date);
        if (report.getGmtCreate() == null) {
            report.setName(getName() + "_" + DateUt.minute2str(date));
            report.setGmtCreate(date);
        }
        mongoTemplate.save(report);
    }

    /**
     * 断点续传
     */
    public synchronized void restart(long count) {
        report.restart(count);
        debug("断点续传, 从第" + count + "条继续执行...");
    }

    public synchronized void debug(String msg) {
        save();
        logger.info(report.showKeepTime() + " " + report.getName() + " : " + msg);
    }

    /**
     * 数据异常
     */
    public synchronized void error(String msg, Exception e) {
        save();
        report.addError(msg);
        logger.error(report.showKeepTime() + " " + report.getName() + "，第" + report.getCount() + "条执行异常:" + msg, e);
    }

    /**
     * 当数据增加时，数据是否保存, 默认 5000 条数据后自动保存
     */
    protected boolean isSave() {
        i++;
        if (i >= getSaveLimit()) {
            i = 0;
            return true;
        }
        return false;
    }

    protected int getSaveLimit() {
        return DEFAULT_SAVE_LIMIT;
    }
    //********************************************

    public T getReport() {
        return report;
    }

    public String getReportId() {
        return report.getId();
    }

}
