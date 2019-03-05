package cose.lexian.util;

import java.util.List;

/**
 * 该类用于实现分页
 * @param <T>
 */
public class PageBean<T> {
    private int pageCode; //当前页码
    private int totalRecord; //总记录数
    private int pageSize; //每页记录数
    private List<T> beanList; //当前页的记录
    private String url; //它是url后的条件

    @Override
    public String toString() {
        return "PageBean{" +
                "pageCode=" + pageCode +
                ", totalRecord=" + totalRecord +
                ", pageSize=" + pageSize +
                ", beanList=" + beanList +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageCode() {
        return pageCode;
    }

    public void setPageCode(int pageCode) {
        this.pageCode = pageCode;
    }

    public int getTotalPage() {
        //通过总记录数和每页记录数来计算总页数
        int totalPage = totalRecord / pageSize;
        return totalRecord%pageSize==0 ? totalPage : totalPage+1;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }
}
