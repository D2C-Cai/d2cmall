package com.d2c.backend.rest.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.report.model.WalletSummary;
import com.d2c.report.query.WalletSummarySearcher;
import com.d2c.report.service.WalletSummaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/report/walletsummary")
public class WalletSummaryCtrl extends BaseCtrl<WalletSummarySearcher> {

    @Reference
    private WalletSummaryService walletSummaryService;

    @Override
    protected Response doList(WalletSummarySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<WalletSummary> pager = walletSummaryService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(WalletSummarySearcher searcher) {
        return walletSummaryService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "wallet";
    }

    @Override
    protected List<Map<String, Object>> getRow(WalletSummarySearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        PageResult<WalletSummary> pager = walletSummaryService.findBySearcher(searcher, page);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (WalletSummary walletSummary : pager.getList()) {
            Map<String, Object> cellsMap = new HashMap<String, Object>();
            cellsMap.put("结算时间",
                    walletSummary.getCalculateDate() == null ? "" : sf.format(walletSummary.getCalculateDate()));
            cellsMap.put("业务名称", walletSummary.getTransactionInfo());
            cellsMap.put("本金", walletSummary.getAmount());
            cellsMap.put("红包", walletSummary.getGiftAmount());
            cellsMap.put("收入/支出", walletSummary.getDirection() == 1 ? "收入" : "支出");
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "钱包收支汇总表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"结算时间", "业务名称", "本金", "红包", "收入/支出"};
    }

    @Override
    protected Response doHelp(WalletSummarySearcher searcher, PageModel page) {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
