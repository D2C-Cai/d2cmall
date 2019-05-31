package com.d2c.backend.rest.crm.oauth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.common.base.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class D2cApiOauth {

    protected static final Log logger = LogFactory.getLog(D2cApiOauth.class);
    private static final String SECRET_KEY = "6d7da05cc8874640ba6aac060192dee2";// 签名密匙
    public static Map<String, Map<String, String>> partners = new HashMap<String, Map<String, String>>();
    private static D2cApiOauth d2cApiOauth = null;

    private D2cApiOauth() {
        // d2c
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("secret_key", SECRET_KEY);
        roles.put("all", "all");
        partners.put("0000", roles);
        roles = new HashMap<String, String>();
        roles.put("secret_key", SECRET_KEY);
        roles.put("all", "all");
        partners.put("CRM_R_M_001", roles);
        partners.put("CRM_R_M_002", roles);
        partners.put("CRM_R_M_003", roles);
        partners.put("CRM_R_M_004", roles);
        partners.put("CRM_R_M_005", roles);
        partners.put("CRM_R_P_001", roles);
        partners.put("CRM_R_P_002", roles);
        partners.put("CRM_R_P_003", roles);
        partners.put("CRM_R_P_004", roles);
        partners.put("CRM_R_O_001", roles);
        partners.put("CRM_R_O_002", roles);
        partners.put("CRM_R_O_003", roles);
        partners.put("CRM_R_O_004", roles);
    }

    public static D2cApiOauth getInstance() {
        if (d2cApiOauth == null) {
            d2cApiOauth = new D2cApiOauth();
        }
        return d2cApiOauth;
    }

    public void sendJson(HttpServletResponse response, JSONObject result) {
        try {
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(result.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public JSONArray getJsonArray(HttpServletRequest request, String role) {
        String json = receivePost(request);
        JSONObject jsonObj = JSONObject.parseObject(json, Feature.OrderedField);
        // 兼容老系统
        String partnerCode = (jsonObj.get("partner") == null ? "0000" : jsonObj.getString("partner"));
        Map<String, String> partner = partners.get(partnerCode);
        if (partner == null) {
            logger.error(partnerCode + "不存在");
            throw new BusinessException(partnerCode + "不存在");
        }
        // String secretKey = partner.get("secret_key");
        String roleSc = partner.get(role);
        if (!("1".equals(roleSc) || partner.get("all").equals("all"))) {
            logger.error(partnerCode + "不存在");
            return null;
        } else {
            // String md5Str1 = jsonObj.getString("md5Str");
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            // String md5Str2 = DigestUtils.md5Hex(jsonArray.toString() +
            // secretKey);
            // if (!md5Str1.equals(md5Str2)) {// 密匙检验不成功
            // logger.error("密匙检验不成功");
            // throw new BusinessException("密匙检验不成功");
            // // return null;
            // }
            return jsonArray;
        }
    }

    private String receivePost(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String reqBody = sb.toString();
            return URLDecoder.decode(reqBody, "utf-8");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
