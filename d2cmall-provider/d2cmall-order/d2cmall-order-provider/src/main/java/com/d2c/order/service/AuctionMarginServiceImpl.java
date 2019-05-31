package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.AuctionMarginMapper;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.dto.AuctionMarginDto;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.query.AuctionMarginSearcher;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.model.Product;
import com.d2c.product.service.AuctionProductService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("auctionMarginService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AuctionMarginServiceImpl extends ListServiceImpl<AuctionMargin> implements AuctionMarginService {

    @Autowired
    private AuctionMarginMapper auctionMarginMapper;
    @Autowired
    private AuctionProductService auctionProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private AddressService addressService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AuctionMargin insert(AuctionMargin margin) {
        return save(margin);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return deleteById(id);
    }

    @Override
    public PageResult<AuctionMargin> findBySearcher(AuctionMarginSearcher searcher, PageModel page) {
        PageResult<AuctionMargin> pager = new PageResult<>(page);
        int totalCount = auctionMarginMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionMargin> list = auctionMarginMapper.findBySearch(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public int countBySearch(AuctionMarginSearcher searcher) {
        int totalCount = auctionMarginMapper.countBySearch(searcher);
        return totalCount;
    }

    @Override
    public PageResult<AuctionMarginDto> findDtoBySearcher(AuctionMarginSearcher searcher, PageModel page) {
        PageResult<AuctionMarginDto> pager = new PageResult<>(page);
        int totalCount = auctionMarginMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionMargin> list = auctionMarginMapper.findBySearch(searcher, page);
            List<AuctionMarginDto> dtos = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (AuctionMargin item : list) {
                    AuctionMarginDto dto = new AuctionMarginDto();
                    BeanUtils.copyProperties(item, dto);
                    if (item.getAuctionId() != null) {
                        AuctionProduct auctionProduct = auctionProductService.findById(item.getAuctionId());
                        dto.setAuctionProduct(auctionProduct);
                    }
                    if (item.getAuctionProductId() != null) {
                        Product product = productService.findById(item.getAuctionProductId());
                        dto.setProduct(product);
                    }
                    if (item.getAddressId() != null) {
                        AddressDto addressDto = addressService.findById(item.getAddressId());
                        dto.setAddress(addressDto);
                    }
                    dtos.add(dto);
                }
            }
            pager.setList(dtos);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public AuctionMargin findById(Long id) {
        return auctionMarginMapper.selectByPrimaryKey(id);
    }

    @Override
    public AuctionMargin findByMarginSn(String marginSn) {
        return auctionMarginMapper.findByMarginSn(marginSn);
    }

    @Override
    public AuctionMarginDto findByIdAndMemberId(Long id, Long memberId) {
        AuctionMargin margin = auctionMarginMapper.findByIdAndMemberId(id, memberId);
        if (margin == null) {
            return null;
        }
        AuctionMarginDto dto = new AuctionMarginDto();
        BeanUtils.copyProperties(margin, dto);
        return dto;
    }

    @Override
    public AuctionMarginDto findByMarginSnAndMemberId(String marginSn, Long memberId) {
        AuctionMargin margin = auctionMarginMapper.findByMarginSnAndMemberId(marginSn, memberId);
        if (margin == null) {
            return null;
        }
        AuctionMarginDto dto = new AuctionMarginDto();
        BeanUtils.copyProperties(margin, dto);
        return dto;
    }

    @Override
    public AuctionMarginDto findByMemberIdAndAuctionId(Long memberId, Long auctionId) {
        AuctionMargin margin = auctionMarginMapper.findByMemberIdAndAuctionId(memberId, auctionId);
        if (margin == null) {
            return null;
        }
        AuctionMarginDto dto = new AuctionMarginDto();
        BeanUtils.copyProperties(margin, dto);
        return dto;
    }

    @Override
    public List<HelpDTO> findHelpDtos(AuctionMarginSearcher searcher, PageModel page) {
        List<HelpDTO> dtos = new ArrayList<>();
        int totalCount = auctionMarginMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AuctionMargin> list = auctionMarginMapper.findBySearch(searcher, page);
            if (list != null && !list.isEmpty()) {
                for (AuctionMargin item : list) {
                    HelpDTO dto = new HelpDTO();
                    dto.setId(item.getId());
                    dto.setName(item.getMarginSn());
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateOffer(BigDecimal offer, Long marginId) {
        return auctionMarginMapper.updateOffer(marginId, offer);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateAddress(Long addressId, Long marginId) {
        return auctionMarginMapper.updateAddress(marginId, addressId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccess(Long marginId, BigDecimal offer) {
        return auctionMarginMapper.doSuccess(marginId, offer);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int doOut(Long auctionId) {
        return auctionMarginMapper.doOut(auctionId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBackMargin(Long id, String refundType, String refundSn, String refunder) {
        return auctionMarginMapper.doBackMargin(id, refundType, refundSn, refunder);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount) {
        AuctionMargin auctionMargin = auctionMarginMapper.findByMarginSn(orderSn.split("-")[0]);
        if (auctionMargin == null) {
            logger.error("[Margin] 拍卖单：" + orderSn + "不存在");
            return 0;
        }
        int result = 0;
        if (("0").equals(orderSn.split("-")[1])) {
            // 支付定金
            if (auctionMargin.getStatus() == 1) {
                logger.error("[Margin] 拍卖单：" + orderSn + "定金已经支付成功了");
                return 0;
            }
            // 要求支付金额和已付金额一致才可以继续
            if (auctionMargin.getTotalAmount().intValue() == payedAmount.intValue()) {
                result = auctionMarginMapper.doPaySuccess1(paymentType, paySn, new Date(), paymentId,
                        auctionMargin.getId());
            }
        } else if (("2").equals(orderSn.split("-")[1])) {
            // 支付余款
            if (auctionMargin.getStatus() == 6) {
                logger.error("[Margin] 拍卖单：" + orderSn + "余款已经支付成功了");
                return 0;
            }
            // 要求支付金额和已付金额一致才可以继续
            if (auctionMargin.getTotalAmount().intValue() == payedAmount.intValue()) {
                result = auctionMarginMapper.doPaySuccess2(paymentType, paySn, new Date(), paymentId,
                        auctionMargin.getId());
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBreachAuction(Long marginId) {
        int result = auctionMarginMapper.doBreachAuction(marginId);
        if (result > 0) {
            AuctionMargin auctionMargin = this.findById(marginId);
            this.doSendMarginMsg(auctionMargin.getMemberId(), auctionMargin.getAuctionId(),
                    auctionMargin.getAuctionTitle(), "BREACH");
        }
        return result;
    }

    private void doSendMarginMsg(Long memberId, Long auctionId, String auctionTitle, String type) {
        MemberInfo memberInfo = memberInfoService.findById(memberId);
        if ("SUCCESS".equalsIgnoreCase(type)) {
            String subject = "中拍提醒";
            String content = "尊敬的顾客，恭喜您！您在[" + auctionTitle + "]的拍卖活动中竞拍得标了！请您在24小时内按照成交价格支付拍品余款，如未按时支付将扣除缴纳的保证金！";
            PushBean pushBean = new PushBean(memberInfo.getId(), content, 29);
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 29, subject, content);
            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(), SmsLogType.REMIND,
                    content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        } else {
            String subject = "违约提醒";
            String content = "尊敬的顾客，你在[" + auctionTitle + "]的拍卖活动中拍了，但在24小时内未支付余款，因此已将你的保证金扣为违约金！";
            PushBean pushBean = new PushBean(memberInfo.getId(), content, 10);
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 10, subject, content);
            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(), SmsLogType.REMIND,
                    content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        }
    }

    @Override
    public int getMaxStatus(Long auctionId) {
        return auctionMarginMapper.getMaxStatus(auctionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDelivery(Long id, String deliveryCorpName, String deliverySn) {
        return auctionMarginMapper.doDelivery(id, deliveryCorpName, deliverySn);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReceived(Long id) {
        return auctionMarginMapper.doReceived(id);
    }

}
