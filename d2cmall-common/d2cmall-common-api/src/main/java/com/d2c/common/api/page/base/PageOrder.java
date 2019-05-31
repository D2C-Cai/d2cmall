package com.d2c.common.api.page.base;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

import java.io.Serializable;

public class PageOrder implements Serializable {

    private static final long serialVersionUID = -2827143886093197242L;
    private String property;
    private boolean isAsc = true;
    private boolean ignoreCase = false;
    private String nullHandling;

    public PageOrder() {
    }

    public PageOrder(String property, boolean isAsc) {
        this.property = property;
        this.isAsc = isAsc;
    }

    public PageOrder(Order order) {
        this.property = order.getProperty();
        this.isAsc = order.getDirection().isAscending();
        this.ignoreCase = order.isIgnoreCase();
        if (!order.getNullHandling().equals(NullHandling.NATIVE)) {
            this.nullHandling = order.getNullHandling().name();
        }
    }

    public Order getOrder() {
        NullHandling nh = null;
        if (nullHandling != null) {
            try {
                nh = NullHandling.valueOf(nullHandling);
            } catch (Exception e) {
            }
        }
        Order order = new Order(getDirection(), property, nh);
        if (ignoreCase) {
            order.ignoreCase();
        }
        return order;
    }

    public Direction getDirection() {
        return isAsc ? Direction.ASC : Direction.DESC;
    }
    //********************************************

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + property.hashCode();
        result = 31 * result + (isAsc ? 1 : 0);
        result = 31 * result + (ignoreCase ? 1 : 0);
        result = 31 * result + nullHandling.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageOrder)) {
            return false;
        }
        PageOrder that = (PageOrder) obj;
        return this.property.equals(that.property) && this.isAsc == that.isAsc
                && this.ignoreCase == that.ignoreCase && this.nullHandling.equals(that.nullHandling);
    }

    public String toSql() {
        return String.format("%s %s", property, isAsc ? "ASC" : "DESC");
    }

    @Override
    public String toString() {
        String result = String.format("%s|%s", property, isAsc ? "ASC" : "DESC");
        if (nullHandling != null) {
            result += "," + nullHandling;
        }
        if (ignoreCase) {
            result += ",ignoring case";
        }
        return result;
    }

    public String getProperty() {
        return property;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

}
