package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.O2oSubscribeDto;
import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.model.O2oSubscribeItem;
import com.d2c.order.query.O2oSubscribeSearcher;
import com.d2c.order.service.O2oSubscribeService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/o2o/o2osubscribe")
public class O2oSubscribeCtrl extends BaseCtrl<O2oSubscribeSearcher> {

    @Autowired
    private O2oSubscribeService o2oSubscribeService;

    @Override
    protected List<Map<String, Object>> getRow(O2oSubscribeSearcher searcher, PageModel page) {
        PageResult<O2oSubscribeDto> pager = o2oSubscribeService.findBySearch(searcher, page);
        List<O2oSubscribeDto> o2oSubscribeDtos = pager.getList();
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (O2oSubscribeDto o2oSubscribeDto : o2oSubscribeDtos) {
            cellsMap = new HashMap<>();
            cellsMap.put("会员", o2oSubscribeDto.getName());
            cellsMap.put("预约单号", o2oSubscribeDto.getSn());
            cellsMap.put("门店名称", o2oSubscribeDto.getStoreName());
            cellsMap.put("门店电话", o2oSubscribeDto.getStoreTel());
            cellsMap.put("门店地址", o2oSubscribeDto.getStoreAddress());
            cellsMap.put("客户", o2oSubscribeDto.getName());
            cellsMap.put("客户联系方式", o2oSubscribeDto.getContact());
            cellsMap.put("客户电话", o2oSubscribeDto.getTel());
            cellsMap.put("客户Email", o2oSubscribeDto.getEmail());
            cellsMap.put("客户微信", o2oSubscribeDto.getWechat());
            cellsMap.put("性别", o2oSubscribeDto.getSex());
            cellsMap.put("人数", o2oSubscribeDto.getNumbers());
            if (o2oSubscribeDto.getEstimateDate() != null) {
                cellsMap.put("预定日期", sdf.format(o2oSubscribeDto.getEstimateDate()));
            }
            cellsMap.put("预定时间", o2oSubscribeDto.getStartToEndTime());
            cellsMap.put("预约次数", o2oSubscribeDto.getTimes());
            cellsMap.put("客户需求", o2oSubscribeDto.getStoreService());
            cellsMap.put("客户其它需求", o2oSubscribeDto.getOptionRequire());
            cellsMap.put("来店伴侣", o2oSubscribeDto.getCompanion());
            cellsMap.put("状态", o2oSubscribeDto.getStatusName());
            cellsMap.put("客户购买金额", o2oSubscribeDto.getPayAmount());
            if (o2oSubscribeDto.getSubmitDate() != null) {
                cellsMap.put("客户提交时间", sdf.format(o2oSubscribeDto.getSubmitDate()));
            }
            if (o2oSubscribeDto.getNoticeDate() != null) {
                cellsMap.put("客服通知时间", sdf.format(o2oSubscribeDto.getNoticeDate()));
            }
            cellsMap.put("客服通知人", o2oSubscribeDto.getNoticeMan());
            if (o2oSubscribeDto.getReceiveDate() != null) {
                cellsMap.put("门店接收时间", sdf.format(o2oSubscribeDto.getReceiveDate()));
            }
            cellsMap.put("门店接收人", o2oSubscribeDto.getReceiveMan());
            if (o2oSubscribeDto.getCompleteDate() != null) {
                cellsMap.put("门店完成时间", sdf.format(o2oSubscribeDto.getCompleteDate()));
            }
            cellsMap.put("门店完成人", o2oSubscribeDto.getCompleteMan());
            cellsMap.put("门店反馈", o2oSubscribeDto.getFeedback());
            cellsMap.put("客服备注", o2oSubscribeDto.getRemark());
            cellsMap.put("客服回访", o2oSubscribeDto.getVisit());
            int index = 0;
            for (O2oSubscribeItem item : o2oSubscribeDto.getItems()) {
                if (index++ > 0) {
                    cellsMap = new HashMap<>();
                }
                cellsMap.put("品牌", item.getDesignerName());
                cellsMap.put("品类", item.getProductCategory());
                cellsMap.put("商品货号", item.getProductSn());
                cellsMap.put("商品名称", item.getProductName());
                cellsMap.put("条码", item.getProductSkuSn());
                cellsMap.put("商品数量", item.getQuantity());
                cellsMap.put("颜色", item.getColorValue());
                cellsMap.put("尺码", item.getSizeValue());
                cellsMap.put("销售价", item.getPrice());
                cellsMap.put("吊牌价", item.getOriginalPrice());
                rowList.add(cellsMap);
            }
        }
        return rowList;
    }

