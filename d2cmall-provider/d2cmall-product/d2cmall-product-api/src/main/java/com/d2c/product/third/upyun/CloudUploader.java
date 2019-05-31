package com.d2c.product.third.upyun;

import com.d2c.product.third.upyun.core.CloudFile;
import com.d2c.product.third.upyun.core.Picture;
import com.d2c.product.third.upyun.core.UpYun;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class CloudUploader implements Runnable, InitializingBean {

    private static final Log logger = LogFactory.getLog(CloudUploader.class);
    private static Queue<CloudFile> FILE_QUEUE = new LinkedList<CloudFile>();
    private static Thread thread;
    private static Object mute = new Object();
    private static CloudUploader instance;
    Properties upyunProperties = new Properties();

    public CloudUploader() {
    }

    public static boolean add(CloudFile file) {
        if (instance == null)
            instance = new CloudUploader();
        synchronized (mute) {
            FILE_QUEUE.add(file);
            mute.notify();
        }
        logger.info("[pic]add to UpYun!");
        return true;
    }

    public void setUpyunProperties(Properties upyunProperties) {
        this.upyunProperties = upyunProperties;
    }

    @Override
    public void run() {
        while (true) {
            if (FILE_QUEUE.isEmpty()) {
                synchronized (mute) {
                    try {
                        mute.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                CloudFile file = FILE_QUEUE.remove();
                boolean result = false;
                if (CloudFile.CLOUD_STORAGE_SUPPORT && (file instanceof Picture)) {
                    Picture picture = (Picture) file;
                    UpYun upyun = new UpYun(upyunProperties.getProperty("pic"));
                    try {
                        result = upyun.writeFile(picture.getPath(), picture.getImageFile(), true);
                        logger.info("[pic]upload to UpYun " + result);
                    } catch (Exception e) {
                        logger.error("[upload picture]" + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

}
