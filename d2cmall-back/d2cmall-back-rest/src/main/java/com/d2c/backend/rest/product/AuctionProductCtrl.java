package com.d2c.backend.rest.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.order.query.AuctionOfferSearcher;
import com.d2c.order.service.AuctionOfferService;
import com.d2c.order.service.tx.AuctionTxService;
import com.d2c.product.dto.AuctionProductDto;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.query.AuctionProductSearcher;
import com.d2c.product.service.AuctionProductService;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/auctionproduct")
public class AuctionProductCtrl extends BaseCtrl<AuctionProductSearcher> {

    @Autowired
    private AuctionProductService auctionProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AuctionOfferService auctionOfferService;
    @Reference
    private AuctionTxService auctionTxService;

    @Override
    protected List<Map<String, Object>> getRow(AuctionProductSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(AuctionProductSearcher searcher) {
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
    protected Response doHelp(AuctionProductSearcher searcher, PageModel page) {
        // 暂时做简化处理吧，一般会做key-value的形式传值
        BeanUt.trimString(searcher);
        PageResult<AuctionProductDto> pager = auctionProductService.findDtoBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("searcher", searcher);
        return result;
    }

    @Override
    protected Response doList(AuctionProductSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AuctionProductDto> pager = auctionProductService.findDtoBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        AuctionProduct ap = auctionProductService.findById(id);
        AuctionProductDto dto = new AuctionProductDto();
        if (ap == null) {
            return new ErrorResponse("无法找到该数据");
        } else {
            BeanUtils.copyProperties(ap, dto);
            dto.setProduct(productService.findDetailById(dto.getProductId()));
        }
        SuccessResponse sr = new SuccessResponse();
        sr.put("auctionProduct", dto);
        return sr;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        if (ids == null || ids.length == 0) {
            return new ErrorResponse("请正确输入id");
        }
        for (Long id : ids) {
            AuctionOfferSearcher searcher = new AuctionOfferSearcher();
            searcher.setAuctionId(id);
            int toltal = auctionOfferService.countBySearch(searcher);
            if (toltal > 0) {
                result.setStatus(-1);
                result.setMessage("有交易记录的拍卖商品不能删除！");
                return result;
            }
            auctionProductService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        AuctionOfferSearcher searcher = new AuctionOfferSearcher();
        searcher.setAuctionId(id);
        int toltal = auctionOfferService.countBySearch(searcher);
        if (toltal > 0) {
            result.setStatus(-1);
            result.setMessage("有交易记录的拍卖商品不能删除！");
            return result;
        }
        auctionProductService.delete(id);
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AuctionProduct product = (AuctionProduct) JsonUtil.instance().toObject(data, AuctionProduct.class);
        if (product.getBeginDate().after(product.getEndDate())) {
            result.setMessage("开始时间不能晚于结束时间，新增失败！");
            return result;
        }
        product = auctionProductService.insert(product);
        result.setMessage("新增成功！");
        result.put("data", product);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AuctionProductDto product = (AuctionProductDto) JsonUtil.instance().toObject(data, AuctionProductDto.class);
        if (product.getBeginDate().after(product.getEndDate())) {
            result.setMessage("开始时间不能晚于结束时间，修改失败！");
            return result;
        }
        AuctionProduct ap = auctionProductService.findById(id);
        if (!ap.isDoing()) {
            product.setCurrentPrice(product.getBeginPrice());
        }
        auctionProductService.update(product);
        result.setMessage("修改保存成功！");
        result.put("data", product);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 批量上下架
     *
     * @return
     */
    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response doMark(@PathVariable(value = "status") Integer status, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        AuctionProduct auction = auctionProductService.findById(ids[0]);
        if (auction.getStatus().intValue() == -1) {
            result.setStatus(-1);
            result.setMessage("删除的拍卖商品不可上下架！");
            return result;
        }
        if (status == 1) {
            for (Long id : ids) {
                int success = auctionProductService.doUp(id);
                if (success > 0) {
                    AuctionProduct auctionProduct = auctionProductService.findById(id);
                    this.breachAuctionMQ(auctionProduct);
                }
            }
        } else {
            for (Long id : ids) {
                auctionProductService.doDown(id);
            }
        }
        return result;
    }

    private void breachAuctionMQ(AuctionProduct auctionProduct) {
        Date now = new Date();
        if (auctionProduct.getEndDate().after(now)) {
            long interval = (auctionProduct.getEndDate().getTime() - now.getTime()) / 1000;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("auctionId", auctionProduct.getId());
            map.put("type", "SUCCESS");
            MqEnum.BREACH_AUCTION.send(map, interval);
        }
    }

    /**
     * 结束拍卖
     *
     * @return
     */
    @RequestMapping(value = "/doEndAuction/{id}", method = RequestMethod.POST)
    public Response doEndAuction(@PathVariable(value = "id") Long id) {
        AuctionProduct auctionProduct = auctionProductService.findById(id);
        auctionTxService.doEndAuction(auctionProduct);
        SuccessResponse result = new SuccessResponse();
        return result;
    }

    /**
     * 拍卖排序
     *
     * @return
     */
    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response sort(@PathVariable(value = "id") Long id, Integer sort) {
        auctionProductService.updateSort(id, sort);
        return new SuccessResponse();
    }

}
