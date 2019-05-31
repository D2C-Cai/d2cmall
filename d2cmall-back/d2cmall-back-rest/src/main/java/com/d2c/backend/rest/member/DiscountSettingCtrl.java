package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.dto.DiscountSettingDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSetting.DiscountType;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DiscountSettingSearcher;
import com.d2c.member.service.DiscountSettingService;
import com.d2c.member.service.DistributorService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/discountsetting")
public class DiscountSettingCtrl extends BaseCtrl<DiscountSettingSearcher> {

    @Autowired
    private DiscountSettingService discountSettingService;
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    /**
     * 折扣组商品列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    public Response cloudList(DiscountSettingSearcher searcher, PageModel page) {
        AdminDto admin = this.getLoginedAdmin();
        Distributor distributor = distributorService.findByMemberInfoId(admin.getMemberId());
        if (distributor == null) {
            return new ErrorResponse("此账号不是经销商账号！");
        }
        if (distributor.getGroupId() == null) {
            return new ErrorResponse("此经销商未绑定折扣组！");
        }
        // 折扣组折扣
        BeanUt.trimString(searcher);
        searcher.setStatus(1);
        searcher.setGroupId(distributor.getGroupId());
        searcher.setDistributorId(distributor.getId());
        searcher.setDisType(DiscountType.PRODUCT.name());
        searcher.initOrderStr();
        PageResult<DiscountSettingDto> groupPager = discountSettingService.findBySearch(searcher, page);
        List<Long> productIds = new ArrayList<>();
        for (DiscountSettingDto item : groupPager.getList()) {
            productIds.add(item.getTargetId());
        }
        // 个人折扣
        searcher = new DiscountSettingSearcher();
        searcher.setStatus(1);
        searcher.setDistributorId(distributor.getId());
        searcher.setDisType(DiscountType.PRODUCT.name());
        searcher.setProductIds(productIds.toArray(new Long[]{}));
        PageResult<DiscountSettingDto> selfPager = discountSettingService.findBySearch(searcher, new PageModel(1, 40));
        // 优先级：个人折扣 > 折扣组折扣
        for (DiscountSettingDto group : groupPager.getList()) {
            for (DiscountSettingDto self : selfPager.getList()) {
                if (group.getTargetId().equals(self.getTargetId())) {
                    group.setDiscount(self.getDiscount());
                }
            }
        }
        SuccessResponse result = new SuccessResponse(groupPager);
        return result;
    }

    @Override
    protected List<Map<String, Object>> getRow(DiscountSettingSearcher searcher, PageModel page) {
        List<DiscountSettingDto> list = discountSettingService.findBySearch(searcher, page).getList();
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        Product product = null;
        Brand brand = null;
        for (DiscountSettingDto setting : list) {
            cellsMap = new HashMap<String, Object>();
            if (DiscountType.PRODUCT.name().equals(setting.getDisType())) {
                product = productService.findById(setting.getTargetId());
                if (product.getStatus() < 0) {
                    continue;
                }
                cellsMap.put("折扣目标", product.getInernalSn());
                cellsMap.put("目标名称", product.getName());
            } else if (DiscountType.DESIGNER.name().equals(setting.getDisType())) {
                brand = brandService.findById(setting.getTargetId());
                cellsMap.put("折扣目标", brand.getCode());
                cellsMap.put("目标名称", brand.getName());
            } else {
                cellsMap.put("折扣目标", "全场");
            }
            cellsMap.put("折扣类型", setting.getDiscountTypeName());
            cellsMap.put("折扣", setting.getDiscount());
            cellsMap.put("状态", setting.getStatusName());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(DiscountSettingSearcher searcher) {
        int totalCount = discountSettingService.countBySearch(searcher);
        return totalCount;
    }

    @Override
    protected String getFileName() {
        return "经销商折扣表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"折扣类型", "折扣目标", "目标名称", "折扣", "状态"};
    }

    @Override
    protected String getExportFileType() {
        return "Discount";
    }

    @Override
    protected Response doHelp(DiscountSettingSearcher searcher, PageModel page) {
        return doList(searcher, page);
    }

    @Override
    protected Response doList(DiscountSettingSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<DiscountSettingDto> pager = discountSettingService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("searcher", searcher);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        DiscountSetting discountSetting = discountSettingService.findById(id);
        result.put("discountSetting", discountSetting);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        String targetIdStr = data.getString("targetIds");
        JSONArray targetIds = JSON.parseArray(targetIdStr);
        data.remove("targetIds");
        DiscountSetting discountSetting = (DiscountSetting) JsonUtil.instance().toObject(data, DiscountSetting.class);
        Admin admin = this.getLoginedAdmin();
        for (int i = 0; i < targetIds.size(); i++) {
            String targetId = targetIds.getString(i).toString();
            if (StringUtil.isBlank(targetId)) {
                continue;
            }
            discountSetting.setTargetId(Long.valueOf(targetId));
            try {
                discountSettingService.insert(discountSetting, admin.getUsername());
            } catch (Exception e) {
                String message = e.getMessage();
                if (message.indexOf("DuplicateKeyException") > 0) {
                    result.setMessage("保存不成功！请勿重复绑定！");
                    result.setStatus(-1);
                    return result;
                } else {
                    logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/updatestatus", method = RequestMethod.POST)
    public Response updateStatus(Integer status, Long[] ids) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            DiscountSetting discountSetting = discountSettingService.findById(id);
            discountSettingService.updateStatusById(discountSetting, status, admin.getUsername());
        }
        return result;
    }

    @RequestMapping(value = "/update/discount", method = RequestMethod.POST)
    public Response updateDiscount(BigDecimal discount, Long id, BigDecimal productPrice, BigDecimal discountPrice)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        DiscountSetting discountSetting = discountSettingService.findById(id);
        discountSettingService.updateDiscountById(discountSetting, discount, admin.getUsername());
        return result;
    }

    /**
     * 导入折扣表
     *
     * @param request
     * @param distributorId
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response importDiscountSetting(HttpServletRequest request, Long distributorId, Long groupId) {
        Admin admin = this.getLoginedAdmin();
        if (distributorId != null && groupId != null) {
            return new ErrorResponse("导入数据异常！");
        }
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String inernalSn = String.valueOf(map.get("商品货号"));
                if (StringUtils.isBlank(inernalSn)) {
                    errorMsg.append("第" + row + "行：商品货号不能为空<br/>");
                    return false;
                }
                Product product = productService.findOneBySn(inernalSn);
                if (product == null) {
                    errorMsg.append("第" + row + "行：该货号的商品不存在<br/>");
                    return false;
                }
                String discountStr = String.valueOf(map.get("折扣"));
                if (StringUtils.isBlank(discountStr)) {
                    errorMsg.append("第" + row + "行：商品折扣不能为空<br/>");
                    return false;
                }
                BigDecimal discount = new BigDecimal(discountStr);
                if (discount.compareTo(new BigDecimal(0)) <= 0 || discount.compareTo(new BigDecimal(1)) >= 0) {
                    errorMsg.append("第" + row + "行：商品折扣必须为0和1之间的数<br/>");
                    return false;
                }
                DiscountSetting discountSetting = new DiscountSetting();
                if (groupId != null) {
                    discountSetting.setGroupId(groupId);
                    discountSetting.setDistributorId(null);
                } else if (distributorId != null) {
                    discountSetting.setGroupId(null);
                    discountSetting.setDistributorId(distributorId);
                }
                discountSetting.setDisType(DiscountType.PRODUCT.toString());
                discountSetting.setTargetId(product.getId());
                discountSetting.setDiscount(discount);
                discountSetting.setStatus(1);
                // 如果重复就覆盖更新
                DiscountSetting old = discountSettingService.findByTargetId(discountSetting.getDisType(),
                        discountSetting.getTargetId(), discountSetting.getGroupId(),
                        discountSetting.getDistributorId());
                if (old == null) {
                    discountSettingService.insert(discountSetting, admin.getUsername());
                } else {
                    discountSetting.setId(old.getId());
                    discountSettingService.update(discountSetting, admin.getUsername());
                }
                return true;
            }
        });
    }

    @RequestMapping(value = "/import/delete", method = RequestMethod.POST)
    public Response importDelete(HttpServletRequest request, Long distributorId, Long groupId) {
        Admin admin = this.getLoginedAdmin();
        if (distributorId != null && groupId != null) {
            return new ErrorResponse("导入数据异常！");
        }
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String inernalSn = String.valueOf(map.get("商品货号"));
                if (StringUtils.isBlank(inernalSn)) {
                    errorMsg.append("第" + row + "行：商品货号不能为空<br/>");
                    return false;
                }
                Product product = productService.findOneBySn(inernalSn);
                if (product == null) {
                    errorMsg.append("第" + row + "行：该货号的商品不存在<br/>");
                    return false;
                }
                DiscountSetting discountSetting = new DiscountSetting();
                if (groupId != null) {
                    discountSetting.setGroupId(groupId);
                    discountSetting.setDistributorId(null);
                } else if (distributorId != null) {
                    discountSetting.setGroupId(null);
                    discountSetting.setDistributorId(distributorId);
                }
                discountSetting.setDisType(DiscountType.PRODUCT.toString());
                discountSetting.setTargetId(product.getId());
                try {
                    discountSettingService.updateStatusByTargetId(discountSetting, 0, admin.getUsername());
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        });
    }

}
