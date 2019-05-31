package com.d2c.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RequisitionErrorMapper;
import com.d2c.order.model.RequisitionError;
import com.d2c.order.query.RequisitionErrorSearcher;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("requisitionErrorService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RequisitionErrorServiceImpl extends ListServiceImpl<RequisitionError> implements RequisitionErrorService {

    @Autowired
    private RequisitionErrorMapper requisitionErrorMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RequisitionError insert(RequisitionError requisitionError) {
        requisitionError = this.save(requisitionError);
        doLog(requisitionError.getId(), "新增", requisitionError.getCreator());
        return requisitionError;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doLog(Long id, String operation, String operator) {
        RequisitionError requisitionError = this.findById(id);
        if (requisitionError.getLog() == null) {
            requisitionError.setLog(new JSONArray().toJSONString());
        }
        JSONArray logArray = JSON.parseArray(requisitionError.getLog());
        JSONObject obj = new JSONObject();
        obj.put("sort", logArray.size());
        obj.put("createDate", DateUtil.second2str(new Date()));
        obj.put("info", "操作:" + operation);
        obj.put("creator", operator);
        logArray.add(obj);
        return this.updateFieldById(id.intValue(), "log", logArray.toJSONString());
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doProcess(Long id, String operator) {
        int success = requisitionErrorMapper.doProcess(id, operator);
        doLog(id, "处理成功", operator);
        return success;
    }

    @Override
    public PageResult<RequisitionError> findBySearcher(RequisitionErrorSearcher searcher, PageModel page) {
        PageResult<RequisitionError> pager = new PageResult<RequisitionError>(page);
        Integer totalCount = requisitionErrorMapper.countBySearcher(searcher);
        List<RequisitionError> list = new ArrayList<>();
        if (totalCount > 0) {
            list = requisitionErrorMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(RequisitionErrorSearcher searcher) {
        return requisitionErrorMapper.countBySearcher(searcher);
    }

    @Override
    public RequisitionError findById(Long id) {
        return requisitionErrorMapper.selectByPrimaryKey(id);
    }

    @Override
    public int doRemark(Long id, String remark, String operator) {
        int success = requisitionErrorMapper.updateFieldById(id.intValue(), "remark", remark);
        doLog(id, "修改备注说明", operator);
        return success;
    }

}
