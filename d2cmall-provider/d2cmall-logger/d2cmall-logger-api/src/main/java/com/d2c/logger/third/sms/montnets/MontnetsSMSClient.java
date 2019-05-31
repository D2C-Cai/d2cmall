package com.d2c.logger.third.sms.montnets;

import com.d2c.logger.third.sms.montnets.http.CHttpPost;

public class MontnetsSMSClient {

    private static final String userId = "J51702";
    private static final String password = "658622";
    private static final String subPort = "*";
    private static CHttpPost client = null;

    private MontnetsSMSClient() {
    }

    public synchronized static CHttpPost getClient() {
        if (client == null) {
            try {
                client = new CHttpPost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public static int sendSms(String strMobiles, String strMessage) {
        return getClient().SendSms(new StringBuffer(), userId, password, strMobiles, strMessage + " (D2C 全球好设计)",
                subPort, "" + System.currentTimeMillis(), false, null);
    }

    public static void main(String[] args) {
        sendSms("18698573501", "尊敬的D2C会员");
    }

}
