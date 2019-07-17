package com.douya.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * create leiqing
 * 价格算法工具类
 */
public class PriceArithmeticUtil {
    private static final double  PRICE315Z= 25.2;//WF-315Z型号裸机一天租赁单价
    private static final double  PRICE315Z01= 31.2;//WF-315Z型号套餐一 一天租赁单价
    private static final double  PRICE315Z02= 40.2;//WF-315Z型号套餐二一天租赁单价

    private static final double  PRICE500Z= 39.6;//WF-500Z型号裸机一天租赁单价
    private static final double  PRICE500Z01= 51.6;//WF-500Z型号套餐一 一天租赁单价
    private static final double  PRICE500Z02= 68.4;//WF-500Z型号套餐二一天租赁单价

    private static final double  PRICE250W= 27.0;//WF-250W型号裸机一天租赁单价
    private static final double  PRICE250W01= 30.0;//WF-250W型号套餐一 一天租赁单价
    private static final double  PRICE250W02= 36.5;//WF-250W型号套餐二一天租赁单价

    private static final double  PRICE500W= 59.4;//WF-500W型号裸机一天租赁单价
    private static final double  PRICE500W01= 69.4;//WF-500W型号套餐一 一天租赁单价
    private static final double  PRICE500W02= 76.9;//WF-500W型号套餐二一天租赁单价

    private static final double  PRICE500N= 72;//WF-500N型号裸机一天租赁单价
    private static final double  PRICE500N01= 94;//WF-500N型号套餐一 一天租赁单价
    private static final double  PRICE500N02= 114.5;//WF-500N型号套餐二一天租赁单价

    private static final double  PRICE60L= 37.8;//WF-60L型号裸机一天租赁单价
    private static final double  PRICE60L01= 43.8;//WF-60L型号套餐一 一天租赁单价

    private static final double  PRICE120L= 68.4;//WF-120L型号裸机一天租赁单价
    private static final double  PRICE120L01= 78.4;//WF-120L型号套餐一 一天租赁单价

