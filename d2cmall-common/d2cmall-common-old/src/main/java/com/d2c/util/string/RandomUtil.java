package com.d2c.util.string;

import com.d2c.common.base.utils.security.MD5Util;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    /**
     * 随机获取UUID字符串(无中划线)
     *
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);
    }

    /**
     * 随机获取字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        return getRandomString(length, false);
    }

    /**
     * 随机获取字符串
     *
     * @param length
     * @param upper
     * @return
     */
    public static String getRandomString(int length, Boolean upper) {
        String string = UUID.randomUUID().toString();
        string = string.replaceAll("-", "");
        if (string.length() >= length) {
            string = string.substring(0, length);
        } else {
            while (string.length() < length) {
                int i = new Random().nextInt(36);
                string += i > 25 ? (char) ('0' + (i - 26)) : (char) ('a' + i);
            }
        }
        if (upper)
            string = string.toUpperCase();
        return string;
    }

    /**
     * 随机获取1至9位数字
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        length = (length > 9 || length < 1) ? 9 : length;
        return String.valueOf(Math.random()).substring(2, 2 + length);
    }

    /**
     * 随机获取不重复的数字
     *
     * @param length
     * @param max
     * @return
     */
    public static HashSet<Integer> getRandomNum(int length, int max) {
        HashSet<Integer> set = new HashSet<Integer>();
        if (length >= max) {
            return set;
        }
        Random random = new Random();
        while (set.size() < length) {
            int value = random.nextInt(max + 1);
            if (value != max) {
                set.add(value);
            }
        }
        return set;
    }

    /**
     * 短链接生成
     *
     * @param url
     * @param i
     * @return
     */
    public static String shortUrl(String url, int i) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "d2cmall";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (MD5Util.encodeMD5Hex(key + url));
        String hex = sMD5EncryptResult;
        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(i * 8, i * 8 + 8);
        // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
        // long ，则会越界
        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
        String outChars = "";
        // 循环获得每组6位的字符串
        for (int j = 0; j < 6; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            // (具体需要看chars数组的长度 以防下标溢出，注意起点为0)
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars += chars[(int) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return outChars.toLowerCase();
    }

    public static void main(String args[]) throws Exception {
        System.out.println(RandomUtil.getRandomString(16));
    }

}
