package com.douya.utils;

/**
 * Created by HASEE on 2018/1/10.
 */
public class DistanceUtils {


	/**
	 * 计算两个经纬度之间的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double caculateDistance(String lat1, String lng1,
										   String lat2, String lng2) {
		try {
			double lat1D = Double.parseDouble(lat1);
			double lng1D = Double.parseDouble(lng1);
			double lat2D = Double.parseDouble(lat2);
			double lng2D = Double.parseDouble(lng2);
			return caculateDistance(lat1D, lng1D, lat2D, lng2D);
		} catch (Exception ex) {
			return 99999.9;
		}
	}

	/**
	 * 计算两个经纬度之间的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	private static double caculateDistance(double lat1, double lng1,
										   double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10;
		// 转成公里
		s = s / 1000;
		// 取小数点后两位
		s = Double.parseDouble(String.format("%.2f", s));
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 地球半径：6378.137KM
	 */
	private final static double EARTH_RADIUS = 6378.137;

	/**
	 * 根据距离排序
	 *//*
	private static class DistanceComparator implements Comparator<HospitalVO> {

		public int compare(HospitalVO o1, HospitalVO o2) {
			if(o1.getDistance() > o2.getDistance()) {
				return 1;
			} else if(o1.getDistance() < o2.getDistance()){
				return -1;
			} else {
				return o1.getHospitalName().compareTo(o2.getHospitalName());
			}
		}
	}*/
	/**
	 * 通过经纬度获取距离(单位：米)
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2,
									 double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s*1000;
		return s;
	}

	public static void main(String[] args) {
		System.out.println(caculateDistance("20.9376600000","112.3404200000","21.9376600000","111.3404200000"));
	}

}