    //型号，租用天数，套餐类型
    public static double Arithmetic(String title,int day,String type){
        double money=0.00;
        if(title.equals("WF-315Z")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE315Z*0.3;
                }else if(day>=30){
                     money=PRICE315Z*0.4;
                }else if(day>=15){
                     money=PRICE315Z*0.6;
                }else if(day>=7){
                     money=PRICE315Z*0.8;
                }else if(day>=1){
                     money=PRICE315Z;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE315Z01*0.3;
                }else if(day>=30){
                     money=PRICE315Z01*0.4;
                }else if(day>=15){
                     money=PRICE315Z01*0.6;
                }else if(day>=7){
                     money=PRICE315Z01*0.8;
                }else if(day>=1){
                     money=PRICE315Z01;
                }
            }else if(type.equals("套餐二")){
                if(day>=60){
                     money=PRICE315Z02*0.3;
                }else if(day>=30){
                     money=PRICE315Z02*0.4;
                }else if(day>=15){
                     money=PRICE315Z02*0.6;
                }else if(day>=7){
                     money=PRICE315Z02*0.8;
                }else if(day>=1){
                     money=PRICE315Z02;
                }
            }
        }else if(title.equals("WF-500Z")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE500Z*0.3;
                }else if(day>=30){
                     money=PRICE500Z*0.4;
                }else if(day>=15){
                     money=PRICE500Z*0.6;
                }else if(day>=7){
                     money=PRICE500Z*0.8;
                }else if(day>=1){
                     money=PRICE500Z;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE500Z01*0.3;
                }else if(day>=30){
                     money=PRICE500Z01*0.4;
                }else if(day>=15){
                     money=PRICE500Z01*0.6;
                }else if(day>=7){
                     money=PRICE500Z01*0.8;
                }else if(day>=1){
                     money=PRICE500Z01;
                }
            }else if(type.equals("套餐二")){
                if(day>=60){
                     money=PRICE500Z02*0.3;
                }else if(day>=30){
                     money=PRICE500Z02*0.4;
                }else if(day>=15){
                     money=PRICE500Z02*0.6;
                }else if(day>=7){
                     money=PRICE500Z02*0.8;
                }else if(day>=1){
                     money=PRICE500Z02;
                }
            }
        }else if(title.equals("WF-250W")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE250W*0.3;
                }else if(day>=30){
                     money=PRICE250W*0.4;
                }else if(day>=15){
                     money=PRICE250W*0.6;
                }else if(day>=7){
                     money=PRICE250W*0.8;
                }else if(day>=1){
                     money=PRICE250W;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE250W01*0.3;
                }else if(day>=30){
                     money=PRICE250W01*0.4;
                }else if(day>=15){
                     money=PRICE250W01*0.6;
                }else if(day>=7){
                     money=PRICE250W01*0.8;
                }else if(day>=1){
                     money=PRICE250W01;
                }
            }else if(type.equals("套餐二")){
                if(day>=60){
                     money=PRICE250W02*0.3;
                }else if(day>=30){
                     money=PRICE250W02*0.4;
                }else if(day>=15){
                     money=PRICE250W02*0.6;
                }else if(day>=7){
                     money=PRICE250W02*0.8;
                }else if(day>=1){
                     money=PRICE250W02;
                }
            }

        }else if(title.equals("WF-500W")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE500W*0.3;
                }else if(day>=30){
                     money=PRICE500W*0.4;
                }else if(day>=15){
                     money=PRICE500W*0.6;
                }else if(day>=7){
                     money=PRICE500W*0.8;
                }else if(day>=1){
                     money=PRICE500W;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE500W01*0.3;
                }else if(day>=30){
                     money=PRICE500W01*0.4;
                }else if(day>=15){
                     money=PRICE500W01*0.6;
                }else if(day>=7){
                     money=PRICE500W01*0.8;
                }else if(day>=1){
                     money=PRICE500W01;
                }
            }else if(type.equals("套餐二")){
                if(day>=60){
                     money=PRICE500W02*0.3;
                }else if(day>=30){
                     money=PRICE500W02*0.4;
                }else if(day>=15){
                     money=PRICE500W02*0.6;
                }else if(day>=7){
                     money=PRICE500W02*0.8;
                }else if(day>=1){
                     money=PRICE500W02;
                }
            }
        }else if(title.equals("WF-500N")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE500N*0.3;
                }else if(day>=30){
                     money=PRICE500N*0.4;
                }else if(day>=15){
                     money=PRICE500N*0.6;
                }else if(day>=7){
                     money=PRICE500N*0.8;
                }else if(day>=1){
                     money=PRICE500N;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE500N01*0.3;
                }else if(day>=30){
                     money=PRICE500N01*0.4;
                }else if(day>=15){
                     money=PRICE500N01*0.6;
                }else if(day>=7){
                     money=PRICE500N01*0.8;
                }else if(day>=1){
                     money=PRICE500N01;
                }
            }else if(type.equals("套餐二")){
                if(day>=60){
                     money=PRICE500N02*0.3;
                }else if(day>=30){
                     money=PRICE500N02*0.4;
                }else if(day>=15){
                     money=PRICE500N02*0.6;
                }else if(day>=7){
                     money=PRICE500N02*0.8;
                }else if(day>=1){
                     money=PRICE500N02;
                }
            }
        }else if(title.equals("WF-60L")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE60L*0.3;
                }else if(day>=30){
                     money=PRICE60L*0.4;
                }else if(day>=15){
                     money=PRICE60L*0.6;
                }else if(day>=7){
                     money=PRICE60L*0.8;
                }else if(day>=1){
                     money=PRICE60L;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE60L01*0.3;
                }else if(day>=30){
                     money=PRICE60L01*0.4;
                }else if(day>=15){
                     money=PRICE60L01*0.6;
                }else if(day>=7){
                     money=PRICE60L01*0.8;
                }else if(day>=1){
                     money=PRICE60L01;
                }
            }
        }else if(title.equals("WF-120L")){
            if(type.equals("裸机")){
                if(day>=60){
                     money=PRICE120L*0.3;
                }else if(day>=30){
                     money=PRICE120L*0.4;
                }else if(day>=15){
                     money=PRICE120L*0.6;
                }else if(day>=7){
                     money=PRICE120L*0.8;
                }else if(day>=1){
                     money=PRICE120L;
                }
            }else if(type.equals("套餐一")){
                if(day>=60){
                     money=PRICE120L01*0.3;
                }else if(day>=30){
                     money=PRICE120L01*0.4;
                }else if(day>=15){
                     money=PRICE120L01*0.6;
                }else if(day>=7){
                     money=PRICE120L01*0.8;
                }else if(day>=1){
                     money=PRICE120L01;
                }
            }
        }
        BigDecimal b   =   new BigDecimal(money);
        money   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return money;
    }

    /**
     * 计算已实名用户押金，未实名不用计算，数据库默认存的是未实名用户押金
     * 型号,套餐类型
     * @return
     */
    public static double depositCount(String title,String type){
        double money=0.00;
        if(title.equals("315Z")){
            if(type.equals("裸机")){
                return money=336;
            }else if(type.equals("套餐一")){
                return money=486;
            }else if(type.equals("套餐二")){
                return money=636;
            }
        }else if(title.equals("500Z")){
            if(type.equals("裸机")){
                return money=528;
            }else if(type.equals("套餐一")){
                return money=828;
            }else if(type.equals("套餐二")){
                return money=1103;
            }
        }else if(title.equals("250W")){
            if(type.equals("裸机")){
                return money=360;
            }else if(type.equals("套餐一")){
                return money=435;
            }else if(type.equals("套餐二")){
                return money=550;
            }
        }else if(title.equals("500W")){
            if(type.equals("裸机")){
                return money=792;
            }else if(type.equals("套餐一")){
                return money=1042;
            }else if(type.equals("套餐二")){
                return money=1142;
            }
        }else if(title.equals("500N")){
            if(type.equals("裸机")){
                return money=960;
            }else if(type.equals("套餐一")){
                return money=1510;
            }else if(type.equals("套餐二")){
                return money=1810;
            }
        }else if(title.equals("60L")){
            if(type.equals("裸机")){
                return money=504;
            }else if(type.equals("套餐一")){
                return money=654;
            }
        }else if(title.equals("120L")){
            if(type.equals("裸机")){
                return money=912;
            }else if(type.equals("套餐一")){
                return money=1162;
            }
        }
        return money;

    }

    public static void main(String args[]){
        System.out.println(Arithmeticd("WF-60L",11,"1"));

    }

    /**
     * 小程序专用
     * @param title 型号名称
     * @param day 租赁天数
     * @param type 套餐名称 0表示裸机 1套餐一 2套餐二
     * @param
     * @return
     */
    public static double Arithmeticd(String title,int day,String type){
        double money=0.00;

        if(title.equals("WF-315Z")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE315Z*0.3;
                }else if(day>=30){
                    money=PRICE315Z*0.4;
                }else if(day>=15){
                    money=PRICE315Z*0.6;
                }else if(day>=7){
                    money=PRICE315Z*0.8;
                }else if(day>=1){
                    money=PRICE315Z;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE315Z01*0.3;
                }else if(day>=30){
                    money=PRICE315Z01*0.4;
                }else if(day>=15){
                    money=PRICE315Z01*0.6;
                }else if(day>=7){
                    money=PRICE315Z01*0.8;
                }else if(day>=1){
                    money=PRICE315Z01;
                }
            }else if(type.equals("2")){
                if(day>=60){
                    money=PRICE315Z02*0.3;
                }else if(day>=30){
                    money=PRICE315Z02*0.4;
                }else if(day>=15){
                    money=PRICE315Z02*0.6;
                }else if(day>=7){
                    money=PRICE315Z02*0.8;
                }else if(day>=1){
                    money=PRICE315Z02;
                }
            }
        }else if(title.equals("WF-500Z")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE500Z*0.3;
                }else if(day>=30){
                    money=PRICE500Z*0.4;
                }else if(day>=15){
                    money=PRICE500Z*0.6;
                }else if(day>=7){
                    money=PRICE500Z*0.8;
                }else if(day>=1){
                    money=PRICE500Z;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE500Z01*0.3;
                }else if(day>=30){
                    money=PRICE500Z01*0.4;
                }else if(day>=15){
                    money=PRICE500Z01*0.6;
                }else if(day>=7){
                    money=PRICE500Z01*0.8;
                }else if(day>=1){
                    money=PRICE500Z01;
                }
            }else if(type.equals("2")){
                if(day>=60){
                    money=PRICE500Z02*0.3;
                }else if(day>=30){
                    money=PRICE500Z02*0.4;
                }else if(day>=15){
                    money=PRICE500Z02*0.6;
                }else if(day>=7){
                    money=PRICE500Z02*0.8;
                }else if(day>=1){
                    money=PRICE500Z02;
                }
            }
        }else if(title.equals("WF-250W")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE250W*0.3;
                }else if(day>=30){
                    money=PRICE250W*0.4;
                }else if(day>=15){
                    money=PRICE250W*0.6;
                }else if(day>=7){
                    money=PRICE250W*0.8;
                }else if(day>=1){
                    money=PRICE250W;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE250W01*0.3;
                }else if(day>=30){
                    money=PRICE250W01*0.4;
                }else if(day>=15){
                    money=PRICE250W01*0.6;
                }else if(day>=7){
                    money=PRICE250W01*0.8;
                }else if(day>=1){
                    money=PRICE250W01;
                }
            }else if(type.equals("2")){
                if(day>=60){
                    money=PRICE250W02*0.3;
                }else if(day>=30){
                    money=PRICE250W02*0.4;
                }else if(day>=15){
                    money=PRICE250W02*0.6;
                }else if(day>=7){
                    money=PRICE250W02*0.8;
                }else if(day>=1){
                    money=PRICE250W02;
                }
            }

        }else if(title.equals("WF-500W")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE500W*0.3;
                }else if(day>=30){
                    money=PRICE500W*0.4;
                }else if(day>=15){
                    money=PRICE500W*0.6;
                }else if(day>=7){
                    money=PRICE500W*0.8;
                }else if(day>=1){
                    money=PRICE500W;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE500W01*0.3;
                }else if(day>=30){
                    money=PRICE500W01*0.4;
                }else if(day>=15){
                    money=PRICE500W01*0.6;
                }else if(day>=7){
                    money=PRICE500W01*0.8;
                }else if(day>=1){
                    money=PRICE500W01;
                }
            }else if(type.equals("2")){
                if(day>=60){
                    money=PRICE500W02*0.3;
                }else if(day>=30){
                    money=PRICE500W02*0.4;
                }else if(day>=15){
                    money=PRICE500W02*0.6;
                }else if(day>=7){
                    money=PRICE500W02*0.8;
                }else if(day>=1){
                    money=PRICE500W02;
                }
            }
        }else if(title.equals("WF-500N")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE500N*0.3;
                }else if(day>=30){
                    money=PRICE500N*0.4;
                }else if(day>=15){
                    money=PRICE500N*0.6;
                }else if(day>=7){
                    money=PRICE500N*0.8;
                }else if(day>=1){
                    money=PRICE500N;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE500N01*0.3;
                }else if(day>=30){
                    money=PRICE500N01*0.4;
                }else if(day>=15){
                    money=PRICE500N01*0.6;
                }else if(day>=7){
                    money=PRICE500N01*0.8;
                }else if(day>=1){
                    money=PRICE500N01;
                }
            }else if(type.equals("2")){
                if(day>=60){
                    money=PRICE500N02*0.3;
                }else if(day>=30){
                    money=PRICE500N02*0.4;
                }else if(day>=15){
                    money=PRICE500N02*0.6;
                }else if(day>=7){
                    money=PRICE500N02*0.8;
                }else if(day>=1){
                    money=PRICE500N02;
                }
            }
        }else if(title.equals("WF-60L")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE60L*0.3;
                }else if(day>=30){
                    money=PRICE60L*0.4;
                }else if(day>=15){
                    money=PRICE60L*0.6;
                }else if(day>=7){
                    money=PRICE60L*0.8;
                }else if(day>=1){
                    money=PRICE60L;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE60L01*0.3;
                }else if(day>=30){
                    money=PRICE60L01*0.4;
                }else if(day>=15){
                    money=PRICE60L01*0.6;
                }else if(day>=7){
                    money=PRICE60L01*0.8;
                }else if(day>=1){
                    money=PRICE60L01;
                }
            }
        }else if(title.equals("WF-120L")){
            if(type.equals("0")){
                if(day>=60){
                    money=PRICE120L*0.3;
                }else if(day>=30){
                    money=PRICE120L*0.4;
                }else if(day>=15){
                    money=PRICE120L*0.6;
                }else if(day>=7){
                    money=PRICE120L*0.8;
                }else if(day>=1){
                    money=PRICE120L;
                }
            }else if(type.equals("1")){
                if(day>=60){
                    money=PRICE120L01*0.3;
                }else if(day>=30){
                    money=PRICE120L01*0.4;
                }else if(day>=15){
                    money=PRICE120L01*0.6;
                }else if(day>=7){
                    money=PRICE120L01*0.8;
                }else if(day>=1){
                    money=PRICE120L01;
                }
            }
        }
        BigDecimal b   =   new BigDecimal(money);
        money   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return money;
    }
}
