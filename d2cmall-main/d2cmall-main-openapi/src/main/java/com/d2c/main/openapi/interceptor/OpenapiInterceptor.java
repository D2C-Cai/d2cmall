package com.d2c.main.openapi.interceptor;

import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.core.propery.AppProperties;
import com.d2c.frame.api.constant.MediaConst;
import com.d2c.frame.web.annotation.SignIgnore;
import com.d2c.frame.web.annotation.SignMethod;
import com.d2c.frame.web.utils.WebUt;
import com.d2c.main.openapi.manager.OpenUserManager;
import com.d2c.main.openapi.security.OpenUserHolder;
import com.d2c.openapi.api.entity.OpenUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class OpenapiInterceptor extends HandlerInterceptorAdapter {

    private static final boolean IS_SIGN = true;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private OpenUserManager openUserManager;
    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            //检查token
            TokenDTO bean = tokenHandler.buildToken(request);
            AssertUt.notBlank(bean.getToken(), "token不能为空...");
            OpenUserDO user = openUserManager.findByToken(bean.getToken());
            if (user == null) {
                throw new AssertException("token不正确或已失效，请联系相关人员..." + bean.getToken());
            }
            //是否进行签名检查
            if (!appProperties.getSign() || !checkSignAnn(handler)) {
                OpenUserHolder.setOpenUser(user);
                return true;
            }
            AssertUt.notNull(bean.getTimestamp(), "timestamp时间戳不能为空...");
            tokenHandler.checkTimestamp(bean.getTimestamp());
            tokenHandler.checkNonces(bean.getToken(), bean.getNonce());
            if (IS_SIGN) {
                AssertUt.notBlank(bean.getSign(), "sign签名不存在...");
                logger.debug("==== sign: ====: " + bean.getSign());
                String designMd = tokenHandler.designPublic(bean.getSign(), user.getPublicKey());
                String signMd = tokenHandler.getSignMd(request, bean.getToken(), bean.getTimestamp(), bean.getNonce());
                logger.debug("==== sign公钥解签名 ====: " + designMd);
                logger.debug("===== 对应校验签名 =====: " + signMd);
                if (!signMd.equals(designMd)) {
                    throw new AssertException("签名异常...");
                }
            }
            OpenUserHolder.setOpenUser(user);
            return true;
        } catch (Exception e) {
            writeError(response, "用户身份校验失败..." + e.getMessage());
            return false;
        }
    }

    /**
     * 根据标签判断是否签名验证
     */
    private Boolean checkSignAnn(Object handler) {
        Method method = WebUt.getMethodOnHandler(handler);
        if (method != null) {
            if (method.isAnnotationPresent(SignMethod.class)) {
                return true;
            } else if (method.isAnnotationPresent(SignIgnore.class)) {
                return false;
            }
        }
        return true;
    }

    private void writeError(HttpServletResponse response, String msg) throws Exception {
        response.setContentType(MediaConst.JSON);
        response.setCharacterEncoding("UTF-8");
        Response error = new ErrorResponse(msg);
        error.setLogin(false);
        response.getWriter().write(JsonUt.serialize(error));
        response.flushBuffer();
    }

}
