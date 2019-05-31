package com.d2c.backend.rest.base.excel;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.common.core.excel.ExcelColumn;
import com.d2c.common.core.excel.ListExcel;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.service.BargainPriceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@Controller
public class BargainPriceExcel extends ListExcel<BargainPriceDO> {

    @Autowired
    private BargainPriceService bargainPriceService;

    public void excelReport() {
        PageBucket<BargainPriceDO> bucket = new PageBucket<BargainPriceDO>(500, 100) {
            @Override
            public List<BargainPriceDO> nextList(int page, int pageSize) {
                return bargainPriceService.findPage(new PageModel(page, pageSize));
            }
        };
        excel(bucket);
    }

    @Override
    protected List<ExcelColumn> initColumn() {
        List<ExcelColumn> list = new ArrayList<ExcelColumn>();
        list.add(new ExcelColumn("memberId", "会员ID"));
        list.add(new ExcelColumn("loginCode", "用户CODE"));
        list.add(new ExcelColumn("bargainId", "砍价活动ID"));
        list.add(new ExcelColumn("bargain.name", "砍价名称"));
        list.add(new ExcelColumn("createDate", "发起砍价时间"));
        list.add(new ExcelColumn("bargain.productId", "商品ID"));
        list.add(new ExcelColumn("productName", "商品名称"));
        list.add(new ExcelColumn("status", "活动状态"));
        list.add(new ExcelColumn("originalPrice", "发起价格"));
        list.add(new ExcelColumn("price", "砍后价"));
        list.add(new ExcelColumn("{\"$count\": \"helpers\"}", "帮砍人数"));
        return list;
    }

    @Override
    public String getTitle() {
        return "用户参与砍价活动数据表";
    }

    @Override
    public String getFileName() {
        return "bargainPrice";
    }

}
