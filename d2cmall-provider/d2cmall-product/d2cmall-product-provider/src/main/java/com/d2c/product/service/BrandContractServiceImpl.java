package com.d2c.product.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandContractMapper;
import com.d2c.product.model.BrandContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("brandContractService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandContractServiceImpl extends ListServiceImpl<BrandContract> implements BrandContractService {

    @Autowired
    private BrandContractMapper brandContractMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BrandContract insert(BrandContract brandContract) {
        return this.save(brandContract);
    }

    @Override
    public List<BrandContract> findByBrandId(Long brandId) {
        return brandContractMapper.findByBrandId(brandId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        return super.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BrandContract brandContract) {
        if (brandContract.getUrl() == null) {
            this.updateFieldById(brandContract.getId().intValue(), "url", null);
        }
        return this.updateNotNull(brandContract);
    }

    @Override
    public List<Long> findIdsByBrandId(Long brandId) {
        List<Long> ids = null;
        if ((ids = brandContractMapper.findIdsByBrandId(brandId)) != null) {
            return ids;
        }
        return new ArrayList<Long>();
    }

    @Override
    public BrandContract findByBrandIdAndType(Long brandId, String type) {
        return brandContractMapper.findByBrandIdAndType(brandId, type);
    }

}
