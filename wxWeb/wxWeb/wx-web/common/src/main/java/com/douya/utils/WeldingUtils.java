package com.douya.utils;

import com.douya.exception.RestException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 焊机工具类
 */
@Transactional(readOnly = false)
public class WeldingUtils {

	//起始符
	private static String initiating = "*";

	//结束符
	private static String endCharacter = "#";

	//分隔符
	private static String regx = "&";

	private static Socket socket;

	//补位符
	private static String placeholder = "~";

	public static void main(String[] args) {
		//String str = "*WW_ENGI0000001A&~~~~~~~~~~~~~~~&QUERY~~&V~~~~~~~~~~&~~~~~~~~~~~&~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~&0.0&1896#";
	String str = "*WW_ENGI0000001A&~~~~~~~~~~~~~~~&QUERY~~&V~~~~~~~~~~&~~~~~~~~~~~&+CENG: 0,\"460,00,25dc,3313,07,54\"~~~~~~~~~~~~~~~~~~~~&0.0&CEE7#";
		// String str = "*WW_ENGI0000001A&~~~~~~~~~~~~~~~&QUERY~~&V~~~~~~~~~~&~~~~~~~~~~~&+CENG: 0,\"460,00,25dc,3313,07,52\"~~~~~~~~~~~~~~~~~~~~&0.0&00E6#";

		Map map = splitStr(str);
		System.out.println(map.get("results"));
		System.out.println(map.get("message"));

	}

