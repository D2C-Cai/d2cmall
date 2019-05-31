package com.d2c.quartz.task.base;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.core.propery.AppProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseTask {

    public static final Log logger = LogFactory.getLog(BaseTask.class);
    @Autowired
    protected AppProperties properties;
    private boolean executed = true;

    public void exec() {
        try {
            if (executed) {
                executed = false;
                logger.info(this.getClass().getName() + " 任务开始...");
                execImpl();
                executed = true;
                logger.info(this.getClass().getName() + " 任务结束...");
            }
        } finally {
            executed = true;
        }
    }

    public abstract void execImpl();

    /**
     * @param pageSize
     * @param each
     */
    protected <O> void processPager(int pageSize, EachPage<O> each) {
        PageModel page = new PageModel(1, pageSize);
        int pageCount = (each.count() + pageSize - 1) / pageSize;
        // 累计错误条数
        int totalError = 0;
        do {
            // 每页具体操作，记录成功条数
            int success = 0;
            PageResult<O> pager = each.search(page);
            for (O object : pager.getList()) {
                try {
                    // 每条具体操作，异常继续执行
                    if (each.each(object)) {
                        success++;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            // 累计错误条数作为起始地址
            totalError = totalError + (pageSize - success);
            page.setOffSet(totalError);
            // 总执行次数-1
            pageCount--;
        } while (pageCount > 0);
    }

}
