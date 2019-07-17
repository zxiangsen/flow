package com.douya.page;


import java.util.Collections;
import java.util.List;

/**
 * 列表分页实现工具类
 */
public class ListPageHelper<T> {
    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 原集合
     */
    private List<T> data;

    public static ListPageHelper getInstance(List data, int pageSize) {

        return new ListPageHelper(data, pageSize);
    }

    private ListPageHelper(List data, int pageSize) {
        if (data == null || data.isEmpty()) {
            return;
        }

        if(pageSize <= 0) {
            pageSize = 10;
        }

        this.data = data;
        this.pageSize = pageSize;
        this.pages = data.size() / pageSize;
        if(data.size() % pageSize!=0){
            this.pages++;
        }
    }

    /**
     * 得到分页后的数据
     *
     * @param pageNum 页码
     * @return 分页后结果
     */
    public PageVO<T> getPagedList(int pageNum) {

        if(pages==0) {
            PageVO vo = new PageVO<String>();
            vo.setList(Collections.emptyList());
            return vo;
        }

        if(pageNum <= 0) {
            pageNum = 1;
        }

        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            PageVO vo = new PageVO<String>();
            vo.setList(Collections.emptyList());
            return vo;
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }

        PageVO info = new PageVO();
        info.setPages(pages);
        info.setPageNum(pageNum);
        info.setPageSize(pageSize);
        info.setList(data.subList(fromIndex, toIndex));

        return info;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageCount() {
        return pages;
    }

}
