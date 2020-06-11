package com.d2c.logger.third.vms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tk.mybatis.mapper.util.StringUtil;

public class DyvmsClient {

    // 产品名称:云通信语音API产品,开发者无需替换
    static final String product = "Dyvmsapi";
    // 产品域名,开发者无需替换
    static final String domain = "dyvmsapi.aliyuncs.com";
    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "";
    static final String accessKeySecret = "";
    private static final Log logger = LogFactory.getLog(DyvmsClient.class);
    // TODO 显号
    static String calledShowNumber = "";

    /**
     * 文本转语音外呼
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param tempId 模板id
     * @param params 替换模板中变量的值 json格式
     * @return
     * @throws ClientException
     */
    public static int singleCallByTts(String mobile, String code, String tempId, JSONObject params) {
        if (StringUtil.isEmpty(tempId)) {
            tempId = "TTS_112800231";
            params = new JSONObject();
            params.put("code", code);
            params.put("product", "d2c");
        }
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            // 组装请求对象-具体描述见控制台-文档部分内容
            SingleCallByTtsRequest request = new SingleCallByTtsRequest();
            // 必填-被叫显号,可在语音控制台中找到所购买的显号
            request.setCalledShowNumber(calledShowNumber);
            // 必填-被叫号码
            request.setCalledNumber(mobile);
            // 必填-Tts模板ID
            request.setTtsCode(tempId);
            // 可选-当模板中存在变量时需要设置此值
            request.setTtsParam(params.toJSONString());
            // 此处可能会抛出异常，注意catch
            SingleCallByTtsResponse singleCallByTtsResponse = acsClient.getAcsResponse(request);
            if (singleCallByTtsResponse != null && "OK".equalsIgnoreCase(singleCallByTtsResponse.getMessage())) {
                return 1;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

}
