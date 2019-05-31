package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.DistributorDto;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DistributorSearcher;
import com.d2c.member.service.DistributorService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/distributor")
public class DistributorCtrl extends BaseCtrl<DistributorSearcher> {

    @Autowired
    private DistributorService distributorService;

    @Override
    protected List<Map<String, Object>> getRow(DistributorSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(DistributorSearcher searcher) {
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
    protected Response doHelp(DistributorSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response doList(DistributorSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<DistributorDto> pager = distributorService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Distributor distributor = distributorService.findById(id);
        result.put("distributor", distributor);
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
        SuccessResponse result = new SuccessResponse();
        Distributor distributor = JsonUtil.instance().toObject(data, Distributor.class);
        distributor = distributorService.insert(distributor);
        result.put("distributor", distributor);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        DistributorDto distributor = JsonUtil.instance().toObject(data, DistributorDto.class);
        distributorService.update(distributor);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 绑定折扣组
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public Response bind(Long[] ids, Long groupId) {
        SuccessResponse result = new SuccessResponse();
        int success = 0;
        for (Long id : ids) {
            Distributor distributor = distributorService.findById(id);
            success += distributorService.doBindGroup(id, groupId, distributor.getMemberId());
        }
        result.setMessage("绑定成功" + success + "条, 不成功" + (ids.length - success) + "条");
        return result;
    }

    /**
     * 解绑折扣组
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Response cancel(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        int success = 0;
        for (Long id : ids) {
            Distributor distributor = distributorService.findById(id);
            success += distributorService.doCancleGroup(id, distributor.getMemberId());
        }
        result.setMessage("取消成功" + success + "条, 不成功" + (ids.length - success) + "条");
        return result;
    }

    /**
     * 更新状态
     */
    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response status(@PathVariable Integer status, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            Distributor distributor = distributorService.findById(id);
            distributorService.updateStatusById(id, status, distributor.getMemberId());
        }
        return result;
    }

    /**
     * 是否允许退货
     */
    @RequestMapping(value = "/reship/{status}", method = RequestMethod.POST)
    public Response reship(@PathVariable Integer status, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            Distributor distributor = distributorService.findById(id);
            distributorService.updateReship(id, status, distributor.getMemberId());
        }
        return result;
    }

    /**
     * 是否免运费
     */
    @RequestMapping(value = "/free/{status}", method = RequestMethod.POST)
    public Response free(@PathVariable Integer status, Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            Distributor distributor = distributorService.findById(id);
            distributorService.updateFreeShipFeeById(id, status, distributor.getMemberId());
        }
        return result;
    }

}
