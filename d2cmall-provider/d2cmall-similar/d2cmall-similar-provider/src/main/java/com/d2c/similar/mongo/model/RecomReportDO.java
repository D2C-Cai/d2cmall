package com.d2c.similar.mongo.model;

import com.d2c.common.mongodb.model.ReportMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 商品推荐计算报表
 *
 * @author wull
 */
@Document
public class RecomReportDO extends ReportMongoDO {

    private static final long serialVersionUID = 7478690642085731389L;
    @Indexed
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
