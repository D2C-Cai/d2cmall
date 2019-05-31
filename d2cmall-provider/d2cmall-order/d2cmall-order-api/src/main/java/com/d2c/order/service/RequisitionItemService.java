package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.BurgeonErrorLog.DocType;
import com.d2c.order.dto.RequisitionSummaryDto;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.query.RequisitionItemSearcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RequisitionItemService {

    /**
     * 根据订单明细ID查询明细
     *
     * @param orderItemId
     * @return
     */
    RequisitionItem findById(Long id);

    /**
     * 根据订单明细ID查询明细
     *
     * @param orderItemId
     * @return
     */
    RequisitionItem findByOrderItemId(Long orderItemId);

    /**
     * 根据单据ID查询明细
     *
     * @param requisitionId
     * @return
     */
    List<RequisitionItem> findByRequisitionId(Long requisitionId);

    /**
     * 根据订单明细ID查询明细
     *
     * @param id
     * @return
     */
    RequisitionItem findLastOne(Integer type);

    /**
     * 根据sn查询
     *
     * @param requisitionSn
     * @return
     */
    RequisitionItem findByRequisitionSn(String requisitionSn);

    /**
     * 根据发货物流号查询调拨单
     *
     * @param sn
     * @return
     */
    List<RequisitionItem> findByDeliverySn(String sn);

    /**
     * 查询明细
     *
     * @param searcher
     * @param pager
     * @return
     */
    PageResult<RequisitionItem> findBySearcher(RequisitionItemSearcher searcher, PageModel pager);

    /**
     * 查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(RequisitionItemSearcher searcher);

    /**
     * 汇总查看
     *
     * @param page
     * @return
     */
    PageResult<RequisitionSummaryDto> findSummary(Integer type, PageModel page);

    /**
     * 查询设计师还未处理的调拨单
     *
     * @return
     */
    List<Map<String, Object>> countGroupByDesignerNew();

    /**
     * 查询设计师即将赔偿的调拨单
     *
     * @return
     */
    List<Map<String, Object>> countGroupByDesignerExpired();

    /**
     * 查询设计师还未处理的退货单
     *
     * @return
     */
    List<Map<String, Object>> countGroupByDesignerReship(List<String> importSn);

    /**
     * 查询门店还未处理的调拨单
     *
     * @return
     */
    List<Map<String, Object>> countGroupByStore();

    /**
     * 生成调拨明细
     *
     * @param requisitionItem
     * @return
     */
    RequisitionItem insert(RequisitionItem requisitionItem, String operator);

    /**
     * 自营退货换做仓库调拨单
     *
     * @param requisitionItem
     * @return
     */
    RequisitionItem doOtherInsert(RequisitionItem requisitionItem, String operator);

    /**
     * 设计师直发，生成调拨单明细
     *
     * @param orderItem
     * @return
     */
    RequisitionItem doDesignerDelivery(OrderItem orderItem, String operator);

    /**
     * 指定门店发货，生成调拨单明细
     *
     * @param orderItem
     * @param storeId
     * @return
     */
    RequisitionItem doStoreDelivery(OrderItem orderItem, Long storeId, String operator);

    /**
     * 系统生成调拨明细，非自营商品
     *
     * @param orderItem
     * @return
     */
    RequisitionItem doSysDesignerDelivery(OrderItem orderItem);

    /**
     * 系统生成调拨明细，自营商品
     *
     * @param orderItem
     * @param storeId
     * @return
     */
    RequisitionItem doSysStoreDelivery(OrderItem orderItem, Long storeId);

    /**
     * 拼团单据特殊处理
     *
     * @param orderSn
     */
    void doSyncDesignerDelivery(List<String> orderSn);

    /**
     * 审核门店采购
     *
     * @param id
     * @param operator
     * @return
     */
    int doSubmit(Long id, String operator);

    /**
     * 设计师或者门店签收
     *
     * @param id
     * @param operator
     * @return
     */
    int doSign(Long id, String operator);

    /**
     * 设计师或者门店拒绝
     *
     * @param id
     * @param operator
     * @param operation
     * @return
     */
    int doRefuse(Long id, String operator, String operation);

    /**
     * 设计师或者门店或者仓库发货
     *
     * @param id
     * @param quantity
     * @param deliverySn
     * @param deliveryCorp
     * @param operator
     * @return
     * @throws Exception
     */
    int doDelivery(Long id, Integer quantity, String deliverySn, String deliveryCorp, String operator) throws Exception;

    /**
     * 设计师或者门店或者仓库收货
     *
     * @param id
     * @param quantity
     * @param operator
     * @param info
     * @return
     */
    int doReceive(Long id, Integer quantity, String operator, String info) throws Exception;

    /**
     * 设计师重发
     *
     * @param id
     * @param remark
     * @param operator
     * @return
     */
    int doCancelDelivery(Long id, String remark, String operator, String reason, String barcode);

    /**
     * 关闭调拨明细
     *
     * @param id
     * @param closeMan
     * @param closeReason
     * @return
     */
    int doClose(Long id, String closeMan, String closeReason);

    /**
     * 重新打开调拨单明细
     *
     * @param id
     * @param type
     * @param contact
     * @param tel
     * @param address
     * @param storeId
     * @param storeName
     * @param operator
     * @param direct
     * @return
     */
    int doCancelClose(Long id, Integer type, String contact, String tel, String address, Long storeId, String storeName,
                      String operator, String operation, Long requisitionId, Integer direct);

    /**
     * 拦截调拨
     *
     * @param id
     * @param lock
     * @param operator
     * @return
     */
    int doLock(Long id, Integer lock, String operator);

    /**
     * 标志为是否处理
     *
     * @param id
     * @param status
     * @param username
     * @param memo
     * @return
     */
    int doHandle(Long id, Integer status, String operator, String memo);

    /**
     * 关闭调拨明细
     *
     * @param orderItemId
     * @param closeMan
     * @param closeReason
     * @return
     */
    int doCloseByOrderItem(Long orderItemId, String closeMan, String closeReason);

    /**
     * 仓库直接发货给消费者
     *
     * @param id
     * @param deliveryBarCode
     * @param deliveryCorpName
     * @param deliveryNo
     * @param operator
     * @return
     */
    int doDeliverToCustomer(Long id, String deliveryBarCode, String deliveryCorpName, String deliveryNo,
                            String operator);

    /**
     * 订单明细发货
     *
     * @param orderItemId
     * @return
     */
    int doDeliverByOrderItem(Long orderItemId);

    /**
     * 超时罚款
     *
     * @param id
     * @param sn
     * @return
     */
    int doFine(Long id, String sn, Integer fine);

    /**
     * 生成退货单
     *
     * @param id
     * @return
     */
    int doReship(Long id, String operator);

    /**
     * 关闭采购单门店评价
     *
     * @param id
     * @param storeComment
     * @param responseSpeed
     * @return
     */
    int doStoreComment(Long id, String storeComment, Integer responseSpeed, Long storeId, String operator);

    /**
     * 更新备注说明
     *
     * @param id
     * @param remark
     * @return
     */
    int updateRemark(Long id, String remark, String operator);

    /**
     * 更新备注说明
     *
     * @param id
     * @param storeMemo
     * @return
     */
    int updateStoreMemo(Long id, String memo, String operator);

    /**
     * 更新备注说明
     *
     * @param id
     * @param memo
     * @param operator
     * @return
     */
    int updateDesignerMemo(Long id, String memo, String operator);

    /**
     * 调整调拨数量（总数量随之变化）
     *
     * @param id
     * @param quantity
     * @return
     */
    int updateQuantity(Long id, Integer quantity, String operator);

    /**
     * 更新设计师发货数量
     *
     * @param id
     * @param quantity
     * @return
     */
    int updateDeliveryQuantity(Long id, Integer quantity, String operator);

    /**
     * 更新次品数量
     *
     * @param id
     * @param quantity
     * @param operator
     * @return
     */
    int updateDefectiveQuantity(Long id, Integer quantity, String operator);

    /**
     * 更新仓库实际收货数量
     *
     * @param id
     * @param quantity
     * @return
     */
    int updateReceiveQuantity(Long id, Integer quantity, String operator);

    /**
     * 设计师修改预计发货时间
     *
     * @param id
     * @param date
     * @return
     */
    int updateDesignerEstimateDate(Long id, Date date, String memo, String operator);

    /**
     * 修改条码
     *
     * @param orderItemId
     * @param barCode
     * @param operator
     * @return
     */
    int updateBarCode(Long orderItemId, String barCode, String operator);

    /**
     * 修改收货地址
     *
     * @param id
     * @param contact
     * @param tel
     * @param address
     * @param operator
     * @param allocateReason
     * @return
     */
    int updateLogisticAddress(Long id, String contact, String tel, String address, String operator,
                              String allocateReason);

    /**
     * 修改物流信息
     *
     * @param id
     * @param deliveryCorp
     * @param deliverySn
     * @param operator
     * @return
     */
    int updateDeliveryInfo(Long id, String deliveryCorp, String deliverySn, String operator);

    /**
     * 更改优先级
     *
     * @param id
     * @param priority
     * @param operator
     * @return
     */
    int updatePriority(Long id, Integer priority, String operator);

    /**
     * 伯俊重新做单
     *
     * @param id
     * @return
     */
    int doReProcess(Long id);

    /**
     * 物流签收
     *
     * @param id
     * @return
     */
    int updateReceive(Long id);

    /**
     * 修改颜色尺码
     *
     * @param id
     * @param skuId
     * @return
     */
    int updateSku(Long id, Long skuId, String operator);

    /**
     * 分页查找门店超时调拨单
     *
     * @param page
     * @return
     */
    PageResult<RequisitionItem> findStoreExpired(PageModel page);

    /**
     * 根据门店统计超时调拨单
     *
     * @return
     */
    List<Map<String, Object>> findExpiredByStoreId(Date deadline);

    /**
     * 查询调拨单超过一定时间但是设计师还未操作的 预计发货时间<=deadline status(1,2) 发货时间和拒绝时间为空
     *
     * @param page
     * @return
     */
    PageResult<RequisitionItem> findDesignerExpired(PageModel page, Date deadline);

    /**
     * 统计设计师赔偿
     *
     * @param deadline
     * @return
     */
    int countDesignerExpired(Date deadline);

    /**
     * 新增日志
     *
     * @param requisitionItemId
     * @param requisitionSn
     * @param logType
     * @param info
     * @param operator
     */
    void insertLog(Long requisitionItemId, String requisitionSn, String logType, String info, String operator);

    /**
     * 销售订单直接伯俊做单(目前只有D+店用，普通门店会通过调拨单或自己直接做单)
     *
     * @return
     */
    int doBurgeonForOrderItem(Long orderItemId, DocType type);

}
