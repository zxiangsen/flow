package com.douya.utils;

import java.util.HashMap;
import java.util.Map;
/*
* zmy
* 2018-05-31
* */
public class GDPRankingUtils {
    public static double getRanking(String province){
        Map<String,Integer>  map=getMap();
        //邀请人收益
        double income=0;
        if (map.get(province)<11){
          income=100000*20/100;
        }
      else if (map.get(province)<21&&map.get(province)>10){
            income=80000*20/100;
        }
        else if (map.get(province)>20){
            income=60000*20/100;
        }
        return income;
    }

    public static Map<String,Integer> getMap(){
        Map<String,Integer> map=new HashMap();
        map.put("广东",1);
        map.put("江苏",2);
        map.put("山东",3);
        map.put("浙江",4);
        map.put("河南",5);
        map.put("四川",6);
        map.put("湖北",7);
        map.put("河北",8);
        map.put("湖南",9);
        map.put("福建",10);
        map.put("上海",11);
        map.put("北京",12);
        map.put("安徽",13);
        map.put("辽宁",14);
        map.put("陕西",15);
        map.put("江西",16);
        map.put("广西",17);
        map.put("重庆",18);
        map.put("天津",19);
        map.put("云南",20);
        map.put("黑龙江",21);
        map.put("内蒙古",22);
        map.put("吉林",23);
        map.put("山西",24);
        map.put("贵州",25);
        map.put("新疆",26);
        map.put("甘肃",27);
        map.put("海南",28);
        map.put("宁夏",29);
        map.put("青海",30);
        map.put("西藏",31);
        return map;
    }

}
