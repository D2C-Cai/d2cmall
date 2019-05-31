package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.model.Admin;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDef.ManagerStatus;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefSearcher;
import com.d2c.order.service.*;
import com.d2c.order.third.payment.wxpay.client.card.WxCard;
import com.d2c.order.third.payment.wxpay.client.card.WxCardBaseInfo;
import com.d2c.order.third.payment.wxpay.client.card.WxCardCash;
import com.d2c.order.third.payment.wxpay.client.card.WxCardCreate;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.RandomUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/coupondef")
public class CouponDefCtrl extends BaseCtrl<CouponDefSearcher> {

    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private SignatureService signatureService;
    @Autowired
    private WeixinGzOauthClient weixinGzOauthClient;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;

    @Override
    protected List<Map<String, Object>> getRow(CouponDefSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(CouponDefSearcher searcher) {
        BeanUt.trimString(searcher);
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(CouponDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CouponDef> pager = couponDefQueryService.findBySearcher(page, searcher);
        SuccessResponse response = new SuccessResponse(pager);
        return response;
    }

    @Override
    protected Response doList(CouponDefSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CouponDef> pager = couponDefQueryService.findBySearcher(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        CouponDef couponDef = couponDefQueryService.findById(id);
        result.put("couponDef", couponDef);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return new ErrorResponse("请输入正确的id");
        }
        for (int i = 0; i < ids.length; i++) {
            couponDefService.delete(ids[i]);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        couponDefService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Object joEnableDate = data.getString("enableDate");// 使用开始时间
        Object joExpireDate = data.getString("expireDate");// 使用结束时间
        Object joClaimStart = data.getString("claimStart");// 领用开始时间
        Object joClaimEnd = data.getString("claimEnd");// 领用结束时间
        if (joEnableDate == null || "null".equals(joEnableDate.toString()) || "".equals(joEnableDate.toString())) {
            data.remove("enableDate");
        }
        if (joExpireDate == null || "null".equals(joExpireDate.toString()) || "".equals(joExpireDate.toString())) {
            data.remove("expireDate");
        }
        if (joClaimStart == null || "null".equals(joClaimStart.toString()) || "".equals(joClaimStart.toString())) {
            data.remove("claimStart");
        }
        if (joClaimEnd == null || "null".equals(joClaimEnd.toString()) || "".equals(joClaimEnd.toString())) {
            data.remove("claimEnd");
        }
        CouponDef couponDef = JsonUtil.instance().toObject(data, CouponDef.class);
        Response checkResponse = checkName(couponDef.getName());
        if (checkResponse.getStatus() == -1) {
            return checkResponse;
        }
        SuccessResponse response = new SuccessResponse();
        String randomCode = RandomUtil.getRandomString(32);
        couponDef.setCode(randomCode);
        if (StringUtil.isBlank(couponDef.getCipher())) {
            couponDef.setCipher(randomCode);
        } else {
            CouponDef cipher = couponDefQueryService.findByCipher(couponDef.getCipher());
            if (cipher != null) {
                response.setStatus(-1);
                response.setMessage("暗码已经存在！");
                return response;
            }
        }
        if (couponDef.getType().equals(CouponType.CASH.toString())) {
            couponDef.setManagerStatus(ManagerStatus.SENDED.toString());
        } else {
            couponDef.setManagerStatus(ManagerStatus.INIT.toString());
        }
        couponDef = couponDefService.insert(couponDef);
        response.put("coupondef", couponDef);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CouponDef couponDef = JsonUtil.instance().toObject(data, CouponDef.class);
        if (StringUtil.isBlank(couponDef.getCipher())) {
            String randomCode = RandomUtil.getRandomString(32);
            couponDef.setCipher(randomCode);
        }
        CouponDef cipher = couponDefQueryService.findByCipher(couponDef.getCipher());
        if (cipher != null && !cipher.getId().equals(id)) {
            result.setStatus(-1);
            result.setMessage("暗码已经存在！");
            return result;
        }
        couponDefService.update(couponDef);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 商品关联的优惠券help，包含优惠券help和已经关联的优惠券
     *
     * @param productId
     * @param page
     * @return
     */
    @RequestMapping(value = "/product/helplist/{productId}", method = RequestMethod.POST)
    public Response helpListForProduct(@PathVariable Long productId, PageModel page) {
        CouponDefSearcher searcher = new CouponDefSearcher();
        searcher.setCheckAssociation("PRODUCT");
        PageResult<HelpDTO> dtos = couponDefQueryService.findHelpDto(searcher, page);
        SuccessResponse response = new SuccessResponse(dtos);
        if (productId != null) {
            List<CouponDef> usedList = couponDefQueryService.findByProductId(productId);
            response.put("usedList", usedList);
        }
        return response;
    }

    /**
     * 绑定优惠券定义组
     *
     * @param defineId
     * @param groupIds
     * @param type
     * @return
     */
    @RequestMapping(value = "/bindGroup", method = RequestMethod.POST)
    public Response bindGroup(String defineId, Long[] groupIds, Integer type) {
        SuccessResponse result = new SuccessResponse();
        if (type == null) {
            result.setStatus(-1);
            result.setMessage("类型不能为空");
            return result;
        }
        if (groupIds == null) {
            return result;
        }
        CouponDefGroup couponDefGroup = null;
        String newIds = "";
        for (Long groupId : groupIds) {
            couponDefGroup = couponDefGroupQueryService.findById(groupId);
            if (couponDefGroup == null) {
                continue;
            }
            if (type == 0) {// 固定
                newIds = this.createNewIds(couponDefGroup.getFixDefIds(), defineId);
                couponDefGroupService.updateFixDefIds(groupId, newIds);
            } else {// 随机
                newIds = this.createNewIds(couponDefGroup.getRandomDefIds(), defineId);
                couponDefGroupService.updateRandomDefIds(groupId, newIds);
            }
        }
        return result;
    }

    private String createNewIds(String oldIds, String newIds) {
        String result = "";
        if (StringUtils.isBlank(oldIds)) {
            result = newIds;
        }
        List<Long> oldList = StringUtil.strToLongList(oldIds);
        List<Long> newList = StringUtil.strToLongList(newIds);
        List<Long> tempList = new ArrayList<>();
        boolean isEquals;
        for (Long newId : newList) {
            isEquals = false;
            if (newId == null) {
                continue;
            }
            for (Long oldId : oldList) {
                if (newId.equals(oldId)) {
                    isEquals = true;
                    break;
                }
            }
            if (!isEquals) {
                tempList.add(newId);
            }
        }
        if (tempList.size() > 0) {
            oldList.addAll(tempList);
            result = StringUtil.longListToStr(oldList);
        } else {
            result = oldIds;
        }
        return result;
    }

    /**
     * 校验优惠券定义名称
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    public Response checkName(String name) {
        SuccessResponse result = new SuccessResponse();
        if (couponDefQueryService.findByName(name) != null) {
            result.setStatus(-1);
            result.setMessage("操作不成功，优惠券名称不能重复！");
        }
        return result;
    }

    /**
     * 根据优惠券定义批量发放优惠券
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/send/{defineId}", method = RequestMethod.POST)
    public Response send(@PathVariable Long defineId) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        couponService.doSendCouponByDefineId(defineId, admin.getUsername(), "批量发放优惠券");
        Response resp = new SuccessResponse();
        resp.setMessage("发放成功！");
        return resp;
    }

    /**
     * 批量发放该优惠券
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response send(Long ids[]) {
        for (int i = 0; i < ids.length; i++) {
            couponDefService.doSendById(ids[i]);
        }
        return new SuccessResponse();
    }

    /**
     * 批量上下架
     *
     * @param flag
     * @param ids
     * @return
     */
    @RequestMapping(value = "/updateEnable/{flag}", method = RequestMethod.POST)
    public Response updateEnable(@PathVariable Integer flag, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long promotionId : ids) {
            couponDefService.doMark(flag, promotionId);
        }
        // 单个时返回修改状态
        if (ids.length == 1) {
            result.put("enable", flag);
        }
        result.setMessage("操作成功！");
        return result;
    }

    /**
     * 校验暗码
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/checkCipher", method = RequestMethod.POST)
    public Response checkCipher(CouponDefSearcher searcher) {
        if (couponDefQueryService.findByCipher(searcher.getCipher()) != null) {
            return new ErrorResponse("校验暗码已存在！");
        }
        return new SuccessResponse();
    }

    /**
     * 创建微信D2C卡券,优惠券定义绑定微信卡券
     *
     * @param model
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/create/wxcard", method = RequestMethod.POST)
    public Response createWxcard(Long couponDefId) throws JSONException {
        SuccessResponse result = new SuccessResponse();
        CouponDef couponDef = couponDefQueryService.findById(couponDefId);
        // 创建微信D2C卡券
        Signature signature = signatureService.findByAppid(weixinGzOauthClient.getWEIXIN_GZ_APPKEY());
        String url = WxCard.cardUrl + signature.getToken();
        WxCardCash card = new WxCardCash();
        // 满LeastCost减ReductCost
        card.setLeastCost(couponDef.getNeedAmount() * 100);
        card.setReductCost(couponDef.getAmount() * 100);
        WxCardBaseInfo baseInfo = card.getBaseInfo();
        baseInfo.setLogoUrl(WxCardBaseInfo.logoUrl);
        // 使用时间种类不同
        if (couponDef.getActiveDay() > 0) {
            baseInfo.setDateInfoFixTerm(couponDef.getActiveDay(), 0);
        } else {
            baseInfo.setDateInfoTimeRange(couponDef.getEnableDate(), couponDef.getExpireDate());
        }
        baseInfo.setBrandName("D2C");
        // 标题
        baseInfo.setTitle(couponDef.getName());
        // 副标题
        baseInfo.setSubTitle(couponDef.getSubTitle());
        baseInfo.setCodeType(1);
        baseInfo.setColor("Color100");
        baseInfo.setNotice("请主动出示卡券");
        // 使用说明
        baseInfo.setDescription(couponDef.getRemark());
        // 总发行量
        baseInfo.setQuantity(couponDef.getQuantity());
        baseInfo.setServicePhone("4008-403-666");
        baseInfo.setCustomUrlName("立即使用");
        baseInfo.setCustomUrl("http://m.d2cmall.com" + "/wxcard");
        baseInfo.setCustomUrlSubTitle("我的新衣");
        // 单人限领用 限使用
        baseInfo.setGetLimit(couponDef.getClaimLimit());
        baseInfo.setUseLimit(couponDef.getClaimLimit());
        baseInfo.setUseCustomCode(false);
        baseInfo.setBindOpenid(false);
        // 是否允许转让
        baseInfo.setCanGiveFriend(couponDef.getTransfer());
        baseInfo.setCanShare(true);
        try {
            String responseText = WxCardCreate.doPostJson(url, card.toString());
            JSONObject responseJo = JSONObject.parseObject(responseText);
            if (responseJo.getInteger("errcode") == 0) {
            } else {
                result.setStatus(-1);
                result.setMessage("微信绑定不成功，错误信息：" + responseText);
                return result;
            }
            String wxCardId = responseJo.getString("card_id");
            couponDefService.doBindWxCard(wxCardId, couponDefId);
            result.setMessage("生成成功，微信卡券ID：" + wxCardId);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage("微信接口异常");
            return result;
        }
        return result;
    }

    /**
     * 查询D2C卡券
     *
     * @param model
     * @param cardId
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/check/wxcard/{cardId}", method = RequestMethod.POST)
    public Response createWxcard(@PathVariable String cardId) throws JSONException {
        SuccessResponse result = new SuccessResponse();
        Signature signature = signatureService.findByAppid(weixinGzOauthClient.getWEIXIN_GZ_APPKEY());
        String url = WxCard.checkUrl + signature.getToken();
        org.json.JSONObject m_data = new org.json.JSONObject();
        m_data.put("card_id", cardId);
        try {
            String responseText = WxCardCreate.doPostJson(url, m_data.toString());
            result.setMessage(responseText);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage("微信接口异常");
            return result;
        }
        return result;
    }

}
