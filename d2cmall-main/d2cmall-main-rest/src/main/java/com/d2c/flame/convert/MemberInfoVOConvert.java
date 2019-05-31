package com.d2c.flame.convert;

import com.d2c.common.api.convert.BaseConvert;
import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.view.MemberInfoVO;
import com.d2c.order.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberInfoVOConvert extends BaseConvert<MemberInfo, MemberInfoVO> {

    @Autowired
    private OrderQueryService orderQueryService;

    @Override
    public MemberInfoVO convert(MemberInfo bean) {
        if (bean == null) return null;
        MemberInfoVO view = ConvertHelper.convert(bean, MemberInfoVO.class);
        view = BeanUt.apply(view, orderQueryService.findOrderStat(bean.getId()));
        return view;
    }

}
