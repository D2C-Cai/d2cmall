package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.*;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.ExchangeTxService;
import com.d2c.order.third.kd100.core.JacksonHelper;
import com.d2c.order.third.kd100.core.NoticeRequest;
import com.d2c.order.third.kd100.core.NoticeResponse;
import com.d2c.order.third.kd100.core.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class KuaiDi100Controller extends BaseController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private SettingService settingService;
    @Reference
    private ExchangeTxService exchangeTxService;

    @RequestMapping(value = "/kuaidi100", method = RequestMethod.POST)
    public void callBack(HttpServletResponse response, HttpServletRequest request) {
        NoticeResponse resp = new NoticeResponse();
        resp.setResult(false);
        resp.setReturnCode("500");
        resp.setMessage("保存不成功");
        int success = 1;
        try {
            // XssRequestWrapper 处理过param，再转回来
            String param = convertParams(request.getParameter("param"));
            NoticeRequest nReq = JacksonHelper.fromJSON(param, NoticeRequest.class);
            Result result = nReq.getLastResult();
            // 保存物流信息
            if (result.getData().size() > 0) {
                Logistics logistics = new Logistics();
                logistics.setDeliverySn(result.getNu());
                logistics.setDeliveryInfo(JSONArray.toJSONString(result.getData()));
                logistics.setDeliveryCode(result.getCom());
                logistics.setStatus(
                        StringUtils.isNotEmpty(result.getState()) ? Integer.parseInt(result.getState()) : null);
                logistics.setLastModifyMan("sys");
                logisticsService.update(logistics);
            }
            if ("5".equals(result.getState())) {
                List<OrderItemDto> orderItems = orderItemService.findByDeliverySn(result.getNu());
                if (orderItems != null) {
                    String subject = "商品配送提醒";
                    String content = "您的商品" + orderItems.get(0).getProductName() + "正在配送中";
                    PushBean pushBean = new PushBean(orderItems.get(0).getBuyerMemberId(), content, 11);
                    pushBean.setAppUrl("/details/order/" + orderItems.get(0).getOrderSn());
                    MsgBean msgBean = new MsgBean(orderItems.get(0).getBuyerMemberId(), 11, subject, content);
                    msgBean.setAppUrl("/details/order/" + orderItems.get(0).getOrderSn());
                    msgUniteService.sendPush(pushBean, msgBean);
                }
            }
            // 处理结果，3表示已签收
            if (result.getState().equals("3")) {
                List<Logistics> logisticsList = logisticsService.findAllBySn(result.getNu(), result.getCom());
                for (Logistics newLogistics : logisticsList) {
                    if (newLogistics != null) {
                        switch (Logistics.BusinessType.valueOf(newLogistics.getType())) {
                            case ORDER:
                                List<OrderItemDto> orderItems = orderItemService.findByDeliverySn(result.getNu());
                                for (OrderItemDto orderItem : orderItems) {
                                    LogisticsCompany logisticsCompany = logisticsService
                                            .findCompanyByName(orderItem.getDeliveryCorpName());
                                    String com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
                                    if (com.equals(result.getCom())
                                            && OrderItem.ItemStatus.DELIVERED.name().equals(orderItem.getStatus())) {
                                        Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
                                        success = orderItemService.doSign(orderItem.getId(), "sys", "物流显示签收，系统确认收货",
                                                Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString()));
                                    }
                                }
                                break;
                            case ALLOT:
                                List<RequisitionItem> list = requisitionItemService.findByDeliverySn(result.getNu());
                                for (RequisitionItem item : list) {
                                    requisitionItemService.updateReceive(item.getId());
                                }
                                break;
                            case RESHIP:
                                List<Reship> reships = reshipService.findByDeliverySn(result.getNu());
                                for (Reship reship : reships) {
                                    LogisticsCompany logisticsCompany = logisticsService
                                            .findCompanyByName(reship.getDeliveryCorpName());
                                    String com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
                                    if (com.equalsIgnoreCase(result.getCom())
                                            && reship.getReshipStatus() == Reship.ReshipStatus.WAITFORRECEIVE.getCode()) {
                                        ReshipLog reshipLog = new ReshipLog();
                                        reshipLog.setLogType(ReshipLog.ReshipLogType.sign.getCode());
                                        reshipLog.setCreator("sys");
                                        reshipLog.setInfo("物流显示已签收，待仓库确认收货");
                                        reshipLog.setReshipId(reship.getId());
                                        reshipLog.setOrderId(reship.getOrderId());
                                        reshipLog.setOrderItemId(reship.getOrderItemId());
                                        reshipLogService.insert(reshipLog);
                                    }
                                }
                                break;
                            case DELIVERY:
                                List<Exchange> exchangesD = exchangeService.findByDeliverySn(result.getNu());
                                for (Exchange exchange : exchangesD) {
                                    LogisticsCompany logisticsCompany = logisticsService
                                            .findCompanyByName(exchange.getExchangeDeliveryCorp());
                                    String com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
                                    if (result.getCom().equalsIgnoreCase(com)
                                            && result.getNu().equals(exchange.getExchangeDeliverySn())
                                            && exchange.getExchangeStatus() == Exchange.ExchangeStatus.DELIVERED.getCode())
                                        exchangeTxService.doReceive(exchange.getId(), "sys", "物流显示已签收，系统确认收货");
                                }
                                break;
                            case EXCHANGE:
                                List<Exchange> exchangesE = exchangeService.findByDeliverySn(result.getNu());
                                for (Exchange exchange : exchangesE) {
                                    LogisticsCompany logisticsCompany = logisticsService
                                            .findCompanyByName(exchange.getExchangeDeliveryCorp());
                                    String com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
                                    if (result.getCom().equalsIgnoreCase(com)
                                            && result.getNu().equals(exchange.getDeliverySn())
                                            && exchange.getExchangeStatus() == Exchange.ExchangeStatus.WAITFORRECEIVE
                                            .getCode()) {
                                        ExchangeLog exchangeLog = new ExchangeLog();
                                        exchangeLog.setLogType(ExchangeLog.ExchangeLogType.sign.getCode());
                                        exchangeLog.setInfo("物流显示已签收，待仓库确认收货");
                                        exchangeLog.setExchangeId(exchange.getId());
                                        exchangeLog.setOrderId(exchange.getOrderId());
                                        exchangeLog.setOrderItemId(exchange.getOrderItemId());
                                        exchangeLog.setCreator("sys");
                                        exchangeLogService.insert(exchangeLog);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            if (success > 0) {
                resp.setResult(true);
                resp.setReturnCode("200");
                resp.setMessage("保存成功");
                response.getWriter().print(JacksonHelper.toJSON(resp)); // 这里必须返回，否则认为不成功，过30分钟又会重复推送。
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resp.setMessage("保存不成功" + e.getMessage());
            try {
                response.getWriter().print(JacksonHelper.toJSON(resp));
            } catch (IOException e1) {
                logger.error(e.getMessage());
            } // 保存不成功，服务端等30分钟会重复推送。
        }
    }

    /**
     * 处理字符串
     *
     * @param param
     * @return
     */
    private String convertParams(String param) {
        if (StringUtils.isNotBlank(param)) {
            param = param.replaceAll("“", "\"").replaceAll("&quot;", "\"").replaceAll("&#39;", "\'").replaceAll("&amp;",
                    "&");
        }
        return param;
    }

}
