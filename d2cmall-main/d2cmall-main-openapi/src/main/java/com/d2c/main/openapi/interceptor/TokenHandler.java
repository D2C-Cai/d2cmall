package com.d2c.main.openapi.interceptor;

import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.HttpUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.base.utils.security.MD5Ut;
import com.d2c.common.base.utils.security.RSAUt;
import com.d2c.common.core.tools.LimitHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * Token用户验证
 *
 * @author wull
 */
@Component
public class TokenHandler {

    protected static final long TIMEOUT_MILLIS = 10 * 60000; // 60秒
    protected static final long NONCES_SIZE = 1000;
    protected static final Map<String, String> nonces = new LimitHashMap<>(NONCES_SIZE);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获得TokenDTO
     */
    public TokenDTO buildToken(HttpServletRequest request) {
        TokenDTO bean = new TokenDTO();
        bean.setToken(getHeaderParam(request, "token"));
        bean.setTimestamp(getHeaderParam(request, "timestamp"));
        bean.setNonce(getHeaderParam(request, "nonce"));
        bean.setSign(getHeaderParam(request, "sign"));
        return bean;
    }

    private String getHeaderParam(HttpServletRequest request, String param) {
        String res = request.getHeader(param);
        if (StringUt.isBlank(res)) {
            res = request.getParameter(param);
        }
        return res;
    }

    /**
     * 检查时间戳
     */
    public void checkTimestamp(long timestamp) {
        if (Math.abs(DateUt.getTimeInMillis() - timestamp) > TIMEOUT_MILLIS) {
            throw new AssertException("timestamp时间戳已过期...");
        }
    }

    /**
     * 检查nonce唯一的请求
     */
    public void checkNonces(String token, String nonce) {
        if (StringUt.isNotBlank(nonce)) {
            nonce = token + "_" + nonce;
            if (nonces.containsKey(nonce)) {
                throw new AssertException("nonce数据已存在...:" + nonce);
            } else {
                nonces.put(nonce, nonce);
            }
        }
    }

    /**
     * Token私钥签名
     */
    public String signToken(HttpServletRequest request, String token, String privateKey, Long timestamp, String nonce) {
        String signStr = getSignString(request, token, timestamp, nonce);
        return RSAUt.encodePrivate(privateKey, MD5Ut.md5(signStr));
    }

    public String signToken(String token, String privateKey, Long timestamp, String nonce, Map<String, Object> params) {
        String signStr = getSignString(token, timestamp, nonce, params);
        logger.debug("签名sign数据:" + signStr);
        return RSAUt.encodePrivate(privateKey, MD5Ut.md5(signStr));
    }

    /**
     * Token公钥解密
     */
    public String designPublic(String sign, String publicKey) {
        return RSAUt.decodePublic(publicKey, sign);
    }

    /**
     * Token Md5值
     */
    public String getSignMd(HttpServletRequest request, String token, Long timestamp, String nonce) {
        String signStr = getSignString(request, token, timestamp, nonce);
        logger.debug("校验签名sign数据:" + signStr);
        return MD5Ut.md5(signStr);
    }
    //***********************************************************

    /**
     * 获取需要签名字符串
     */
    public String getSignString(HttpServletRequest request, String token, Long timestamp, String nonce) {
        TreeMap<String, Object> params = new TreeMap<>();
        request.getParameterMap().forEach((k, v) -> params.put(k, StringUt.join(v, ",")));
        return StringUt.join("_", token, timestamp, nonce, getUrlParams(params));
    }

    public String getSignString(String token, Long timestamp, String nonce, Map<String, Object> params) {
        return StringUt.join("_", token, timestamp, nonce, getUrlParams(new TreeMap<>(params)));
    }

    public String getUrlParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return "";
        params.remove("token");
        params.remove("timestamp");
        params.remove("nonce");
        params.remove("sign");
        return HttpUt.getUrlParams(params);
    }

}
