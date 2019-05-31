package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.LogisticsTemplateMapper;
import com.d2c.order.dto.LogisticsTemplateDto;
import com.d2c.order.model.LogisticsCompany;
import com.d2c.order.model.LogisticsPostage;
import com.d2c.order.model.LogisticsTemplate;
import com.d2c.order.query.LogisticsTemplateSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("logisticsTemplateService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class LogisticsTemplateServiceImpl extends ListServiceImpl<LogisticsTemplate>
        implements LogisticsTemplateService {

    @Autowired
    private LogisticsTemplateMapper logisticsTemplateMapper;
    @Autowired
    private LogisticsPostageService logisticsPostageService;
    @Autowired
    private LogisticsService logisticsService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public LogisticsTemplateDto insert(LogisticsTemplateDto dto) {
        LogisticsCompany company = logisticsService.findCompanyByName(dto.getDeliveryName());
        if (company != null) {
            LogisticsTemplate oneTemplate = this.findByDeliveryCode(company.getCode());
            if (oneTemplate != null) {
                throw new BusinessException("已存在" + dto.getDeliveryName() + "的模板了，请先下架，再添加");
            } else {
                dto.setDeliveryCode(company.getCode());
                LogisticsTemplate template = this.save(dto);
                if (template.getId() != null && dto.getLogisticsPostage() != null) {
                    logisticsPostageService.insertBatch(dto.getLogisticsPostage(), template.getId());
                }
            }
        }
        return null;
    }

    @Override
    public PageResult<LogisticsTemplateDto> findBySearcher(LogisticsTemplateSearcher searcher, PageModel page) {
        PageResult<LogisticsTemplateDto> pager = new PageResult<>(page);
        int totalCount = logisticsTemplateMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<LogisticsTemplate> logisticsTemplates = logisticsTemplateMapper.findBySearcher(searcher, page);
            List<LogisticsTemplateDto> dtos = new ArrayList<>();
            for (LogisticsTemplate template : logisticsTemplates) {
                List<LogisticsPostage> postageSettings = logisticsPostageService.findByTemplateId(template.getId());
                LogisticsTemplateDto dto = new LogisticsTemplateDto();
                BeanUtils.copyProperties(template, dto);
                dto.setLogisticsPostage(postageSettings);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(LogisticsTemplateDto template) {
        // template.validate();
        if (template.getLogisticsPostage() != null) {
            for (LogisticsPostage postage : template.getLogisticsPostage()) {
                if (postage.getId() == null || postage.getId() == 0) {
                    logisticsPostageService.insert(postage, template.getId());
                } else {
                    logisticsPostageService.update(postage);
                }
            }
        }
        return logisticsTemplateMapper.updateTemplate(template);
    }

    @Override
    public int updateStatus(Long id, Integer status, String admin) {
        return logisticsTemplateMapper.updateStatus(id, status, admin);
    }

    @Override
    public LogisticsTemplateDto findById(Long id) {
        LogisticsTemplate template = this.findOneById(id);
        LogisticsTemplateDto dto = new LogisticsTemplateDto();
        BeanUtils.copyProperties(template, dto);
        List<LogisticsPostage> list = logisticsPostageService.findByTemplateId(id);
        dto.setLogisticsPostage(list);
        return dto;
    }

    public LogisticsTemplateDto findByDeliveryCode(String deliveryCode) {
        LogisticsTemplate template = logisticsTemplateMapper.findByDeliveryCode(deliveryCode);
        if (template != null) {
            LogisticsTemplateDto dto = new LogisticsTemplateDto();
            BeanUtils.copyProperties(template, dto);
            List<LogisticsPostage> list = logisticsPostageService.findByTemplateId(template.getId());
            dto.setLogisticsPostage(list);
            return dto;
        }
        return null;
    }

}
