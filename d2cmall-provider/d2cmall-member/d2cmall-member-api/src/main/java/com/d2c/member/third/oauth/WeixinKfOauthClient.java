package com.d2c.member.third.oauth;

import com.d2c.util.http.SSLUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信开放平台
 * <p>
 * 1 第一步：用户同意授权，获取code 2 第二步：通过code换取网页授权access_token 3 第三步：刷新access_token（如果需要）
 * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 */
public class WeixinKfOauthClient {

    private String WEIXIN_KF_APPKEY = "wx7ba0639a2b90c743";
    private String WEIXIN_KF_APPSECRET = "ff286d2130774abfe82ff480dfe82e19";
    private String WEIXIN_KF_CALLBACK = "http://www.d2cmall.com/callback/WeixinClient";

    /**
     * 微信授权
     * <p>
     * 1 第一步：用户同意授权，获取code
     *
     * @param authorize TRUE scope=snsapi_userinfo FALSE scope=snsapi_base
     * @param referUrl  重定向后会带上state参数(最多128字节)
     */
    public String getCode() {
        String redirect_uri = "";
        try {
            redirect_uri = URLEncoder.encode(WEIXIN_KF_CALLBACK, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:https://open.weixin.qq.com/connect/qrconnect?appid=" + WEIXIN_KF_APPKEY + "&redirect_uri="
                + redirect_uri + "&response_type=code&scope=snsapi_login#wechat_redirect";
    }

    /**
     * 2 第二步：通过code换取网页授权access_token
     *
     * @param code
     * @return
     */
    public String getAccessTokenAndOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WEIXIN_KF_APPKEY + "&secret="
                + WEIXIN_KF_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        CloseableHttpClient client = null;
        HttpGet get = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getUser(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
        CloseableHttpClient client = null;
        HttpGet get = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            // client = WebClientDevWrapper.wrapClient(client);
            get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getOldUser(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
        CloseableHttpClient client = null;
        HttpGet get = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getWEIXIN_KF_APPKEY() {
        return WEIXIN_KF_APPKEY;
    }

    public void setWEIXIN_KF_APPKEY(String wEIXIN_KF_APPKEY) {
        WEIXIN_KF_APPKEY = wEIXIN_KF_APPKEY;
    }

    public String getWEIXIN_KF_APPSECRET() {
        return WEIXIN_KF_APPSECRET;
    }

    public void setWEIXIN_KF_APPSECRET(String wEIXIN_KF_APPSECRET) {
        WEIXIN_KF_APPSECRET = wEIXIN_KF_APPSECRET;
    }

    public String getWEIXIN_KF_CALLBACK() {
        return WEIXIN_KF_CALLBACK;
    }

    public void setWEIXIN_KF_CALLBACK(String wEIXIN_KF_CALLBACK) {
        WEIXIN_KF_CALLBACK = wEIXIN_KF_CALLBACK;
    }

}
