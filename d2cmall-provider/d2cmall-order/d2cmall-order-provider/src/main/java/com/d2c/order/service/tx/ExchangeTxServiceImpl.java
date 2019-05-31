package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.ExchangeLog.ExchangeLogType;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.order.model.Exchange;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service(protocol = {"dubbo"})
public class ExchangeTxServiceImpl implements ExchangeTxService {

    @Autowired
    private ExchangeService exchangeService;
    @Reference
    private OrderItemTxService orderItemTxService;
    @Autowired
    private ExchangeLogService exchangeLogService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doReceive(Long exchangeId, String modifyMan, String info) {
        Exchange exchange = exchangeService.findById(exchangeId);
        if (exchange.getExchangeStatus().equals(ExchangeStatus.DELIVERED.getCode())) {
            exchange.setExchangeStatus(ExchangeStatus.SUCCESS.getCode());
            int success = exchangeService.doReceive(exchange.getId());
            if (success > 0) {
                orderItemTxService.doSysSuccess(exchange.getOrderItemId(), modifyMan, false, new BigDecimal(0));
                this.insertExchangeLog(exchange.getId(), exchange.getOrderItemId(), exchange.getOrderId(), info,
                        ExchangeLogType.completed, modifyMan);
            }
            return success;
        } else {
            throw new BusinessException("确认收货不成功，状态不匹配！");
        }
    }

    private void insertExchangeLog(Long exchangeId, Long orderItemId, Long orderId, String info, ExchangeLogType type,
                                   String creator) {
        ExchangeLog exchangeLog = new ExchangeLog();
        exchangeLog.setCreateDate(new Date());
        exchangeLog.setCreator(creator);
        exchangeLog.setInfo(info);
        exchangeLog.setExchangeLogType(type);
        exchangeLog.setExchangeId(exchangeId);
        exchangeLog.setOrderItemId(orderItemId);
        exchangeLog.setOrderId(orderId);
        exchangeLogService.insert(exchangeLog);
    }

}
