package com.d2c.common.api.dto;

import java.util.List;

public class RelateDTO<T> extends BaseDTO {

    private static final long serialVersionUID = 8897920378378927553L;
    private List<T> addList;
    private List<T> updateList;
    private List<T> deleteList;

    public List<T> getAddList() {
        return addList;
    }

    public void setAddList(List<T> addList) {
        this.addList = addList;
    }

    public List<T> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<T> updateList) {
        this.updateList = updateList;
    }

    public List<T> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<T> deleteList) {
        this.deleteList = deleteList;
    }

}
