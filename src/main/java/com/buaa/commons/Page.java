package com.buaa.commons;

import java.util.List;

public class Page {
    private List records;            //记录
    private int pageSize = 3;        //每页显示的记录条数
    private int pageNum;             //当前页码

    private int totalPageSize;       //总页数
    private int startIndex;          //每页开始的记录索引
    private int totalRecordsNum;     //总记录条数

    private int prePageNum;          //上一页
    private int netxPageNum;         //下一页

    private String url;              //查询分页的地址

    /**
     * 当前页码和所有记录数目。
     * @param pageNum
     * @param totalRecordsNum
     */
    public Page(int pageNum, int totalRecordsNum)
    {
        this.pageNum = pageNum;
        this.totalRecordsNum = totalRecordsNum;

        totalPageSize = totalRecordsNum % pageSize == 0 ? totalRecordsNum / pageSize : totalRecordsNum /pageSize + 1;
        startIndex = (pageNum - 1) * pageSize;
        prePageNum = (pageNum - 1)  < 1 ? 1 : pageNum - 1;
        netxPageNum = (pageNum + 1) > totalPageSize ? totalPageSize : pageNum + 1;
    }

    public List getRecords() {
        return records;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public int getPrePageNum() {
        return prePageNum;
    }

    public int getNetxPageNum() {
        return netxPageNum;
    }

    public String getUrl() {
        return url;
    }

    public void setRecords(List records) {
        this.records = records;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public void setPrePageNum(int prePageNum) {
        this.prePageNum = prePageNum;
    }

    public void setNetxPageNum(int netxPageNum) {
        this.netxPageNum = netxPageNum;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
