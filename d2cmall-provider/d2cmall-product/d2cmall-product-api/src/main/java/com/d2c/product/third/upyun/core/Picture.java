package com.d2c.product.third.upyun.core;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.security.MD5Ut;
import com.d2c.product.third.upyun.CloudUploader;
import com.d2c.product.third.upyun.ImageUtil;
import com.d2c.util.string.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Picture extends PreUserDO implements CloudFile {

    public static final String PICTURE_LOCAL_STOARGE = "/mnt/www/upload/pictures";
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(Picture.class);
    /**
     * 宽度
     */
    protected Integer width = 0;
    /**
     * 高度
     */
    protected Integer height = 0;
    /**
     * 路径
     */
    protected String path;
    /**
     * 类型
     */
    protected String contentType;
    /**
     * 排序
     */
    protected int orderWeight;
    /**
     * 归属
     */
    protected Long belongTo;
    /**
     * 备注
     */
    protected String text;
    private File imageFile;
    private BufferedImage image;
    /**
     * 原路径
     */
    private String oldPath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 扩展名
     */
    private String extension;

    public Picture() {
    }

    public Picture(File imageFile) throws IOException {
        this.initImageFile(imageFile);
    }

    public void initImageFile(File imageFile) throws IOException {
        this.imageFile = imageFile;
        this.setFileName(imageFile.getName());
        this.initWidthAndHeight();
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(Long belongTo) {
        this.belongTo = belongTo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (!StringUtils.isBlank(fileName)) {
            int pos = fileName.lastIndexOf(".");
            if (pos > 0)
                fileName = fileName.substring(0, pos);
            this.fileName = fileName;
        }
    }

    public String getExtension() {
        if (this.fileName != null) {
            int i = this.fileName.lastIndexOf(".");
            String e;
            if (i > 0) {
                e = this.fileName.substring(i + 1);
            } else {
                e = "jpg";
            }
            this.extension = e;
        }
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRandomFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String name = RandomUtil.getRandomString(10);
        if (pos > 0) {
            String extention = fileName.substring(pos, fileName.length());
            name = name + extention;
        }
        return name;
    }

    public int getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(int orderWeight) {
        this.orderWeight = orderWeight;
    }

    /***
     * 初始化图片长宽
     *
     * @throws IOException
     */
    public void initWidthAndHeight() throws IOException {
        if (image == null && imageFile != null) {
            image = ImageIO.read(imageFile);
        }
        if (image != null && imageFile != null) {
            this.width = image.getWidth(null);
            this.height = image.getHeight(null);
        }
    }

    protected String getMD5FileName(File file) {
        String name;
        try {
            name = MD5Ut.getFileMD5String(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            name = String.valueOf(System.currentTimeMillis());
        }
        return name;
    }

    public void resizeTo(int width, int height) throws IOException {
        resizeTo(width, height, false);
    }

    /**
     * ImageUtil 生成缩略图
     */
    public void resizeTo(int width, int height, boolean crop) throws IOException {
        if (image != null) {
            File destFile = new File(getDiskPath() + "_" + width);
            if (crop)
                ImageUtil.zoomCrop(image, destFile, width, height, getExtension());
            else
                ImageUtil.zoom(image, destFile, width, height, getExtension());
        }
    }

    public Picture newInstanceFromUrl(String url) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IOException {
        File file = new File(PICTURE_LOCAL_STOARGE + "/" + url);
        Picture picture = newInstance(file);
        picture.setOldPath(url);
        return picture;
    }

    public Picture newInstance(File file) throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<?>[] constructors = this.getClass().getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0].equals(File.class)) {
                Picture picture = (Picture) constructor.newInstance(file);
                return picture;
            }
        }
        return null;
    }

    public abstract void resize() throws IOException;

    /**
     * 获取子目录 eg. 商品 /product/ 设计师 /designer/
     *
     * @return
     */
    public abstract String getSubdirectory();

    /**
     * 存储路径, 包含文件名 eg. /product/20/544320/T2gku2XbVaXXXXXXXX
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayYearAndMonth() {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyMM");
        return dateFormate.format(new Date());
    }

    /**
     * 物理存储目录
     *
     * @return
     */
    public String getDiskDirectory() {
        return PICTURE_LOCAL_STOARGE + "/" + this.getSubdirectory();
    }

    public abstract Picture getPicture(File newFile) throws IOException;

    /**
     * 物理存储路径， eg. /home/www/upload/pictures/product/1408/T2gku2XbVaXXXXXXXX
     *
     * @return
     */
    public String getDiskPath() {
        return PICTURE_LOCAL_STOARGE + "/" + getPath();
    }

    public String getDiskSubdirectory() {
        return PICTURE_LOCAL_STOARGE + "/" + getSubdirectory();
    }

    public String getOldPath() {
        return oldPath;
    }

    public void setOldPath(String oldPath) {
        this.oldPath = oldPath;
    }

    public void upload2CloudStore() {
        if (Picture.CLOUD_STORAGE_SUPPORT) {
            File destDir = imageFile.getParentFile();
            if (destDir.isDirectory()) {
                File[] files = destDir.listFiles();
                String md5FileName = imageFile.getName();
                for (int i = 0; i < files.length; i++) {
                    File destFile1 = files[i];
                    String fileName = md5FileName;
                    if (!destFile1.getName().startsWith(imageFile.getName())) {
                        continue;
                    } else if (destFile1.getName().equals(imageFile.getName())) {
                        if (CLOUD_STORAGE_SUPPORT) {
                            CloudUploader.add(this);
                        }
                    } else {
                        int pos = destFile1.getName().lastIndexOf("_");
                        String extention = "";
                        if (pos > 0) {
                            extention = destFile1.getName().substring(pos, destFile1.getName().length());
                            fileName = md5FileName + extention;
                        }
                        File target = new File(destDir + "/" + fileName);
                        Picture temp;
                        try {
                            temp = newInstance(target);
                            temp.setPath(this.getPath() + extention);
                            if (CLOUD_STORAGE_SUPPORT) {
                                CloudUploader.add(temp);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

}