    @Override
    protected int count(O2oSubscribeSearcher searcher) {
        return o2oSubscribeService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "预约表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"会员", "预约单号", "门店名称", "门店电话", "门店地址", "客户", "客户联系方式", "客户电话", "客户Email", "客户微信", "性别",
                "人数", "预定日期", "预定时间", "预约次数", "客户需求", "客户其它需求", "来店伴侣", "状态", "客户购买金额", "客户提交时间", "客服通知时间", "客服通知人",
                "门店接收时间", "门店接收人", "门店完成时间", "门店完成人", "门店反馈", "客服备注", "客服回访", "商品货号", "商品名称", "条码", "品牌", "品类", "商品数量",
                "颜色", "尺码", "吊牌价", "销售价"};
    }

    @Override
    protected Response doHelp(O2oSubscribeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(O2oSubscribeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        try {
            AdminDto dto = this.getLoginedAdmin();
            if (dto.getStoreId() != null) {
                searcher.setStoreId(dto.getStoreId());
                searcher.setGtStatus(1);
            }
        } catch (NotLoginException e) {
            return new ErrorResponse("账号未登录");
        }
        PageResult<O2oSubscribeDto> pager = o2oSubscribeService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        O2oSubscribe o2oSubscribe = o2oSubscribeService.findById(id);
        result.put("o2oSubscribe", o2oSubscribe);
        return result;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        O2oSubscribe o2oSubscribe = JsonUtil.instance().toObject(data, O2oSubscribe.class);
        SuccessResponse result = new SuccessResponse();
        try {
            o2oSubscribeService.update(o2oSubscribe);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return "O2oSubscribe";
    }

    /**
     * 客服关闭预约
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Response cancel(Long id, String cancelReason) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            int success = o2oSubscribeService.doCancel(id, admin.getUsername(), cancelReason);
            if (success != 1) {
                return new ErrorResponse("订单状态已经变更，取消不成功！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    /**
     * 客服备注
     */
    @RequestMapping(value = "/remark", method = RequestMethod.POST)
    public Response remark(Long id, String remark) {
        SuccessResponse result = new SuccessResponse();
        try {
            o2oSubscribeService.updateRemarkById(id, remark);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    /**
     * 客服回访说明
     */
    @RequestMapping(value = "/visit", method = RequestMethod.POST)
    public Response visit(Long id, String visit) {
        SuccessResponse result = new SuccessResponse();
        try {
            o2oSubscribeService.updateVisitById(id, visit);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    /**
     * 标记已通知门店
     */
    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public Response notice(Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            o2oSubscribeService.doNotice(id, admin.getUsername());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    /**
     * 门店接收
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/receive/{id}", method = RequestMethod.POST)
    public Response doReceive(@PathVariable("id") Long id) {
        try {
            AdminDto dto = this.getLoginedAdmin();
            if (dto.getStoreId() == null) {
                return new ErrorResponse("该账号没有门店权限");
            }
            o2oSubscribeService.doReceive(id, dto.getUsername());
        } catch (NotLoginException e) {
            return new ErrorResponse("账号未登录");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
        return new SuccessResponse();
    }

    /**
     * 门店准备就绪
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/ready/{id}", method = RequestMethod.POST)
    public Response doReady(@PathVariable("id") Long id) {
        try {
            AdminDto dto = this.getLoginedAdmin();
            if (dto.getStoreId() == null) {
                return new ErrorResponse("该账号没有门店权限");
            }
            o2oSubscribeService.doReady(id);
        } catch (NotLoginException e) {
            return new ErrorResponse("账号未登录");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
        return new SuccessResponse();
    }

    /**
     * 门店服务结束
     *
     * @param id
     * @param feedback      门店结果反馈说明
     * @param payAmount     实际购买金额
     * @param payStatus     实际购买状态
     * @param actualNumbers 实际到店人数
     * @param retailSn      实际购买零售单
     * @return
     */
    @RequestMapping(value = "/finish/{id}", method = RequestMethod.POST)
    public Response doFinish(@PathVariable("id") Long id, String feedback, BigDecimal payAmount, Integer payStatus,
                             Integer actualNumbers, String retailSn) {
        try {
            AdminDto dto = this.getLoginedAdmin();
            if (dto.getStoreId() == null) {
                return new ErrorResponse("该账号没有门店权限");
            }
            o2oSubscribeService.doComplete(id, dto.getUsername(), feedback, payAmount, payStatus, actualNumbers,
                    retailSn);
        } catch (NotLoginException e) {
            return new ErrorResponse("账号未登录");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
        return new SuccessResponse();
    }

}
