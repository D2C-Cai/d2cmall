package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.dto.AuctionMarginDto;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.model.AuctionOffer;
import com.d2c.order.query.AuctionMarginSearcher;
import com.d2c.order.query.AuctionOfferSearcher;
import com.d2c.order.service.AddressService;
import com.d2c.order.service.AuctionMarginService;
import com.d2c.order.service.AuctionOfferService;
import com.d2c.order.service.tx.AuctionTxService;
import com.d2c.product.dto.AuctionProductDto;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.query.AuctionProductSearcher;
import com.d2c.product.service.AuctionProductService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/auction")
public class AuctionController extends BaseController {

    @Autowired
    private AuctionProductService auctionProductService;
    @Autowired
    private AuctionMarginService auctionMarginService;
    @Autowired
    private AuctionOfferService auctionOfferService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;
    @Reference
    private AuctionTxService auctionTxService;

    /**
     * 拍卖商品列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String auctionProductList(ModelMap model, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        AuctionProductSearcher searcher = new AuctionProductSearcher();
        searcher.setStatus(1);
        PageResult<AuctionProductDto> pager = auctionProductService.findDtoBySearcher(searcher, page);
        result.put("pager", pager);
        return "product/auction_product_list";
    }

    /**
     * 拍卖商品详情
     *
     * @param model
     * @param auctionId
     * @return
     */
    @RequestMapping(value = "/product/{auctionId}", method = RequestMethod.GET)
    public String auctionProductDetail(ModelMap model, @PathVariable Long auctionId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        AuctionProduct auctionProduct = auctionProductService.findById(auctionId);
        if (auctionProduct == null) {
            throw new BusinessException("拍卖商品下架了，下次早点来！");
        }
        AuctionProductDto productDto = new AuctionProductDto();
        BeanUtils.copyProperties(auctionProduct, productDto);
        productDto.setProduct(productService.findDetailById(auctionProduct.getProductId()));
        result.put("product", productDto);
        PageModel page = new PageModel();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            AuctionMargin myMargin = auctionMarginService.findByMemberIdAndAuctionId(memberInfo.getId(), auctionId);
            model.put("myMargin", myMargin);
            if (myMargin != null) {
                AuctionOfferSearcher searcher = new AuctionOfferSearcher();
                searcher.setMemberInfoId(memberInfo.getId());
                searcher.setAuctionId(auctionId);
                PageResult<AuctionOffer> myOffer = auctionOfferService.findBySearch(searcher, page);
                model.put("myOffer", myOffer.getList());
            }
        } catch (NotLoginException e) {
        }
        // 获取所有出价列表
        AuctionOfferSearcher searcher = new AuctionOfferSearcher();
        searcher.setAuctionId(auctionId);
        PageResult<AuctionOffer> offers = auctionOfferService.findBySearch(searcher, page);
        result.put("offers", offers);
        return "product/auction_product_detail";
    }

    /**
     * 创建保证单，进入保证金支付页面
     *
     * @param model
     * @param auctionId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/margin/create/{auctionId}", method = RequestMethod.POST)
    public String createMargin(ModelMap model, @PathVariable Long auctionId) throws Exception {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("result", result);
        AuctionProduct product = auctionProductService.findById(auctionId);
        if (!product.isDoing()) {
            throw new BusinessException("拍卖已结束！");
        }
        AuctionMargin margin = auctionMarginService.findByMemberIdAndAuctionId(memberInfo.getId(), auctionId);
        if (margin == null) {
            margin = new AuctionMargin(product);
            margin.setMemberId(memberInfo.getId());
            margin.setLoginCode(memberInfo.getLoginCode());
            margin.setMemberNick(memberInfo.getDisplayName());
            margin = auctionMarginService.insert(margin);
        }
        if (margin.getStatus() == 1) {
            throw new BusinessException("您已经支付过保证金了！");
        } else if (margin.getStatus() != 0) {
            throw new BusinessException("保证金状态异常！");
        }
        result.put("marginSn", margin.getMarginSn());
        AuctionMarginDto dto = new AuctionMarginDto();
        BeanUtils.copyProperties(margin, dto);
        result.put("params", this.getPayParams(dto));
        return "";
    }

    /**
     * 拍卖出价，半分钟之内不能再次出价
     *
     * @param model
     * @param id
     * @param offer
     * @return
     */
    @RequestMapping(value = "/offer", method = RequestMethod.POST)
    public String auctionOffer(ModelMap model, Long id, @RequestParam(required = true) BigDecimal offer) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("result", result);
        AuctionProduct product = auctionProductService.findById(id);
        if (!product.isDoing()) {
            result.setStatus(-3);
            result.setMsg("拍卖已结束，下次抓紧哦！");
            return "";
        }
        AuctionMargin margin = auctionMarginService.findByMemberIdAndAuctionId(memberInfo.getId(), id);
        if (margin == null || margin.getStatus() != 1) {
            throw new BusinessException("未支付保证金，请先支付保证金！");
        }
        if (product.getCurrentPrice().compareTo(offer) >= 0) {
            result.setMsg("已经有人出过更高的价格，出价不成功！");
            result.setStatus(-2);
            result.put("maxPrice", product.getCurrentPrice());
            return "";
        }
        if (!isInteger(offer.subtract(product.getBeginPrice()).divide(product.getStepPrice()).toString())) {
            throw new BusinessException("出价价格只能是阶梯价格的整数倍！");
        }
        if (product.getMemberId() != null && product.getMemberId().equals(memberInfo.getId())) {
            Date now = new Date();
            long interval = (now.getTime() - product.getModifyDate().getTime()) / 1000;
            if (interval <= 30) {
                result.setMsg("30秒之内不能再次出价，请稍后再试！");
                result.setStatus(-1);
                result.put("maxPrice", product.getCurrentPrice());
                return "";
            }
        }
        auctionTxService.insertOffer(product, memberInfo.getId(), offer, margin.getId());
        result.put("maxPrice", product.getCurrentPrice());
        return "";
    }

    /**
     * 拍卖成功，进入余款确认页面
     *
     * @param model
     * @param marginSn
     * @return
     */
    @RequestMapping(value = "/margin/confirm/{marginSn}", method = RequestMethod.POST)
    public String confirmMargin(ModelMap model, @PathVariable String marginSn) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        AuctionMargin margin = auctionMarginService.findByMarginSnAndMemberId(marginSn, memberInfo.getId());
        if (margin == null) {
            throw new BusinessException("拍卖记录不存在！");
        } else {
            if (margin.getStatus() == 2) {
                model.put("auctionMargin", margin);
            } else if (margin.getStatus() == -6) {
                throw new BusinessException("您未在指定时间内付清余款，系统已经关闭交易！");
            } else {
                throw new BusinessException("系统正在处理拍卖结果，请稍后！");
            }
        }
        AuctionProduct product = auctionProductService.findById(margin.getAuctionId());
        if (product == null) {
            throw new BusinessException("拍卖商品不存在！");
        }
        AuctionProductDto productDto = new AuctionProductDto();
        BeanUtils.copyProperties(product, productDto);
        productDto.setProduct(productService.findDetailById(product.getProductId()));
        model.put("product", productDto);
        PageResult<AddressDto> pager = addressService.findByMemberInfoId(memberInfo.getId(), new PageModel(1, 10),
                null);
        model.put("addresses", pager.getList());
        return "order/confirm_auction";
    }

    /**
     * 更新保证单，进入余款支付页面
     *
     * @param model
     * @param marginId
     * @param addressId
     * @param address
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/margin/update/{marginId}", method = RequestMethod.POST)
    public String updateMargin(ModelMap model, @PathVariable Long marginId, Long addressId, AddressDto address)
            throws Exception {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("result", result);
        AuctionMargin margin = auctionMarginService.findByIdAndMemberId(marginId, memberInfo.getId());
        if (margin == null || margin.getStatus() != 2) {
            throw new BusinessException("您未支付保证金或未拍得此商品！");
        }
        if (addressId == null || addressId <= 0) {
            if (address != null) {
                address.setMemberId(memberInfo.getId());
                address = addressService.insert(address);
                addressId = address.getId();
            } else {
                throw new BusinessException("请填写收货地址！");
            }
        }
        auctionMarginService.updateAddress(addressId, marginId);
        result.put("marginSn", margin.getMarginSn());
        AuctionMarginDto dto = new AuctionMarginDto();
        BeanUtils.copyProperties(margin, dto);
        result.put("params", this.getPayParams(dto));
        return "";
    }

    /**
     * 去支付页面 1.支付保证金 2.支付余款
     *
     * @param model
     * @param marginSn
     * @return
     */
    @RequestMapping(value = "/margin/payment/{marginSn}", method = RequestMethod.GET)
    public String payMargin(ModelMap model, @PathVariable String marginSn) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        AuctionMarginDto margin = auctionMarginService.findByMarginSnAndMemberId(marginSn, memberInfo.getId());
        if (margin == null) {
            throw new BusinessException("订单不存在！");
        }
        if (!margin.isWaitPay()) {
            result.setStatus(-3);
            result.setMessage("订单已支付或关闭，支付不成功！");
            return "error/503";
        }
        Account account = accountService.findByMemberId(memberInfo.getId());
        model.put("account", account);
        result.put("auctionMargin", margin);
        return "pay/auction_payment";
    }

    /**
     * 拍卖出价列表
     *
     * @param model
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/offer/{id}", method = RequestMethod.GET)
    public String offerList(ModelMap model, @PathVariable Long id, PageModel page) {
        AuctionOfferSearcher searcher = new AuctionOfferSearcher();
        searcher.setAuctionId(id);
        PageResult<AuctionOffer> pager = auctionOfferService.findBySearch(searcher, page);
        model.put("pager", pager);
        return "product/auction_offer_list";
    }

    /**
     * 我的拍卖出价列表
     *
     * @param model
     * @param auctionId
     * @param page
     * @return
     */
    @RequestMapping(value = "/member/myoffer", method = RequestMethod.GET)
    public String myOfferList(ModelMap model, Long auctionId, PageModel page) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        AuctionOfferSearcher searcher = new AuctionOfferSearcher();
        searcher.setMemberInfoId(memberInfo.getId());
        searcher.setAuctionId(auctionId);
        PageResult<AuctionOffer> pager = auctionOfferService.findBySearch(searcher, page);
        model.put("pager", pager);
        return "member/my_auction_offer";
    }

    /**
     * 我的保证金列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/member/mymargin", method = RequestMethod.GET)
    public String myMarginList(ModelMap model, PageModel page) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        AuctionMarginSearcher searcher = new AuctionMarginSearcher();
        searcher.setMemberInfoId(memberInfo.getId());
        PageResult<AuctionMarginDto> pager = auctionMarginService.findDtoBySearcher(searcher, page);
        model.put("pager", pager);
        return "member/my_auction_margin";
    }

    /**
     * 确认收货
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/received/{id}", method = RequestMethod.POST)
    public String doReceived(ModelMap model, @PathVariable Long id) {
        this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        int success = auctionMarginService.doReceived(id);
        if (success <= 0) {
            throw new BusinessException("状态异常，请联系客服！");
        }
        return "";
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
