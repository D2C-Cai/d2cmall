package com.d2c.report.services.base;

import com.d2c.common.base.utils.StringUt;
import com.d2c.member.enums.PartnerLevelEnum;
import com.d2c.member.model.PartnerCounselor;
import com.d2c.member.service.PartnerCounselorService;
import com.d2c.report.dao.PartnerSaleMapper;
import com.d2c.report.mongo.dto.SaleStatDTO;
import com.d2c.report.mongo.model.base.PartnerSaleBaseDO;
import com.d2c.report.mongo.model.base.PartnerSaleTimeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 买手销售统计数据 - 父类
 *
 * @author wull
 */
public class PartnerSaleBaseServiceImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected PartnerSaleMapper partnerSaleMapper;
    @Autowired
    protected PartnerCounselorService partnerCounselorService;

    /**
     * 创建单条买手统计数据
     */
    public void initReport(PartnerSaleBaseDO bean) {
        Long partnerId = bean.getPartnerId();
        Date start = null;
        Date end = null;
        if (bean instanceof PartnerSaleTimeDO) {
            PartnerSaleTimeDO tb = (PartnerSaleTimeDO) bean;
            start = tb.getStartTime();
            end = tb.getEndTime();
        }
        bean.setInviteBuyer(partnerSaleMapper.countInvite(partnerId, PartnerLevelEnum.BUYER.getCode(), start, end));
        bean.setInviteDM(partnerSaleMapper.countInvite(partnerId, PartnerLevelEnum.DM.getCode(), start, end));
        bean.setSaleStat(findSaleStat("partner_id", partnerId, start, end));
        bean.setSaleStatDM(findSaleStat("parent_id", partnerId, start, end));
        bean.setSaleStatSDM(findSaleStat("super_id", partnerId, start, end));
        bean.setSaleStatAM(findSaleStat("master_id", partnerId, start, end));
        if (bean.getCounselorId() != null && partnerCounselorService != null) {
            PartnerCounselor pc = partnerCounselorService.findById(bean.getCounselorId());
            bean.setCounselorName(pc.getName());
            bean.setCounselorWeixin(pc.getWeixin());
        }
        bean.init();
    }

    public SaleStatDTO findSaleStat(String fieldName, Long partnerId, Date start, Date end) {
        SaleStatDTO dto = partnerSaleMapper.findSaleStat(fieldName, partnerId, start, end);
        dto.setPartnerId(partnerId);
        return dto;
    }

    public <T extends PartnerSaleBaseDO> List<T> toView(List<T> list) {
        if (list != null) {
            list.forEach(bean -> {
                toView(bean);
            });
        }
        return list;
    }

    public <T extends PartnerSaleBaseDO> T toView(T bean) {
        if (bean != null) {
            bean.setLoginCode(StringUt.hideMobile(bean.getLoginCode()));
        }
        return bean;
    }

}
