package com.d2c.backend.rest.base.excel;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.common.core.excel.ExcelColumn;
import com.d2c.common.core.excel.ListExcel;
import com.d2c.order.mongo.model.BargainHelpDO;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.service.BargainPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@Controller
public class BargainHelpExcel extends ListExcel<BargainHelpDO> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BargainPriceService bargainPriceService;

    public void excelReport() {
        PageBucket<BargainHelpDO> bucket = new PageBucket<BargainHelpDO>(50000, 50) {
            List<BargainPriceDO> list;

            @Override
            public List<BargainHelpDO> nextList(int page, int pageSize) {
                List<BargainHelpDO> helpers = new ArrayList<>();
                list = bargainPriceService.findPage(new PageModel(page, pageSize));
                logger.info("BargainHelpDO list:" + helpers.size() + this);
                return helpers;
            }

            public boolean hasNext() {
                if (maxSize != null && maxSize > 0 && count >= maxSize) {
                    return false;
                }
                while (it == null || !it.hasNext()) {
                    page = page + 1;
                    it = nextList(page, pageSize).listIterator();
                    if (list.isEmpty()) {
                        page--;
                        return false;
                    }
                }
                return true;
            }

            public void setStartPage(int startPage) {
                if (startPage <= 1) {
                    page = 0;
                } else {
                    page = startPage - 1;
                }
                count = 0;
                it = null;
            }
        };
        bucket.setStartPage(291);
        excel(bucket);
    }

    @Override
    protected List<ExcelColumn> initColumn() {
        List<ExcelColumn> list = new ArrayList<ExcelColumn>();
        list.add(new ExcelColumn("bargainId", "砍价用户活动ID"));
        list.add(new ExcelColumn("helpMemberId", "帮砍会员ID"));
        list.add(new ExcelColumn("userName", "用户名称"));
        list.add(new ExcelColumn("subPrice", "帮砍价格"));
        list.add(new ExcelColumn("helpDate", "帮砍时间"));
        return list;
    }

    @Override
    public String getTitle() {
        return "帮砍用户数据表";
    }

    @Override
    public String getFileName() {
        return "BargainHelp";
    }

}
