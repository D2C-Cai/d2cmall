package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.AwardRecordDto;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.query.AwardRecordSearcher;
import com.d2c.order.service.AwardRecordService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.tx.AwardTxService;
import com.d2c.product.dto.AwardProductDto;
import com.d2c.product.model.AwardProduct.AwardType;
import com.d2c.product.query.AwardProductSearcher;
import com.d2c.product.service.AwardProductService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/rest/award/record")
public class AwardRecordCtrl extends BaseCtrl<AwardRecordSearcher> {

    @Autowired
    private AwardRecordService awardRecordService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AwardProductService awardProductService;
    @Reference
    private AwardTxService awardTxService;

    @Override
    protected Response doList(AwardRecordSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AwardRecordDto> pager = awardRecordService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(AwardRecordSearcher searcher) {
        return awardRecordService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "AwardResult";
    }

    @Override
    protected List<Map<String, Object>> getRow(AwardRecordSearcher searcher, PageModel page) {
        PageResult<AwardRecordDto> pager = awardRecordService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (AwardRecord awardRecord : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("会员ID", awardRecord.getMemberId());
            cellsMap.put("D2C账号", awardRecord.getLoginNo());
            cellsMap.put("会员昵称", awardRecord.getMemberName());
            cellsMap.put("奖品级别", awardRecord.getLevelName());
            cellsMap.put("奖品名称", awardRecord.getAwardName());
            cellsMap.put("抽奖时间", DateUtil.second2str(awardRecord.getLotteryTime()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "中奖名单表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"会员ID", "D2C账号", "会员昵称", "奖品级别", "奖品名称", "抽奖时间"};
    }

    @Override
    protected Response doHelp(AwardRecordSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @RequestMapping(value = "/awardlevel", method = RequestMethod.GET)
    public Response awardlevel() {
        SuccessResponse result = new SuccessResponse();
        List<String> list = awardRecordService.findAwardLevelName();
        result.put("list", list);
        return result;
    }

    /**
     * 导入订单返利表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rebate/import", method = RequestMethod.POST)
    public Response importOrderRebate(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                Long memberId = Long.parseLong(map.get("会员ID").toString());
                BigDecimal rabate = new BigDecimal(map.get("返利金额").toString());
                String uniqueMark = map.get("唯一标识").toString();
                try {
                    awardTxService.doRecome(memberId, rabate, uniqueMark, admin.getUsername());
                    return true;
                } catch (Exception e) {
                    errorMsg.append("唯一标识：" + uniqueMark + "发生错误，" + e.getMessage() + "<br/>");
                    return false;
                }
            }
        });
    }

    @RequestMapping(value = "/rebate/export", method = RequestMethod.POST)
    public Response exportOrderRebate(Date beginFinishDate, Date endFinishDate, Long sessionId) {
        // 奖品信息
        AwardProductSearcher searcher = new AwardProductSearcher();
        searcher.setSessionId(sessionId);
        searcher.setType(AwardType.RABATE.name());
        PageResult<AwardProductDto> awardPager = awardProductService.findBySearcher(searcher, new PageModel());
        if (awardPager.getTotalCount() == 0) {
            return new SuccessResponse();
        }
        Map<String, BigDecimal> rabateMap = new HashMap<>();
        List<Long> awardIds = new ArrayList<>();
        for (AwardProductDto awardProduct : awardPager.getList()) {
            rabateMap.put(String.valueOf(awardProduct.getId()), new BigDecimal(awardProduct.getParam()));
            awardIds.add(awardProduct.getId());
        }
        // 中奖会员和返利信息
        // AwardSession awardSession = awardSessionService.findById(sessionId);
        List<Map<String, Object>> list = orderItemService.findActualAmountForAward(DateUtil.str2day("2018-05-20"),
                DateUtil.str2day("2018-05-21"), beginFinishDate, endFinishDate, awardIds);
        String dateStr = DateUtil.day2str(beginFinishDate) + "~" + DateUtil.day2str(endFinishDate);
        for (Map<String, Object> map : list) {
            map.put("唯一标识", dateStr + map.get("会员ID"));
            map.put("返利比", rabateMap.get(map.get("中奖ID").toString()));
            map.put("返利金额",
                    rabateMap.get(map.get("中奖ID").toString()).multiply(new BigDecimal(map.get("完结订单实付金额").toString()))
                            .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        }
        // 生成报表
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        String fileName = admin.getUsername() + "_" + "返利中奖用户支付金额";
        String[] titleNames = new String[]{"会员ID", "会员账号", "完结订单实付金额", "返利比", "返利金额", "唯一标识"};
        ExcelUtil excelUtil = new ExcelUtil(fileName, admin.getUsername());
        boolean exportSuccess = createExcel(excelUtil, titleNames, list);
        createExcelResult(result, excelUtil.getExportFileBean());
        if (exportSuccess) {
            this.saveLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                    excelUtil.getExportFileBean().getFileSize(), "AwardRebateActual");
        }
        return result;
    }

}
