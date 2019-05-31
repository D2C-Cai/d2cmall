package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.ConsultDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Consult;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherConsult;
import com.d2c.member.search.query.ConsultSearchBean;
import com.d2c.member.search.service.ConsultSearcherService;
import com.d2c.member.service.ConsultService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consult")
public class ConsultController extends BaseController {

    @Autowired
    private ConsultService consultService;
    @Reference
    private ConsultSearcherService consultSearcherService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 商品咨询列表
     *
     * @param model
     * @param page
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String list(ModelMap model, PageModel page, @PathVariable Long productId) {
        ConsultSearchBean search = new ConsultSearchBean();
        search.setStatus(2);
        search.setProductId(productId);
        PageResult<SearcherConsult> pager = consultSearcherService.search(search, page);
        model.put("pager", pager);
        return "/product/product_consult";
    }

    /**
     * 发表咨询
     *
     * @param model
     * @param consult
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(ModelMap model, Consult consult) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        consult.setNickName(memberInfo.getDisplayName());
        consult.setMemberId(memberInfo.getId());
        consult.setHeadPic(memberInfo.getHeadPic());
        Product product = productService.findById(consult.getProductId());
        consult.setProductName(product.getName());
        consult.setProductPic(product.getProductImageListFirst());
        consult.setInernalSn(product.getInernalSn());
        consult.setDevice(isMobileDevice() ? DeviceTypeEnum.MOBILE.toString() : DeviceTypeEnum.PC.toString());
        consult.setAppVersion(VERSION);
        Brand brand = brandService.findById(product.getDesignerId());
        consult.setOperation(brand.getOperation());
        consultService.insert(consult);
        return "";
    }

    /**
     * 咨询详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable Long id) {
        SearcherConsult consult = consultSearcherService.findById(id.toString());
        ConsultDto dto = new ConsultDto();
        BeanUtils.copyProperties(consult, dto);
        SearcherProduct product = productSearcherQueryService.findById(consult.getProductId().toString());
        dto.setSalePrice(product.getMinPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setPrice(product.getCurrentPrice());
        model.put("consult", dto);
        return "/society/consult_reply";
    }

}
