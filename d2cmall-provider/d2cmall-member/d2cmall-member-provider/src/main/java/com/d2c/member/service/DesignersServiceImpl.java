package com.d2c.member.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.DesignersLog;
import com.d2c.logger.service.DesignersLogService;
import com.d2c.member.dao.DesignersMapper;
import com.d2c.member.model.Designers;
import com.d2c.member.query.DesignersSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(value = "designersService")
@Transactional(readOnly = true, noRollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DesignersServiceImpl extends ListServiceImpl<Designers> implements DesignersService {

    @Autowired
    private DesignersMapper designersMapper;
    @Autowired
    private DesignersLogService designersLogService;

    @Override
    public Designers findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<Designers> findBySearcher(DesignersSearcher searcher, PageModel page) {
        PageResult<Designers> pager = new PageResult<>(page);
        int totalCount = designersMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Designers> list = designersMapper.findBySearcher(page, searcher);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Designers insert(Designers designers, String operator) {
        Designers oldDesigners = designersMapper.findByCode(designers.getCode());
        if (oldDesigners != null) {
            throw new BusinessException("已存在该编码的设计师");
        }
        designers = this.save(designers);
        if (designers.getId() != null) {
            DesignersLog designersLog = new DesignersLog();
            JSONObject obj = new JSONObject(true);
            obj.put("操作", "新增设计师");
            obj.put("内容", "编号：" + designers.getCode() + "；名称：" + designers.getName());
            designersLog.setInfo(obj.toString());
            designersLog.setCreator(operator);
            designersLog.setCreateDate(new Date());
            designersLog.setDesignersId(designers.getId());
            designersLogService.insert(designersLog);
        }
        return designers;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Designers designers, String operator) {
        Designers oldDesigners = this.findById(designers.getId());
        if (!(oldDesigners.getCode().equals(designers.getCode()))) {
            Designers sameDesigners = designersMapper.findByCode(designers.getCode());
            if (sameDesigners != null && (!sameDesigners.getId().equals(oldDesigners.getId()))) {
                throw new BusinessException("已存在该编码的设计师");
            }
        }
        int result = this.updateNotNull(designers);
        if (result > 0 && !(designers.getCode().equals(oldDesigners.getCode())
                && designers.getName().equals(oldDesigners.getName()))) {
            DesignersLog designersLog = new DesignersLog();
            JSONObject obj = new JSONObject(true);
            obj.put("操作", "修改设计师");
            obj.put("修改前", "编号：" + oldDesigners.getCode() + "；名称：" + oldDesigners.getName());
            obj.put("修改后", "编号：" + designers.getCode() + "；名称：" + designers.getName());
            designersLog.setInfo(obj.toString());
            designersLog.setCreator(operator);
            designersLog.setCreateDate(new Date());
            designersLog.setDesignersId(designers.getId());
            designersLogService.insert(designersLog);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int bindMemberInfo(Long id, Long memberId, String loginCode) {
        return designersMapper.updateMemberInfo(id, memberId, loginCode);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int cancelMemberInfo(Long memberId) {
        return designersMapper.cancelMemberInfo(memberId);
    }

}
