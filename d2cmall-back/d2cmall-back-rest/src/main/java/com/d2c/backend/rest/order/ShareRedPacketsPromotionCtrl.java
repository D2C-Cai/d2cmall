package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.query.ShareRedPacketsPromotionSearcher;
import com.d2c.order.service.ShareRedPacketsPromotionService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/sharered")
public class ShareRedPacketsPromotionCtrl extends BaseCtrl<ShareRedPacketsPromotionSearcher> {

    @Autowired
    private ShareRedPacketsPromotionService shareRedPacketsPromotionService;

    @Override
    protected Response doList(ShareRedPacketsPromotionSearcher searcher, PageModel page) {
        PageResult<ShareRedPacketsPromotion> pager = shareRedPacketsPromotionService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(ShareRedPacketsPromotionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ShareRedPacketsPromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(ShareRedPacketsPromotionSearcher searcher, PageModel page) {
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
        SuccessResponse result = new SuccessResponse();
        ShareRedPacketsPromotion shareRedPacketsPromotion = JsonUtil.instance().toObject(data,
                ShareRedPacketsPromotion.class);
        if (shareRedPacketsPromotion.getMaxMoney().compareTo(shareRedPacketsPromotion.getMinMoney()) < 0
                || shareRedPacketsPromotion.getMinMoney().multiply(shareRedPacketsPromotion.getMaxNumber())
                .compareTo(shareRedPacketsPromotion.getTotalMoney()) > 0) {
            result.setStatus(-1);
            result.setMessage("金额分配不合理。");
            return result;
        }
        shareRedPacketsPromotionService.update(shareRedPacketsPromotion);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ShareRedPacketsPromotion shareRedPacketsPromotion = JsonUtil.instance().toObject(data,
                ShareRedPacketsPromotion.class);
        if (shareRedPacketsPromotion.getMaxMoney().compareTo(shareRedPacketsPromotion.getMinMoney()) < 0
                || shareRedPacketsPromotion.getMinMoney().multiply(shareRedPacketsPromotion.getMaxNumber())
                .compareTo(shareRedPacketsPromotion.getTotalMoney()) > 0) {
            result.setStatus(-1);
            result.setMessage("金额分配不合理。");
            return result;
        }
        shareRedPacketsPromotion.setStatus(0);
        shareRedPacketsPromotion = shareRedPacketsPromotionService.insert(shareRedPacketsPromotion);
        result.put("shareRedPacketsPromotion", shareRedPacketsPromotion);
        return result;
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

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response markPromotion(@PathVariable Integer status, Long id) {
        SuccessResponse result = new SuccessResponse();
        if (status == 1) {
            ShareRedPacketsPromotionSearcher searcher = new ShareRedPacketsPromotionSearcher();
            searcher.setStatus(1);
            if (shareRedPacketsPromotionService.countBySearcher(searcher) > 0) {
                result.setStatus(-1);
                result.setMessage("分享红包活动只允许上架一个。");
                return result;
            }
        }
        shareRedPacketsPromotionService.updateStatus(id, status);
        return result;
    }

}
