package com.d2c.logger.third.sms.emay;

import com.d2c.logger.third.sms.emay.global.Client;

import java.rmi.RemoteException;

/***
 * 国际短信发送
 */
public class EmayGlobalSMSClient {

    private static final String softwareSerialNo = "6INT-EMY-6600-JCZRK";// 账号
    private static final String password = "535776";// 密码
    private static final String key = "LLS255XLL884S";// 自建密匙
    private static final String prefix = "【D2C】";
    private static final String suffix = "（D2C全球好设计）";
    private static Client client = null;

    static {
        registEx();
    }

    private EmayGlobalSMSClient() {
        registEx();
    }

    public synchronized static Client getClient() {
        if (client == null) {
            try {
                client = new Client(softwareSerialNo, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    /**
     * 软件序列号注册、或则说是激活、软件序列号首次使用必须激活 registEx(String serialpass) 1、serialpass
     * 软件序列号密码、密码长度为6位的数字字符串、软件序列号和密码请通过亿美销售人员获取
     */
    public static int registEx() {
        try {
            return getClient().registEx(password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 发送短信、可以发送定时和即时短信 sendSMS(String[] mobiles,String smsContent, String
     * addSerial, int smsPriority) 1、mobiles 手机数组长度不能超过1000 2、smsContent
     * 最多500个汉字或1000个纯英文
     * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
     * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
     * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号) 5、其它短信发送请参考使用手册自己尝试使用
     */
    public static int sendSMS(String[] mobiles, String smsContent) {
        try {
            smsContent = prefix + smsContent + suffix;
            return getClient().sendSMS(mobiles, smsContent, "", 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 短信发送
     */
    public static int sendSMS(String mobile, String smsContent) {
        smsContent = prefix + smsContent + suffix;
        return sendSMS(new String[]{mobile}, smsContent);
    }

    /**
     * 获取软件序列号的余额
     */
    public static double getBalance() {
        try {
            return getClient().getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 软件注销 1、软件注销后像发送短信、接受上行短信接口都无法使用 2、软件可以重新注册、注册完成后软件序列号的金额保持注销前的状态
     */
    public static int logout() {
        try {
            return getClient().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        // System.out.println(SMSClient.registEx());
        // System.out.println(SMSClient.logout());
        // System.out.println(SMSClient.getBalance());
        // System.out.println(SMSClient.sendSMS(new String[] { "8613957109020"
        // },
        // "您好， ！" + System.currentTimeMillis()));
        System.out.println(sendSMS(new String[]{"16502249349"}, "您好，这里是D2C ！" + System.currentTimeMillis()));
        System.out.println(EmayGlobalSMSClient.getBalance());
    }

}
