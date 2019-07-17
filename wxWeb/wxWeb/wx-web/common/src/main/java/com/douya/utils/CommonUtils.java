package com.douya.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lb on 2017/12/5.
 */
public class CommonUtils {
    static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     *  根据租赁开始时间点计算某天后的结束时间点
     * @param startDate
     * @param day
     * @return
     */
    public static Date getEndTime(Date startDate, int day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     *  获取某个小时后的时间点
     * @param hour
     * @return
     * @throws ParseException
     */
    public static Date getAfterTime(Date startDate, int hour) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }


    /**
     *  校验手机号码格式
     * @param phone
     * @return
     */
    public static boolean verify(String phone) {
        if(StringUtils.isBlank(phone)) {
            return false;
        }

        String regexp = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9]|6[0-9])\\d{8}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static String createUUID() {
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String token = uuid.toString();
        // 转换为大写
        token = token.toUpperCase();
        // 替换 “-”变成空格
        token = token.replaceAll("-", "");
        return token;
    }

    /**
     * md5加密方法
     * @param password
     * @return
     */
    public static String md5(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *  生成带时间戳的唯一字符，年月日时分秒 + 随机五位数
     * @return
     */
    public static String crateFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return str + rannum;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));//递归删除目录中的子目录下
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 读取用户在磁盘下的头像,返回web项目的地址
     * @param iconPath 头像的磁盘路径
     */
    public static void readIcon(String iconPath, HttpServletResponse response) {
        //response.setContentType("application/octet-stream;charset=UTF-8");
        try {
            FileInputStream inputStream = new FileInputStream(iconPath);
            int length = inputStream.available(); //获取流长度
            byte[] bytes = new byte[length];
            //将流中的字节缓冲到数组中，返回的这个数组中的字节个数，这个缓冲区没有满的话，则返回真实的字节个数，到未尾时都返回-1
            inputStream.read(bytes);
            inputStream.close();

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            logger.error("系统找不到图片文件：" + iconPath);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 *  获取当前时间
	 * @return
	 */
	public static String getFormatDate() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
    public static String getFormatDateToMinute() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        return format.format(new Date());
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     *  校验是否为纯数字，true为纯数字
     * @param str
     * @return
     */
    public static boolean verifyNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 随机生成指定邀请码
     * @param len
     * @return
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
        }
        return rtnStr;
    }

    /**
     * 生成订单id：业务号（1位）+ 用户id (4位) + 时间（年后两位+月+日 6位）+ 随机数4位
     * @param userId
     * @return
     */
    public static Long createOrderId(Long userId, String type) {
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (9999 - 1000 + 1)) + 1000;// 获取4位随机数
        return Long.valueOf(String.format("%s%s%s%s", type, userId.toString().substring(userId.toString().length() - 4), getFormatDateToMinute(), rannum));
    }

}