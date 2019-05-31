package com.d2c.common.core.propery;

import com.d2c.common.base.exception.FileException;
import com.d2c.common.base.utils.JsonUt;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * 加载JSON资源文件
 *
 * @author wull
 */
public class BaseJsonProperties {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 	举例字段:
     *  @Value("classpath:json/tag_type.json")
     *	private Resource tagType;
     */
    /**
     * Json转对象
     */
    protected <T> T toJson(Resource res, Class<T> valueType) {
        return JsonUt.deserialize(toFile(res), valueType);
    }

    protected <T> T toJson(Resource res, TypeReference<T> valueTypeRef) {
        return JsonUt.deserialize(toFile(res), valueTypeRef);
    }

    protected File toFile(Resource res) {
        try {
            return res.getFile();
        } catch (IOException e) {
            throw new FileException("Resource资源转换文件失败...", e);
        }
    }

}
