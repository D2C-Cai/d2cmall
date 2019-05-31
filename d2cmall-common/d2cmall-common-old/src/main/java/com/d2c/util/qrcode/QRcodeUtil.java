package com.d2c.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRcodeUtil {

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成一维条形码
     */
    public static BufferedImage createBarcode(String str, Integer width, Integer height) {
        if (width == null || width < 200) {
            width = 200;
        }
        if (height == null || height < 50) {
            height = 50;
        }
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
            return toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成QRCode二维码
     */
    public static BufferedImage createQRCode(QRcodeEntity zxing) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, zxing.getErrorCorrectionLevel());
            hints.put(EncodeHintType.CHARACTER_SET, zxing.getCharacterSet());
            hints.put(EncodeHintType.MARGIN, zxing.getMargin());
            BitMatrix bitMatrix = new MultiFormatWriter().encode(zxing.getContent(), BarcodeFormat.QR_CODE,
                    zxing.getWidth(), zxing.getHeight(), hints);
            return createImg(bitMatrix, zxing.getLogoPath(), zxing.getLowerContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage createImg(BitMatrix bitMatrix, String logoPath, String lowerContent)
            throws IOException {
        BufferedImage bi = toBufferedImageContents(bitMatrix);
        if (StringUtils.isNotBlank(lowerContent)) {
            Graphics2D g1 = (Graphics2D) bi.getGraphics();
            g1.setColor(Color.BLACK);// 设置图片颜色
            g1.setFont(new Font("黑体", Font.PLAIN, 35));
            g1.drawString(lowerContent, 120, 456);
        }
        if (StringUtils.isNotBlank(logoPath)) {
            int width_4 = bitMatrix.getWidth() / 4;
            int width_8 = width_4 / 2;
            int height_4 = bitMatrix.getHeight() / 4;
            int height_8 = height_4 / 2;
            /* 返回由指定矩形区域定义的子图像 */
            BufferedImage bi2 = bi.getSubimage(width_4 + width_8, height_4 + height_8, width_4, height_4);
            /* 获取一个绘图工具笔 */
            Graphics2D g2 = bi2.createGraphics();
            /* 读取logo图片信息 */
            Image img = ImageIO.read(new File(logoPath));// 实例化一个Image对象。
            /* 当前图片的宽与高 */
            int currentImgWidth = img.getWidth(null);
            int currentImgHeight = img.getHeight(null);
            /* 处理图片的宽与高 */
            int resultImgWidth = 0;
            int resultImgHeight = 0;
            if (currentImgWidth != width_4) {
                resultImgWidth = width_4;
            }
            if (currentImgHeight != width_4) {
                resultImgHeight = width_4;
            }
            /* 绘制图片 */
            g2.drawImage(img, 0, 0, resultImgWidth, resultImgHeight, null);
            g2.dispose();
        }
        bi.flush();
        return bi;
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static BufferedImage toBufferedImageContents(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }

}
