package com.douya.page;

import com.github.pagehelper.Page;
import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;    // 第几页
    private int pageSize;    // 每页记录数
    private int pages;        // 总页数
    private String orderBy;   // 排序
    private List<T> list;    //结果集

    public PageBean() {
        this.pageNum = 1;
        this.pageSize = 10;
        this.orderBy = "createDate DESC";
    }

    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            //mybatis配置了PageHelper分页插件返回类型是否为PageInfo或Page
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.orderBy = page.getOrderBy();
            this.list = page;
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}