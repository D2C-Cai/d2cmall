package com.d2c.order.third.kd100;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ExpressUtil {

    private static final Log logger = LogFactory.getLog(ExpressUtil.class);
    private static final String apikey = "19d9e25ff71a2345";
    /**
     * 返回类型： 0：返回json字符串， 1：返回xml对象， 2：返回html对象， 3：返回text文本。 如果不填，默认返回json字符串。
     */
    private static final String show = "";
    /**
     * 返回信息数量： 1:返回多行完整的信息， 0:只返回一行信息。 不填默认返回多行。
     */
    private static final String muti = "";
    /**
     * 排序： desc：按时间由新到旧排列， asc：按时间由旧到新排列。 不填默认返回倒序（大小写不敏感）
     */
    private static final String order = "";

    public static String query(String nu, String com) {
        String URL = "http://api.kuaidi100.com/api?id=" + apikey + "&com=" + com + "&nu=" + nu + "&show=" + show
                + "&muti=" + muti + "&order=" + order;
        String content = "";
        try {
            URL url = new URL(URL);
            URLConnection con = url.openConnection();
            con.setAllowUserInteraction(false);
            InputStream urlStream = url.openStream();
            String type = URLConnection.guessContentTypeFromStream(urlStream);
            String charSet = null;
            if (type == null)
                type = con.getContentType();
            if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
                return "";
            if (type.indexOf("charset=") > 0)
                charSet = type.substring(type.indexOf("charset=") + 8);
            byte b[] = new byte[10000];
            int numRead = urlStream.read(b);
            content = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlStream.read(b);
                if (numRead != -1) {
                    // String newContent = new String(b, 0, numRead);
                    String newContent = new String(b, 0, numRead, charSet);
                    content += newContent;
                }
            }
            urlStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return content;
    }

    public static void main(String[] agrs) {
        String result = query("50190350703375", "huitongkuaidi");
        JSONObject Object = JSONObject.parseObject(result);
        System.out.println(Object.toString());
    }

}
