package com.d2c.backend.rest.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchInfoSearcher;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.MemberSearchKeyService;
import com.d2c.logger.service.MemberSearchSumService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.PinYinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rest/cms/membersearchsum")
public class MemberSearchSumCtrl extends BaseCtrl<MemberSearchSumSearcher> {

    @Autowired
    private MemberSearchSumService memberSearchSumService;
    @Autowired
    private MemberSearchKeyService memberSearchKeyService;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;

    @Override
    protected List<Map<String, Object>> getRow(MemberSearchSumSearcher searcher, PageModel page) {
        List<MemberSearchSum> list = memberSearchSumService.findBySearcher(searcher, page).getList();
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        for (MemberSearchSum memberSearchSum : list) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("关键字", memberSearchSum.getKeyword());
            cellsMap.put("展示名", memberSearchSum.getDisplayName());
            cellsMap.put("搜索次数", memberSearchSum.getNumber());
            cellsMap.put("排序", memberSearchSum.getSort());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(MemberSearchSumSearcher searcher) {
        return this.memberSearchSumService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "搜索关键字汇总表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"关键字", "展示名", "搜索次数", "排序"};
    }

    @Override
    protected Response doHelp(MemberSearchSumSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(MemberSearchSumSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberSearchSum> pager = memberSearchSumService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            this.doDelete(id);
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        memberSearchSumService.delete(id);
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        MemberSearchSum memberSearchSum = (MemberSearchSum) JsonUtil.instance().toObject(data, MemberSearchSum.class);
        if (memberSearchSum.check()) {
            result.setStatus(-1);
            result.setMsg("搜索关键字不能为空！");
            return result;
        }
        String keyword = memberSearchSum.getKeyword();
        if (StringUtils.isBlank(memberSearchSum.getDisplayName()))
            memberSearchSum.setDisplayName(keyword);
        memberSearchSum.setKeyword(keyword.toLowerCase());
        memberSearchSum.setNumber(0);
        memberSearchSum = memberSearchSumService.save(memberSearchSum);
        result.put("memberSearchSum", memberSearchSum);
        result.setMessage("添加成功！");
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Response result = new SuccessResponse();
        MemberSearchSum newMs = (MemberSearchSum) JsonUtil.instance().toObject(data, MemberSearchSum.class);
        memberSearchSumService.update(newMs);
        result.setMessage("修改成功！");
        return result;
    }

    @Override
    protected String getExportFileType() {
        return "MemberSearchSum";
    }

    @RequestMapping(value = "/updateStatus", method = {RequestMethod.GET})
    public Response updateStatus(int status, Long id) {
        Response result = new SuccessResponse();
        memberSearchSumService.updateStatus(id, status);
        result.setMessage("设置成功！");
        return result;
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, int status) {
        SuccessResponse result = new SuccessResponse();
        memberSearchSumService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public Response system(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            try {
                memberSearchSumService.doSystem(id);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        result.setMessage("变更成功！");
        return result;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response sort(@PathVariable Long id, int sort) {
        SuccessResponse result = new SuccessResponse();
        if (id == null) {
            result.setStatus(-1);
            return result;
        }
        int n = memberSearchSumService.updateSort(id, sort);
        if (n != 1) {
            result.setStatus(-1);
        }
        return result;
    }

    @RequestMapping(value = "/sum", method = RequestMethod.POST)
    public Response sum() {
        SuccessResponse result = new SuccessResponse();
        PageModel page = new PageModel();
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        MemberSearchInfoSearcher searcher = new MemberSearchInfoSearcher();
        searcher.setStatistic(0);
        searcher.setEndCreateDate(new Date());
        PageResult<CountDTO<String>> pager = memberSearchKeyService.findGroupBySearcher(searcher, page);
        for (CountDTO<String> vc : pager.getList()) {
            this.doSum(vc, searcher.getEndCreateDate());
        }
        while (pager.isNext()) {
            page.setPageNumber(pager.getPageNumber() + 1);
            pager = memberSearchKeyService.findGroupBySearcher(searcher, page);
            for (CountDTO<String> vc : pager.getList()) {
                this.doSum(vc, searcher.getEndCreateDate());
            }
        }
        result.put("redirect", "/memberSearchSum/list");
        return result;
    }

    private void doSum(CountDTO<String> vc, Date date) {
        if (vc == null || StringUtils.isBlank(vc.getValue())) {
            return;
        }
        if (PinYinUtil.isMessyCode(vc.getValue())) {// 乱码处理
            memberSearchKeyService.remove(vc.getValue(), date, 0);
            return;
        }
        int n = memberSearchSumService.updateNumberByKeyword(vc.getValue(), vc.getCount());
        MemberSearchSum memberSearchSum = null;
        if (n == 0) {
            memberSearchSum = new MemberSearchSum();
            memberSearchSum.setNumber(vc.getCount());
            memberSearchSum.setKeyword(vc.getValue());
            memberSearchSum.setDisplayName(vc.getValue());
            try {
                memberSearchSumService.insert(memberSearchSum);
                SearcherMemberSum dto = new SearcherMemberSum();
                BeanUtils.copyProperties(memberSearchSum, dto);
                this.searchKeySearcherService.insert(dto, 1);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            memberSearchSum = this.memberSearchSumService.findByKey(vc.getValue());
            if (memberSearchSum != null) {
                this.searchKeySearcherService.removeByKey(vc.getValue());
                SearcherMemberSum dto = new SearcherMemberSum();
                BeanUtils.copyProperties(memberSearchSum, dto);
                searchKeySearcherService.insert(dto, 1);
            }
        }
        memberSearchKeyService.updateStatistic(vc.getValue(), date, 0);
    }

}
