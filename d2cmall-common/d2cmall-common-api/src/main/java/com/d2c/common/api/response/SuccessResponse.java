package com.d2c.common.api.response;

import com.d2c.common.api.page.PageResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 一般成功提示
 */
public class SuccessResponse extends Response {

    private static final long serialVersionUID = 1L;
    /**
     * 页内容
     */
    private List<Object> list = new ArrayList<Object>();
    /**
     * 总记录数
     */
    private long total = 1;
    /**
     * 页码
     */
    private int index = 1;
    /**
     * 总页数
     */
    private int pageCount = 1;
    /**
     * 每页记录数
     */
    private int pageSize = 1;
    /**
     * 是否有上一页
     */
    private boolean previous;
    /**
     * 是否存在下页
     */
    private boolean next;

    public SuccessResponse() {
        super();
    }

    public SuccessResponse(Object o) {
        this(o, "");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SuccessResponse(PageResult pager) {
        super();
        if (pager != null) {
            this.list = pager.getList();
            this.total = pager.getTotalCount();
            this.index = pager.getPageNumber();
            this.pageCount = pager.getPageCount();
            this.pageSize = pager.getPageSize();
            this.previous = pager.isForward();
            this.next = pager.isNext();
        }
    }

    @SuppressWarnings("rawtypes")
    public SuccessResponse(Object o, String message) {
        if (o != null) {
            if (o instanceof PageResult) {
                pageRecord((PageResult) o);
            } else if (o instanceof Collection) {
                listRecord((Collection) o);
            } else {
                singleRecord(o);
            }
        }
        setMessage(message);
    }

    public void addRecord(Object value) {
        this.getList().add(value);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total, long limit) {
        this.total = total;
        this.pageSize = (int) (total / limit + 1);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public void setNext(int index) {
        if (this.pageSize > index) {
            this.next = true;
        } else {
            this.next = false;
        }
    }

    public boolean getPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public void init(long total, int index, int limit) {
        this.setTotal(total, limit);
        this.setIndex(index);
        this.setNext(index);
    }

    private void singleRecord(Object record) {
        list.add(record);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void listRecord(Collection records) {
        this.list.addAll(records == null ? Collections.emptyList() : records);
        this.total = records.size();
        this.pageSize = records.size();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void pageRecord(PageResult page) {
        if (page.getList() != null)
            this.list.addAll(page.getList());
        this.index = page.getPageNumber();
        this.total = page.getTotalCount();
        this.pageSize = page.getPageSize();
        this.pageCount = page.getPageCount();
    }

}
