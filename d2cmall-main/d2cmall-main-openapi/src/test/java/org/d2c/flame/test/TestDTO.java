package org.d2c.flame.test;

import com.d2c.common.api.dto.BaseDTO;

public class TestDTO extends BaseDTO {

    private static final long serialVersionUID = 2809925651333038665L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
