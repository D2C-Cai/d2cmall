package com.d2c.quartz.task;

import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class NoticeRequisitionTask extends BaseTask {

    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private BrandService brandService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.processRequisitionSmsToDesigner();
        this.processRequisitionSmsToStore();
    }

    private void processRequisitionSmsToDesigner() {
        List<Map<String, Object>> list = requisitionItemService.countGroupByDesignerNew();
        for (Map<String, Object> map : list) {
            Long designerId = (Long) map.get("designerId");
            String designerName = map.get("designerName").toString();
            Long count = (Long) map.get("count");
            if (designerId != null && designerId != 0) {
                Brand brand = brandService.findById(designerId);
                MemberInfo memberInfo = memberInfoService.findByDesignerId(brand.getDesignersId());
                if (memberInfo != null) {
                    String subject = "采购单提醒";
                    String content = "亲爱的" + designerName + "品牌主理人，截止至"
                            + DateUtil.convertDate2Str(new Date(), "yyyy年MM月dd日 HH:mm") + "您还有" + count
                            + "条未处理的采购单，为了防止超期赔偿，请及时处理。";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsgBoss(smsBean, pushBean, msgBean, null);
                }
            }
        }
    }

    private void processRequisitionSmsToStore() {
        List<Map<String, Object>> list = requisitionItemService.countGroupByStore();
        for (Map<String, Object> map : list) {
            Long StoreId = (Long) map.get("storeId");
            String storeName = map.get("storeName").toString();
            Long count = (Long) map.get("count");
            if (StoreId != null && StoreId != 0) {
                MemberInfo memberInfo = memberInfoService.findByStoreId(StoreId);
                if (memberInfo != null) {
                    String subject = "调拨单提醒";
                    String content = "亲爱的" + storeName + "店长，今天有" + count + "单调拨等待您处理";
                    PushBean pushBean = new PushBean(memberInfo.getId(), content, 13);
                    MsgBean msgBean = new MsgBean(memberInfo.getId(), 13, subject, content);
                    SmsBean smsBean = new SmsBean(memberInfo.getNationCode(), memberInfo.getLoginCode(),
                            SmsLogType.REMIND, content);
                    msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
                }
            }
        }
    }

}
