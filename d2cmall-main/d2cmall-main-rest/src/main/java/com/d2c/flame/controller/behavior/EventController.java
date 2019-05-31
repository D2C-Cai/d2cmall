package com.d2c.flame.controller.behavior;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.mongo.dto.EventDTO;
import com.d2c.behavior.mongo.dto.PersonDeviceDTO;
import com.d2c.behavior.mongo.enums.EventEnum;
import com.d2c.behavior.mongo.model.EventDO;
import com.d2c.behavior.mongo.model.PersonSessionDO;
import com.d2c.behavior.services.EventService;
import com.d2c.behavior.services.ProductHistoryService;
import com.d2c.behavior.services.SessionService;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.flame.controller.base.BaseSessionController;
import com.d2c.frame.web.annotation.SignIgnore;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户行为
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/behavior/event")
public class EventController extends BaseSessionController {

    @Autowired
    private SessionService sessionService;
    @Reference
    private EventService eventService;
    @Reference
    private ProductHistoryService productHistoryService;

    /**
     * 用户加载，同步设备数据，启动Session
     * <p>API: /v3/api/behavior/event/onload
     * <p> App在header中添加 设备唯一标识 udid：
     * <br>Android: IMEI
     * <br>IOS: UDID
     * <br>WEB: 存入Cookie中UDID,  SessionId
     *
     * @param PersonDeviceDO 设备相关参数
     * @return 是否成功
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/onload", method = RequestMethod.POST)
    public Response onload(PersonDeviceDTO device) {
        PersonSessionDO session = validateSession(device);
        return ResultHandler.success(session);
    }

    /**
     * 埋点事件提交
     * <p>API: /v3/api/behavior/event/add
     * <p>body: EventDTO event {
     * event 事件名称
     * data 数据对象
     * }
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response addEvent(@RequestBody EventDTO event) {
        AssertUt.notNull(event, "event对象不能为空...");
        AssertUt.notNull(event.getEvent(), "事件名称不能为空...");
        AssertUt.isTrue(EventEnum.contains(event.getEvent()), "该事件暂停或不可用...event:" + event.getEvent());
        PersonSessionDO session = validateSession();
        EventDO bean = null;
        if (session != null) {
            bean = eventService.event(session, event, session.getLastEvent());
            session.setLastEvent(bean);
            savePersonSession(session);
        }
        // 打开商品详情，记录足迹数据
        if (EventEnum.PRODUCT.eq(bean.getEvent())) {
            Long productId = ConvertUt.convertType(bean.getTargetId(), Long.class);
            productHistoryService.addProductHistory(session.getMemberId(), productId);
        }
        return ResultHandler.success(bean);
    }

    /**
     * 埋点事件批量提交提交
     * <p>API: /v3/api/behavior/event/add
     * <p>body: List<EventDTO> eventList [{
     * event 事件名称
     * data 数据对象
     * gmtModified 修改时间
     * }, ...]
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/addList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response addEventList(@RequestBody List<EventDTO> eventList) {
        AssertUt.notEmpty(eventList, "事件列表不能为空");
        eventList = checkEvents(eventList);
        PersonSessionDO session = validateSession();
        List<EventDO> list = null;
        if (session != null) {
            list = eventService.event(session, eventList, session.getLastEvent());
            session.setLastEvent(list.get(list.size() - 1));
            savePersonSession(session);
        }
        return ResultHandler.success(list);
    }

    /**
     * 我的足迹 - 删除用户足迹
     * <p>API: /v3/api/behavior/event/unshow/{eventId}
     */
    @ResponseBody
    @RequestMapping(value = "/unshow/{eventId}")
    public ResponseResult findProductEventList(@PathVariable String eventId) {
        getLoginMemberInfo();
        eventService.updateUnShow(eventId, true);
        return new ResponseResult();
    }

    /**
     * 第三方账户登录
     */
    public PersonSessionDO onThirdLogin(Member member) {
        try {
            AssertUt.notNull(member);
            PersonSessionDO session = validateSession();
            session.setToken(member.getToken());
            return savePersonSession(sessionService.onThirdLogin(session, member));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 会员用户登录
     */
    public PersonSessionDO onLogin(MemberInfo memberInfo, String token) {
        try {
            AssertUt.notNull(memberInfo);
            PersonSessionDO session = validateSession();
            session.setToken(token);
            return savePersonSession(sessionService.onLogin(session, memberInfo));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 会员用户登出
     */
    public void onLogout() {
        try {
            PersonSessionDO session = getPersonSession();
            if (session != null) {
                sessionService.updateSessionClose(session.getId());
                cleanPersonSession();
            }
        } catch (Exception e) {
        }
    }
    //****************************************************

    private List<EventDTO> checkEvents(List<EventDTO> events) {
        List<EventDTO> resList = new ArrayList<>();
        events.forEach(event -> {
            if (!StringUtils.isEmpty(event.getEvent()) && EventEnum.contains(event.getEvent())) {
                resList.add(event);
            }
        });
        AssertUt.notEmpty(resList, "提交事件列表暂停或不可用..");
        return resList;
    }

}
