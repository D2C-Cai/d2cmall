package com.d2c.common.api.dto;

public class ResDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private boolean success = true;
    private String message;
    private Integer total;
    private Object data;

    public ResDTO() {
        this.success = true;
    }

    public ResDTO(Integer total, Object data) {
        this.total = total;
        this.data = data;
    }

    public ResDTO(Object data) {
        this.data = data;
    }

    public ResDTO(Boolean success) {
        this.success = success;
    }

    public ResDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResDTO(Object data, Boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
