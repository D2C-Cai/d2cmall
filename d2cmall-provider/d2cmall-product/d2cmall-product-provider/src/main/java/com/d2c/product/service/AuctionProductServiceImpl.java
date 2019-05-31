package com.d2c.product.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.AuctionProductMapper;
import com.d2c.product.dto.AuctionProductDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.model.Product;
import com.d2c.product.query.AuctionProductSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("auctionProductService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AuctionProductServiceImpl extends ListServiceImpl<AuctionProduct> implements AuctionProductService {

    @Autowired
    private AuctionProductMapper auctionProductMapper;
    @Autowired
    private ProductService productService;

    @Cacheable(value = "auction_product", key = "'auction_product_'+#id", unless = "#result == null")
    public AuctionProduct findById(Long id) {
        return super.findOneById(id);
    }

    public List<AuctionProduct> findBySearcher(AuctionProductSearcher searcher, PageModel pager) {
        return auctionProductMapper.findBySearcher(searcher, pager);
    }

    public int countBySearcher(AuctionProductSearcher searcher) {
        return auctionProductMapper.countBySearcher(searcher);
    }

    public PageResult<AuctionProductDto> findDtoBySearcher(AuctionProductSearcher searcher, PageModel page) {
        PageResult<AuctionProductDto> pager = new PageResult<AuctionProductDto>(page);
        int totalCount = auctionProductMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<AuctionProduct> list = auctionProductMapper.findBySearcher(searcher, page);
            List<AuctionProductDto> dtos = new ArrayList<AuctionProductDto>();
            if (list != null && !list.isEmpty()) {
                for (AuctionProduct ap : list) {
                    AuctionProductDto dto = new AuctionProductDto();
                    BeanUtils.copyProperties(ap, dto);
                    if (dto.getProductId() != null && dto.getProductId() > 0) {
                        Product product = productService.findById(dto.getProductId());
                        ProductDto pdto = new ProductDto();
                        BeanUtils.copyProperties(product, pdto);
                        dto.setProduct(pdto);
                    }
                    dtos.add(dto);
                }
            }
            pager.setList(dtos);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AuctionProduct insert(AuctionProduct record) {
        record.setCurrentPrice(record.getBeginPrice());
        auctionProductMapper.insert(record);
        return record;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return super.deleteById(id);
    }

    @CacheEvict(value = "auction_product", key = "'auction_product_'+#record.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(AuctionProduct record) {
        int result = this.updateNotNull(record);
        return result;
    }

    @CacheEvict(value = "auction_product", key = "'auction_product_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUp(Long id) {
        return auctionProductMapper.doUp(id);
    }

    @CacheEvict(value = "auction_product", key = "'auction_product_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDown(Long id) {
        return auctionProductMapper.doDown(id);
    }

    @TxTransaction
    @CacheEvict(value = "auction_product", key = "'auction_product_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateCurrentPrice(Long id, BigDecimal offer, Long marginId, Long memberId) {
        return auctionProductMapper.updateCurrentPrice(id, offer, marginId, memberId);
    }

    @TxTransaction
    @CacheEvict(value = "auction_product", key = "'auction_product_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateOfferId(Long id, Long offerId) {
        return auctionProductMapper.updateOfferId(id, offerId);
    }

    @Override
    public List<AuctionProduct> findExpiredAuctionProduct() {
        return auctionProductMapper.findExpiredAuctionProduct();
    }

    @Override
    @CacheEvict(value = "auction_product", key = "'auction_product_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return auctionProductMapper.updateSort(id, sort);
    }
    // // 中拍后一天内不支付余款就算违约，直接改为手工操作。
    // private void doBreachAuction(AuctionProduct auctionProduct) {
    // Date now = new Date();
    // if (auctionProduct.getEndDate().after(now)) {
    // long interval = (auctionProduct.getEndDate().getTime() - now.getTime()) /
    // 1000 + 30;// 延迟一天
    // auctionProductQueueProducer.doAuctionEndAndBreach(auctionProduct.getId(),
    // "BREACH", interval);
    // }
    // }
}
