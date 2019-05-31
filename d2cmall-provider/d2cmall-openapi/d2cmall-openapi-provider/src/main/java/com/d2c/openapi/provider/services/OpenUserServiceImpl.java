package com.d2c.openapi.provider.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.base.utils.security.RSAUt;
import com.d2c.common.base.utils.security.core.RSAKey;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.openapi.api.entity.OpenUserDO;
import com.d2c.openapi.api.services.OpenUserService;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;

/**
 * 买手销售数据
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class OpenUserServiceImpl extends ListServiceImpl<OpenUserDO> implements OpenUserService {

    @Reference
    private BrandService brandService;

    public OpenUserDO findByBrandId(Long brandId) {
        return findOneByFieldName("brandId", brandId);
    }

    public OpenUserDO findByToken(String token) {
        return super.findOneByFieldName("token", token);
    }

    /**
     * 用户注册
     */
    public OpenUserDO register(OpenUserDO bean) {
        AssertUt.notNull(bean.getBrandId(), "品牌ID不能为空");
        Brand brand = brandService.findById(bean.getBrandId());
        if (brand == null) {
            throw new BusinessException("该品牌ID不存在, 请检查...");
        }
        OpenUserDO last = findByBrandId(bean.getBrandId());
        if (last != null) {
            throw new BusinessException("该会员已经注册, 请检查...");
        }
        RSAKey keys = RSAUt.initKey();
        bean.setToken(StringUt.getUUID());
        bean.setPrivateKey(keys.getPrivateStr());
        bean.setPublicKey(keys.getPublicStr());
        bean.setBrandCode(brand.getCode());
        bean.setBrandName(brand.getName());
        return super.save(bean);
    }

}
