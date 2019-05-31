package com.d2c.util.qrcode;

import java.awt.*;
import java.util.Random;

/**
 * 图片验证码
 */
public class RandomCodeUtil {

    /**
     * 放到session中的key
     */
    public static final String RANDOMKEY = "RANDCODE";
    /**
     * 随机产生的字符串
     */
    private static String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 图片宽
     */
    public int width = 90;
    /**
     * 图片高
     */
    public int height = 38;
    /**
     * 干扰线数量
     */
    public int lineSize = 40;
    /**
     * 随机产生字符数量
     */
    public int stringNum = 4;
    private Random random = new Random();

    /**
     * 获得字体
     */
    public Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 26);
    }

    /**
     * 获得颜色
     */
    public Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 绘制字符串
     */
    public String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 16 * i, 23);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    public void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public int getStringNum() {
        return stringNum;
    }

    public void setStringNum(int stringNum) {
        this.stringNum = stringNum;
    }

}
