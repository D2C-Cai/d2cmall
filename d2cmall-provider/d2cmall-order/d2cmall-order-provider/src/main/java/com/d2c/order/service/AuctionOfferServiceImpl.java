package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.AuctionOfferMapper;
import com.d2c.order.dto.AuctionOfferDto;
import com.d2c.order.model.AuctionOffer;
import com.d2c.order.query.AuctionOfferSearcher;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("auctionOfferService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AuctionOfferServiceImpl extends ListServiceImpl<AuctionOffer> implements AuctionOfferService {

    @Autowired
    private AuctionOfferMapper auctionOfferMapper;
    @Autowired
    private ProductService productService;

    public PageResult<AuctionOffer> findBySearch(AuctionOfferSearcher searcher, PageModel page) {
        PageResult<AuctionOffer> pager = new PageResult<>(page);
        int totalCount = auctionOfferMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionOffer> list = auctionOfferMapper.findBySearch(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    public int countBySearch(AuctionOfferSearcher searcher) {
        return auctionOfferMapper.countBySearch(searcher);
    }

    @Override
    public AuctionOffer findTopByAuctionId(Long auctionId) {
        return auctionOfferMapper.findTopByAuctionId(auctionId);
    }

    public PageResult<AuctionOfferDto> findDtoBySearcher(AuctionOfferSearcher searcher, PageModel page) {
        PageResult<AuctionOfferDto> pager = new PageResult<AuctionOfferDto>(page);
        int totalCount = auctionOfferMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionOffer> list = auctionOfferMapper.findBySearch(searcher, page);
            List<AuctionOfferDto> dtos = new ArrayList<AuctionOfferDto>();
            if (list != null && !list.isEmpty()) {
                for (AuctionOffer item : list) {
                    AuctionOfferDto dto = new AuctionOfferDto();
                    BeanUtils.copyProperties(item, dto);
                    Product auctionProduct = productService.findById(item.getAuctionProductId());
                    dto.setAuctionProduct(auctionProduct);
                    dtos.add(dto);
                }
            }
            pager.setList(dtos);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public List<HelpDTO> findHelpDtos(AuctionOfferSearcher searcher, PageModel page) {
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        int totalCount = auctionOfferMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionOffer> list = auctionOfferMapper.findBySearch(searcher, page);
            if (list != null && !list.isEmpty()) {
                for (AuctionOffer ao : list) {
                    HelpDTO dto = new HelpDTO();
                    dto.setId(ao.getId());
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AuctionOffer insert(AuctionOffer offer) {
        return this.save(offer);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doOut(Long auctionId, BigDecimal offer) {
        return auctionOfferMapper.doOut(auctionId, offer);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccess(Long id) {
        int success = auctionOfferMapper.doSuccess(id);
        return success;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return auctionOfferMapper.doMerge(memberSourceId, memberTargetId);
    }

}
