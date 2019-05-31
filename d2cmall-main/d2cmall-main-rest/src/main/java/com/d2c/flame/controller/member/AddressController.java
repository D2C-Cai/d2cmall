package com.d2c.flame.controller.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.third.email.EmailClient;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.service.AddressService;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 我的地址
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/address")
public class AddressController extends BaseController {

    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private AddressService addressService;

    /**
     * 我的收货地址
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<AddressDto> pager = addressService.findByMemberInfoId(memberInfo.getId(), page, null);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("addresses", pager, array);
        return result;
    }

    /**
     * 新增收货地址
     *
     * @param address
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insert(AddressDto address) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtil.hasBlack(new Object[]{address.getStreet(), address.getName(), address.getMobile(),
                address.getRegionPrefix(), address.getRegionMiddle(), address.getRegionSuffix()})) {
            throw new BusinessException("收货信息填写不完整！");
        }
        if (address.getStreet().contains("http")) {
            EmailClient.send("709931138@qq.com", "非法修改地址IP", getLoginIp());
        }
        address.setMemberId(memberInfo.getId());
        AddressDto newAddress = addressService.insert(address);
        if (newAddress == null || newAddress.getId() == null) {
            throw new BusinessException("收货地址保存不成功！");
        }
        result.put("address", newAddress.toJson());
        return result;
    }

    /**
     * 收货地址详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        AddressDto address = addressService.findById(id);
        if (address == null) {
            throw new BusinessException("收货地址不存在！");
        }
        result.put("address", address.toJson());
        return result;
    }

    /**
     * 更新收货地址
     *
     * @param address
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(AddressDto address) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtil.hasBlack(new Object[]{address.getStreet(), address.getName(), address.getMobile(),
                address.getRegionPrefix(), address.getRegionMiddle(), address.getRegionSuffix()})) {
            throw new BusinessException("收货信息填写不完整！");
        }
        AddressDto oldAddress = addressService.findById(address.getId());
        if (oldAddress == null) {
            throw new BusinessException("收货地址不存在！");
        }
        if (address.getMobile() != null && address.getMobile().contains("*")) {
            address.setMobile(oldAddress.getMobile());
        }
        if (address.getStreet().contains("http")) {
            EmailClient.send("709931138@qq.com", "非法修改地址IP", getLoginIp());
        }
        address.setMemberId(memberInfo.getId());
        address = addressService.update(address);
        result.put("address", address.toJson());
        return result;
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = addressService.delete(id, memberInfo.getId());
        if (success < 1) {
            throw new BusinessException("删除不成功！");
        }
        return result;
    }

    /**
     * 设置默认地址
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/default/{id}", method = RequestMethod.POST)
    public ResponseResult doDefault(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = addressService.doSettingDefault(id, memberInfo.getId());
        if (success < 1) {
            throw new BusinessException("设置不成功！");
        }
        return result;
    }

    /**
     * 获取经纬度
     *
     * @param address
     * @return
     */
    @RequestMapping(value = "/gps", method = RequestMethod.GET)
    public JSONObject gps(String address) {
        String url = "https://apis.map.qq.com/ws/geocoder/v1?key=XHYBZ-XODWX-PEA4M-7HQGU-UQWZ6-MZFVF&address="
                + address;
        try {
            String json = restTemplate.getForObject(url, String.class);
            JSONObject jsonObj = JSONObject.parseObject(json);
            return jsonObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
