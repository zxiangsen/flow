package com.douya.utils;

import java.util.HashMap;
import java.util.Map;

public class Paging {


    /**
     * 	 page  [页数]
     * 	limit  [每一页展示数量]
     *  type   [0 初始化 /1 查询]
     */
    public Map getpaging(int page, int limit, int type){
        Map map = new HashMap();
        map.put("page",page);
        if(type ==0){
            map.put("pages",page*10-10);
            map.put("pagee",10);

        }else if(type ==1 && page ==1){
            map.put("pages",0);
            map.put("pagee",10);

        }else if(type ==1 && page >1){
            map.put("pages",page*10-10);
            map.put("pagee",10);
        }
        return map;
    }

    public static void main(String[] args) throws Exception {
        Paging pg = new Paging();
        System.out.println(pg.getpaging(1,10,0));
    }

}
