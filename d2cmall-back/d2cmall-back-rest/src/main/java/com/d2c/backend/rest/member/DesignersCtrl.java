package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.DesignersLog;
import com.d2c.logger.service.DesignersLogService;
import com.d2c.member.dto.DesignersDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Designers;
import com.d2c.member.query.DesignersSearcher;
import com.d2c.member.service.DesignersService;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/designers")
public class DesignersCtrl extends BaseCtrl<DesignersSearcher> {

    @Autowired
    private DesignersService designersService;
    @Autowired
    private DesignersLogService designersLogService;
    @Autowired
    private BrandService brandService;

    @Override
    protected Response doList(DesignersSearcher searcher, PageModel page) {
        page.setPageSize(20);
        PageResult<Designers> pager = designersService.findBySearcher(searcher, page);
        List<DesignersDto> dtos = new ArrayList<>();
        for (Designers designers : pager.getList()) {
            DesignersDto dto = new DesignersDto();
            BeanUtils.copyProperties(designers, dto);
            List<Brand> brands = brandService.findByDesignersId(designers.getId(), null);
            dto.setBrandList(brands.toArray());
            dtos.add(dto);
        }
        PageResult<DesignersDto> designersPager = new PageResult<>(page);
        designersPager.setTotalCount(pager.getTotalCount());
        designersPager.setList(dtos);
        return new SuccessResponse(designersPager);
    }

    @Override
    protected int count(DesignersSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(DesignersSearcher searcher, PageModel page) {
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
    protected Response doHelp(DesignersSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Designers designers = JsonUtil.instance().toObject(data, Designers.class);
        try {
            int success = designersService.update(designers, admin.getUsername());
            if (success < 1) {
                result.setStatus(-1);
                result.setMessage("操作不成功");
            }
            List<Brand> list = brandService.findByDesignersId(designers.getId(), null);
            for (Brand brand : list) {
                brandService.updateDesigners(brand.getId(), designers.getCode(), designers.getName());
            }
        } catch (BusinessException e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Designers designers = JsonUtil.instance().toObject(data, Designers.class);
        try {
            designersService.insert(designers, admin.getUsername());
        } catch (BusinessException e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/brand/list/{id}", method = RequestMethod.GET)
    public Response getBrandList(@PathVariable("id") Long id) {
        SuccessResponse result = new SuccessResponse();
        List<Brand> brands = brandService.findByDesignersId(id, new Integer[]{0, 1});
        result.put("brands", brands);
        return result;
    }

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response getLogList(@PathVariable("id") Long id, PageModel page) {
        PageResult<DesignersLog> pager = designersLogService.findByDesignersId(id, page);
        return new SuccessResponse(pager);
    }

}
