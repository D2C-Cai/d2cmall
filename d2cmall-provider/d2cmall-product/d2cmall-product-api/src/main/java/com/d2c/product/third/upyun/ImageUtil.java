package com.d2c.product.third.upyun;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * 工具类 - 图片处理
 */
public class ImageUtil {

    protected static Log logger = LogFactory.getLog(ImageUtil.class);

    /**
     * 图片缩放(图片等比例缩放为指定大小，空白部分以白色填充)
     *
     * @param srcBufferedImage 源图片
     * @param destFile         缩放后的图片文件
     */
    public static void zoom(Image srcImage, File destFile, int destWidth, int destHeight, String extension) {
        try {
            int imgWidth = destWidth;
            int imgHeight = destHeight;
            int srcWidth = srcImage.getWidth(null);
            int srcHeight = srcImage.getHeight(null);
            // 不指定高度的话 计算出粗来
            if (destHeight == 0) {
                destHeight = (destWidth * srcHeight / srcWidth);
                imgHeight = destHeight;
            }
            if (srcHeight >= srcWidth) {
                imgWidth = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
            } else {
                imgHeight = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
            }
            BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = destBufferedImage.createGraphics();
            graphics2D.setBackground(Color.WHITE);
            graphics2D.clearRect(0, 0, destWidth, destHeight);
            graphics2D.drawImage(srcImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH),
                    (destWidth / 2) - (imgWidth / 2), (destHeight / 2) - (imgHeight / 2), null);
            graphics2D.dispose();
            // ImageIO.write(destBufferedImage, "JPEG", destFile);
            saveTo(destBufferedImage, destFile, extension);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void zoomCrop(BufferedImage image, File destFile, int dstWidth, int dstHeight, String extension) {
        int srcWidth = image.getWidth(null);
        int srcHeight = image.getHeight(null);
        ImageFilter cropFilter = null;
        if (srcWidth < dstWidth && srcHeight < dstHeight) {
            try {
                saveTo(image, destFile, extension);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return;
        }
        Image newImage = image;
        if (srcWidth >= srcHeight) {
            int tmpWidth = srcWidth * dstHeight / srcHeight;
            newImage = image.getScaledInstance(tmpWidth, dstHeight, Image.SCALE_SMOOTH);
            int cWidth = (tmpWidth - dstWidth) / 2;
            cropFilter = new CropImageFilter(cWidth, 0, dstWidth, dstHeight);
        } else {
            int tmpHeight = srcHeight * dstWidth / srcWidth;
            newImage = image.getScaledInstance(dstWidth, tmpHeight, Image.SCALE_SMOOTH);
            int cHeight = (tmpHeight - dstHeight) / 2;
            cropFilter = new CropImageFilter(0, cHeight, dstWidth, dstHeight);
        }
        newImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(newImage.getSource(), cropFilter));
        BufferedImage destBufferedImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = destBufferedImage.createGraphics();
        graphics2D.drawImage(newImage, 0, 0, null);
        try {
            saveTo(destBufferedImage, destFile, extension);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void saveTo(BufferedImage image, File outfile, String extension) throws IOException {
        FileOutputStream out = new FileOutputStream(outfile);
        ImageIO.write(image, extension, out);
        out.close();
    }

    /**
     * 获取图片文件的类型.
     *
     * @param imageFile 图片文件对象.
     * @return 图片文件类型
     */
    public static String getImageFormatName(File imageFile) {
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
            Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
            if (!iterator.hasNext()) {
                return null;
            }
            ImageReader imageReader = (ImageReader) iterator.next();
            imageInputStream.close();
            return imageReader.getFormatName().toLowerCase();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param srcImgPath       源图片地址
     * @param waterMarkContent 水印内容
     * @param font             字体
     * @param color            颜色
     * @param x                水印x轴
     * @param y                水印y轴
     * @param rotate           旋转角度
     * @param translatex       旋转原点x
     * @param translatey       旋转原点y
     * @param defaultWith      最大的字符串（居中用，无需居中或截断可不填）
     * @return
     */
    public static BufferedImage addWaterMark(String waterMarkContent, String srcImgPath, Font font, Color color, int x,
                                             int y, double rotate, int translatex, int translatey, String defaultWith) {
        try {
            // 读取原图片信息
            URL url = new URL(srcImgPath);
            Image srcImg = ImageIO.read(url);// 文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color); // 根据图片的背景设置水印颜色
            g.setFont(font); // 设置字体
            // 设置水印的坐标
            if (rotate != 0 && (translatex != 0 || translatey != 0)) {
                g.translate(translatex, translatey);
                g.rotate(Math.toRadians(rotate), (double) bufImg.getWidth() / 2, (double) bufImg.getHeight() / 2);
            }
            if (StringUtils.isNoneBlank(defaultWith)) {
                FontMetrics metrics = g.getFontMetrics(font);
                Integer defaultWidth = metrics.stringWidth("CHARLES"); // 默认字符串宽度
                if (metrics.stringWidth(waterMarkContent) > defaultWidth) {// 水印宽度超出截取
                    StringBuffer sb = new StringBuffer();
                    for (char c : waterMarkContent.toCharArray()) {
                        if (metrics.stringWidth(sb.append(c).toString()) > defaultWidth) {
                            waterMarkContent = sb.substring(0, sb.length() - 1) + "..";
                            break;
                        }
                    }
                } else if (metrics.stringWidth(waterMarkContent) < defaultWidth) {// 水印调整位置
                    x = x + (defaultWidth - metrics.stringWidth(waterMarkContent)) / 2;
                }
            }
            g.drawString(waterMarkContent, x, y); // 画出水印
            g.dispose();
            return bufImg;
            // 输出图片
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}