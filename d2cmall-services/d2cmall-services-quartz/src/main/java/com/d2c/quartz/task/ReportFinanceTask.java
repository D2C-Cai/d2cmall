package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.member.service.AccountService;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.OrderReportService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.report.model.FinanceCodDailyAmount;
import com.d2c.report.model.FinanceOnlineDailyAmount;
import com.d2c.report.model.WalletDailyAmount;
import com.d2c.report.model.WalletSummary;
import com.d2c.report.service.FinanceCodDailyAmountService;
import com.d2c.report.service.FinanceOnlineDailyAmountService;
import com.d2c.report.service.WalletDailyAmountService;
import com.d2c.report.service.WalletSummaryService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ReportFinanceTask extends BaseTask {

    private static final Log logger = LogFactory.getLog(ReportFinanceTask.class);
    private static boolean executed = true;
    @Reference
    private FinanceOnlineDailyAmountService financeOnlineDailyAmountService;
    @Reference
    private FinanceCodDailyAmountService financeCodDailyAmountService;
    @Autowired
    private AccountItemService accountItemService;
    @Reference
    private WalletSummaryService walletSummaryService;
    @Autowired
    private OrderReportService orderReportService;
    @Reference
    private WalletDailyAmountService walletDailyAmountService;
    @Autowired
    private AccountService accountService;

    /**
     * 生成财务付款统计报表，按月统计，每月1号算上一个月的
     */
    @Scheduled(cron = "0 30 6 1 * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        try {
            this.createOnlineAmount();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            this.createCodAmount();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            this.createWalletAmount();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 钱包明细执行
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void execute2() {
        try {
            if (executed) {
                executed = false;
                doWalletSummary();
                logger.info(this.getClass().getName() + " executed");
            }
        } catch (Exception e) {
            logger.error("doWalletSummary error" + e.getMessage());
        } finally {
            executed = true;
        }
    }

    /**
     * 第三方支付
     *
     * @throws ParseException
     */
    private void createOnlineAmount() throws Exception {
        FinanceOnlineDailyAmount lastOne = financeOnlineDailyAmountService.findLastOne();
        // 发货金额
        List<Map<String, Object>> deliveryList = new ArrayList<>();
        // 仅退款
        List<Map<String, Object>> refundAmount = new ArrayList<>();
        // 退款退货
        List<Map<String, Object>> reshipAmount = new ArrayList<>();
        // 付款金额
        List<Map<String, Object>> readyAmount = new ArrayList<>();
        // 从2016-1-1号开始算起
        Date calculateDate = DateUtil.str2second("2016-01-01 00:00:00");
        // 从上次统计时间的后面一天算起
        if (lastOne != null) {
            calculateDate = DateUtil.getIntervalMonth(lastOne.getCalculateDate(), 1);
        }
        BigDecimal zero = new BigDecimal(0);
        // 统计至昨天为止
        while (formatDate(calculateDate).compareTo(formatDate(new Date())) < 0) {
            deliveryList = orderReportService.findOnlineDeliveryAmount(calculateDate);
            readyAmount = orderReportService.findOnlineReadyAmount(calculateDate);
            // 根据reshipId 是否为null区分仅退款和退款退货
            reshipAmount = orderReportService.findOnlineRefundAmount(calculateDate, true);
            refundAmount = orderReportService.findOnlineRefundAmount(calculateDate, false);
            for (PaymentTypeEnum payment : PaymentTypeEnum.values()) {
                // 除去第三方支付和钱包支付
                if (payment.getCode() != 7 && payment.getCode() != 3) {
                    FinanceOnlineDailyAmount f = new FinanceOnlineDailyAmount(calculateDate, payment.getCode());
                    // 发货金额
                    for (Map<String, Object> deliery : deliveryList) {
                        if (Integer.parseInt(deliery.get("payment_type").toString()) == payment.getCode()) {
                            f.setDeliveryAmount(new BigDecimal(deliery.get("delivery_amount").toString())
                                    .add(f.getDeliveryAmount()));
                            break;
                        }
                    }
                    // 仅退款
                    for (Map<String, Object> refund : refundAmount) {
                        if (Integer.parseInt(refund.get("payment_type").toString()) == payment.getCode()) {
                            f.setRefundAmount(
                                    new BigDecimal(refund.get("refund_amount").toString()).add(f.getRefundAmount()));
                            break;
                        }
                    }
                    // 订单金额
                    for (Map<String, Object> ready : readyAmount) {
                        if (Integer.parseInt(ready.get("payment_type").toString()) == payment.getCode()) {
                            f.setPreAmount(new BigDecimal(ready.get("pre_amount").toString()).add(f.getPreAmount()));
                            break;
                        }
                    }
                    // 退款退货
                    for (Map<String, Object> reship : reshipAmount) {
                        if (Integer.parseInt(reship.get("payment_type").toString()) == payment.getCode()) {
                            f.setReshipAmount(
                                    f.getReshipAmount().add(new BigDecimal(reship.get("refund_amount").toString())));
                            break;
                        }
                    }
                    // 都是0的就不要插入了
                    if (f.getRefundAmount().compareTo(zero) != 0 || f.getPreAmount().compareTo(zero) != 0
                            || f.getDeliveryAmount().compareTo(zero) != 0) {
                        financeOnlineDailyAmountService.insert(f);
                    }
                }
            }
            calculateDate = DateUtil.getIntervalMonth(calculateDate, 1);
        }
    }

    /**
     * 货到付款
     *
     * @throws ParseException
     */
    private void createCodAmount() throws Exception {
        FinanceCodDailyAmount lastOne = financeCodDailyAmountService.findLastCod();
        Date calculateDate = DateUtil.str2second("2016-1-1 00:00:00");
        // 从上次统计时间的后面一天算起
        if (lastOne != null) {
            calculateDate = DateUtil.getIntervalMonth(lastOne.getCalculateDate(), 1);
        }
        BigDecimal zero = new BigDecimal(0);
        while (formatDate(calculateDate).compareTo(formatDate(new Date())) < 0) { // 订单金额
            BigDecimal readyAmount = orderReportService.findCodReadyAmount(calculateDate);
            // 发货金额
            BigDecimal deliveryAmount = orderReportService.findCodDeliveryAmount(calculateDate);
            // 退款金额
            BigDecimal refundAmount = orderReportService.findCodRefundAmount(calculateDate);
            // 结算金额
            BigDecimal balanceAmount = orderReportService.findCodBalanceAmount(calculateDate);
            // 拒收的取申请金额
            BigDecimal refuseAmount = orderReportService.findRefuseAmount(calculateDate);
            if (readyAmount.compareTo(zero) != 0 || deliveryAmount.compareTo(zero) != 0
                    || refundAmount.compareTo(zero) != 0 || balanceAmount.compareTo(zero) != 0) {
                FinanceCodDailyAmount f = new FinanceCodDailyAmount();
                f.setReadyAmount(readyAmount);
                f.setDeliveryAmount(deliveryAmount);
                f.setRefundAmount(refundAmount);
                f.setBalanceAmount(balanceAmount);
                f.setCalculateDate(calculateDate);
                f.setRefuseAmount(refuseAmount);
                financeCodDailyAmountService.insert(f);
            }
            calculateDate = DateUtil.getIntervalMonth(calculateDate, 1);
        }
    }

    // 每月1号执行，算上个月的数据
    // 钱包支付的订单
    private void createWalletAmount() throws Exception {
        List<Map<String, Object>> readyAmount = new ArrayList<>();
        List<Map<String, Object>> deliveryAmount = new ArrayList<>();
        List<Map<String, Object>> refundAmount = new ArrayList<>();
        List<Map<String, Object>> reshipAmount = new ArrayList<>();
        // 查找最近一条记录的统计时间
        WalletDailyAmount lastOne = walletDailyAmountService.findLastOne();
        Date calculateDate = DateUtil.str2second("2016-1-1 00:00:00");
        if (lastOne != null) {
            calculateDate = DateUtil.getIntervalMonth(lastOne.getCalculateDate(), 1);
        }
        while (formatDate(calculateDate).compareTo(formatDate(new Date())) < 0) {
            WalletDailyAmount walletAmount = new WalletDailyAmount();
            deliveryAmount = orderReportService.findWalletDeliveryAmount(calculateDate);
            readyAmount = orderReportService.findWalletReadyAmount(calculateDate);
            // 根据reshipId 是否为null区分仅退款和退款退货
            reshipAmount = orderReportService.findWalletRefundAmount(calculateDate, true);
            refundAmount = orderReportService.findWalletRefundAmount(calculateDate, false);
            for (Map<String, Object> map : deliveryAmount) {
                walletAmount.setDeliveryAmount(new BigDecimal(map.get("amount").toString()));
            }
            for (Map<String, Object> map : readyAmount) {
                walletAmount.setReadyAmount(new BigDecimal(map.get("amount").toString()));
                walletAmount.setReadyGiftAmount(new BigDecimal(map.get("giftAmount").toString()));
            }
            for (Map<String, Object> map : reshipAmount) {
                walletAmount.setReshipAmount(new BigDecimal(map.get("amount").toString()));
                walletAmount.setReshipGiftAmount(new BigDecimal(map.get("giftAmount").toString()));
            }
            for (Map<String, Object> map : refundAmount) {
                walletAmount.setRefundAmount(new BigDecimal(map.get("amount").toString()));
                walletAmount.setRefundGiftAmount(new BigDecimal(map.get("giftAmount").toString()));
            }
            walletAmount.setCalculateDate(calculateDate);
            walletDailyAmountService.insert(walletAmount);
            calculateDate = DateUtil.getIntervalMonth(calculateDate, 1);
        }
    }

    /**
     * 钱包收支金额统计
     *
     * @throws ParseException
     */
    private void doWalletSummary() throws Exception {
        WalletSummary walletSummary = walletSummaryService.findLastOne();
        Date calculateDate = DateUtil.str2second("2016-1-1 00:00:00");
        if (walletSummary != null) {
            calculateDate = DateUtil.getIntervalMonth(walletSummary.getCalculateDate(), 1);
        }
        while (formatDate(calculateDate).compareTo(formatDate(new Date())) < 0) {
            // 当前总的钱包余额
            try {
                Map<String, BigDecimal> accounts = accountService.countAccountAmount();
                if (accounts.size() > 0) {
                    WalletSummary f = new WalletSummary();
                    f.setAmount(new BigDecimal(accounts.get("cashAmount").toString()));
                    f.setGiftAmount(new BigDecimal(accounts.get("giftAmount").toString()));
                    f.setBusinessType("钱包总额");
                    f.setDirection(1);
                    f.setCalculateDate(new Date());
                    walletSummaryService.insert(f);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            // 查出钱包各消费明细
            List<Map<String, Object>> walletList = accountItemService.findWalletAmount(calculateDate);
            for (Map<String, Object> map : walletList) {
                try {
                    WalletSummary f = new WalletSummary();
                    f.setBusinessType(String.valueOf(map.get("businessType")));
                    f.setCalculateDate(calculateDate);
                    f.setAmount(new BigDecimal(String.valueOf(map.get("amount"))));
                    f.setGiftAmount(new BigDecimal(String.valueOf(map.get("giftAmount"))));
                    f.setDirection(Integer.parseInt(String.valueOf(map.get("direction"))));
                    f.setPayType(String.valueOf(map.get("payType")));
                    walletSummaryService.insert(f);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            calculateDate = DateUtil.getIntervalMonth(calculateDate, 1);
        }
    }

    private Date formatDate(Date date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        return sf.parse(sf.format(date));
    }

}
