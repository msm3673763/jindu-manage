package com.ucsmy.jindu.manage.manage.ext;

/**
 * Created by ucs_zhengfucheng on 2017/6/20.
 */
public class DataPageResult {

    private Object resultList;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private int pages;

    public Object getResultList() {
        return resultList;
    }

    public void setResultList(Object resultList) {
        this.resultList = resultList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
