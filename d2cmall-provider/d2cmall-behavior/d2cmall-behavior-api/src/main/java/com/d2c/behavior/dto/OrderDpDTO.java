package com.d2c.behavior.dto;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.order.model.OrderItem;
import com.d2c.product.search.model.SearcherProduct;

/**
 * 销售数据模型
 *
 * @author wull
 */
public class OrderDpDTO extends BaseDTO {

    private static final long serialVersionUID = -4544241950569744088L;
    private String id;
    private OrderItem orderItem;
    private SearcherProduct product;

    public OrderDpDTO() {
    }

    public OrderDpDTO(OrderItem orderItem) {
        this.orderItem = orderItem;
        this.id = orderItem.getId().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public SearcherProduct getProduct() {
        return product;
    }

    public void setProduct(SearcherProduct product) {
        this.product = product;
    }

}

