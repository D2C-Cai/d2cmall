package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MagazinePage;
import com.d2c.member.query.MagazinePageSearcher;
import com.d2c.member.service.MagazinePageService;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductMagazineRelationService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/magazinepage")
public class MagazinePageCtrl extends BaseCtrl<MagazinePageSearcher> {

    @Autowired
    private MagazinePageService magazinePageService;
    @Autowired
    private ProductMagazineRelationService productMagazineRelationService;

    @Override
    protected Response doList(MagazinePageSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.setOrderStr("id ASC");
        PageResult<MagazinePage> pager = magazinePageService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(MagazinePageSearcher searcher) {
        return magazinePageService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MagazinePageSearcher searcher, PageModel page) {
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
    protected Response doHelp(MagazinePageSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        MagazinePage magazinePage = magazinePageService.findById(id);
        return new SuccessResponse(magazinePage);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        MagazinePage magazinePage = JsonUtil.instance().toObject(data, MagazinePage.class);
        int update = magazinePageService.update(magazinePage);
        SuccessResponse responseStatus = new SuccessResponse();
        responseStatus.setStatus(update);
        return responseStatus;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MagazinePage magazinePage = JsonUtil.instance().toObject(data, MagazinePage.class);
        magazinePage.setCode(SerialNumUtil.buildMagazinePageSn());
        magazinePage = magazinePageService.insert(magazinePage);
        return new SuccessResponse(magazinePage);
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

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        magazinePageService.updateStatus(id, status, admin.getUsername());
        return new SuccessResponse();
    }

    /**
     * 绑定杂志
     *
     * @param id
     * @param magazineId
     * @return
     */
    @RequestMapping(value = "/bind/magazine", method = RequestMethod.POST)
    public Response bindMagazine(Long id, Long magazineId) {
        SuccessResponse result = new SuccessResponse();
        magazinePageService.updateMagazineId(id, magazineId);
        return result;
    }

    /**
     * 更新页码
     *
     * @param id
     * @param sort
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        magazinePageService.updateSort(id, sort);
        return result;
    }

    /**
     * 已绑定商品列表
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public Response productList(Long id, PageModel page) {
        PageResult<Product> pager = productMagazineRelationService.findProductByPageId(id, page);
        return new SuccessResponse(pager);
    }

    /**
     * 绑定商品
     *
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping(value = "/bind/product", method = RequestMethod.POST)
    public Response bindProduct(Long id, Long productId) {
        SuccessResponse result = new SuccessResponse();
        productMagazineRelationService.insert(id, productId);
        return result;
    }

    /**
     * 解绑商品
     *
     * @param id
     * @param productId
     * @return
     */
    @RequestMapping(value = "/unbind/product", method = RequestMethod.POST)
    public Response unbindProduct(Long id, Long productId) {
        SuccessResponse result = new SuccessResponse();
        productMagazineRelationService.deleteOne(id, productId);
        return result;
    }

    /**
     * 更新排序
     *
     * @param id
     * @param productId
     * @param sort
     * @return
     */
    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateRelationSort(Long id, Long productId, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        productMagazineRelationService.updateSort(id, productId, sort);
        return result;
    }

}
