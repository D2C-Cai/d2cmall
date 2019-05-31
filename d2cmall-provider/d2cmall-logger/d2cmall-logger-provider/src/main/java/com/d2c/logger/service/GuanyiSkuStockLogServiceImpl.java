package com.d2c.logger.service;

import com.d2c.logger.dao.GuanyiSkuStockLogMapper;
import com.d2c.logger.model.GuanyiSkuStockLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("guanyiSkuStockLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class GuanyiSkuStockLogServiceImpl extends ListServiceImpl<GuanyiSkuStockLog>
        implements GuanyiSkuStockLogService {

    @Autowired
    private GuanyiSkuStockLogMapper guanyiSkuStockLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void insert(GuanyiSkuStockLog guanyiSkuStockLog) {
        Integer result = guanyiSkuStockLogMapper.updateBySku(guanyiSkuStockLog.getSku(), guanyiSkuStockLog.getStock());
        if (result == 0) {
            this.save(guanyiSkuStockLog);
        }
    }

}
