package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.AccountItem;
import com.d2c.order.query.AccountItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountItemMapper extends SuperMapper<AccountItem> {

    AccountItem findById(Long id);

    AccountItem findBySn(@Param("sn") String sn);

    AccountItem findByTransactionSn(@Param("transactionSn") String transactionSn, @Param("payType") String payType);

    List<AccountItem> findBySearch(@Param("searcher") AccountItemSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") AccountItemSearcher searcher);

    List<Map<String, Object>> findWalletAmount(@Param("calculateDate") Date calculateDate);

    int doConfirm(@Param("payId") Long payId, @Param("man") String man);

    int doCancel(@Param("payId") Long payId, @Param("man") String man, @Param("closeInfo") String closeInfo);

    BigDecimal sumTotalAmount(@Param("sourceId") Long sourceId);

    BigDecimal sumTotalGiftAmount(@Param("sourceId") Long sourceId);

    int updateTransactionInfo(@Param("billId") Long billId, @Param("transactionId") Long transactionId,
                              @Param("transactionInfo") String transactionInfo);

}
