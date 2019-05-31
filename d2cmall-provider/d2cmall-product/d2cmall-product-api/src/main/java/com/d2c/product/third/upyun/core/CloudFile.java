package com.d2c.product.third.upyun.core;

public interface CloudFile {

    public static final boolean CLOUD_STORAGE_SUPPORT = true; // 开启云存储

    public java.io.File getImageFile();

    public String getPath();

}
