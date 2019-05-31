package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.BrandLog;
import com.d2c.logger.model.BrandLog.DesignerLogType;
import com.d2c.logger.service.BrandLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandMapper;
import com.d2c.product.dto.BrandDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandDetail;
import com.d2c.product.query.BrandSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.service.DesignerSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("brandService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BrandServiceImpl extends ListServiceImpl<Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Autowired
    private BrandLogService brandLogService;
    @Autowired
    private BrandDetailService brandDetailService;

    @Override
    @Cacheable(value = "brand", key = "'brand_'+#id", unless = "#result == null")
    public Brand findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public Brand findByCode(String code) {
        return brandMapper.findByCode(code);
    }

    @Override
    public Map<String, Object> findSimpleByCode(String code) {
        return brandMapper.findSimpleByCode(code);
    }

    @Override
    public Brand findByBrandCode(String brandCode) {
        return brandMapper.findByBrandCode(brandCode);
    }

    @Override
    public Brand findByDomain(String domain) {
        return brandMapper.findByDomain(domain);
    }

    @Override
    public PageResult<Brand> findByCouponDefId(Long defineId, PageModel page) {
        PageResult<Brand> pager = new PageResult<>(page);
        int totalCount = brandMapper.countByCouponDefId(defineId);
        if (totalCount > 0) {
            pager.setTotalCount(totalCount);
            pager.setList(brandMapper.findByCouponDefId(page, defineId));
        }
        return pager;
    }

    @Override
    public PageResult<Brand> findByDesignerTagId(Long tagId, PageModel page) {
        PageResult<Brand> pager = new PageResult<>(page);
        int totalCount = brandMapper.countByDesignerTagId(tagId);
        if (totalCount > 0) {
            List<Brand> list = brandMapper.findByDesignerTagId(tagId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public List<Brand> findByDesignersId(Long id, Integer[] status) {
        return brandMapper.findByDesignersId(id, status);
    }

    @Override
    public List<Long> findIdsByDesignersId(Long id) {
        return brandMapper.findIdsByDesignersId(id);
    }

    @Override
    public String findNameByIds(Long[] brandId) {
        return brandMapper.findNameByIds(brandId);
    }

    /**
     * 获取索引模型
     *
     * @param designer
     * @return
     */
    private SearcherDesigner toSearcher(Brand designer) {
        SearcherDesigner sd = new SearcherDesigner();
        BeanUtils.copyProperties(designer, sd);
        return sd;
    }

    @Override
    public PageResult<BrandDto> findBySearch(BrandSearcher searcher, PageModel page) {
        PageResult<BrandDto> pager = new PageResult<>(page);
        int totalCount = brandMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Brand> list = brandMapper.findBySearch(searcher, page);
            List<BrandDto> dtos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                BrandDto dto = new BrandDto();
                BeanUtils.copyProperties(list.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public int countBySearch(BrandSearcher searcher) {
        return brandMapper.countBySearch(searcher);
    }

    @Override
    @Cacheable(value = "brand_letter", key = "'brand_letter'", unless = "#result == null")
    public List<Brand> findByLetters() {
        return brandMapper.findByLetters();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Brand insert(Brand brand) {
        Brand newBrand = this.findByBrandCode(brand.getBrandCode());
        if (newBrand != null) {
            throw new BusinessException("已存在编码为" + brand.getBrandCode() + "的品牌了！");
        }
        brand = this.save(brand);
        // 插入品牌档案
        brandDetailService.insert(new BrandDetail(brand));
        if (brand.getId() != null && brand.getId() > 0) {
            designerSearcherService.insert(this.toSearcher(brand));
            JSONObject info = new JSONObject();
            info.put("操作", "设计师新增");
            brandLogService.insert(
                    new BrandLog(brand.getCreator(), info.toJSONString(), DesignerLogType.Insert, brand.getId()));
        }
        return brand;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#brand.id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(Brand brand) {
        Brand sameBrand = this.findByBrandCode(brand.getBrandCode());
        if (sameBrand != null && (!sameBrand.getId().equals(brand.getId()))) {
            throw new BusinessException("已存在编码为" + brand.getBrandCode() + "的品牌了！");
        }
        Brand newBrand = this.findById(brand.getId());
        int result = this.updateNotNull(brand);
        if (brand.getBigPic() == null && newBrand.getBigPic() != null) {
            this.updateBigPic(brand.getId(), brand.getBigPic());
        }
        if (result > 0) {
            designerSearcherService.update(this.toSearcher(brand));
            JSONObject info = new JSONObject();
            info.put("操作", "设计师修改");
            if (newBrand != null) {
                info.put("修改前", newBrand.toJson());
            }
            info.put("修改后", brand.toJson());
            brandLogService.insert(
                    new BrandLog(brand.getLastModifyMan(), info.toJSONString(), DesignerLogType.Update, brand.getId()));
        }
        return result;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#brandId"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long brandId, String operator) {
        int result = deleteById(brandId);
        if (result > 0) {
            designerSearcherService.remove(brandId);
            JSONObject info = new JSONObject();
            info.put("操作", "设计师归档");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Delete, brandId));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "brand", key = "'brand_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateMark(Long id, int mark, String markMan) {
        int result = brandMapper.updateMark(id, mark, markMan);
        if (result > 0) {
            if (mark == 1) {
                designerSearcherService.insert(this.toSearcher(this.findById(id)));
            } else {
                designerSearcherService.remove(id);
            }
            JSONObject info = new JSONObject();
            info.put("操作", mark == 1 ? "上架" : "下架");
            brandLogService.insert(new BrandLog(markMan, info.toJSONString(),
                    mark == 1 ? DesignerLogType.Up : DesignerLogType.Down, id));
        }
        return result;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSubscribe(Long id, int subscribe, String operator) {
        int result = brandMapper.updateSubscribe(id, subscribe);
        if (result > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", subscribe == 1 ? "设置允许门店预约" : "取消允许门店预约");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Subscribe, id));
        }
        return result;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateAfter(Long id, Integer after, String operator) {
        int success = brandMapper.updateAfter(id, after);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", after.intValue() == 1 ? "设置允许售后" : "取消允许售后");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.After, id));
        }
        return success;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateCod(Long id, Integer cod, String operator) {
        int success = brandMapper.updateCod(id, cod);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", cod.intValue() == 1 ? "设置允许货到付款" : "取消允许货到付款");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Cod, id));
        }
        return success;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRecommend(Long id, Integer recommend, String operator) {
        int success = brandMapper.updateRecommend(id, recommend);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", recommend.intValue() == 1 ? "设置推荐" : "取消推荐");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Recommend, id));
        }
        return success;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updateFansCount(Long id, Integer count) {
        int result = brandMapper.updateFans(id, count);
        return result;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateTags(Long id, String tags) {
        return brandMapper.updateTags(id, tags);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateDesigners(Long id, String code, String designers) {
        return brandMapper.updateDesigners(id, code, designers);
    }

    @Override
    @CacheEvict(value = "brand", key = "'brand_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateVideoById(Long id, String video) {
        return brandMapper.updateVideoById(id, video);
    }

    @Override
    @CacheEvict(value = "brand", key = "'brand_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateBigPic(Long id, String bigPic) {
        return brandMapper.updateBigPic(id, bigPic);
    }

    @Override
    @CacheEvict(value = "brand", key = "'brand_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateDirect(Long id, Integer direct, String operator) {
        int success = this.updateFieldById(id.intValue(), "direct", direct);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", direct.intValue() == 1 ? "设置设计师直发" : "取消设计师直发");
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Direct, id));
        }
        return success;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSales(Long id, Integer sales) {
        int success = brandMapper.updateSales(id, sales);
        if (success > 0) {
            designerSearcherService.updateSales(id, sales);
        }
        return success;
    }

    @Override
    public List<Long> findAllSales() {
        return brandMapper.findAllSales();
    }

    @Caching(evict = {@CacheEvict(value = "brand", key = "'brand_'+#id"),
            @CacheEvict(value = "brand_letter", key = "'brand_letter'")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public int updateStyleAndPrice(Long brandId, String style, String price) {
        int result = brandMapper.updateStyle(brandId, style);
        if (result > 0) {
            designerSearcherService.updateStyleAndPrice(brandId, style, price);
        }
        return result;
    }

}
