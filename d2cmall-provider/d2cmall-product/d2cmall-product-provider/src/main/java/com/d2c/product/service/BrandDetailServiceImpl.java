package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandDetailMapper;
import com.d2c.product.dto.BrandDetailDto;
import com.d2c.product.model.BrandContract;
import com.d2c.product.model.BrandDetail;
import com.d2c.product.query.BrandDetailSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("brandDetailService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BrandDetailServiceImpl extends ListServiceImpl<BrandDetail> implements BrandDetailService {

    @Autowired
    private BrandDetailMapper brandDetailMapper;
    @Autowired
    private BrandContractService brandContractService;

    @Override
    public BrandDetailDto findById(Long id) {
        BrandDetail brandDetail = brandDetailMapper.selectByPrimaryKey(id);
        BrandDetailDto dto = new BrandDetailDto();
        BeanUtils.copyProperties(brandDetail, dto);
        dto.setContracts(brandContractService.findByBrandId(brandDetail.getBrandId()));
        return dto;
    }

    @Override
    public BrandDetail findByBrandId(Long brandId) {
        return brandDetailMapper.findByBrandId(brandId);
    }

    @Override
    public PageResult<BrandDetailDto> findBySearcher(BrandDetailSearcher searcher, PageModel page) {
        PageResult<BrandDetailDto> pager = new PageResult<BrandDetailDto>(page);
        Integer totalCount = brandDetailMapper.countBySearcher(searcher);
        List<BrandDetailDto> dtos = new ArrayList<BrandDetailDto>();
        if (totalCount > 0) {
            List<BrandDetail> list = brandDetailMapper.findBySearcher(searcher, page);
            for (BrandDetail brandDetail : list) {
                BrandDetailDto dto = new BrandDetailDto();
                BeanUtils.copyProperties(brandDetail, dto);
                dto.setContracts(brandContractService.findByBrandId(brandDetail.getBrandId()));
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public Integer countBySearcher(BrandDetailSearcher searcher) {
        return brandDetailMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BrandDetail insert(BrandDetail brandDetail) {
        return this.save(brandDetail);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BrandDetailDto brandDetail) {
        int success = this.updateNotNull(brandDetail);
        List<Long> brandContractIds = brandContractService.findIdsByBrandId(brandDetail.getBrandId());
        if (success > 0) {
            for (BrandContract brandContract : brandDetail.getContracts()) {
                if (brandContract.getId() == null || brandContract.getId() == 0) {
                    brandContract.setBrandId(brandDetail.getBrandId());
                    brandContractService.insert(brandContract);
                } else {
                    brandContractService.update(brandContract);
                    brandContractIds.remove(brandContract.getId());
                }
            }
            if (brandContractIds.size() > 0) {
                for (Long brandContractId : brandContractIds) {
                    brandContractService.deleteById(brandContractId);
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateContractStatus(Long id, Integer status) {
        return this.updateFieldById(id.intValue(), "contract_status", status);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRatioById(Long id, String partnerRatio) {
        return this.updateFieldById(id.intValue(), "partner_ratio", partnerRatio);
    }

}
