package org.d2c.flame.test;

import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.view.BaseVO;

public class TestVO extends BaseVO {

    private static final long serialVersionUID = 2809925651333038665L;
    @HideColumn
    private String name;
    private Integer age;

    public TestVO convert(TestDTO bean) {
        setAge(1111);
        return this;
    }

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
