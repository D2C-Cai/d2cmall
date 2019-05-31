package com.d2c.flame.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session会话启动和销毁调用
 * <p>
 * 分布式Session启动销毁，会有多个Web Controller接到消息， 要求事件调用方法必须为幂等性的
 *
 * @author wull
 */
public class SessionEventListener implements HttpSessionListener {
    // private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    // private SessionService sessionService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // try{
        // Object obj =
        // se.getSession().getAttribute(BaseDeviceController.PERSON_SESSION_KEY);
        // if(obj != null){
        // PersonSessionDO bean = (PersonSessionDO) obj;
        // getSessionService().updateSessionClose(bean.getId());
        // }
        // }catch (NullPointerException e){
        // }catch (Exception e) {
        // logger.error("Session销毁时，关闭用户会话失败...", e);
        // }
    }
    // private SessionService getSessionService(){
    // if(sessionService == null){
    // sessionService = SpringMvcHelper.getBean(SessionService.class);
    // }
    // return sessionService;
    // }
}
