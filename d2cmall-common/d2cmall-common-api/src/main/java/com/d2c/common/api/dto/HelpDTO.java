package com.d2c.common.api.dto;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class HelpDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    protected Long id;
    /**
     * 名称
     */
    protected String name;
    /**
     * 图片
     */
    protected String pic;

    public HelpDTO() {
    }

    public HelpDTO(Object o) {
        BeanUtils.copyProperties(o, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
