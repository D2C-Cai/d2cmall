package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.member.service.PiliLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/piliLive")
public class PiliLiveCtrl extends BaseCtrl<PiliLiveSearcher> {

    @Autowired
    private PiliLiveService piliLiveService;

    @Override
    protected Response doList(PiliLiveSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PiliLive> pager = piliLiveService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PiliLiveSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(PiliLiveSearcher searcher, PageModel page) {
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
    protected Response doHelp(PiliLiveSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        PiliLive piliLive = piliLiveService.findById(id);
        result.put("piliLive", piliLive);
        return result;
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
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        piliLiveService.deleteById(id);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 上下架
     *
     * @param ids
     * @param mark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/mark/{mark}", method = RequestMethod.POST)
    public Response updateMark(Long[] ids, @PathVariable Integer mark) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            piliLiveService.updateMark(id, mark, admin.getUsername());
        }
        return result;
    }

    /**
     * 置顶
     *
     * @param id
     * @param top
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/top/{top}", method = RequestMethod.POST)
    public Response updateTop(Long id, @PathVariable Integer top) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        piliLiveService.updateTop(id, top, admin.getUsername());
        return result;
    }

    /**
     * 更改僵尸粉数量
     *
     * @param id
     * @param vfans
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/vfans", method = RequestMethod.POST)
    public Response updateVcount(Long id, Integer vfans) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        piliLiveService.updateVfans(id, vfans, admin.getUsername());
        return result;
    }

    /**
     * 更改实时人数区间
     *
     * @param id
     * @param vrate
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/vrate", method = RequestMethod.POST)
    public Response updateVrate(Long id, Integer vrate) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        piliLiveService.updateVrate(id, vrate, admin.getUsername());
        return result;
    }

    /**
     * 更改红包倍率
     *
     * @param id
     * @param ratio
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/ratio", method = RequestMethod.POST)
    public Response updateRatio(Long id, Integer ratio) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        piliLiveService.updateRatio(id, ratio, admin.getUsername());
        return result;
    }

}
