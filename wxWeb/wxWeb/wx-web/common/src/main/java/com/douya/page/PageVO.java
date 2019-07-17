package com.douya.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linpz on 2017/6/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) //作用是实体类的参数查询到的为null的不显示
@JsonIgnoreProperties({}) //需要忽略的属性，请添加在{}内，多个使用请“,”号隔开
public class PageVO<T> implements Serializable {

    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 数据
     */
    private List<T> list;

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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