	/**
	 * 分割得到字符串数组
	 */
	public static Map<String, Object> splitStr(String string) {
		Map<String, Object> map = new HashedMap();
		//去掉起始符号
		String str = string.substring(1, string.length() - 1);

		System.out.println("接收分割数据长度为:" + string.getBytes().length);

		if (string.length() != 128) {
			map.put("message", "数据长度错误");
			return map;
		}

		//按照分隔符进行字符串分割
		String strs[] = str.split(regx);

		// 如果数据的长度不为6，则返回(协议长度)
		//if (strs.length != 6) {
		if (strs.length !=8) {
			map.put("message", "切割数组长度不正确!");
			return map;
		}

		//获取到需要CRC校验的字符串数据
		String res = string.substring(0, 123);

		//获取到数据中的CRC校验码
		String crc = string.substring(123, 127);
		/**
		 * 进行CRC校验
		 */
		//根据数据获取CRC校验码
		String crcResult = getCRC(res);

		//校验码进行校验
		if (!crcResult.equals(crc)) {
			map.put("message", "CRC校验不一致!");
			return map;
		}

		/**
		 * 对数据的补位符进行清除，获取到最终数据
		 */
		String results[] = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].length() <= 0) {
				map.put("message", "数据切割后无数据获取!!");
				return map;
			} else {
				if (i == 3 && strs[i].contains("V")) {
					//V代表经纬度无效
					strs[i] = strs[i].replace("V", "");
				}
				String temp[] = strs[i].split(placeholder);
				if (temp.length <= 0) {
					if (i == 1) {
						/**
						 * 时间戳生成
						 */
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
						results[i] = sdf.format(new Date());
					} else if (i == 3) {
						/**
						 * 获取辅助定位信息
						 */
						System.out.println("============暂无经纬度信息,获取辅助定位信息==============");
						//1.切割获取到辅助定位字符串,调用函数获取到经纬度
						Map<String, String> logMap = Cell2GPSUtil.getCoordinates(string.substring(65, string.length() - 5).split("~")[0]);
						//if (null == logMap)
						if (logMap!=null){
							//2.赋值 longitude latitude
							results[3] = logMap.get("latitude");
						results[4] = logMap.get("longitude");}
					} else if (i == 4) {
						//null
					}else if (i == 5) {
						//null
					}else {
						map.put("message", "数据切割后无数据获取!!");
						return map;
					}
				} else {
					String t = temp[0];
					results[i] = t;
					if (i == 1) {
						//时间戳进行处理_
						String[] s = t.split("_");
						results[i] = s[0] + s[1];
					}
					//对焊机状态进行转换成数据库状态
					if (i == 2) {
						String status = t;
						if (status.equals("ACT")) {
							//开机指令(3.使用中)
							status = "3";
						} else if (status.equals("NG")) {
							//设备状态异常指令(4.设备不可用)
							status = "4";
						} else if (status.equals("UNACT")) {
							//设备未通电(1.未激活)
							status = "1";
						}  else if (status.equals("QUERY")) {
							//设备查询
							//null
						}else {
							map.put("message", "错误的状态指令!");
							return map;
						}
						results[i] = status;
					}
				}
			}
		}
		map.put("message", "SUCCESS");
		map.put("results", results);
		return map;
	}

	/**
	 * 发送指令到嵌入式
	 */
	public static void sendInstructions(String weldingNum, String stat, String longitude, String latitude,String voltage, BufferedOutputStream outputStream) {
		if (stat.equals("0")) {
			//未激活(关机)
			stat = "UNACT";
		} else if (stat.equals("1")) {
			//已激活(开机)
			stat = "ACT";
		} else {
			throw new RestException("错误的状态指令,请重新输入!!");
		}
		try {
			//获取拼接数据字符串
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String str = getSendString(weldingNum, sdf.format(new Date()), stat, longitude, latitude,voltage);
			System.out.println("发送到客户端的数据为:" + str + ",该发送数据字节长度为:" + str.getBytes().length);
			//发送到客户端
			outputStream.write(str.getBytes());
			outputStream.flush();
			System.out.println("==================发送成功==================");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拼接发送字符串
	 */
	public static String getSendString(String weldingId, String timestamp, String stat, String longitude, String latitude,String voltage) {
		/**
		 * 根据字符串长度判断是否达到协议长度
		 * 若不，则循环添加补位符
		 */
		if (weldingId.length() < 15) {
			int length = weldingId.length();
			for (int i = 0; i < 15 - length; i++) {
				weldingId += placeholder;
			}
		}
		if (timestamp.length() < 15) {
			int length = timestamp.length();
			for (int i = 0; i < 15 - length; i++) {
				timestamp += placeholder;
			}
		}
		if (stat.length() < 7) {
			int length = stat.length();
			for (int i = 0; i < 7 - length; i++) {
				stat += placeholder;
			}
		}
		if (longitude.length() < 11) {
			int length = longitude.length();
			for (int i = 0; i < 11 - length; i++) {
				longitude += placeholder;
			}
		}
		if (latitude.length() < 11) {
			int length = latitude.length();
			for (int i = 0; i < 11 - length; i++) {
				latitude += placeholder;
			}
		}
		if (voltage.length() < 3) {
			int length = voltage.length();
			for (int i = 0; i < 3 - length; i++) {
				voltage += placeholder;
			}
		}
		String str = initiating + weldingId + regx + timestamp + regx + stat + regx + longitude + regx + latitude + regx;
		String t = "";
		//校验码之前补位58位的占位符
		for (int i = 0; i < 53; i++) {
			t += "~";
		}
		str += t;
		//生成校验码
		String b=regx+voltage+regx;
		str+=b;
		String crc = getCRC(str);
		String s =crc + endCharacter;
		str += s;
		return str;
	}


	/**
	 * 获取CRC校验码
	 *
	 * @param str 需要校验的字符串
	 * @return
	 */
	public static String getCRC(String str) {

		byte[] values = str.getBytes();

		long CRC = 0xffffL;
		for (int crc : values) {

			CRC = (CRC / 256) * 256L + (CRC % 256L) ^ crc;
			for (int J = 0; J <= 7; J++) {
				// 2.把CRC寄存器的内容右移一位（朝低位）用0填补最高位，并检查最低位；
				// 3.如果最低位为0：重复第3步（再次右移一位）；
				// 如果最低位为1：CRC寄存器与多项式A001（1010 0000 0000
				// 0001）进行异或；
				// 4.重复步骤3和4，直到右移8次，这样整个8位数据全部进行了处理；
				long d0 = 0;
				d0 = CRC & 1L;
				CRC = CRC / 2;
				if (d0 == 1)
					CRC = CRC ^ 0xa001L;
			} // 5.重复步骤2到步骤5，进行通讯信息帧下一字节的处理；
			// 6.最后得到的CRC寄存器内容即为：CRC码。

		}
		//解决Java输出二进制时略去高位零的问题

		/**
		 * 转换成16进制
		 */
		/**
		 * 校验码进行补位
		 */
		String st = Integer.toHexString((int) CRC).toUpperCase();
		String message = "0";
		if (st.length() < 4) {
              message+=st;
			message = "0"+message;
		} else {
			message = st;
		}
		return message;
	}


	/**
	 * ascii转换为string
	 *
	 * @param i
	 * @return
	 */
	public static String AToString(int i) {
		return Character.toString((char) i);
	}

}