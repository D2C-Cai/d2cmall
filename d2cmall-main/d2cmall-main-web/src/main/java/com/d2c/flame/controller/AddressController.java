package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.third.email.EmailClient;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberDetailService;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.model.Address;
import com.d2c.order.model.Area;
import com.d2c.order.service.AddressService;
import com.d2c.order.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private MemberDetailService memberDetailService;

    /**
     * 我的收货地址
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<AddressDto> pr = addressService.findByMemberInfoId(memberInfo.getId(), new PageModel(), null);
        model.put("addresses", pr.getList());
        return "member/my_address";
    }

    /**
     * 编辑收货地址页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, ModelMap model) {
        this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Address addr = addressService.findById(id);
        result.put("address", addr);
        return "";
    }

    /**
     * 添加收货地址
     *
     * @param address
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(Address address, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        address = this.addressHelp(memberInfo.getId(), address);
        if (address == null) {
            throw new BusinessException("新增不成功");
        } else {
            result.put("address", address);
            result.setMsg("新增成功");
        }
        return "";
    }

    /**
     * 修改收货地址
     *
     * @param address
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Address address, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (address.getStreet().contains("http")) {
            EmailClient.send("709931138@qq.com", "非法修改地址IP", getLoginIp());
        }
        address.setMemberId(memberInfo.getId());
        AddressDto addressDto = addressService.update(address);
        model.put("addressDto", addressDto);
        return "";
    }

    /**
     * 设置默认收货地址
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/default/{id}", method = RequestMethod.POST)
    public String updateDefaultById(@PathVariable Long id, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        addressService.doSettingDefault(id, memberInfo.getId());
        result.setMsg("设置成功");
        return "";
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        addressService.delete(id, memberInfo.getId());
        result.setMsg("删除成功");
        return "";
    }

    /**
     * 确认订单信息时添加收货地址
     *
     * @param address
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertOnForm", method = RequestMethod.POST)
    public String insertOnForm(Address address, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        address = this.addressHelp(memberInfo.getId(), address);
        if (address != null) {
            Area province = areaService.findAreaByCode(Integer.parseInt(address.getRegionSuffix()));
            if (province != null) {
                result.put("regionPrefix", province.getName());
            } else {
                result.put("regionPrefix", "");
            }
            Area city = areaService.findAreaByCode(Integer.parseInt(address.getRegionMiddle()));
            if (city != null) {
                result.put("regionMiddle", city.getName());
            } else {
                result.put("regionMiddle", "");
            }
            Area district = areaService.findAreaByCode(Integer.parseInt(address.getRegionPrefix()));
            if (district != null) {
                result.put("regionSuffix", district.getName());
            } else {
                result.put("regionSuffix", "");
            }
            result.put("street", address.getStreet());
            result.put("name", address.getName());
            result.put("mobile", address.getMobile());
            result.put("id", address.getId());
            result.put("isdefault", address.getIsdefault());
        }
        return "";
    }

    /**
     * 确认订单时修改收货地址
     *
     * @param address
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateOnForm", method = RequestMethod.POST)
    public String updateOnForm(Address address, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (address.getStreet().contains("http")) {
            EmailClient.send("709931138@qq.com", "非法修改地址IP", getLoginIp());
        }
        addressService.update(address);
        PageResult<AddressDto> addresses = addressService.findByMemberInfoId(memberInfo.getId(), new PageModel(), null);
        model.put("addresses", addresses.getList());
        model.put("order", getSession().getAttribute("order"));
        model.put("add", "add");
        return "order/confirm_order";
    }

    /**
     * 添加收货地址帮助方法
     *
     * @return
     */
    private Address addressHelp(Long memberInfoId, Address address) {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.setName(address.getName());
        memberDetail.setRegionPrefix(Integer.parseInt(address.getRegionPrefix()));
        memberDetail.setRegionMiddle(Integer.parseInt(address.getRegionMiddle()));
        memberDetail.setRegionSuffix(Integer.parseInt(address.getRegionSuffix()));
        memberDetail.setStreet(address.getStreet());
        memberDetailService.updateByMemberId(memberInfoId, memberDetail);
        if (address.getStreet().contains("http")) {
            EmailClient.send("709931138@qq.com", "非法修改地址IP", getLoginIp());
        }
        address.setMemberId(memberInfoId);
        address = addressService.insert(address);
        return address;
    }

}
