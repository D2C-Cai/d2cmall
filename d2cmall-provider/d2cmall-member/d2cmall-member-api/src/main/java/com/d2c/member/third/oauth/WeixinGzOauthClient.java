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
 * 微信公众平台授权
 * <p>
 * 1 第一步：用户同意授权，获取code 2 第二步：通过code换取网页授权access_token 3 第三步：刷新access_token（如果需要）
 * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 */
public class WeixinGzOauthClient {

    private String WEIXIN_GZ_APPKEY;
    private String WEIXIN_GZ_APPSECRET;
    private String WEIXIN_GZ_CALLBACK;
    private String WEIXIN_GZ_APPKEY_PART;
    private String WEIXIN_GZ_APPSECRET_PART;
    private String WEIXIN_GZ_CALLBACK_PART;

    /**
     * 微信授权
     * <p>
     * 1 第一步：用户同意授权，获取code
     *
     * @param authorize TRUE scope=snsapi_userinfo FALSE scope=snsapi_base
     * @param referUrl  重定向后会带上state参数(最多128字节)
     */
    public String getCode(boolean authorize, String referUrl) {
        String redirect_uri = "";
        try {
            referUrl = referUrl.replaceAll("&", "__");
            redirect_uri = WEIXIN_GZ_CALLBACK + "?path=" + referUrl;
            redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
            // 为了满足最多128字节
            // 其余自定义授权后跳转URL去除参数
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (authorize) {
            // snsapi_userinfo
            // （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
            return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WEIXIN_GZ_APPKEY + "&redirect_uri="
                    + redirect_uri + "&response_type=code&scope=snsapi_userinfo&state=d2c" + "#wechat_redirect";
        } else {
            // snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
            return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WEIXIN_GZ_APPKEY + "&redirect_uri="
                    + redirect_uri + "&response_type=code&scope=snsapi_base&state=d2c" + "#wechat_redirect";
        }
    }

    /**
     * 微信授权
     * <p>
     * 1 第一步：用户同意授权，获取code
     *
     * @param authorize TRUE scope=snsapi_userinfo FALSE scope=snsapi_base
     * @param referUrl  重定向后会带上state参数(最多128字节)
     */
    public String getPartnerCode(boolean authorize, String referUrl) {
        String redirect_uri = "";
        try {
            referUrl = referUrl.replaceAll("&", "__");
            redirect_uri = WEIXIN_GZ_CALLBACK_PART + "?path=" + referUrl;
            redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
            // 为了满足最多128字节
            // 其余自定义授权后跳转URL去除参数
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (authorize) {
            // snsapi_userinfo
            // （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
            return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WEIXIN_GZ_APPKEY_PART
                    + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=snsapi_userinfo&state=d2c"
                    + "#wechat_redirect";
        } else {
            // snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
            return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WEIXIN_GZ_APPKEY_PART
                    + "&redirect_uri=" + redirect_uri + "&response_type=code&scope=snsapi_base&state=d2c"
                    + "#wechat_redirect";
        }
    }

    /**
     * 2 第二步：通过code换取网页授权access_token
     *
     * @param code
     * @return
     * @throws IOException
     */
    public String getAccessTokenAndOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WEIXIN_GZ_APPKEY + "&secret="
                + WEIXIN_GZ_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        CloseableHttpClient client = null;
        HttpGet get = null;
        try {
            client = getHttpClient();
            get = new HttpGet(url);
            get.setHeader("Connection", "close");
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
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 2 第二步：通过code换取网页授权access_token
     *
     * @param code
     * @return
     */
    public String getPartnerAccessTokenAndOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WEIXIN_GZ_APPKEY_PART + "&secret="
                + WEIXIN_GZ_APPSECRET_PART + "&code=" + code + "&grant_type=authorization_code";
        HttpGet get = null;
        CloseableHttpClient client = null;
        try {
            client = getHttpClient();
            get = new HttpGet(url);
            get.setHeader("Connection", "close");
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
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private CloseableHttpClient getHttpClient() {
        return SSLUtil.createSSLClientDefault();
    }

    /**
     * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public String getUser(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        HttpGet get = null;
        CloseableHttpClient client = null;
        try {
            client = getHttpClient();
            get = new HttpGet(url);
            get.setHeader("Connection", "close");
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
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
    public String getPartnerUser(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        CloseableHttpClient client = null;
        HttpGet get = null;
        try {
            client = getHttpClient();
            get = new HttpGet(url);
            get.setHeader("Connection", "close");
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
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public String getWEIXIN_GZ_APPKEY() {
        return WEIXIN_GZ_APPKEY;
    }

    public void setWEIXIN_GZ_APPKEY(String wEIXIN_GZ_APPKEY) {
        WEIXIN_GZ_APPKEY = wEIXIN_GZ_APPKEY;
    }

    public String getWEIXIN_GZ_APPSECRET() {
        return WEIXIN_GZ_APPSECRET;
    }

    public void setWEIXIN_GZ_APPSECRET(String wEIXIN_GZ_APPSECRET) {
        WEIXIN_GZ_APPSECRET = wEIXIN_GZ_APPSECRET;
    }

    public String getWEIXIN_GZ_CALLBACK() {
        return WEIXIN_GZ_CALLBACK;
    }

    public void setWEIXIN_GZ_CALLBACK(String wEIXIN_GZ_CALLBACK) {
        WEIXIN_GZ_CALLBACK = wEIXIN_GZ_CALLBACK;
    }

    public String getWEIXIN_GZ_APPKEY_PART() {
        return WEIXIN_GZ_APPKEY_PART;
    }

    public void setWEIXIN_GZ_APPKEY_PART(String wEIXIN_GZ_APPKEY_PART) {
        WEIXIN_GZ_APPKEY_PART = wEIXIN_GZ_APPKEY_PART;
    }

    public String getWEIXIN_GZ_APPSECRET_PART() {
        return WEIXIN_GZ_APPSECRET_PART;
    }

    public void setWEIXIN_GZ_APPSECRET_PART(String wEIXIN_GZ_APPSECRET_PART) {
        WEIXIN_GZ_APPSECRET_PART = wEIXIN_GZ_APPSECRET_PART;
    }

    public String getWEIXIN_GZ_CALLBACK_PART() {
        return WEIXIN_GZ_CALLBACK_PART;
    }

    public void setWEIXIN_GZ_CALLBACK_PART(String wEIXIN_GZ_CALLBACK_PART) {
        WEIXIN_GZ_CALLBACK_PART = wEIXIN_GZ_CALLBACK_PART;
    }

}
