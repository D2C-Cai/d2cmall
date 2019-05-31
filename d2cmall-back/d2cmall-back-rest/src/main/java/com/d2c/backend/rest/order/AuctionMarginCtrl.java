package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.model.Admin;
import com.d2c.member.support.OrderInfo;
import com.d2c.order.dto.AuctionMarginDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.query.AuctionMarginSearcher;
import com.d2c.order.service.AuctionMarginService;
import com.d2c.order.service.tx.AuctionTxService;
import com.d2c.order.support.AuctionMarginBean;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/auctionmargin")
public class AuctionMarginCtrl extends BaseCtrl<AuctionMarginSearcher> {

    @Autowired
    private AuctionMarginService auctionMarginService;
    @Reference
    private AuctionTxService auctionTxService;

    @Override
    protected List<Map<String, Object>> getRow(AuctionMarginSearcher searcher, PageModel page) {
        PageResult<AuctionMarginDto> pager = auctionMarginService.findDtoBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap;
        for (AuctionMarginDto auctionMarginDto : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("ID", auctionMarginDto.getId());
            cellsMap.put("拍卖活动ID", auctionMarginDto.getAuctionId());
            cellsMap.put("拍卖活动标题", auctionMarginDto.getAuctionTitle());
            cellsMap.put("会员ID", auctionMarginDto.getMemberId());
            cellsMap.put("登录账号", auctionMarginDto.getLoginCode());
            cellsMap.put("订单编号", auctionMarginDto.getMarginSn());
            cellsMap.put("保证金支付方式", PaymentTypeEnum.getByCode(auctionMarginDto.getPaymentType()).getDisplay());
            cellsMap.put("支付流水号", auctionMarginDto.getPaySn());
            cellsMap.put("保证金", auctionMarginDto.getMargin());
            cellsMap.put("保证金状态", auctionMarginDto.getStatuText());
            cellsMap.put("退款时间",
                    auctionMarginDto.getRefundDate() == null ? "" : sdf.format(auctionMarginDto.getRefundDate()));
            cellsMap.put("退款人", auctionMarginDto.getRefunder());
            cellsMap.put("退款方式", auctionMarginDto.getRefundType());
            cellsMap.put("退款交易号", auctionMarginDto.getRefundSn());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(AuctionMarginSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = auctionMarginService.countBySearch(searcher); // 通过查询规则查找出对应的数量
        return count;
    }

    @Override
    protected String getFileName() {
        return "拍卖保证金列表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"ID", "拍卖活动ID", "拍卖活动标题", "会员ID", "登录账号", "订单编号", "保证金支付方式", "支付流水号", "保证金", "保证金状态",
                "退款时间", "退款人", "退款方式", "退款交易号"};
    }

    @Override
    protected Response doHelp(AuctionMarginSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<HelpDTO> dtos = auctionMarginService.findHelpDtos(searcher, page);
        return new SuccessResponse(dtos);
    }

    @Override
    protected Response doList(AuctionMarginSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AuctionMarginDto> pager = auctionMarginService.findDtoBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        if (id == null) {
            return new ErrorResponse("请正确输入id");
        }
        return new SuccessResponse(auctionMarginService.findById(id));
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            auctionMarginService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        auctionMarginService.delete(id);
        result.setMessage("删除成功！！！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        AuctionMargin margin = JsonUtil.instance().toObject(data, AuctionMargin.class);
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        margin.setCreator(admin.getUsername());
        margin = auctionMarginService.insert(margin);
        result.setMessage("新增成功！！！");
        result.put("margin", margin);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // AuctionMargin margin = (AuctionMargin)
        // JsonHelper.instance().toObject(data, AuctionMargin.class);
        // SuccessResponse result = new SuccessResponse();
        // auctionMarginService.update(margin);
        // result.setMessage("修改保存成功！！！");
        // result.put("margin", margin);
        // return result;
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "AuctionMargin";
    }

    @RequestMapping(value = "/back/{id}", method = RequestMethod.POST)
    public Response doBack(@PathVariable(value = "id") Long id, Integer refundType, String refundSn, String refunder)
            throws Exception {
        SuccessResponse result = new SuccessResponse();
        AuctionMargin auctionMargin = auctionMarginService.findById(id);
        if (auctionMargin.getStatus() != null && auctionMargin.getStatus() == -2) {
            PaymentTypeEnum refundTypeEnum = PaymentTypeEnum.getByCode(refundType);
            if (refundType != null && refundType == 7) {
                OrderInfo bill = new OrderInfo(BusinessTypeEnum.MARGIN.name(), PayTypeEnum.REFUND.name());
                BeanUtils.copyProperties(auctionMargin, bill, "memo");
                auctionTxService.doBackToWallet(bill, id, refundTypeEnum == null ? "" : refundTypeEnum.getDisplay(),
                        refundSn, refunder);
            } else {
                auctionMarginService.doBackMargin(id, refundTypeEnum == null ? "" : refundTypeEnum.getDisplay(),
                        refundSn, refunder);
            }
        } else {
            result.setStatus(-1);
            result.setMessage("保证金状态不正确！只有未中拍的保证金才能退款！");
        }
        return result;
    }

    @RequestMapping(value = "/delivery/{id}", method = RequestMethod.POST)
    public Response doDelivery(@PathVariable(value = "id") Long id, String deliveryCorpName, String deliverySn) {
        SuccessResponse result = new SuccessResponse();
        AuctionMargin auctionMargin = auctionMarginService.findById(id);
        if (auctionMargin.getStatus() != null && auctionMargin.getStatus() == 6) {
            auctionMarginService.doDelivery(id, deliveryCorpName, deliverySn);
        } else {
            result.setStatus(-1);
            result.setMessage("保证金状态不正确！只有完成付款的保证金才能发货！");
        }
        return result;
    }

    @RequestMapping(value = "/dobreach/{id}", method = RequestMethod.POST)
    public Response doBreach(@PathVariable(value = "id") Long id) {
        SuccessResponse result = new SuccessResponse();
        AuctionMargin auctionMargin = auctionMarginService.findById(id);
        if (auctionMargin.getStatus() != null && auctionMargin.getStatus() == 2) {
            auctionMarginService.doBreachAuction(id);
        } else {
            result.setStatus(-1);
            result.setMessage("订单已经完成支付！");
        }
        return result;
    }

    /**
     * 导入保证金退款表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/back/excel/import", method = RequestMethod.POST)
    public Response importMarginRefund(HttpServletRequest request) {
        this.getLoginedAdmin();
        List<Map<String, Object>> excelData = this.getExcelData(request);
        List<AuctionMarginBean> beans = null;
        try {
            beans = this.getAuctionMarginBeanList(excelData);
        } catch (Exception e) {
            return new ErrorResponse("导入数据异常：请删除多余的空行，确认订单编号，退款时间，退款人和退款交易号不能为空。日期格式为 yyyy-MM-dd");
        }
        return this.processImportExcel(beans, new EachBean() {
            @Override
            public boolean process(Object object, Integer row, StringBuilder errorMsg) {
                AuctionMarginBean bean = (AuctionMarginBean) object;
                if (bean == null || StringUtils.isEmpty(bean.getMarginSn())
                        || StringUtils.isEmpty(bean.getRefundSn())) {
                    return false;
                }
                AuctionMargin auctionMargin = auctionMarginService.findByMarginSn(bean.getMarginSn());
                if (auctionMargin == null) {
                    errorMsg.append("第" + row + "行，订单编号：" + bean.getMarginSn() + "，错误原因：保证金单据未找到！<br/>");
                    return false;
                } else {
                    if (auctionMargin.getStatus() != -2) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getMarginSn() + "，错误原因：保证金单据状态不对！<br/>");
                        return false;
                    } else if (!auctionMargin.getPaySn().equals(bean.getPaySn())) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getMarginSn() + "，错误原因：支付流水号错误！<br/>");
                        return false;
                    } else if (auctionMargin.getMargin().compareTo(bean.getMargin()) < 0) {
                        errorMsg.append("第" + row + "行，订单编号：" + bean.getMarginSn() + "，错误原因：退款金额不能大于支付金额！<br/>");
                        return false;
                    }
                    if (bean != null && "钱包支付".equals(bean.getRefundType())) {
                        OrderInfo bill = new OrderInfo(BusinessTypeEnum.MARGIN.name(), PayTypeEnum.REFUND.name());
                        BeanUtils.copyProperties(auctionMargin, bill, "memo");
                        auctionTxService.doBackToWallet(bill, auctionMargin.getId(), bean.getRefundType(),
                                bean.getMarginSn(), bean.getRefunder());
                    } else {
                        auctionMarginService.doBackMargin(auctionMargin.getId(), bean.getRefundType(),
                                bean.getMarginSn(), bean.getRefunder());
                    }
                    return true;
                }
            }
        });
    }

    private List<AuctionMarginBean> getAuctionMarginBeanList(List<Map<String, Object>> excelData) throws Exception {
        List<AuctionMarginBean> list = new ArrayList<>();
        AuctionMarginBean bean = null;
        for (Map<String, Object> map : excelData) {
            bean = new AuctionMarginBean();
            bean.setMarginSn(String.valueOf(map.get("订单编号")));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            bean.setRefundDate(df.parse(String.valueOf(map.get("退款时间"))));
            bean.setRefunder(String.valueOf(map.get("退款人")));
            bean.setRefundType(String.valueOf(map.get("退款方式")));
            bean.setRefundSn(String.valueOf(map.get("退款交易号")));
            bean.setMargin((map.get("保证金") == null ? null : new BigDecimal(String.valueOf(map.get("保证金")))));
            bean.setPaySn(String.valueOf(map.get("支付流水号")));
            list.add(bean);
        }
        return list;
    }

}
