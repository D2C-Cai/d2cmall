package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.BrandLog;
import com.d2c.logger.model.BrandLog.DesignerLogType;
import com.d2c.logger.service.BrandLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.SeriesMapper;
import com.d2c.product.dto.SeriesDto;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.model.Series;
import com.d2c.product.query.SeriesSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherSeries;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.search.service.SeriesSearcherService;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("seriesService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SeriesServiceImpl extends ListServiceImpl<Series> implements SeriesService {

    @Autowired
    private SeriesMapper seriesMapper;
    @Autowired
    private ProductSearcherService productSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private BrandLogService brandLogService;
    @Reference
    private SeriesSearcherService seriesSearcherService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private BrandService brandService;

    @Override
    public Series findById(Long serieId) {
        return super.findOneById(serieId);
    }

    @Override
    public List<Series> findByIds(List<Long> seriesIds) {
        return seriesMapper.findByIds(seriesIds);
    }

    @Override
    public Series findByName(String name) {
        return seriesMapper.findByName(name);
    }

    @Override
    public List<String> findSeason() {
        return seriesMapper.findSeason();
    }

    @Override
    public PageResult<SeriesDto> findBySearch(SeriesSearcher searcher, PageModel page) {
        PageResult<SeriesDto> pager = new PageResult<>(page);
        int totalCount = seriesMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Series> list = seriesMapper.findBySearch(searcher, page);
            List<SeriesDto> dtos = new ArrayList<>();
            for (Series series : list) {
                SeriesDto dto = new SeriesDto();
                BeanUtils.copyProperties(series, dto);
                if (StringUtil.isNotBlank(series.getStyle())) {
                    BrandCategory styleCategory = brandCategoryService.findById(Long.parseLong(series.getStyle()));
                    if (styleCategory != null) {
                        dto.setStyleName(styleCategory.getName());
                    }
                }
                if (StringUtil.isNotBlank(series.getPrice())) {
                    BrandCategory priceCategory = brandCategoryService.findById(Long.parseLong(series.getPrice()));
                    if (priceCategory != null) {
                        dto.setPriceName(priceCategory.getName());
                    }
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(SeriesSearcher searcher) {
        return seriesMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Series insert(Series series) {
        series = this.save(series);
        if (series != null && series.getId() > 0) {
            updateBrandStyle(series.getDesignerId());
            SearcherSeries seriesBean = new SearcherSeries();
            BeanUtils.copyProperties(series, seriesBean);
            seriesSearcherService.insert(seriesBean);
            JSONObject info = new JSONObject();
            info.put("操作", "新增系列+" + series.getName() + "，系列Id：" + series.getId());
            brandLogService.insert(new BrandLog(series.getCreator(), info.toJSONString(), DesignerLogType.Series,
                    series.getDesignerId()));
        }
        return series;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(Series series) {
        Series old = this.findById(series.getId());
        int success = this.updateNotNull(series);
        if (success > 0) {
            if (!old.getUpDateTime().equals(series.getUpDateTime())) {
                this.updateSortTime(series);
            }
            updateBrandStyle(series.getDesignerId());
            SearcherSeries seriesBean = new SearcherSeries();
            BeanUtils.copyProperties(series, seriesBean);
            seriesSearcherService.rebuild(seriesBean);
            JSONObject info = new JSONObject();
            info.put("操作", "修改系列");
            info.put("修改前", old.toJson());
            info.put("修改后", series.toJson());
            brandLogService.insert(new BrandLog(series.getCreator(), info.toJSONString(), DesignerLogType.Series,
                    series.getDesignerId()));
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long serieId, Long designerId, String operator) {
        Series series = this.findById(serieId);
        int success = seriesMapper.delete(serieId);
        if (success > 0) {
            updateBrandStyle(series.getDesignerId());
            seriesSearcherService.remove(serieId);
            JSONObject info = new JSONObject();
            info.put("操作", "删除系列，系列Id：" + serieId);
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.Series, designerId));
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSortTime(Series serie) {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        searcherBean.setSeriesId(serie.getId());
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, new PageModel(1, 100));
        for (SearcherProduct searcherProduct : pager.getList()) {
            SearcherProduct newSp = new SearcherProduct();
            newSp.setId(searcherProduct.getId());
            newSp.setSeriesUpDate(serie.getUpDateTime());
            productSearcherService.update(newSp);
        }
        return 1;
    }

    @Override
    public Map<String, String> findStyleAndPriceByBrand(Long brandId) {
        return seriesMapper.findStyleAndPriceByBrand(brandId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    private int updateBrandStyle(Long brandId) {
        Map<String, String> map = findStyleAndPriceByBrand(brandId);
        return brandService.updateStyleAndPrice(brandId, map.get("style").toString(), map.get("price").toString());
    }

}
