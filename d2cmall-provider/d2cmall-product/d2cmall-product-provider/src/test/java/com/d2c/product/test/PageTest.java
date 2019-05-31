package com.d2c.product.test;

import com.d2c.common.api.page.base.PageOrder;
import com.d2c.common.api.page.base.PageSort;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.product.search.enums.ProductOrderTypeEnum;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PageTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("********* 开始测试  *******");
        String key = "pa";
        PageSort sort = PageSort.build(false, "store").add(ProductOrderTypeEnum.getPageSort(key));
        sort.addDesc("");
        sort.addDesc("recomScore");
        if (!ProductOrderTypeEnum.sa.equals(key) && !ProductOrderTypeEnum.sd.equals(key)) {
            sort.addDesc("sales");
        }
        sort.addDesc("upMarketDate");
        List<PageOrder> ol = sort.getOrders();
        String[] sortFields = new String[ol.size()];
        SortOrder[] sortOrders = new SortOrder[ol.size()];
        int i = 0;
        for (PageOrder o : ol) {
            sortFields[i] = o.getProperty();
            sortOrders[i] = o.isAsc() ? SortOrder.ASC : SortOrder.DESC;
            i++;
        }
        logger.info("PageSort : " + sort);
        logger.info("sortFields : " + JsonUt.serialize(sortFields));
        logger.info("sortOrders : " + JsonUt.serialize(sortOrders));
    }

}
