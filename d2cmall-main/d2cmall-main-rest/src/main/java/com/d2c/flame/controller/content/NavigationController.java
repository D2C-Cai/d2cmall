package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.dto.NavigationDto;
import com.d2c.content.service.NavigationService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.model.BrandTag;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.DesignerTagRelationSearcherService;
import com.d2c.product.service.BrandCategoryService;
import com.d2c.product.service.BrandTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导航
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/navigation")
public class NavigationController extends BaseController {

    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private NavigationService navigationService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Autowired
    private BrandTagService brandTagService;

    /**
     * 导航菜单
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/{parentId}", method = RequestMethod.GET)
    public ResponseResult getNavigations(@PathVariable Long parentId) {
        ResponseResult result = new ResponseResult();
        List<NavigationDto> navigations = navigationService.findByParentId(parentId, 1, "v3");
        JSONArray array = new JSONArray();
        for (NavigationDto navigation : navigations) {
            JSONObject obj = navigation.toJson();
            if (navigation.getNavigationItems() != null && navigation.getNavigationItems().size() > 0) {
                JSONArray arrayc = new JSONArray();
                navigation.getNavigationItems().forEach(item -> arrayc.add(item.toJson()));
                obj.put("items", arrayc);
            }
            array.add(obj);
        }
        result.put("navigations", array);
        return result;
    }

    /**
     * 热门品牌
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public ResponseResult brandTag(Long id, PageModel page) {
        ResponseResult result = new ResponseResult();
        if (id == null || id == 0) {
            BrandTag tag = brandTagService.findFixedOne();
            if (tag != null) {
                id = tag.getId();
            }
        }
        PageResult<SearcherDesigner> pager = new PageResult<SearcherDesigner>(new PageModel(1, 100));
        List<String> designerIds = new ArrayList<>();
        PageResult<String> relationPager = designerTagRelationSearcherService.findDesignerByTagId(id,
                new PageModel(1, 100));
        designerIds.addAll(relationPager.getList());
        Map<Long, SearcherDesigner> map = designerSearcherService
                .findMapByIds(designerIds.toArray(new String[designerIds.size()]), null);
        List<SearcherDesigner> brandList = new ArrayList<>();
        designerIds.forEach(item -> brandList.add(map.get(Long.valueOf(item))));
        pager.setTotalCount(designerIds.size());
        pager.setList(brandList);
        JSONArray array = new JSONArray();
        for (SearcherDesigner designer : pager.getList()) {
            if (designer != null) {
                JSONObject obj = new JSONObject();
                obj.put("id", designer.getId());
                obj.put("headPic", designer.getHeadPic());
                array.add(obj);
            }
        }
        result.putPage("designers", pager, array);
        return result;
    }

    /**
     * 品牌 A-Z
     *
     * @return
     */
    @RequestMapping(value = "/alphabetical", method = RequestMethod.GET)
    public ResponseResult brandAlphabetical() {
        ResponseResult result = new ResponseResult();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "0-9"};
        Map<String, List<JSONObject>> letterArray = designerSearcherService.findGroupLetter();
        JSONArray array = new JSONArray();
        for (String letter : letters) {
            JSONObject obj = new JSONObject();
            obj.put("letter", letter);
            obj.put("list", letterArray.get(letter.toLowerCase()));
            array.add(obj);
        }
        result.put("letter", array);
        return result;
    }

    /**
     * 品牌分类
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/category/{type}", method = RequestMethod.GET)
    public ResponseResult brandCategory(@PathVariable String type) {
        ResponseResult result = new ResponseResult();
        List<BrandCategory> datas = brandCategoryService.findByType(type);
        if (datas == null || datas.size() <= 0) {
            throw new BusinessException("参数异常");
        }
        JSONArray array = new JSONArray();
        datas.forEach(item -> array.add(item.toJson()));
        result.put("datas", datas);
        return result;
    }

}
