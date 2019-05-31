package com.d2c.frame.backweb.helper;

import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.FileUt;
import com.d2c.common.base.utils.SystemUt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * web服务器帮助类
 */
//@Component
public class WebHelper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpServletResponse response;
    /**
     * 文件服务器上传地址
     */
    @Value("${update.path:}")
    private String updatePath;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(updatePath)) {
            if (SystemUt.isWindow()) {
                updatePath = "D:/mywork/update";
            } else {
                updatePath = "/mnt/tmp/update";
            }
        }
        logger.info("文件服务器上传地址:" + updatePath);
    }

    /**
     * 设置导出Excel配置
     */
    public HttpServletResponse downloadExcel(File file) {
        AssertUt.notNull(response);
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
        FileUt.copy(file, response);
        return response;
    }

    public HttpServletResponse downloadZip(File file) {
        AssertUt.notNull(response);
        response.reset();
        response.setContentType("application/x-zip-compressed");
        response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
        FileUt.copy(file, response);
        return response;
    }

    public String getUpdatePath() {
        return updatePath;
    }

    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

}
