package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.util.serial.SerialNumUtil;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 调拨单
 */
@Table(name = "o_requisition_item")
public class RequisitionItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 调拨单ID
     */
    private Long requisitionId;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 调拨时间
     */
    private Date requisitionDate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 收货人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 运营备注
     */
    private String remark;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单日期
     */
    private Date orderDate;
    /**
     * 订单明细ID
     */
    private Long orderItemId;
    /**
     * 订单明细状态
     */
    private String orderItemStatus;
    /**
     * 答应用户的预计发货时间
     */
    private Date estimateDate;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 官网货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 一级分类
     */
    private String topCategory;
    /**
     * 二级分类
     */
    private String productCategory;
    /**
     * 官网条码
     */
    private String barCode;
    /**
     * 设计师条码
     */
    private String externalCode;
    /**
     * 颜色
     */
    private String sp1;
    /**
     * 尺码
     */
    private String sp2;
    /**
     * 调拨数量
     */
    private Integer quantity;
    /**
     * 实际入库数量（当实际收货数量≠设计师发货数量时，差异处理，不能确认收货）
     */
    private Integer receiveQuantity;
    /**
     * 实际发货数量
     */
    private Integer deliveryQuantity;
    /**
     * 次品数量
     */
    private Integer defectiveQuantity;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 门店备注
     */
    private String storeMemo;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 设计师备注
     */
    private String designerMemo;
    /**
     * 设计师预计发货时间
     */
    private Date designerEstimateDate;
    /**
     * 延迟人
     */
    private String delayMan;
    /**
     * 延迟说明
     */
    private String delayReason;
    /**
     * 删除人
     */
    private String closeMan;
    /**
     * 删除说明
     */
    private String closeReason;
    /**
     * 最后的日志信息
     */
    private String lastLogInfo;
    /**
     * 是否设计师直发：0否，1是
     */
    private Integer direct = 0;
    /**
     * 出库标志 0：未出库 1：已出库
     */
    private Integer delivered = 0;
    /**
     * 罚款标志 0：不赔偿 1：待赔偿 2：已赔偿
     * <li>只用于设计师产生的赔偿，后续要用做门店时要注意修改相应查询条件
     */
    private Integer fine = 0;
    /**
     * 最迟发货时间
     */
    private Date expiredDate;
    /**
     * 发货时间
     */
    private Date deliveryDate;
    /**
     * 物流公司名称
     */
    private String deliveryCorp;
    /**
     * 物流编号
     */
    private String deliverySn;
    /**
     * 入库时间
     */
    private Date receiveDate;
    /**
     * 是否处理
     */
    private Integer handle = 0;
    /**
     * 是否锁定
     */
    private Integer locked = 0;
    /**
     * 是否物流已签收
     */
    private Integer received = 0;
    /**
     * 优先级 0：一般 1：紧急 2:非常紧急
     */
    private Integer priority = 1;
    /**
     * 关联采购单号
     */
    private String relationSn;
    /**
     * 采购原因（类型） 客欠，热销，正常
     */
    private String purchaseReason;
    /**
     * 申请采购人 store，designer，admin
     */
    private String purchaseRole;
    /**
     * 调拨原因（类型）次品，滞销，正常
     */
    private String allocateReason;
    /**
     * 订单买家备注
     */
    private String orderCusMemo;
    /**
     * 采购单门店评论
     */
    private String storeComment;
    /**
     * 审核响应速度评论
     */
    private Integer responseSpeed;
    /**
     * 商品编码分类
     */
    private String saleCategory;

    public RequisitionItem() {
    }

    ;

    private RequisitionItem(Product product, ProductSku sku, Brand brand, Store store) {
        this.requisitionSn = SerialNumUtil.buildRequisitionSn();
        this.requisitionDate = new Date();
        this.productId = product.getId();
        this.productSn = product.getInernalSn();
        this.externalSn = product.getExternalSn();
        this.productName = product.getName();
        this.productImg = product.getProductImageCover();
        this.topCategory = product.getTopCategory();
        this.productCategory = product.getProductCategory();
        this.saleCategory = product.getSaleCategory();
        this.barCode = sku.getBarCode();
        this.externalCode = sku.getExternalSn();
        this.sp1 = sku.getSp1();
        this.sp2 = sku.getSp2();
        this.designerId = brand.getId();
        this.designerCode = brand.getCode();
        this.designerName = brand.getName();
        this.operation = brand.getOperation();
        this.storeId = store.getId();
        this.storeName = store.getName();
    }

    private RequisitionItem(Product product, ProductSku sku, Brand brand, Store store, Order order,
                            OrderItem orderItem) {
        this(product, sku, brand, store);
        this.orderDate = order.getCreateDate();
        this.orderSn = order.getOrderSn();
        this.orderStatus = order.getOrderStatus();
        this.orderCusMemo = order.getMemo();
        this.orderItemId = orderItem.getId();
        this.orderItemStatus = orderItem.getStatus();
        this.quantity = orderItem.getProductQuantity();
        this.barCode = orderItem.getDeliveryBarCode();
        this.sp1 = orderItem.getSp1();
        this.sp2 = orderItem.getSp2();
    }

    ;

    /**
     * 1.订单采购 3.门店调拨
     *
     * @param order
     * @param orderItem
     * @param store
     * @param brand
     * @param product
     * @param sku
     * @param type
     * @param status
     */
    public RequisitionItem(Order order, OrderItem orderItem, Store store, Brand brand, Product product, ProductSku sku,
                           ItemType type, ItemStaus status) {
        this(product, sku, brand, store, order, orderItem);
        this.type = type.getCode();
        this.status = status.getCode();
        this.priority = 2;
        if (type.equals(ItemType.STOREALLC) || (type.equals(ItemType.ORDERPCH) && brand.getDirect() == 1)) {
            this.contact = order.getReciver();
            this.tel = order.getContact();
            this.address = order.getProvince() + order.getCity() + order.getAddress();
        } else {
            this.contact = store.getLinker();
            this.tel = store.getTel();
            this.address = store.getAddress();
        }
        if (type.equals(ItemType.ORDERPCH)) {
            this.estimateDate = orderItem.getEstimateDate();
            this.purchaseReason = PurchaseType.CUSTOMER.name();
            this.direct = brand.getDirect();
        }
    }

    /**
     * 2.门店采购 4.货需采购 5.POP退货 6.自营退货 7.仓库调拨
     *
     * @param store
     * @param brand
     * @param product
     * @param sku
     * @param type
     * @param status
     * @param orderSn
     * @param quantity
     */
    public RequisitionItem(Store store, Brand brand, Product product, ProductSku sku, ItemType type, ItemStaus status,
                           String orderSn, Integer quantity) {
        this(product, sku, brand, store);
        this.type = type.getCode();
        this.status = status.getCode();
        this.priority = 1;
        if (type.equals(ItemType.RESHIPRTN) || type.equals(ItemType.QUALITYRTN)) {
            this.contact = brand.getConsignee();
            this.tel = brand.getMobile();
            this.address = brand.getAddress();
        } else {
            this.contact = store.getLinker();
            this.tel = store.getTel();
            this.address = store.getAddress();
        }
        this.orderSn = orderSn;
        if (StringUtil.isBlank(orderSn)) {
            this.orderSn = SerialNumUtil.buildRequisitionItemSn(store.getCode());
        }
        this.orderDate = new Date();
        this.quantity = quantity;
    }

    /**
     * 8.店主申请 9.店主退回
     *
     * @param store
     * @param address
     * @param brand
     * @param product
     * @param sku
     * @param type
     * @param status
     * @param orderSn
     * @param quantity
     */
    public RequisitionItem(Store store, Store address, Brand brand, Product product, ProductSku sku, ItemType type,
                           ItemStaus status, String orderSn, Integer quantity) {
        this(product, sku, brand, store);
        this.type = type.getCode();
        this.status = status.getCode();
        this.priority = 1;
        if (type.equals(ItemType.LEAGUEAPPLY)) {
            this.contact = store.getLinker();
            this.tel = store.getTel();
            this.address = store.getAddress();
        } else if (type.equals(ItemType.LEAGUERETURN)) {
            this.contact = address.getLinker();
            this.tel = address.getTel();
            this.address = address.getAddress();
        }
        this.orderSn = SerialNumUtil.buildRequisitionItemSn(store.getCode());
        this.orderDate = new Date();
        this.quantity = quantity;
    }

    public String getTypeName() {
        if (this.type == null) {
            return "未知";
        }
        return ItemType.holder.get(this.type).getDisplay();
    }

    public String getStatusName() {
        if (this.status == null) {
            return "未知";
        }
        return ItemStaus.holder.get(this.status).getDisplay();
    }

    public String getStatusString() {
        ItemStaus itemStaus = ItemStaus.getStatus(this.getStatus());
        return itemStaus.name();
    }

    public String getSpeedString() {
        ItemStaus itemStaus = ItemStaus.getStatus(this.getStatus());
        return itemStaus.name();
    }

    public PurchaseType transferPurchase(String name) {
        switch (name) {
            case "正常采购":
                return PurchaseType.NORMAL;
            case "畅销采购":
                return PurchaseType.BIGDEMAND;
            case "客欠采购":
                return PurchaseType.CUSTOMER;
        }
        return null;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(String orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp1Value() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(this.sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSp2Value() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(this.sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Integer receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getDefectiveQuantity() {
        return defectiveQuantity;
    }

    public void setDefectiveQuantity(Integer defectiveQuantity) {
        this.defectiveQuantity = defectiveQuantity;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDesignerMemo() {
        return designerMemo;
    }

    public void setDesignerMemo(String designerMemo) {
        this.designerMemo = designerMemo;
    }

    public Date getDesignerEstimateDate() {
        return designerEstimateDate;
    }

    public void setDesignerEstimateDate(Date designerEstimateDate) {
        this.designerEstimateDate = designerEstimateDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getDelayMan() {
        return delayMan;
    }

    public void setDelayMan(String delayMan) {
        this.delayMan = delayMan;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public String getCloseMan() {
        return closeMan;
    }

    public void setCloseMan(String closeMan) {
        this.closeMan = closeMan;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getLastLogInfo() {
        return lastLogInfo;
    }

    public void setLastLogInfo(String lastLogInfo) {
        this.lastLogInfo = lastLogInfo;
    }

    public String getStoreMemo() {
        return storeMemo;
    }

    public void setStoreMemo(String storeMemo) {
        this.storeMemo = storeMemo;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryCorp() {
        return deliveryCorp;
    }

    public void setDeliveryCorp(String deliveryCorp) {
        this.deliveryCorp = deliveryCorp;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getRelationSn() {
        return relationSn;
    }

    public void setRelationSn(String relationSn) {
        this.relationSn = relationSn;
    }

    public String getOrderCusMemo() {
        return orderCusMemo;
    }

    public void setOrderCusMemo(String orderCusMemo) {
        this.orderCusMemo = orderCusMemo;
    }

    public String getStoreComment() {
        return storeComment;
    }

    public void setStoreComment(String storeComment) {
        this.storeComment = storeComment;
    }

    public Integer getResponseSpeed() {
        return responseSpeed;
    }

    public void setResponseSpeed(Integer responseSpeed) {
        this.responseSpeed = responseSpeed;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    public String getPurchaseRole() {
        return purchaseRole;
    }

    public void setPurchaseRole(String purchaseRole) {
        this.purchaseRole = purchaseRole;
    }

    public String getAllocateReason() {
        return allocateReason;
    }

    public void setAllocateReason(String allocateReason) {
        this.allocateReason = allocateReason;
    }

    public String getSaleCategory() {
        return saleCategory;
    }

    public void setSaleCategory(String saleCategory) {
        this.saleCategory = saleCategory;
    }

    public enum ItemType {
        ORDERPCH(1, "订单采购"), STOREPCH(2, "门店采购"), GOODSPCH(4, "货需采购"),
        STOREALLC(3, "门店调拨"), DEPOTALLC(7, "仓库调拨"),
        RESHIPRTN(5, "POP退货"), QUALITYRTN(6, "自营退货"),
        LEAGUEAPPLY(8, "店主申请"), LEAGUERETURN(9, "店主退回");
        private static Map<Integer, ItemType> holder = new HashMap<>();

        static {
            for (ItemType itemType : values()) {
                holder.put(itemType.getCode(), itemType);
            }
        }

        private int code;
        private String display;

        ItemType(int code) {
            this.code = code;
        }

        ItemType(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static ItemType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum ItemStaus {
        INIT(0, "待审核"), WAITSIGN(1, "待接收"), WAITDELIVERED(2, "待发货"), DELIVERED(3, "待入库"),
        CLOSE(-4, "已关闭"), REFUSE(-1, "已拒绝"), SUCCESS(8, "已完成");
        private static Map<Integer, ItemStaus> holder = new HashMap<>();

        static {
            for (ItemStaus itemStaus : values()) {
                holder.put(itemStaus.getCode(), itemStaus);
            }
        }

        private int code;
        private String display;

        ItemStaus(int code) {
            this.code = code;
        }

        ItemStaus(int code, String display) {
            this.code = code;
            this.display = display;
        }

        public static ItemStaus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum SpeedType {
        SLOW(1, "慢"), GENERAL(2, "一般"), FAST(3, "快");
        private static Map<Integer, SpeedType> holder = new HashMap<>();

        static {
            for (SpeedType speedType : values()) {
                holder.put(speedType.getCode(), speedType);
            }
        }

        private int code;
        private String name;

        SpeedType(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static SpeedType getByCode(Integer i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum PurchaseType {
        NORMAL("正常采购"), BIGDEMAND("畅销采购"), CUSTOMER("客欠采购");
        private String display;

        PurchaseType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum AllocateType {
        NORMAL("正常退回"), INFERIOR("次品退回"), UNSALE("滞销退回");
        private String display;

        AllocateType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum PurchaseRole {
        ADMIN, DESIGNER, STORE;
    }

}
