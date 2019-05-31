package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.CityCardEnum;
import com.d2c.logger.model.MemberStoreLog;
import com.d2c.logger.service.MemberStoreLogService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductService;
import com.d2c.product.third.upyun.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping(value = "")
public class PartnerController extends BaseController {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberStoreLogService memberStoreLogService;

    /**
     * 买手礼包页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/partner/gift", method = RequestMethod.GET)
    public String gift(ModelMap model) {
        return "member/partner_gift";
    }

    /**
     * DM介绍页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/partner/joinpartner", method = RequestMethod.GET)
    public String joinPartner(ModelMap model) {
        return "member/partner_joinpartner";
    }

    /**
     * 买手介绍页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/partner/joinbuyer", method = RequestMethod.GET)
    public String joinBuyer(ModelMap model) {
        return "member/partner_joinbuyer";
    }

    /**
     * 买手服务授权成功
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/partner/grant", method = RequestMethod.GET)
    public String grant(ModelMap model) {
        return "member/partner_grant";
    }

    /**
     * 买手服务授权失败
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/partner/fail", method = RequestMethod.GET)
    public String fail(ModelMap model) {
        return "member/partner_fail";
    }

    /**
     * 加入买手股东计划
     *
     * @param model
     * @param city
     * @param storeName
     * @return
     */
    @RequestMapping(value = "/memberstore/insert", method = RequestMethod.POST)
    public String memberStore(ModelMap model, String city, String storeName) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberStoreLog memberStoreLog = memberStoreLogService.findByLoginCode(memberInfo.getLoginCode());
        if (memberStoreLog != null) {
            throw new BusinessException("您已经加入过D2C[全球买手店]股东计划，前往D2CAPP->我的，实时关注开店动态，提取股东分红！");
        }
        memberStoreLog = new MemberStoreLog(memberInfo.getLoginCode(), memberInfo.getCreateDate(), city, storeName);
        memberStoreLogService.insert(memberStoreLog);
        return "";
    }

    /**
     * 城市卡片
     *
     * @param response
     * @param waterMarkContent
     * @param city
     */
    @ResponseBody
    @RequestMapping(value = "/city/card", method = RequestMethod.GET)
    public void cityCard(HttpServletResponse response, String waterMarkContent, String city) {
        if (waterMarkContent == null) {
            waterMarkContent = "CHARLES";
        }
        if (city == null) {
            city = "MOERBEN";
        }
        CityCardEnum storeCity = CityCardEnum.valueOf(city.toUpperCase());
        BufferedImage bufferdImage = ImageUtil.addWaterMark(waterMarkContent, storeCity.getUrl(), storeCity.getFont(),
                storeCity.getColor(), storeCity.getX(), storeCity.getY(), storeCity.getRotate(),
                storeCity.getTranslatex(), storeCity.getTranslatey(), "CHARLES");
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferdImage, "PNG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 升级app
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/to/partner/team", "/to/partner/cash", "/to/partner/sales", "/to/partner/account/item",
            "/to/partner/store", "/to/partner/mine"}, method = RequestMethod.GET)
    public String toUpdateApp(ModelMap model) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        return "update_app";
    }

    /**
     * 更新分销商
     *
     * @param partner
     * @return
     */
    @RequestMapping(value = "/partner/update", method = RequestMethod.POST)
    public String updatePartner(ModelMap model, Partner partner) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner old = partnerService.findById(partner.getId());
        if (old == null || !old.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("分销商数据不正确！");
        }
        partner.setMemberId(old.getMemberId());
        partner.setLoginCode(old.getLoginCode());
        partner.setTotalAmount(null);
        partner.setTotalOrderAmount(null);
        partner.setCashAmount(null);
        partner.setApplyAmount(null);
        partner.setBalanceAmount(null);
        partner.setLastModifyMan(memberInfo.getLoginCode());
        if (partner.getAlipay() != null && partner.getAlipay().contains("*")) {
            partner.setAlipay(null);
        }
        if (partner.getBankSn() != null && partner.getBankSn().contains("*")) {
            partner.setBankSn(null);
        }
        if (partner.getIdentityCard() != null && partner.getIdentityCard().contains("*")) {
            partner.setIdentityCard(null);
        }
        if (partner.getRealName() != null && partner.getRealName().contains("*")) {
            partner.setRealName(null);
        }
        if (partner.getLicenseNum() != null && partner.getLicenseNum().contains("*")) {
            partner.setLicenseNum(null);
        }
        partnerService.update(partner);
        partner = partnerService.findById(partner.getId());
        result.put("partner", partner.toJson());
        return "";
    }

    /**
     * 礼包商品列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/partner/gift/list", method = RequestMethod.GET)
    public String giftList(ModelMap model, PageModel page) {
        ProductSearcher searcher = new ProductSearcher();
        searcher.setStatus(-1);
        searcher.setMark(1);
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        model.put("pager", pager);
        return "";
    }

}
