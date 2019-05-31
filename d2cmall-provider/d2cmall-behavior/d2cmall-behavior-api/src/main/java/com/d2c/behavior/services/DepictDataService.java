package com.d2c.behavior.services;

import com.d2c.order.model.OrderItem;

public interface DepictDataService {

    /**
     * 发送画像数据并保存
     */
    public void addByOrderItem(OrderItem item);

    public void addByOrderItem(Iterable<OrderItem> list);

    public <T> void addDepictDatas(Iterable<T> beans, String memberIdField);

    public <T> void addDepictData(T bean, String memberIdField);

}