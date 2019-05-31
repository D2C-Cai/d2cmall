package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.query.RefundSearcher;
import com.d2c.order.query.ReshipSearcher;
import com.d2c.order.service.*;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class NoticeCompensationTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private BrandService brandService;

    @Scheduled(cron = "0 0 14 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        Setting setting = settingService.findByCode(Setting.COMPENSATION);
        if (setting != null && setting.getStatus() == 1) {
            // this.expiredDelivery();
            // this.expitedReship();
            // this.expiredRefund();
        }
        this.expiredDesignerRequisition();
        this.expiredStoreRequisition();
    }

    /**
     * 超过预计发货时间1天的告知有赔偿
     *
     * @throws ParseException
     */
    private void expiredDelivery() throws ParseException {
        Date nowDate = new Date();
        Date beginDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -2));
        Date endDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -1));
        PageModel page = new PageModel();
        PageResult<OrderItem> pager = new PageResult<>(page);
        StringBuffer buffer = new StringBuffer();
        boolean expired = false;
        do {
            pager = orderItemService.findExpiredDelivery(beginDate, endDate, page);
            for (OrderItem orderItem : pager.getList()) {
                // 设计师调拨的情况
                if (orderItem.getRequisition() == 2 || orderItem.getRequisition() == 8) {
                    RequisitionItem requisitionItem = requisitionItemService.findByOrderItemId(orderItem.getId());
                    if (requisitionItem != null && DateUtil.daysBetween(orderItem.getEstimateDate(),
                            requisitionItem.getDeliveryDate()) >= 1) {
                        // 发送信息
                        expired = true;
                    }
                } else {
                    expired = true;
                }
                if (expired) {
                    buffer.append("尊敬的用户，因您的订单").append(orderItem.getOrderSn()).append("中的商品")
                            .append(orderItem.getProductName())
                            .append("超时未发货。 根据D2C客户服务规则，针对该商品发货延迟的客户赔偿已生效。赔付款项将在该订单完结后汇入您的D2C钱包，感谢您的关注！");
                    String subject = "发货超期赔偿";
                    String content = buffer.toString();
                    PushBean pushBean = new PushBean(orderItem.getBuyerMemberId(), content, 29);
                    MsgBean msgBean = new MsgBean(orderItem.getBuyerMemberId(), 29, subject, content);
                    msgUniteService.sendPush(pushBean, msgBean);
                    if (buffer.length() > 0) {
                        buffer.delete(0, buffer.length());
                    }
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
    }

    /**
     * 退款退货客服响应时间
     */
    private void expitedReship() {
        Date nowDate = new Date();
        Date beginDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -2));
        Date endDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -1));
        ReshipSearcher searcher = new ReshipSearcher();
        searcher.setBeginCreateDate(beginDate);
        searcher.setEndCreateDate(endDate);
        searcher.setReshipStatus(new Integer[]{ReshipStatus.APPLY.getCode()});
        PageModel page = new PageModel();
        PageResult<ReshipDto> pager = new PageResult<>();
        StringBuffer buffer = new StringBuffer();
        do {
            pager = reshipService.findBySearch(searcher, page);
            for (Reship reship : pager.getList()) {
                // 发送消息
                buffer.append("尊敬的用户，因您的退货单").append(reship.getReshipSn()).append("中的商品")
                        .append(reship.getProductName())
                        .append("超时未处理。 根据D2C客户服务规则，针对该退货单处理延迟的客户赔偿已生效。赔付款项将在该订单完结后汇入您的D2C钱包，感谢您的关注！");
                String subject = "售后处理超期赔偿";
                String content = buffer.toString();
                PushBean pushBean = new PushBean(reship.getMemberId(), content, 23);
                MsgBean msgBean = new MsgBean(reship.getMemberId(), 23, subject, content);
                msgUniteService.sendPush(pushBean, msgBean);
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
    }

    private void expiredRefund() {
        Date nowDate = new Date();
        Date beginDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -2));
        Date endDate = DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -1));
        RefundSearcher searcher = new RefundSearcher();
        searcher.setBeginCreateDate(beginDate);
        searcher.setEndCreateDate(endDate);
        searcher.setRefundStatus(new Integer[]{RefundStatus.APPLY.getCode()});
        PageModel page = new PageModel();
        PageResult<RefundDto> pager = new PageResult<>();
        StringBuffer buffer = new StringBuffer();
        do {
            pager = refundService.findBySearch(searcher, page);
            for (Refund refund : pager.getList()) {
                // 发送消息
                buffer.append("尊敬的用户，因您的售后单").append(refund.getRefundSn()).append("中的商品")
                        .append(refund.getProductName())
                        .append("超时未处理。 根据D2C客户服务规则，针对该售后单处理延迟的客户赔偿已生效。赔付款项将在该订单完结后汇入您的D2C钱包，感谢您的关注！");
                String subject = "售后处理超期赔偿";
                String content = buffer.toString();
                PushBean pushBean = new PushBean(refund.getMemberId(), content, 22);
                pushBean.setAppUrl("/member/refund" + refund.getId());
                MsgBean msgBean = new MsgBean(refund.getMemberId(), 22, subject, content);
                msgBean.setAppUrl("/member/refund" + refund.getId());
                msgBean.setPic(refund.getProductImg());
                msgUniteService.sendPush(pushBean, msgBean);
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
        // 财务的
        searcher = new RefundSearcher();
        searcher.setBeginAuditDate(DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -8)));
        searcher.setEndAuditDate(DateUtil.getStartOfDay(DateUtil.add(nowDate, Calendar.DAY_OF_YEAR, -7)));
        searcher.setRefundStatus(new Integer[]{RefundStatus.WAITFORPAYMENT.getCode()});
        do {
            pager = refundService.findBySearch(searcher, page);
            for (Refund refund : pager.getList()) {
                // 发送消息
                buffer.append("尊敬的用户，因您的售后单").append(refund.getRefundSn()).append("中的商品")
                        .append(refund.getProductName())
                        .append("超时未处理。 根据D2C客户服务规则，针对该售后单处理延迟的客户赔偿已生效。赔付款项将在该订单完结后汇入您的D2C钱包，感谢您的关注！");
                String subject = "售后处理超期赔偿";
                String content = buffer.toString();
                PushBean pushBean = new PushBean(refund.getMemberId(), content, 22);
                MsgBean msgBean = new MsgBean(refund.getMemberId(), 22, subject, content);
                msgBean.setAppUrl("/member/refund" + refund.getId());
                msgBean.setPic(refund.getProductImg());
                msgUniteService.sendPush(pushBean, msgBean);
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
    }

    private void expiredDesignerRequisition() {
        List<Map<String, Object>> list = requisitionItemService.countGroupByDesignerExpired();
        for (Map<String, Object> map : list) {
            Long designerId = (Long) map.get("designerId");
            String designerName = map.get("designerName").toString();
            Long count = (Long) map.get("count");
            if (designerId != null && designerId != 0) {
                Brand brand = brandService.findById(designerId);
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String subject = "采购单提醒";
                    String content = "亲爱的" + designerName + "品牌主理人，截止至"
                            + DateUtil.convertDate2Str(new Date(), "yyyy年MM月dd日 HH:mm") + "您还有" + count
                            + "条未处理的采购单，需在今日之内做相关处理，超期将按照D2C规则进行赔偿，请您及时处理，谢谢！";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    pushBean.setAppUrl("/rest/order/requisitionitem/list?status=1&type=124");
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    msgBean.setAppUrl("/rest/order/requisitionitem/list?status=1&type=124");
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                }
            }
        }
    }

    /**
     * 提醒门店
     */
    private void expiredStoreRequisition() {
        Date date = DateUtil.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        List<Map<String, Object>> maps = requisitionItemService.findExpiredByStoreId(calendar.getTime());
        for (Map<String, Object> map : maps) {
            Long storeId = (Long) map.get("storeId");
            Integer count = Integer.parseInt(map.get("count").toString());
            String storeName = map.get("storeName").toString();
            MemberInfo memberInfo = memberInfoService.findByStoreId(storeId);
            String content = "亲爱的" + storeName + "门店管理员，截止至" + DateUtil.second2str(new Date()) + ", 您有" + count
                    + "条调拨单将于明天凌晨1点超期，为防止超期赔偿，请及时处理。";
            MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, "调拨单提醒", content);
            SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(), SmsLogType.REMIND,
                    content);
            msgUniteService.sendMsgBoss(smsBean, null, msgBean, null);
        }
    }

}
