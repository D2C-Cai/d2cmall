package com.d2c.util.qrcode;

public class QRcodeEntity {

    /**
     * 二维码内容
     */
    private String content;
    /**
     * 二维码下方内容
     */
    private String lowerContent;
    /**
     * 图片的宽度
     */
    private int width;
    /**
     * 图片的高度
     */
    private int height;
    /**
     * logo图地址
     */
    private String logoPath;
    /**
     * 生成图片的格式
     */
    private String format;
    /**
     * 纠错级别
     */
    private Object errorCorrectionLevel;
    /**
     * 编码格式
     */
    private String characterSet;
    /**
     * 二维码边缘留白
     */
    private int margin;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Object getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public void setErrorCorrectionLevel(Object errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLowerContent() {
        return lowerContent;
    }

    public void setLowerContent(String lowerContent) {
        this.lowerContent = lowerContent;
    }

}
