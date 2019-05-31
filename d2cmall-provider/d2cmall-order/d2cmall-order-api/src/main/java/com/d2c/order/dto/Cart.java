package com.d2c.order.dto;

import com.d2c.order.model.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 购物车类
 */
public class Cart implements Serializable {

    public static final String COOKIE_NAME = "d2c_cart_";
    private static final long serialVersionUID = 1L;
    private Long memberInfoId;
    private ConcurrentHashMap<Long, CartItemDto> itemMapBySku = new ConcurrentHashMap<Long, CartItemDto>();
    private TreeMap<Long, CartItemDto> itemMapById = new TreeMap<Long, CartItemDto>();

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public ConcurrentHashMap<Long, CartItemDto> getItemMapBySku() {
        return itemMapBySku;
    }

    public TreeMap<Long, CartItemDto> getItemMapById() {
        return itemMapById;
    }

    public boolean addToCart(CartItemDto cartItem) {
        itemMapBySku.put(cartItem.getProductSkuId(), cartItem);
        itemMapById.put(cartItem.getId(), cartItem);
        return true;
    }

    /**
     * 获取购物车有几种商品
     */
    public int getTypeCount() {
        return itemMapBySku.size();
    }

    /**
     * 获取该购物车有几件商品
     */
    public int getNumCount() {
        int totalCount = 0;
        for (CartItemDto cartItem : itemMapBySku.values()) {
            totalCount = totalCount + cartItem.getQuantity();
        }
        return totalCount;
    }

    public List<CartItem> getItems() {
        Iterator<CartItemDto> iterator = itemMapById.values().iterator();
        List<CartItem> items = new ArrayList<CartItem>();
        while (iterator.hasNext()) {
            CartItemDto item = iterator.next();
            items.add(item);
        }
        return items;
    }

}
