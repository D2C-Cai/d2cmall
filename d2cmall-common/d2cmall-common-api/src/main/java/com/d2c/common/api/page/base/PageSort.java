package com.d2c.common.api.page.base;

import com.d2c.common.base.utils.StringUt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageSort implements Iterable<PageOrder>, Serializable {

    private static final long serialVersionUID = -59436730248878662L;
    private List<PageOrder> orders = new ArrayList<>();

    public PageSort() {
    }

    public PageSort(Sort sort) {
        sort.forEach(order -> {
            orders.add(new PageOrder(order));
        });
    }

    public PageSort(boolean isAsc, String... propertys) {
        for (String prop : propertys) {
            orders.add(new PageOrder(prop, isAsc));
        }
    }

    public static PageSort build(boolean isAsc, String... propertys) {
        return new PageSort(isAsc, propertys);
    }

    /**
     * 创建排序，根据 price|asc 表示
     */
    public static PageSort build(String... keys) {
        PageSort pageSort = new PageSort();
        for (String key : keys) {
            if (StringUtils.isBlank(key)) continue;
            String[] ss = StringUtils.split(key, "|");
            if (ss.length >= 2) {
                if (ss[1].equalsIgnoreCase("asc")) {
                    pageSort.add(true, ss[0]);
                } else {
                    pageSort.add(false, ss[0]);
                }
            } else {
                pageSort.add(false, key);
            }
        }
        return pageSort;
    }

    public PageSort addDesc(String property) {
        add(false, property);
        return this;
    }

    public PageSort add(boolean isAsc, String property) {
        if (StringUtils.isNotBlank(property)) {
            orders.add(new PageOrder(property, isAsc));
        }
        return this;
    }

    public PageSort add(PageOrder order) {
        if (order != null) {
            orders.add(order);
        }
        return this;
    }

    public PageSort add(PageSort sort) {
        if (sort == null) {
            return this;
        }
        sort.forEach(order -> {
            orders.add(order);
        });
        return this;
    }

    public PageOrder orderFor(String property) {
        for (PageOrder order : orders) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Iterator<PageOrder> iterator() {
        return this.orders.iterator();
    }

    public Sort getSort() {
        if (orders == null || orders.isEmpty()) return null;
        List<Order> list = new ArrayList<>();
        for (PageOrder order : orders) {
            list.add(order.getOrder());
        }
        return new Sort(list);
    }
    //*****************************************

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageSort)) {
            return false;
        }
        PageSort that = (PageSort) obj;
        return this.orders.equals(that.orders);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + orders.hashCode();
        return result;
    }

    public String toSql() {
        return StringUt.join(orders, ",", v -> {
            return v.toSql();
        });
    }

    @Override
    public String toString() {
        return StringUt.join(orders, ",");
    }

    public List<PageOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PageOrder> orders) {
        this.orders = orders;
    }

    public List<String> getSortFields() {
        List<String> fields = new ArrayList<>();
        orders.forEach(o -> {
            fields.add(o.getProperty());
        });
        return fields;
    }

    public List<Direction> getDirections() {
        List<Direction> ds = new ArrayList<>();
        orders.forEach(o -> {
            ds.add(o.getDirection());
        });
        return ds;
    }

}
