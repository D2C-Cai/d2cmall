package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.service.ErrorLogService;
import com.d2c.member.model.Admin;
import com.d2c.order.model.*;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.query.StatementItemSearcher;
import com.d2c.order.service.*;
import com.d2c.order.support.StatementItemBean;
import com.d2c.product.model.Brand;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.SerialNumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/order/statementitem")
public class StatementItemCtrl extends BaseCtrl<StatementItemSearcher> {

    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private StatementItemService statementItemService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ErrorLogService errorLogService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StatementService statementService;

    @Override
    protected Response doList(StatementItemSearcher searcher, PageModel page) {
        PageResult<StatementItem> pager = statementItemService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(StatementItemSearcher searcher) {
        return statementItemService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "statementItem";
    }

    @Override
    protected List<Map<String, Object>> getRow(StatementItemSearcher searcher, PageModel page) {
        PageResult<StatementItem> statementItems = statementItemService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap = null;
        for (StatementItem statementItem : statementItems.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("订单编号", statementItem.getOrderSn());
            cellsMap.put("订单创建时间",
                    statementItem.getOrderItemTime() == null ? "" : sdf.format(statementItem.getOrderItemTime()));
            cellsMap.put("退货单创建时间",
                    statementItem.getReshipTime() == null ? "" : sdf.format(statementItem.getReshipTime()));
            cellsMap.put("发生时间",
                    statementItem.getTransactionTime() == null ? "" : sdf.format(statementItem.getTransactionTime()));
            cellsMap.put("门店名称", statementItem.getStoreName());
            cellsMap.put("设计师品牌", statementItem.getDesignerName());
            cellsMap.put("D2C款号", statementItem.getInernalSn());
            cellsMap.put("设计师款号", statementItem.getExternalSn());
            cellsMap.put("D2C条码", statementItem.getBarCode());
            cellsMap.put("设计师条码", statementItem.getExternalCode());
            String color = statementItem.getSp1Value();
            String size = statementItem.getSp2Value();
            cellsMap.put("颜色", color);
            cellsMap.put("尺码", size);
            cellsMap.put("数量", statementItem.getQuantity());
            cellsMap.put("销售金额", statementItem.getProductPrice());
            cellsMap.put("吊牌价", statementItem.getTagPrice());
            cellsMap.put("实付金额", statementItem.getFactAmount());
            cellsMap.put("订单优惠金额", statementItem.getOrderPromotionAmount());
            cellsMap.put("优惠券金额", statementItem.getCouponPromotionAmount());
            cellsMap.put("满减金额", statementItem.getPromotionAmount());
            cellsMap.put("结算单价", statementItem.getSettlePrice());
            cellsMap.put("结算金额", statementItem.getSettleAmount());
            cellsMap.put("状态", statementItem.getStatusName());
            cellsMap.put("设计师备注", statementItem.getDesignerMemo());
            cellsMap.put("财务备注", statementItem.getRemark());
            cellsMap.put("支付方式", statementItem.getPayType());
            cellsMap.put("支付流水号", statementItem.getPaySn());
            cellsMap.put("来源", statementItem.getTypeName());
            cellsMap.put("订单类型", statementItem.getOrderTypeName());
            cellsMap.put("占单类型",
                    statementItem.getPop() != null ? (statementItem.getPop() == 1 ? "pop占单" : "自营占单") : "");
            cellsMap.put("关联订单号", statementItem.getRelationSn());
            // 是否是D+店订单
            Long orderItemId = null;
            if (statementItem.getDirection() == 1) {
                orderItemId = statementItem.getOrderItemId();
            } else {
                Reship reship = reshipService.findBySn(statementItem.getOrderSn());
                if (reship != null) {
                    orderItemId = reship.getOrderItemId();
                }
            }
            OrderItem orderItem = orderItemService.findById(orderItemId);
            if (orderItem != null) {
                cellsMap.put("联营店名称", orderItem.getDplusName());
                cellsMap.put("是否线下订单", BusType.DPLUS.name().equals(orderItem.getBusType()) ? "是" : "");
            }
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "对账单明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"订单编号", "订单创建时间", "退货单创建时间", "发生时间", "门店名称", "设计师品牌", "D2C款号", "设计师款号", "D2C条码", "设计师条码",
                "颜色", "尺码", "数量", "销售金额", "吊牌价", "实付金额", "订单优惠金额", "优惠券金额", "满减金额", "结算单价", "结算金额", "状态", "设计师备注",
                "财务备注", "支付方式", "支付流水号", "来源", "订单类型", "占单类型", "关联订单号", "联营店名称", "是否线下订单"};
    }

    @Override
    protected Response doHelp(StatementItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success;
        try {
            success = statementItemService.deleteById(id, admin.getUsername());
            if (success < 1) {
                result.setMessage("删除不成功!");
                result.setStatus(-1);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int count = 0;
        String errorMsg = "";
        for (Long id : ids) {
            int success = 0;
            try {
                success = statementItemService.deleteById(id, admin.getUsername());
                if (success > 0) {
                    count++;
                }
            } catch (Exception e) {
                errorMsg = errorMsg + e.getMessage() + ";";
                result.setStatus(-1);
            }
        }
        result.setMessage("删除成功" + count + "条，不成功" + (ids.length - count) + "条");
        return result;
    }

    /**
     * 导入线下对账单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response uploadStatement(HttpServletRequest request) throws Exception {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> execelData = this.getExcelData(request);
        List<StatementItem> beans = this.getStatementList(execelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                StatementItem bean = (StatementItem) object;
                // 取的是门店编号
                Brand brand = brandService.findByBrandCode(bean.getBrandCode());
                if (brand != null) {
                    bean.setDesignerId(brand.getId());
                    bean.setDesignerName(brand.getName());
                    bean.setDesignerCode(brand.getCode());
                } else {
                    errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode() + "编码："
                            + bean.getBrandCode() + "的品牌不存在，请先添加该品牌" + "<br/>");
                    return false;
                }
                if (StringUtils.isNotEmpty(bean.getStoreName())) {
                    Store store = storeService.findByCode(bean.getStoreName());
                    if (store == null) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                + "该门店编号不存在" + "<br/>");
                        return false;
                    }
                    bean.setStoreId(store.getId());
                    bean.setStoreName(store.getName());
                } else {
                    bean.setStoreName("官网");
                    bean.setStoreId(0L);
                }
                if (bean.getFactAmount().multiply(new BigDecimal(bean.getQuantity()))
                        .compareTo(new BigDecimal(0)) < 0) {
                    errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                            + "实付金额和数量正负不一致" + "<br/>");
                    return false;
                }
                if (bean.getSettlePrice() != null && bean.getProductPrice() != null) {
                    if (bean.getProductPrice().compareTo(bean.getSettlePrice()) < 0) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                + "结算价不能大于销售价" + "<br/>");
                        return false;
                    }
                    if (bean.getSettlePrice().compareTo(new BigDecimal(0)) < 0) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                + "结算单价不能小于0" + "<br/>");
                        return false;
                    }
                }
                bean.setStatus(0);
                bean.setCreator(admin.getUsername());
                try {
                    statementItemService.insert(bean);
                } catch (Exception e) {
                    errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                            + e.getMessage() + "<br/>");
                    return false;
                }
                return true;
            }
        });
    }

    private List<StatementItem> getStatementList(List<Map<String, Object>> execelData) throws Exception {
        List<StatementItem> beans = new ArrayList<>();
        for (Map<String, Object> map : execelData) {
            StatementItem statementItem = new StatementItem();
            statementItem.setStatus(0);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            // 不能为空的数据
            if (map.get("订单编号") == null || StringUtils.isEmpty(String.valueOf(map.get("订单编号")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行订单编号不能为空");
            }
            if (map.get("D2C条码") == null || StringUtils.isEmpty(String.valueOf(map.get("D2C条码")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行D2C条码不能为空");
            }
            if (map.get("商品数量") == null || StringUtils.isEmpty(String.valueOf(map.get("商品数量")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行商品数量不能为空");
            }
            if (map.get("吊牌价") == null || StringUtils.isEmpty(String.valueOf(map.get("吊牌价")))
                    || map.get("支付流水号") == null || StringUtils.isEmpty(String.valueOf(map.get("支付流水号")))
                    || map.get("实付金额") == null || StringUtils.isEmpty(String.valueOf(map.get("实付金额")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行吊牌价,支付流水号,实付金额不能为空");
            }
            if (map.get("销售价") == null || StringUtils.isEmpty(String.valueOf(map.get("销售价")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行销售价不能为空");
            }
            if (map.get("品牌编码") == null || StringUtils.isEmpty(String.valueOf(map.get("品牌编码")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行品牌编码不能为空");
            }
            statementItem.setBarCode(String.valueOf(map.get("D2C条码")));
            statementItem.setOrderSn(String.valueOf(map.get("订单编号")));
            statementItem.setQuantity(new BigDecimal(map.get("商品数量").toString()).intValue());
            if (statementItem.getQuantity() < 0) {
                statementItem.setDirection(-1);
                statementItem.setType(StatementItem.SourceType.LineReship.getCode());
            } else {
                statementItem.setDirection(1);
                statementItem.setType(StatementItem.SourceType.LineOrder.getCode());
            }
            statementItem.setTagPrice(new BigDecimal(String.valueOf(map.get("吊牌价"))));
            statementItem.setProductPrice(new BigDecimal(String.valueOf(map.get("销售价"))));
            statementItem.setPaymentType(18);
            statementItem.setPaySn(String.valueOf(map.get("支付流水号")));
            statementItem.setFactAmount(new BigDecimal(String.valueOf(map.get("实付金额"))));
            statementItem.setBrandCode(String.valueOf(map.get("品牌编码")));
            // 其他数据
            Date date = new Date();
            statementItem.setTransactionTime(date);
            statementItem
                    .setOrderItemTime(map.get("订单创建时间") == null ? date : df.parse(String.valueOf(map.get("订单创建时间"))));
            if (statementItem.getDirection() == -1) {
                statementItem.setReshipTime(
                        map.get("退货单创建时间") == null ? date : df.parse(String.valueOf(map.get("退货单创建时间"))));
            }
            Calendar c = Calendar.getInstance();
            c.setTime(statementItem.getOrderItemTime());
            if (map.get("结算年份") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算年份")))) {
                statementItem.setSettleYear(new BigDecimal(String.valueOf(map.get("结算年份"))).intValue());
            } else {
                statementItem.setSettleYear(c.get(Calendar.YEAR));
            }
            if (map.get("结算月份") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算月份")))) {
                statementItem.setSettleMonth(new BigDecimal(String.valueOf(map.get("结算月份"))).intValue());
            } else {
                statementItem.setSettleMonth(c.get(Calendar.MONTH) + 1);
            }
            if (map.get("结算日") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算日")))) {
                statementItem.setSettleDay(new BigDecimal(String.valueOf(map.get("结算日"))).intValue());
            } else {
                statementItem.setSettleDay(c.get(Calendar.DAY_OF_MONTH));
            }
            // 先把门店编号塞到门店名里
            if (map.get("门店编号") != null && StringUtils.isNotEmpty(String.valueOf(map.get("门店编号")))) {
                statementItem.setStoreName(String.valueOf(map.get("门店编号")));
            }
            if (map.get("结算单价") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算单价")))) {
                statementItem.setSettlePrice(new BigDecimal(String.valueOf(map.get("结算单价"))));
            }
            statementItem.setOrderPromotionAmount(
                    (map.get("优惠券金额") == null || StringUtils.isEmpty(String.valueOf(map.get("优惠券金额"))))
                            ? new BigDecimal(0)
                            : new BigDecimal(String.valueOf(map.get("优惠券金额"))));
            statementItem.setPromotionAmount(
                    (map.get("订单优惠金额") == null || StringUtils.isEmpty(String.valueOf(map.get("订单优惠金额"))))
                            ? new BigDecimal(0)
                            : new BigDecimal(String.valueOf(map.get("订单优惠金额"))));
            statementItem.setCouponPromotionAmount(
                    (map.get("商品优惠金额") == null || StringUtils.isEmpty(String.valueOf(map.get("商品优惠金额"))))
                            ? new BigDecimal(0)
                            : new BigDecimal(String.valueOf(map.get("商品优惠金额"))));
            statementItem.setInernalSn(
                    (map.get("D2C款号") == null || StringUtils.isEmpty(String.valueOf(map.get("D2C款号")))) ? null
                            : String.valueOf(map.get("D2C款号")));
            statementItem.setExternalSn(
                    (map.get("设计师款号") == null || StringUtils.isEmpty(String.valueOf(map.get("设计师款号")))) ? null
                            : String.valueOf(map.get("设计师款号")));
            statementItem.setExternalCode(
                    (map.get("设计师条码") == null || StringUtils.isEmpty(String.valueOf(map.get("设计师条码")))) ? null
                            : String.valueOf(map.get("设计师条码")));
            statementItem.setSp1((map.get("颜色") == null || StringUtils.isEmpty(String.valueOf(map.get("颜色")))) ? null
                    : String.valueOf(map.get("颜色")));
            statementItem.setSp2((map.get("尺码") == null || StringUtils.isEmpty(String.valueOf(map.get("尺码")))) ? null
                    : String.valueOf(map.get("尺码")));
            beans.add(statementItem);
        }
        return beans;
    }

    /**
     * 导入结算单价
     *
     * @param requset
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/price", method = RequestMethod.POST)
    public Response uploadSettlePrice(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> execelData = this.getExcelData(request);
        List<StatementItemBean> beans = this.getSettleBean(execelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                StatementItemBean bean = (StatementItemBean) object;
                // 获取颜色，尺码
                StatementItem statementItem = statementItemService.findBySnAndBarCode(bean.getOrderSn(),
                        bean.getBarCode());
                if (statementItem != null) {
                    try {
                        statementItemService.updateSettlePrice(statementItem.getId(), bean.getSettlePrice(),
                                admin.getUsername());
                    } catch (Exception e) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                + e.getMessage() + "<br/>");
                    }
                    return true;
                } else {
                    errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                            + "不存在该对账单明细，请核对是否填写正确" + "<br/>");
                    return false;
                }
            }
        });
    }

    private List<StatementItemBean> getSettleBean(List<Map<String, Object>> excelData) {
        List<StatementItemBean> beans = new ArrayList<>();
        for (Map<String, Object> map : excelData) {
            StatementItemBean bean = new StatementItemBean();
            if (map.get("结算单价") == null || map.get("订单编号") == null || map.get("条码") == null || map.get("结算单价") == ""
                    || map.get("订单编号") == "" || map.get("条码") == "") {
                throw new BusinessException("第" + (beans.size() + 2) + "结算金额,订单编号或者条码不能为空！");
            }
            bean.setOrderSn(String.valueOf(map.get("订单编号")));
            bean.setBarCode(String.valueOf(map.get("条码")));
            bean.setSettlePrice(new BigDecimal(String.valueOf(map.get("结算单价"))));
            if (map.get("结算年份") != null && map.get("结算年份") != "") {
                bean.setYear(new BigDecimal(map.get("结算年份").toString()).intValue());
            }
            if (map.get("结算月份") != null && map.get("结算月份") != "") {
                bean.setMonth(new BigDecimal(map.get("结算月份").toString()).intValue());
            }
            if (map.get("结算日") != null && map.get("结算日") != "") {
                bean.setDay(new BigDecimal(map.get("结算日").toString()).intValue());
            }
            beans.add(bean);
        }
        return beans;
    }

    @RequestMapping(value = "/create/statement", method = RequestMethod.POST)
    public Response createStatement(int settleYear, int settleMonth, Integer periodOfMonth, String fromType,
                                    Long designerId, String adminMemo) throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        periodOfMonth = periodOfMonth == null ? 0 : periodOfMonth;
        // 查找结算单价为空的对账单明细
        if (fromType != null && !"online".equals(fromType) && !"line".equals(fromType)) {
            throw new BusinessException("类型只能为空或online,line");
        }
        List<StatementItem> statementItems = statementItemService.findEmptySettle(settleYear, settleMonth,
                periodOfMonth, fromType, designerId);
        if (statementItems.size() > 0) {
            String errorInfo = "生成对账单不成功，以下品牌的结算单价为空：";
            for (StatementItem item : statementItems) {
                errorInfo = errorInfo + "品牌：" + item.getDesignerName() + "订单编号：" + item.getOrderSn() + "官网条码："
                        + item.getBarCode() + ";" + "<br/>";
            }
            throw new BusinessException(errorInfo);
        }
        String sn = "";
        // 统计对账单明细拼成对账单
        List<Statement> list = statementService.findByDesigner(settleYear, settleMonth, periodOfMonth, fromType,
                designerId);
        // 统计设计师已生成的对账单
        List<Map<String, Object>> countStatement = statementService.countByDesigner(settleYear, settleMonth,
                periodOfMonth, fromType, designerId);
        int successCount = 0;
        for (Statement statement : list) {
            statement.setPeriodOfMonth(periodOfMonth);
            statement.setTitle(statement.getDesignerName() + "-" + statement.getYear() + "年" + statement.getMonth()
                    + "月" + statement.getPeriodOfMonthName() + "对账单"
                    + (fromType != null ? ("online".equals(fromType) ? "(线上)" : "(线下)") : ""));
            for (int i = countStatement.size() - 1; i >= 0; i--) {
                if (statement.getDesignerId()
                        .equals(Long.valueOf(String.valueOf(countStatement.get(i).get("designerId"))))) {
                    statement.setTitle(statement.getDesignerName() + "-" + statement.getYear() + "年"
                            + statement.getMonth() + "月" + statement.getPeriodOfMonthName() + "对账单补"
                            + String.valueOf(countStatement.get(i).get("count"))
                            + (fromType != null ? ("online".equals(fromType) ? "(线上)" : "(线下)") : ""));
                    countStatement.remove(i);
                    break;
                }
            }
            sn = SerialNumUtil.buildStatementSn(statement.getDesignerCode(), statement.getYear(), statement.getMonth());
            statement.setStatus(1);
            statement.setSn(sn);
            statement.setCreator(admin.getUsername());
            statement.setSendDate(new Date());
            statement.setSender(admin.getUsername());
            statement.setFromType(fromType);
            statement.setTotalPayAmount(BigDecimal.ZERO);
            Brand brand = brandService.findById(statement.getDesignerId());
            statement.setOperation(brand.getOperation());
            int success = statementService.createStatement(statement);
            if (success > 0) {
                successCount++;
            }
        }
        result.setMessage("生成对账单" + successCount + "单");
        return result;
    }

    /**
     * 更新结算时间
     *
     * @param settleYear
     * @param settleMonth
     * @param id
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/settledate", method = RequestMethod.POST)
    public Response updateSettleDate(Integer settleYear, Integer settleMonth, Integer settleDay, Long id)
            throws BusinessException, NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (settleYear == null || settleMonth == null || settleDay == null) {
            result.setStatus(-1);
            result.setMessage("结算年月日不能为空");
            return result;
        }
        StatementItem statementItem = statementItemService.findById(id);
        if (statementItem == null) {
            result.setStatus(-1);
            result.setMessage("该对账单明细不存在");
            return result;
        }
        if (settleYear != statementItem.getSettleYear() || settleMonth != statementItem.getSettleMonth()) {
            int success = statementItemService.updateSettleDate(settleYear, settleMonth, settleDay, id,
                    admin.getUsername());
            if (success < 1) {
                result.setStatus(-1);
                result.setMessage("操作不成功");
            }
        }
        return result;
    }

    /**
     * 更新结算单价
     *
     * @param id
     * @param settlePrice
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/amount", method = RequestMethod.POST)
    public Response updateSettleAmount(Long id, BigDecimal settlePrice) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (settlePrice == null) {
            result.setMessage("结算单价不能为空");
            result.setStatus(-1);
            return result;
        }
        int success = statementItemService.updateSettlePrice(id, settlePrice, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
            return result;
        }
        return result;
    }

    /**
     * 结算备注
     *
     * @param id
     * @param adminMemo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/item/adminMemo", method = RequestMethod.POST)
    public Response updateAdminMemo(Long id, String adminMemo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = statementItemService.updateAdminMemo(id, adminMemo, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 系统创建对账明细
     *
     * @return
     */
    @RequestMapping(value = "/sync/item", method = RequestMethod.POST)
    public Response doSyncItem() {
        SuccessResponse result = new SuccessResponse();
        insertStatementItem();
        return result;
    }

    private void insertStatementItem() {
        try {
            StatementItem statement = statementItemService.findLastOne(StatementItem.SourceType.OnlineOrder.getCode());
            if (statement != null) {
                this.doSysInsert(new Date(statement.getCreateDate().getTime()),
                        StatementItem.SourceType.OnlineOrder.getCode());
            } else {
                createStatement(StatementItem.SourceType.OnlineOrder.getCode());
            }
            StatementItem reshipStatement = statementItemService
                    .findLastOne(StatementItem.SourceType.OnlineReship.getCode());
            if (reshipStatement != null) {
                this.doSysInsert(new Date(reshipStatement.getCreateDate().getTime()),
                        StatementItem.SourceType.OnlineReship.getCode());
            } else {
                createStatement(StatementItem.SourceType.OnlineReship.getCode());
            }
        } catch (Exception e) {
            logger.error("insertStatementItem  error" + e.getMessage());
        }
    }

    private void createStatement(Integer type) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 开启时间
        try {
            Date date = sf.parse("2018-01-01");
            // 当下时间
            Calendar c = Calendar.getInstance();
            Date date1 = sf.parse(
                    c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + (c.get(Calendar.DAY_OF_MONTH)));
            if (date1.after(date)) {
                // 往前推一天
                this.doSysInsert(new Date(date.getTime()), type);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String doSysInsert(Date beginDate, Integer type) {
        String error = "";
        // 签收的
        if (type != null && type == StatementItem.SourceType.OnlineOrder.getCode()) {
            PageModel page = new PageModel();
            PageResult<OrderItem> orderItems = null;
            do {
                orderItems = orderItemService.findDtoForStatement(beginDate, page);
                for (OrderItem orderItem : orderItems.getList()) {
                    try {
                        ProductSku productSku = productSkuService.findById(orderItem.getProductSkuId());
                        Order order = orderService.findById(orderItem.getOrderId());
                        Store store = storeService.findById(orderItem.getStoreId());
                        StatementItem statementItem = new StatementItem(orderItem, productSku, order, store,
                                StatementItem.ItemStaus.INIT.getCode());
                        statementItem.setType(StatementItem.SourceType.OnlineOrder.getCode());
                        // 款号，订单编号一样的就做补偿
                        statementItemService.insert(statementItem);
                    } catch (Exception e) {
                        error = "订单编号：" + orderItem.getOrderSn() + "；款号 ：" + orderItem.getProductSkuSn() + "," + "id："
                                + orderItem.getDesignerId() + e.getMessage();
                        ErrorLog errorLog = new ErrorLog();
                        errorLog.createErrorLog("sys", "backend", error);
                        errorLogService.insert(errorLog);
                    }
                }
                page.setP(page.getP() + 1);
            } while (orderItems.isNext());
        } else if (type != null && type == StatementItem.SourceType.OnlineReship.getCode()) {
            PageModel page = new PageModel();
            PageResult<Reship> reships = null;
            do {
                reships = reshipService.findReshipForStatement(beginDate, page);
                for (Reship reship : reships.getList()) {
                    try {
                        ProductSku productSku = productSkuService.findById(reship.getProductSkuId());
                        OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
                        StatementItem statementItem = new StatementItem(reship, productSku, orderItem,
                                StatementItem.ItemStaus.INIT.getCode());
                        statementItem.setType(StatementItem.SourceType.OnlineReship.getCode());
                        statementItemService.insert(statementItem);
                    } catch (Exception e) {
                        error = "退货编号：" + reship.getReshipSn() + "；款号 ：" + reship.getProductSkuSn() + "," + "id："
                                + reship.getId() + e.getMessage();
                        ErrorLog errorLog = new ErrorLog();
                        errorLog.createErrorLog("sys", "backend", error);
                        errorLogService.insert(errorLog);
                    }
                }
                page.setP(page.getP() + 1);
            } while (reships.isNext());
        }
        return error;
    }

    /**
     * 导入线上退货对账明细
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import/online", method = RequestMethod.POST)
    public Response uploadStatement4Online(HttpServletRequest request) throws Exception {
        Admin admin = this.getLoginedAdmin();
        List<Map<String, Object>> execelData = this.getExcelData(request);
        List<StatementItem> beans = this.getStatementList4Online(execelData);
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                StatementItem bean = (StatementItem) object;
                // 其他订单信息
                OrderItem orderItem = orderItemService.findByOrderSnAndSku(bean.getOrderSn(), bean.getBarCode());
                if (orderItem == null) {
                    errorMsg.append("第" + row + "行订单不存在" + "<br/>");
                    return false;
                } else {
                    Calendar c = Calendar.getInstance();
                    c.setTime(orderItem.getCreateDate());
                    if (bean.getSettleYear() == 0) {
                        bean.setSettleYear(c.get(Calendar.YEAR));
                    }
                    if (bean.getSettleMonth() == 0) {
                        bean.setSettleMonth(c.get(Calendar.MONTH) + 1);
                    }
                    bean.setTagPrice(orderItem.getOriginalPrice());
                    bean.setProductPrice(orderItem.getProductPrice());
                    bean.setPaymentType(orderItem.getPaymentType());
                    Order order = orderService.findByOrderSn(bean.getOrderSn());
                    bean.setPaySn(order.getPaymentSn());
                    bean.setDesignerCode(orderItem.getDesignerCode());
                    bean.setOrderPromotionAmount(orderItem.getOrderPromotionAmount());
                    bean.setPromotionAmount(orderItem.getPromotionAmount());
                    bean.setCouponPromotionAmount(orderItem.getCouponAmount());
                    bean.setInernalSn(orderItem.getProductSkuSn());
                    bean.setExternalSn(orderItem.getExternalSn());
                    bean.setExternalCode(orderItem.getExternalSn());
                    bean.setFactAmount(orderItem.getActualAmount());
                    bean.setProductId(orderItem.getProductId());
                    bean.setProductImg(orderItem.getProductImg());
                    bean.setSp1(orderItem.getSp1());
                    bean.setSp2(orderItem.getSp2());
                    bean.setOrderItemId(orderItem.getId());
                    bean.setOrderItemTime(orderItem.getCreateDate());
                    bean.setDesignerId(orderItem.getDesignerId());
                    bean.setDesignerName(orderItem.getDesignerName());
                    bean.setOrderSn(bean.getOrderSn() + "-1");
                    bean.setRelationSn(orderItem.getOrderSn());
                    bean.setOrderType(orderItem.getType());
                    if (bean.getFactAmount().compareTo(new BigDecimal(0)) > 0) {
                        bean.setFactAmount(bean.getFactAmount().multiply(new BigDecimal(-1)));
                    }
                    if (bean.getSettlePrice() != null && bean.getProductPrice() != null) {
                        if (bean.getProductPrice().compareTo(bean.getSettlePrice()) < 0) {
                            errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                    + "结算价不能大于销售价" + "<br/>");
                            return false;
                        }
                        if (bean.getSettlePrice().compareTo(new BigDecimal(0)) < 0) {
                            errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                    + "结算单价不能小于0" + "<br/>");
                            return false;
                        }
                    }
                    bean.setCreator(admin.getUsername());
                    try {
                        statementItemService.insert(bean);
                        return true;
                    } catch (Exception e) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getOrderSn() + "，条码:" + bean.getBarCode()
                                + e.getMessage() + "<br/>");
                        return false;
                    }
                }
            }
        });
    }

    private List<StatementItem> getStatementList4Online(List<Map<String, Object>> execelData) throws Exception {
        List<StatementItem> beans = new ArrayList<>();
        for (Map<String, Object> map : execelData) {
            StatementItem statementItem = new StatementItem();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            // 不能为空的数据
            if (map.get("订单编号") == null || StringUtils.isEmpty(String.valueOf(map.get("订单编号")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行订单编号不能为空");
            }
            if (map.get("D2C条码") == null || StringUtils.isEmpty(String.valueOf(map.get("D2C条码")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行D2C条码不能为空");
            }
            if (map.get("商品数量") == null || StringUtils.isEmpty(String.valueOf(map.get("商品数量")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行商品数量不能为空");
            }
            if (map.get("实退金额") == null || StringUtils.isEmpty(String.valueOf(map.get("实退金额")))) {
                throw new BusinessException("第" + (beans.size() + 2) + "行实退金额不能为空");
            }
            if (map.get("退货单创建时间") == null) {
                throw new BusinessException("第" + (beans.size() + 2) + "行退货单创建时间不能为空");
            }
            if (map.get("结算年份") == null) {
                throw new BusinessException("第" + (beans.size() + 2) + "行结算年份不能为空");
            }
            if (map.get("结算月份") == null) {
                throw new BusinessException("第" + (beans.size() + 2) + "行结算月份不能为空");
            }
            if (map.get("结算日") == null) {
                throw new BusinessException("第" + (beans.size() + 2) + "行结算日不能为空");
            }
            statementItem.setBarCode(String.valueOf(map.get("D2C条码")));
            statementItem.setOrderSn(String.valueOf(map.get("订单编号")));
            statementItem.setQuantity(new BigDecimal(map.get("商品数量").toString()).intValue());
            if (statementItem.getQuantity() >= 0) {
                statementItem.setQuantity(-statementItem.getQuantity());
            }
            Date date = new Date();
            statementItem.setTransactionTime(date);
            String reshipDate = null;
            if (map.get("退货单创建时间") != null) {
                reshipDate = String.valueOf(map.get("退货单创建时间"));
                reshipDate = reshipDate.replace("/", "-");
            }
            statementItem.setReshipTime(reshipDate == null ? date : df.parse(reshipDate));
            statementItem.setDirection(-1);
            statementItem.setStatus(0);
            statementItem.setType(StatementItem.SourceType.OnlineRefund.getCode());
            statementItem.setStoreName("官网");
            statementItem.setStoreId(0L);
            beans.add(statementItem);
            if (map.get("结算年份") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算年份")))) {
                statementItem.setSettleYear(new BigDecimal(String.valueOf(map.get("结算年份"))).intValue());
            }
            if (map.get("结算月份") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算月份")))) {
                statementItem.setSettleMonth(new BigDecimal(String.valueOf(map.get("结算月份"))).intValue());
            }
            if (map.get("结算日") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算日")))) {
                statementItem.setSettleDay(new BigDecimal(String.valueOf(map.get("结算日"))).intValue());
            }
            if (map.get("结算单价") != null && StringUtils.isNotEmpty(String.valueOf(map.get("结算单价")))) {
                statementItem.setSettlePrice(new BigDecimal(String.valueOf(map.get("结算单价"))));
            }
        }
        return beans;
    }

}
