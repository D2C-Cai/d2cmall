package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberDetailService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.ComplaintDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.*;
import com.d2c.order.model.Complaint.ComplaintStatus;
import com.d2c.order.query.ComplaintSearcher;
import com.d2c.order.service.*;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/crm/complaint")
public class ComplaintCtrl extends BaseCtrl<ComplaintSearcher> {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private ComplainantService complainantService;
    @Autowired
    private ComplaintTrackService complaintTrackService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private ProductService productService;

    @Override
    protected List<Map<String, Object>> getRow(ComplaintSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<ComplaintDto> list = complaintService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String complaintReson = "";
        for (Complaint c : list) {
            int itemTotal = 0;
            Order order = orderService.findByOrderSn(c.getOrderSn());
            if (order == null)
                continue;
            cellsMap = new HashMap<>();
            cellsMap.put("订单编号", c.getOrderSn());
            cellsMap.put("订单状态", order.getOrderStatusName());
            cellsMap.put("订单创建时间", sdf.format(order.getCreateDate()));
            if (org.springframework.util.StringUtils.isEmpty(c.getBusinessType())) {
                complaintReson = "其它";
            } else {
                switch (c.getBusinessName()) {
                    case "QUALITY":
                        complaintReson = "质量问题";
                        break;
                    case "REFUND":
                        complaintReson = "申请退款";
                        break;
                    case "RESHIP":
                        complaintReson = "申请退货";
                        break;
                    case "EXCHANGE":
                        complaintReson = "申请换货";
                        break;
                    case "DELIVERY":
                        complaintReson = "催发货";
                        break;
                    case "CHANGEINFO":
                        complaintReson = "改信息";
                        break;
                    default:
                        break;
                }
            }
            cellsMap.put("投诉原因", complaintReson);
            cellsMap.put("收货人", order.getReciver());
            cellsMap.put("联系方式", order.getContact());
            cellsMap.put("收货地址", order.getAddress());
            PaymentTypeEnum paymentType = PaymentTypeEnum.getByCode(order.getPaymentType());
            cellsMap.put("支付方式", paymentType.getDisplay());
            cellsMap.put("卖家备注", order.getAdminMemo());
            cellsMap.put("买家备注", order.getMemo());
            switch (c.getLevel()) {
                case 1:
                    cellsMap.put("紧急程度", "非常紧急");
                    break;
                case 2:
                    cellsMap.put("紧急程度", "紧急");
                    break;
                case 3:
                    cellsMap.put("紧急程度", "一般");
            }
            cellsMap.put("售后受理时间", sdf.format(c.getCreateDate()));
            cellsMap.put("是否处理完毕", c.getStatus().equals(ComplaintStatus.SUCCESS.toString()) ? "是" : "否");
            cellsMap.put("是否回复客户", c.getCallBackDate() == null ? "否" : "是");
            cellsMap.put("售后是否结束", c.getOverMan() == null ? "否" : "是");
            cellsMap.put("受理人", c.getCreator());
            cellsMap.put("完成人", c.getOverMan());
            cellsMap.put("售后备注", c.getMemo());
            if (org.springframework.util.StringUtils.isEmpty(c.getType())) {
                cellsMap.put("类别", "其它");
            } else {
                switch (c.getType()) {
                    case "1":
                        cellsMap.put("类别", "订单投诉");
                        break;
                    case "2":
                        cellsMap.put("类别", "退款投诉");
                        break;
                    case "3":
                        cellsMap.put("类别", "退货投诉");
                }
            }
            List<OrderItem> items = this.orderItemService.findSimpleByOrderId(order.getId());
            for (OrderItem item : items) {
                itemTotal = item.getProductQuantity() + itemTotal;
                Product product = this.productService.findById(item.getProductId());
                cellsMap.put("物流公司", item.getDeliveryCorpName());
                cellsMap.put("物流编号", item.getDeliverySn());
                cellsMap.put("发货时间",
                        item.getDeliveryTime() == null ? item.getDeliveryTime() : sdf.format(item.getDeliveryTime()));
                cellsMap.put("货号", item.getProductSn());
                if (item.getRefundId() != null) {
                    cellsMap.put("售后类别", "退款申请");
                }
                if (item.getReshipId() != null) {
                    cellsMap.put("售后类别", "退货申请");
                }
                if (item.getReshipId() == null && item.getRefundId() == null) {
                    cellsMap.put("售后类别", "未申请售后");
                }
                cellsMap.put("品牌", item.getDesignerName());
                cellsMap.put("吊牌价", product.getOriginalPrice());
                cellsMap.put("标题", product.getSubTitle());
                cellsMap.put("条码", item.getProductSkuSn());
                cellsMap.put("颜色", item.getSp1Value());
                cellsMap.put("尺码", item.getSp2Value());
                cellsMap.put("销售价", item.getProductPrice());
            }
            cellsMap.put("购买数量", itemTotal);
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(ComplaintSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = complaintService.countBySearch(searcher); // 通过查询规则查找出对应的数量
        return count;
    }

    @Override
    protected String getFileName() {
        return "用户投诉反馈信息";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"订单编号", "订单状态", "订单创建时间", "投诉原因", "收货人", "联系方式", "收货地址", "支付方式", "买家备注", "卖家备注", "品牌",
                "货号", "条码", "颜色", "尺码", "吊牌价", "销售价", "购买数量", "物流公司", "物流编号", "发货时间", "类别", "标题", "售后类别", "紧急程度",
                "售后受理时间", "是否处理完毕", "是否回复客户", "售后是否结束", "受理人", "完成人", "售后备注"};
    }

    @Override
    protected Response doHelp(ComplaintSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ComplaintSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ComplaintDto> pager = complaintService.findPageBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        String complainantStr = data.getString("complainant");// 获取投诉人信息
        data.remove("complainant");
        data.remove("complaints");
        data.remove("complaintTracks");
        data.remove("filePathList");
        data.remove("fileList");
        JSONObject joComplainant = JSON.parseObject(complainantStr);
        Complaint complaint = JsonUtil.instance().toObject(data, Complaint.class);// 获取投诉单信息
        Complainant complainant = JsonUtil.instance().toObject(joComplainant, Complainant.class);// 获取投诉人信息
        Admin admin = this.getLoginedAdmin();
        // 保存投诉人的信息
        SuccessResponse result = new SuccessResponse();
        Complainant cant = complainantService.findByMemberId(complainant.getMemberId());
        Long complainantId = null;
        if (cant != null) {
            complainant.setTimes(cant.getTimes() + 1);
            complainantId = cant.getId();
            complainantService.update(complainant);
        } else {
            complainant = complainantService.insert(complainant);
            complainantId = complainant.getId();
        }
        // 保存投诉单
        complaint.setComplainantId(complainantId);
        complaint.setCreator(admin.getName());
        if (StringUtils.isBlank(complaint.getPromiseMan())) {
            complaint.setPromiseMan(admin.getUsername());
        }
        complaint.setName(complainant.getName());
        complaint.setEmail(complainant.getEmail());
        complaint.setTel(complainant.getTel());
        complaint.setWechat(complainant.getWechat());
        complaint.setQq(complainant.getQq());
        complaint.setMemberId(complainant.getMemberId());
        complaint.setLoginCode(complainant.getLoginCode());
        complaintService.insert(complaint);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        data.remove("complainant");
        Complaint complaint = JsonUtil.instance().toObject(data, Complaint.class);
        String modifyMan = complaintService.findById(complaint.getId() + "").getLastModifyMan();
        complaint.setLastModifyMan(StringUtils.isBlank(modifyMan) ? null : modifyMan);
        int flag = complaintService.update(complaint);
        if (flag == 0) {
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return "Complaint";
    }

    /**
     * 填写客户反馈单
     *
     * @param type
     * @param id
     * @param transactionSn
     * @return
     */
    @RequestMapping(value = "/addCustomFeedback/{type}/{id}/{transactionSn}", method = RequestMethod.POST)
    public Response addCustomFeedback(@PathVariable("type") String type, @PathVariable("id") Long id,
                                      @PathVariable("transactionSn") String transactionSn) {
        SuccessResponse result = new SuccessResponse();
        Complaint complaint = complaintService.findByTypeAndTransactionSn(type, transactionSn);
        ComplaintDto complaintDto = null;
        if (complaint != null) {
            // 投诉过的
            complaintDto = new ComplaintDto();
            BeanUtils.copyProperties(complaint, complaintDto);
            Complainant complainant = complainantService.findByMemberId(complaint.getMemberId());
            ComplaintSearcher searcher = new ComplaintSearcher();
            searcher.setType(type);
            searcher.setTransactionSn(transactionSn);
            List<ComplaintDto> complaints = complaintService.findBySearch(searcher, new PageModel());
            complaintDto.setComplaints(complaints);
            complaintDto.setComplainant(complainant);
        } else {
            MemberInfo memberInfo = null;
            Long transactionId = id;
            Date transactionTime = null;
            Long memberInfoId = null;
            String orderSn = null;
            switch (type) {
                case "1":
                    Order order = orderService.findById(id);
                    transactionTime = order.getCreateDate();
                    memberInfoId = order.getMemberId();
                    orderSn = order.getOrderSn();
                    break;
                case "2":
                    Refund refund = refundService.findById(id);
                    transactionTime = refund.getCreateDate();
                    memberInfoId = refund.getMemberId();
                    orderSn = refund.getRefundSn();
                    break;
                case "3":
                    Reship reship = reshipService.findById(id);
                    transactionTime = reship.getCreateDate();
                    memberInfoId = reship.getMemberId();
                    orderSn = reship.getReshipSn();
                    break;
                case "4":
                    Exchange exchange = exchangeService.findById(id);
                    transactionTime = exchange.getCreateDate();
                    memberInfoId = exchange.getMemberId();
                    orderSn = exchange.getExchangeSn();
                    break;
            }
            Complainant complainant = complainantService.findByMemberId(memberInfoId);
            if (complainant == null) {
                memberInfo = memberInfoService.findById(memberInfoId);
                MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
                complainant = new Complainant();
                complainant.setSex(memberInfo.getSex());
                complainant.setName(memberInfo.getDisplayName());
                complainant.setEmail(memberInfo.getEmail());
                complainant.setTel(memberInfo.getMobile());
                complainant.setMemberId(memberInfo.getId());
                complainant.setLoginCode(memberInfo.getLoginCode());
                complainant.setWechat(memberDetail.getWeixin());
                complainant.setQq(memberDetail.getQq());
            }
            complaintDto = new ComplaintDto();
            complaintDto.setComplainant(complainant);
            complaintDto.setTransactionId(transactionId);
            complaintDto.setType(type);
            complaintDto.setTransactionSn(transactionSn);
            complaintDto.setCmpDate(new Date());
            complaintDto.setTransactionTime(transactionTime);
            complaintDto.setOrderSn(orderSn);
        }
        result.put("complaint", complaintDto);
        return result;
    }

    /**
     * 处理投诉单页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/dealComplaint", method = RequestMethod.GET)
    public Response dealComplaint(String id) {
        Complaint complaint = complaintService.findById(id);
        SuccessResponse result = new SuccessResponse();
        result.put("complaint", complaint);
        return result;
    }

    /**
     * 处理投诉单
     *
     * @param complaint
     * @param complaintTrack
     * @param complaintId
     * @param filesPath
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/doDealComplaint/{complaintId}", method = RequestMethod.POST)
    public Response doDealComplaint(Complaint complaint, ComplaintTrack complaintTrack,
                                    @PathVariable("complaintId") Long complaintId, String[] filesPath) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        complaintTrack.setCreator(admin.getUsername());
        if (complaint.getStatus().equals(ComplaintStatus.FINISH.name())) {
            complaintTrack.setTrackStatus(2);
        } else if (complaint.getStatus().equals(ComplaintStatus.CLOSE.name())) {
            complaintTrack.setTrackStatus(-1);
        } else {
            complaintTrack.setTrackStatus(1);
        }
        StringBuilder pic = new StringBuilder();
        for (String filePath : filesPath) {
            if (StringUtils.isNotBlank(filePath)) {
                pic.append(filePath + ",");
            }
        }
        Complaint complaintOld = complaintService.findById(complaintId.toString());
        complaint.setId(complaintId);
        complaint.setFilePath(pic.toString());
        complaint.setComplainantId(complaintOld.getComplainantId());
        complaintService.update(complaint);
        complaintTrack = complaintTrackService.insert(complaintTrack);
        result.put("complaintTrack", complaintTrack);
        return result;
    }

    /**
     * 处理办结
     *
     * @param complaint
     * @param id
     * @param filesPath
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/doOverComplaint/{id}", method = RequestMethod.POST)
    public Response doOverComplaint(Complaint complaint, @PathVariable("id") Long id, String[] filesPath)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        complaint.setOverMan(admin.getUsername());
        ComplaintTrack complaintTrack = new ComplaintTrack();
        complaintTrack.setComplaintId(id);
        if (complaint.getStatus().equals(ComplaintStatus.SUCCESS.name())) {
            // 完结
            complaintTrack.setTrackStatus(2);
        }
        if (complaint.getStatus().equals(ComplaintStatus.CLOSE.name())) {
            // 关闭
            complaintTrack.setTrackStatus(-1);
        }
        // 保存附件
        StringBuilder pic = new StringBuilder();
        for (String filePath : filesPath) {
            if (StringUtils.isNotBlank(filePath)) {
                pic.append(filePath + ",");
            }
        }
        Complaint complaintOld = complaintService.findById(id.toString());
        complaint.setComplainantId(complaintOld.getComplainantId());
        complaint.setFilePath(pic.toString());
        complaintService.update(complaint);
        complaintTrack.setCreator(admin.getUsername());
        complaintTrack.setLastModifyMan(admin.getUsername());
        complaintTrack.setDealContent(complaint.getOverContent());
        complaintTrack.setDealDate(complaint.getOverDate());
        complaintTrack = complaintTrackService.insert(complaintTrack);
        result.put("complaintTrack", complaintTrack);
        return result;
    }

    /**
     * 重新处理
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancelComplaint", method = RequestMethod.POST)
    public Response cancelComplaint(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        ComplaintTrack complaintTrack = new ComplaintTrack();
        complaintTrack.setComplaintId(id);
        complaintTrack.setTrackStatus(1);
        complaintTrack.setCreator(admin.getUsername());
        complaintTrack.setLastModifyMan(admin.getUsername());
        complaintTrack.setDealDate(new Date());
        complaintTrack.setDealContent("取消办结状态，重新处理！");
        complaintTrackService.insert(complaintTrack);
        Complaint complaint = new Complaint();
        Complaint complaintOld = complaintService.findById(id.toString());
        complaint.setComplainantId(complaintOld.getComplainantId());
        complaint.setId(id);
        complaint.setStatus(ComplaintStatus.REPROCESS.toString());
        complaintService.update(complaint);
        return result;
    }

}
