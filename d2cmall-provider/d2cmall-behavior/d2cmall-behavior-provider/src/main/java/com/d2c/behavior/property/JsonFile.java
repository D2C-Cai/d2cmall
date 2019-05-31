package com.d2c.behavior.property;

import com.d2c.behavior.mongo.dto.load.TagTypeDTO;
import com.d2c.common.core.propery.BaseJsonProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonFile extends BaseJsonProperties {

    @Value("classpath:json/tag_type.json")
    private Resource tagType;

    public List<TagTypeDTO> getTagTypeList() {
        return toJson(tagType, new TypeReference<List<TagTypeDTO>>() {
        });
    }

}
