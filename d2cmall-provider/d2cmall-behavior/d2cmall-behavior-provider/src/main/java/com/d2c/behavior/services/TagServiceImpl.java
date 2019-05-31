package com.d2c.behavior.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.behavior.mongo.dao.TagMongoDao;
import com.d2c.behavior.mongo.dao.TagTypeMongoDao;
import com.d2c.behavior.mongo.dto.load.TagTypeDTO;
import com.d2c.behavior.mongo.enums.TagFromEnum;
import com.d2c.behavior.mongo.model.TagDO;
import com.d2c.behavior.mongo.model.TagTypeDO;
import com.d2c.behavior.property.JsonFile;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.ListUt;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户标签定义
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class TagServiceImpl implements TagService {

    @Autowired
    protected TagMongoDao tagMongoDao;
    @Autowired
    protected TagTypeMongoDao tagTypeMongoDao;
    @Autowired
    private JsonFile jsonFile;

    /**
     * 初始化用户标签定义
     */
    public List<TagTypeDO> initTag() {
        List<TagTypeDTO> dtoList = jsonFile.getTagTypeList();
        List<TagTypeDO> list = new ArrayList<>();
        dtoList.forEach(dto -> {
            if (!dto.getOverride() && tagTypeMongoDao.exist(dto.getId())) {
                return;
            }
            list.add(save(convert(dto)));
        });
        return list;
    }

    public void cleanAll() {
        tagMongoDao.cleanAll();
        tagTypeMongoDao.cleanAll();
    }

    /**
     * 获取标签Map
     */
    public Map<String, List<TagTypeDO>> findTagTypeMap() {
        return ListUt.groupToMap(tagTypeMongoDao.findAll(), "inputClz", String.class);
    }

    /**
     * 获取需要处理的用户标签
     */
    public List<TagTypeDO> findTagType(Class<?> inputClz) {
        return tagTypeMongoDao.findByInputClz(inputClz.getName());
    }

    public List<TagTypeDO> findTagType(String inputClz) {
        return tagTypeMongoDao.findByInputClz(inputClz);
    }
    //**********************************

    /**
     * 标签解析转换
     */
    private TagTypeDO convert(TagTypeDTO dto) {
        TagTypeDO bean = ConvertUt.convertBean(dto, TagTypeDO.class);
        TagFromEnum tagFrom = TagFromEnum.getTagFrom(dto);
        bean.setTagFrom(tagFrom.name());
        bean.setTags(tagFrom.initTags(dto));
        return bean;
    }

    /**
     * 新增保存
     */
    public TagTypeDO save(TagTypeDO type) {
        if (type == null) return null;
        if (type.getId() == null) {
            type.setId(new ObjectId().toString());
        }
        if (ListUt.notEmpty(type.getTags())) {
            for (TagDO tag : type.getTags()) {
                if (tag.getId() == null) {
                    tag.setTypeId(type.getId());
                    tag.initId();
                }
                tagMongoDao.save(tag);
            }
        }
        tagTypeMongoDao.save(type);
        return type;
    }

    public Iterable<TagTypeDO> saveAll(Iterable<TagTypeDO> types) {
        if (types != null) {
            types.forEach(type -> save(type));
        }
        return types;
    }

}
