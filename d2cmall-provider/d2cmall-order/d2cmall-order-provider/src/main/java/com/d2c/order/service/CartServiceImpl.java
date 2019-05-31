package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CartItemMapper;
import com.d2c.order.dto.Cart;
import com.d2c.order.dto.CartItemDto;
import com.d2c.order.model.CartItem;
import com.d2c.order.query.CartItemSearcher;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service(value = "cartService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CartServiceImpl extends ListServiceImpl<CartItem> implements CartService {

    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartItem findById(Long id) {
        return cartItemMapper.findById(id);
    }

    @Override
    public CartItemDto findOneByMemberId(Long id, Long memberId) {
        CartItem item = cartItemMapper.findOneByMemberId(id, memberId);
        if (item == null) {
            return null;
        }
        CartItemDto dto = new CartItemDto();
        BeanUtils.copyProperties(item, dto);
        return dto;
    }

    @Override
    public CartItem findBySkuAndMember(Long skuId, Long memberId) {
        return cartItemMapper.findBySkuAndMember(skuId, memberId);
    }

    @Override
    @Cacheable(value = "cart_count", key = "'cart_count_'+#memberInfoId", unless = "#result == null")
    public int countByMemberId(Long memberInfoId) {
        return this.cartItemMapper.countByMemberId(memberInfoId);
    }

    @Override
    public PageResult<CartItem> findPageBySearcher(CartItemSearcher searcher, PageModel page) {
        PageResult<CartItem> pager = new PageResult<>(page);
        int count = cartItemMapper.countBySearcher(searcher);
        if (count > 0) {
            List<CartItem> items = cartItemMapper.findBySearcher(searcher, page);
            pager.setList(items);
            pager.setTotalCount(count);
        }
        return pager;
    }

    @Override
    public List<CartItem> findBySearcher(CartItemSearcher searcher, PageModel page) {
        return cartItemMapper.findBySearcher(searcher, page);
    }

    @Override
    public int countBySearcher(CartItemSearcher searcher) {
        return cartItemMapper.countBySearcher(searcher);
    }

    @Override
    @Cacheable(value = "cart_list", key = "'cart_list_'+#memberId", unless = "#result == null")
    public Cart findCart(Long memberId) {
        Cart cart = new Cart();
        cart.setMemberInfoId(memberId);
        if (memberId != null) {
            List<CartItem> items = cartItemMapper.findByMemberId(memberId);
            for (CartItem item : items) {
                item.setBuyMemberId(memberId);
                CartItemDto dto = new CartItemDto();
                BeanUtils.copyProperties(item, dto);
                cart.addToCart(dto);
            }
        }
        return cart;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public CartItem insert(CartItem cartItem, Long memberInfoId) {
        return this.save(cartItem);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updateQuantity(Long productSkuId, Long memberInfoId, int quantity) {
        return cartItemMapper.updateQuantity(productSkuId, memberInfoId, quantity);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updatePrice(Long productSkuId, Long memberInfoId, BigDecimal originalCost, BigDecimal price) {
        return cartItemMapper.updatePrice(productSkuId, memberInfoId, originalCost, price);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updateSku(Long cartItemId, Long skuId, Long memberInfoId) {
        ProductSku sku = productSkuService.findById(skuId);
        return cartItemMapper.updateSku(cartItemId, sku.getSn(), sku.getSn(), skuId, sku.getSp1(), sku.getSp2(),
                sku.getSp3(), sku.getPrice());
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteCartItem(Long cartItemId, Long memberInfoId) {
        return cartItemMapper.delete(cartItemId, memberInfoId);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteCartItems(List<Long> cartItemIds, Long memberInfoId) {
        if (cartItemIds != null && cartItemIds.size() > 0) {
            return cartItemMapper.deletes(cartItemIds, memberInfoId);
        }
        return 1;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteCart(Long memberInfoId) {
        return cartItemMapper.deleteByMemberId(memberInfoId);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "cart_count", key = "'cart_count_'+#memberInfoId"),
            @CacheEvict(value = "cart_list", key = "'cart_list_'+#memberInfoId")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int mergeSku(Long cartItemId, Long skuId, int quantity, Long memberInfoId) {
        // 先更新改后的sku数量
        int success = this.updateQuantity(skuId, memberInfoId, quantity);
        if (success > 0) {
            // 删除原来的
            this.deleteCartItem(cartItemId, memberInfoId);
        }
        return success;
    }

}
