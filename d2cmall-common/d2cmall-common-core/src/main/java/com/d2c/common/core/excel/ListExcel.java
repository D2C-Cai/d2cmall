package com.d2c.common.core.excel;

import com.d2c.common.api.query.QueryItem;
import jxl.write.Label;

import java.util.List;

public abstract class ListExcel<T> extends BaseExcel<T> {

    protected List<QueryItem> queryList;

    public void excel(List<T> list, List<QueryItem> queryList) {
        this.queryList = queryList;
        super.excel(list);
    }

    @Override
    protected void buildHeader() throws Exception {
        if (queryList != null) {
            int i = 0;
            for (QueryItem query : queryList) {
                ws.mergeCells(2 * i, getRow(), 2 * i + 1, getRow());
                ws.addCell(new Label(0 + 2 * i, getRow(), query.getShowName() + ":" + query.getValue(), font.wcfTitle));
                i++;
            }
            nextLine();
        }
    }

}
