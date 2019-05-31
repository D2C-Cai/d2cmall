package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.dto.AuctionOfferDto;
import com.d2c.order.query.AuctionOfferSearcher;
import com.d2c.order.service.AuctionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/auctionoffer")
public class AuctionOfferCtrl extends BaseCtrl<AuctionOfferSearcher> {

    @Autowired
    private AuctionOfferService auctionOfferService;

    @Override
    protected List<Map<String, Object>> getRow(AuctionOfferSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(AuctionOfferSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
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
    protected Response doHelp(AuctionOfferSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<HelpDTO> dtos = auctionOfferService.findHelpDtos(searcher, page);
        return new SuccessResponse(dtos);
    }

    /**
     * 拍卖出价列表
     *
     * @return
     */
    @Override
    protected Response doList(AuctionOfferSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AuctionOfferDto> pager = auctionOfferService.findDtoBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

}