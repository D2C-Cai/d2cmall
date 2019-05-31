package com.d2c.flame.property;

import java.awt.*;

public enum CityCardEnum {
    BALI("card_bali", 330, 428, new Color(255, 255, 255), 0d, 0, 0, new Font("Dialog", Font.BOLD, 55)),
    BEIJING("card_beijing", 357, 830, new Color(255, 255, 255), 0d, 0, 0, new Font("Dialog", Font.BOLD, 33)),
    DONGJING("card_dongjing", 880, 1340, new Color(37, 37, 37), 90d, 560, -500, new Font("Dialog", Font.BOLD, 28)),
    LUNDUN("card_lundun", 545, 810, new Color(37, 37, 37), 0d, 0, 0, new Font("Dialog", Font.BOLD, 33)),
    MILAN("card_milan", 310, 322, new Color(255, 255, 255), 0d, 0, 0, new Font("Dialog", Font.BOLD, 48)),
    MOERBEN("card_moerben", 622, 698, new Color(255, 255, 255), 0d, 0, 0, new Font("Dialog", Font.BOLD, 45)),
    NIUYUE("card_niuyue", 262, 348, new Color(0, 0, 0), 0d, 0, 0, new Font("Dialog", Font.BOLD, 52)),
    SHANGHAI("card_shanghai", 140, 250, new Color(230, 146, 13), 0d, 0, 0, new Font("Dialog", Font.BOLD, 41)),
    XINJIAPO("card_xinjiapo", 325, 600, new Color(255, 255, 255), 0d, 0, 0, new Font("Dialog", Font.BOLD, 57));
    private static final String BASE_URL = "http://static.d2c.cn/img/promo/buyer/";
    /**
     * 图片名称
     */
    private String pic;
    /**
     * 水印x轴
     */
    private int x;
    /**
     * 水印y轴
     */
    private int y;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 水印旋转角度
     */
    private double rotate;
    /**
     * 旋转原点x
     */
    private int translatex;
    /**
     * 旋转原点y
     */
    private int translatey;
    /**
     * 字体
     */
    private Font font;

    CityCardEnum(String pic, int x, int y, Color color, double rotate, int translatex, int translatey, Font font) {
        this.pic = pic;
        this.x = x;
        this.y = y;
        this.color = color;
        this.rotate = rotate;
        this.translatex = translatex;
        this.translatey = translatey;
        this.font = font;
    }

    public static void main(String[] args) {
        Font font = new Font("微软雅黑", Font.BOLD, 55);
        System.out.println(font.getFontName());
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public int getTranslatex() {
        return translatex;
    }

    public void setTranslatex(int translatex) {
        this.translatex = translatex;
    }

    public int getTranslatey() {
        return translatey;
    }

    public void setTranslatey(int translatey) {
        this.translatey = translatey;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getUrl() {
        return BASE_URL + this.getPic() + ".png";
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
