package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.dto.RequisitionSummaryDto;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.query.RequisitionItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RequisitionItemMapper extends SuperMapper<RequisitionItem> {

    RequisitionItem findByOrderItemId(@Param("orderItemId") Long orderItemId);

    List<RequisitionItem> findByRequisitionId(Long requisitionId);

    RequisitionItem findLastOne(@Param("type") Integer type);

    RequisitionItem findByRequisitionSn(String requisitionSn);

    List<RequisitionItem> findByDeliverySn(@Param("sn") String sn);

    List<RequisitionItem> findBySearcher(@Param("searcher") RequisitionItemSearcher searcher,
                                         @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") RequisitionItemSearcher searcher);

    List<RequisitionSummaryDto> findSummary(@Param("type") Integer type, @Param("pager") PageModel pager);

    int countSummary(@Param("type") Integer type);

    List<Map<String, Object>> countGroupByDesignerNew();

    List<Map<String, Object>> countGroupByDesignerExpired();

    List<Map<String, Object>> countGroupByDesignerReship(@Param("importSn") List<String> importSn);

    List<Map<String, Object>> countGroupByStore();

    int doSubmit(@Param("id") Long id);

    int doSign(@Param("id") Long id);

    int doRefuse(@Param("id") Long id, @Param("delayMan") String delayMan, @Param("delayReason") String delayReason);

    int doDelivery(@Param("id") Long id, @Param("quantity") Integer quantity, @Param("deliverySn") String deliverySn,
                   @Param("deliveryCorp") String deliveryCorp);

    int doCancelDelivery(@Param("id") Long id);

    int doReceive(@Param("id") Long id, @Param("quantity") Integer quantity);

    int doFine(@Param("id") Long id, @Param("fine") Integer fine);

    int doDeliverToCustomer(@Param("id") Long id);

    int doDeliverByOrderItem(@Param("orderItemId") Long orderItemId);

    int doClose(@Param("id") Long id, @Param("closeMan") String closeMan, @Param("closeReason") String closeReason);

    int doCancelClose(@Param("id") Long id, @Param("type") Integer type, @Param("contact") String contact,
                      @Param("tel") String tel, @Param("address") String address, @Param("storeId") Long storeId,
                      @Param("storeName") String storeName, @Param("direct") Integer direct);

    int doCloseByOrderItem(@Param("orderItemId") Long orderItemId, @Param("closeMan") String closeMan,
                           @Param("closeReason") String closeReason);

    int doLock(@Param("id") Long id, @Param("lock") int lock);

    int doHandle(@Param("id") Long id, @Param("handle") Integer handle, @Param("operator") String operator);

    int doStoreComment(@Param("id") Long id, @Param("storeComment") String storeComment,
                       @Param("responseSpeed") Integer responseSpeed);

    int updateRemark(@Param("id") Long id, @Param("remark") String remark);

    int updateStoreMemo(@Param("id") Long id, @Param("memo") String memo);

    int updateDesignerMemo(@Param("id") Long id, @Param("memo") String memo);

    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int updateDeliveryQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int updateDefectiveQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int updateReceiveQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int updateDesignerEstimateDate(@Param("id") Long id, @Param("memo") String memo, @Param("date") Date date);

    int updateBarCode(@Param("id") Long id, @Param("barCode") String barCode, @Param("sp1") String sp1,
                      @Param("sp2") String sp2);

    int updateLogisticAddress(@Param("id") Long id, @Param("contact") String contact, @Param("tel") String tel,
                              @Param("address") String address, @Param("allocateReason") String allocateReason);

    int updateDeliveryInfo(@Param("id") Long id, @Param("deliveryCorp") String deliveryCorp,
                           @Param("deliverySn") String deliverySn, @Param("operator") String operator);

    int updatePriority(@Param("id") Long id, @Param("priority") Integer priority);

    int updateLastLogInfo(@Param("id") Long requisitionItemId, @Param("info") String info);

    int updateReceive(@Param("id") Long id);

    /**
     * 查询门店超时调拨单
     *
     * @param page
     * @return
     */
    List<RequisitionItem> findStoreExpired(@Param("page") PageModel page);

    /**
     * 统计门店超时调拨单
     *
     * @return
     */
    int countStoreExpired();

    /**
     * 根据门店统计超时调拨单
     *
     * @return
     */
    List<Map<String, Object>> findExpireByStoreId(@Param("deadline") Date deadline);

    /**
     * 查询设计师超时赔偿
     *
     * @param page
     * @param deadline
     * @return
     */
    List<RequisitionItem> findDesignerExpired(@Param("page") PageModel page, @Param("deadline") Date deadline);

    /**
     * 统计设计师赔偿
     *
     * @param deadline
     * @return
     */
    int countDesignerExpired(@Param("deadline") Date deadline);

}
