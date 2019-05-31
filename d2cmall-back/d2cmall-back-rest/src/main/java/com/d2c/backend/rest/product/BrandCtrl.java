package com.d2c.backend.rest.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.BrandLog;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.BrandLogService;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Designers;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.DesignersService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberShareService;
import com.d2c.product.dto.BrandDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.query.BrandSearcher;
import com.d2c.product.service.BrandCategoryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rest/shop/designer")
public class BrandCtrl extends BaseCtrl<BrandSearcher> {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Autowired
    private BrandLogService brandLogService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private UpyunTaskService upyunTaskService;

    @Override
    protected List<Map<String, Object>> getRow(BrandSearcher searcher, PageModel page) {
        PageResult<BrandDto> pager = brandService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        List<BrandCategory> designAreaList = brandCategoryService.findByType(BrandCategory.DESIGNAREATYPE);
        Map<String, Object> cellsMap = null;
        for (Brand brand : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("设计师", brand.getDesigners());
            cellsMap.put("品牌", brand.getName());
            cellsMap.put("品牌ID", brand.getId());
            cellsMap.put("运营小组", brand.getOperation());
            if (brand.getDesginAreas() != null) {
                cellsMap.put("设计领域", this.getDesignAreaName(designAreaList, brand.getDesginAreas()));
            }
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(BrandSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = brandService.countBySearch(searcher); // 通过查询规则查找出对应的数量
        return count;
    }

    @Override
    protected String getFileName() {
        return "设计师品牌表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"设计师", "品牌", "品牌ID", "设计领域", "运营小组"};
    }

    @Override
    protected Response doHelp(BrandSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response doList(BrandSearcher searcher, PageModel page) {
        AdminDto admin = this.getLoginedAdmin();
        searcher.setDesignersId(admin.getDesignerId());
        BeanUt.trimString(searcher);
        if (searcher.getPageGroup() == null || "ALL".equals(searcher.getPageGroup().toUpperCase())) {
            searcher.setPageGroup("");
        }
        PageResult<BrandDto> pager = brandService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Brand brand = brandService.findById(id);
        result.put("designer", brand);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        brandService.delete(id, admin.getUsername());
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        data.remove("designAreaList");
        String jsignPicStr = data.getString("signPics");// 商品图片
        data.remove("signPics");
        String taskIds = data.getString("task_ids"); // 短视频转码
        data.remove("task_ids");
        String sortDate = data.getString("sortDate");// 排序时间
        if (StringUtils.isBlank(sortDate)) {
            data.remove("sortDate");
        }
        Brand brand = JsonUtil.instance().toObject(data, Brand.class);
        if (StringUtils.isBlank(brand.getAddress())) {
            brand.setAddress(null);
        }
        if (StringUtils.isBlank(brand.getConsignee())) {
            brand.setConsignee(null);
        }
        if (StringUtils.isBlank(brand.getMobile())) {
            brand.setMobile(null);
        }
        if (brand.getDomain() == null) {
            brand.setDomain(Long.toString(System.currentTimeMillis()));
        } else {
            if (brandService.findByDomain(brand.getDomain()) != null) {
                result.setStatus(-1);
                result.setMessage("出现相同的域名，请更改域名！");
                return result;
            }
        }
        if (org.springframework.util.StringUtils.isEmpty(brand.getDesginAreas())) {
            result.setStatus(-1);
            result.setMsg("设计师领域不能为空！");
            return result;
        }
        String signPic = "";
        JSONArray jsignPics = JSON.parseArray(jsignPicStr);// 商品图片
        for (int i = 0; i < jsignPics.size(); i++) {
            signPic += jsignPics.getString(i).toString() + ",";
        }
        if (signPic.length() > 0) {
            signPic = signPic.substring(0, signPic.length() - 1);
        }
        brand.setSignPic(signPic);
        brand.setCreator(admin.getUsername());
        if (brand.getPrior() == null) {
            brand.setPrior(0);
        }
        Designers designers = designersService.findById(brand.getDesignersId());
        if (designers == null) {
            result.setStatus(-1);
            result.setMsg("编号为" + brand.getCode() + "的设计师不存在，请先添加该设计师！");
            return result;
        }
        try {
            brand = brandService.insert(brand);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        if (brand.getVideo() == null) {
            productService.updateVideoById(brand.getId(), null);
        } else if (StringUtils.isNotBlank(taskIds)) { // 短视频转码任務
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskIds);
            upyunTask.setSourceType(SourceType.BRAND.toString());
            upyunTask.setSourceId(brand.getId());
            upyunTask.setStatus(0);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                brandService.updateVideoById(brand.getId(), upyunTask.getVideo());
            }
        }
        result.put("designer", brand);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        data.remove("designAreaList");
        String jsignPicStr = data.getString("signPics");// 商品图片
        data.remove("signPics");
        String taskIds = data.getString("task_ids"); // 短视频转码
        data.remove("task_ids");
        String sortDate = data.getString("sortDate");// 排序时间
        if (StringUtils.isBlank(sortDate)) {
            data.remove("sortDate");
        }
        Brand brand = JsonUtil.instance().toObject(data, Brand.class);
        if (StringUtils.isBlank(brand.getAddress())) {
            brand.setAddress(null);
        }
        if (StringUtils.isBlank(brand.getConsignee())) {
            brand.setConsignee(null);
        }
        if (StringUtils.isBlank(brand.getMobile())) {
            brand.setMobile(null);
        }
        if (org.springframework.util.StringUtils.isEmpty(brand.getDesignArea())) {
            result.setStatus(-1);
            result.setMsg("设计师领域不能为空");
            return result;
        } else if (brand.getDesignArea().contains("NaN,")) {
            brand.setDesignArea(brand.getDesignArea().replace("NaN,", ""));
        }
        if (brand.getDomain() != null) {
            Brand db = brandService.findByDomain(brand.getDomain());
            if (db != null && !db.getId().equals(brand.getId())) {
                result.setStatus(-1);
                result.setMessage("出现相同的域名，请更改域名！");
                return result;
            }
        }
        String signPic = "";
        JSONArray jsignPics = JSON.parseArray(jsignPicStr);// 商品图片
        for (int i = 0; i < jsignPics.size(); i++) {
            signPic += jsignPics.getString(i).toString() + ",";
        }
        if (signPic.length() > 0) {
            signPic = signPic.substring(0, signPic.length() - 1);
        }
        brand.setSignPic(signPic);
        brand.setLastModifyMan(admin.getLastModifyMan());
        Designers designers = designersService.findById(brand.getDesignersId());
        if (designers == null) {
            result.setStatus(-1);
            result.setMsg("编号为" + brand.getCode() + "的设计师不存在，请先添加该设计师！");
            return result;
        }
        try {
            int success = brandService.update(brand);
            if (brand.getVideo() == null) {
                brandService.updateVideoById(brand.getId(), null);
            } else if (success > 0 && StringUtils.isNotBlank(taskIds)) { // 短视频转码任務
                UpyunTask upyunTask = new UpyunTask();
                upyunTask.setTaskIds(taskIds);
                upyunTask.setSourceType(SourceType.BRAND.toString());
                upyunTask.setSourceId(brand.getId());
                upyunTask.setStatus(0);
                upyunTask = upyunTaskService.insert(upyunTask);
                if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                    brandService.updateVideoById(brand.getId(), upyunTask.getVideo());
                }
            }
            // 更新商品时，如售后或货到付款有变动且状态为0才会去处理商品
            if (brand.getCod() == 0 || brand.getAfter() == 0) {
                Brand newDesigner = brandService.findById(brand.getId());
                if (success > 0 && newDesigner != null) {
                    List<Long> productIds = productService.findProductId(id);
                    for (Long productId : productIds) {
                        if (!newDesigner.getCod().equals(brand.getCod())) {
                            if (brand.getCod().intValue() == 0) {
                                productService.updateCodByDesigner(id, 0, productId);
                            }
                        }
                        if (!newDesigner.getAfter().equals(brand.getAfter())) {
                            if (brand.getAfter().intValue() == 0) {
                                productService.updateAfterByDesigner(id, 0, productId);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return "Designer";
    }

    private String getDesignAreaName(List<BrandCategory> designAreaList, String[] ids) {
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            if (!org.springframework.util.StringUtils.isEmpty(id)) {
                for (BrandCategory designArea : designAreaList) {
                    if (designArea.getId().equals(Long.parseLong(id))) {
                        sb.append(designArea.getName()).append(",");
                    }
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 刷新买家秀设计师id
     *
     * @param designerId
     * @param status
     */
    private void updateDesignerMemberShare(Long designerId, Integer status) {
        if (status.intValue() == 1) {
            Brand brand = brandService.findById(designerId);
            MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
            if (memberInfo != null) {
                MemberShareSearchBean search = new MemberShareSearchBean();
                search.setMemberId(memberInfo.getId());
                PageModel page = new PageModel();
                page.setPageSize(PageModel.MAX_PAGE_SIZE);
                PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(search, page);
                for (SearcherMemberShare share : pager.getList()) {
                    memberShareService.updateDesignerId(share.getId(), designerId);
                }
            }
        } else {
            MemberShareSearchBean search = new MemberShareSearchBean();
            search.setDesignerId(designerId);
            PageModel page = new PageModel();
            page.setPageSize(PageModel.MAX_PAGE_SIZE);
            PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(search, page);
            for (SearcherMemberShare share : pager.getList()) {
                memberShareService.updateDesignerId(share.getId(), 0L);
            }
        }
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            brandService.updateMark(id, status, admin.getUsername());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    updateDesignerMemberShare(id, status);
                }
            });
            executor.shutdown();
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/subscribe/{status}", method = RequestMethod.POST)
    public Response subscribe(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            brandService.updateSubscribe(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/recommend/{status}", method = RequestMethod.POST)
    public Response recommend(@PathVariable Integer recommend, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            brandService.updateRecommend(id, recommend, admin.getUsername());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/direct/{direct}", method = RequestMethod.POST)
    public Response updateDirect(@PathVariable Integer direct, Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            brandService.updateDirect(id, direct, admin.getUsername());
        }
        return result;
    }

    @RequestMapping(value = "/after/{status}", method = RequestMethod.POST)
    public Response after(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            int result = brandService.updateAfter(id, status, admin.getUsername());
            if (result > 0 && status != null && status == 0) {
                List<Long> productIds = productService.findProductId(id);
                for (Long productId : productIds) {
                    productService.updateAfterByDesigner(id, 0, productId);
                }
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/cod/{status}", method = RequestMethod.POST)
    public Response cod(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            int result = brandService.updateCod(id, status, admin.getUsername());
            if (result > 0 && status != null && status == 0) {
                List<Long> productIds = productService.findProductId(id);
                for (Long productId : productIds) {
                    productService.updateCodByDesigner(id, 0, productId);
                }
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/checkcode", method = RequestMethod.POST)
    public Response checkCode(String code) {
        SuccessResponse result = new SuccessResponse();
        Brand brand = brandService.findByCode(code);
        if (brand == null) {
            result.setStatus(0);
        }
        return result;
    }

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response log(@PathVariable Long id, PageModel page) {
        PageResult<BrandLog> pager = brandLogService.findByDesignerId(id, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

}
