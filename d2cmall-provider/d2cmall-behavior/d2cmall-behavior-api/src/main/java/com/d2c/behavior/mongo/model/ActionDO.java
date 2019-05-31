package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户行为
 *
 * @author wull
 */
@Document
public class ActionDO extends BaseMongoDO {

    private static final long serialVersionUID = 5049158952120571446L;
    /**
     * who
     */
    @Indexed
    private Object userId;
    private String userName;
    /**
     * where 哪里进来
     */
    private String action;
    /**
     * where
     */
    private double score;

    public ActionDO() {
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
