package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Exchange;
import com.d2c.order.query.ExchangeSearcher;
import com.d2c.product.model.ProductSku;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ExchangeMapper extends SuperMapper<Exchange> {

    Exchange findByIdAndMemberInfoId(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    Exchange findBySn(@Param("exchangeSn") String exchangeSn);

    List<Exchange> findByDeliverySn(@Param("nu") String nu);

    List<Exchange> findBySearch(@Param("searcher") ExchangeSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ExchangeSearcher searcher);

    List<Exchange> findDeliveredByDate(@Param("date") Date date, @Param("interval") int interval,
                                       @Param("page") PageModel page);

    int countDeliveredByDate(@Param("date") Date date, @Param("interval") int interval);

    List<Map<String, Object>> countByStatusAndMemberId(@Param("memberInfoId") Long memberInfoId);

    int doLogistic(@Param("id") Long id, @Param("deliveryCorp") String deliveryCorp,
                   @Param("deliverySn") String deliverySn);

    int doReceive(@Param("id") Long id);

    int doCancel(Exchange exchange);

    int doCustomAgree(Exchange exchange);

    int doCustomRefuse(Exchange exchange);

    int doCustomClose(Exchange exchange);

    int doCustomerDeliver(Exchange exchange);

    int doStoreAgree(Exchange exchange);

    int doStoreRefuse(Exchange exchange);

    int updateRefundId(@Param("exchangeId") Long exchangeId, @Param("refundId") Long refundId);

    int updateToChangeSku(@Param("sku") ProductSku sku, @Param("exchangeId") Long exchangeId);

    int updateBackAddress(@Param("id") Long id, @Param("backAddress") String backAddress,
                          @Param("backMobile") String backMobile, @Param("backConsignee") String backConsignee);

}
