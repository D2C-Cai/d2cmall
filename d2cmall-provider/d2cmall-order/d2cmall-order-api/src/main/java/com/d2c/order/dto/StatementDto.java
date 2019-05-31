package com.d2c.order.dto;

import com.d2c.order.model.Statement;
import com.d2c.order.model.StatementItem;

import java.util.List;

public class StatementDto extends Statement {

    private static final long serialVersionUID = 1L;
    /**
     * 对账单明细
     */
    private List<StatementItem> statementItems;

    public List<StatementItem> getStatementItems() {
        return statementItems;
    }

    public void setStatementItems(List<StatementItem> statementItems) {
        this.statementItems = statementItems;
    }

}
