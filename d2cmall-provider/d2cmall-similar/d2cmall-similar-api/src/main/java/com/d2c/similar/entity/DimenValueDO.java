package com.d2c.similar.entity;

import com.d2c.common.api.model.BaseDO;
import com.d2c.similar.constant.Constant;

import javax.persistence.Table;

/**
 * 离散型多维度数据对应维度值
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "dimen_value")
public class DimenValueDO extends BaseDO {

    private static final long serialVersionUID = 7902814112969375973L;
    private Integer keyId;
    private Integer tplId;
    private Double dist;

    public DimenValueDO() {
    }

    public DimenValueDO(Integer keyId, Integer tplId, Double dist) {
        this.keyId = keyId;
        this.tplId = tplId;
        this.dist = dist;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public Integer getTplId() {
        return tplId;
    }

    public void setTplId(Integer tplId) {
        this.tplId = tplId;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

}
