package com.douya.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by lb on 2017/12/23.
 */
public class OSSUtils {
	//深圳的endpoint
	//private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
	//private static String accessKeyId = "LTAIiyAM1ss5pzux";
	//private static String accessKeySecret = "bNF3eFhmhEmfmxRgCOopMFjoJhYASy";

	//上传bucket的名
	//private static String bucketName = "douya-welding";

	//返回的图片全路径
	//private static String path = "http://douya-welding.oss-cn-shenzhen.aliyuncs.com/";
	//文件夹名
	//private static String childName = "user-icon/";

	/**
	 * 上传
	 * @param file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String putObject(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String childName, File file, String fileName) throws IOException {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			System.out.println("准备上传OSS图片============================");
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.length());
			// 上传文件
			InputStream input = new FileInputStream(file);
			ossClient.putObject(bucketName, childName + fileName, input, objectMeta);

			return childName + fileName;
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		} finally {
			ossClient.shutdown();
		}
		return null;
	}

	/**
	 * 删除
	 * @param fileName
	 */
	public static void deleteObject(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String fileName) {
		// 创建OSSClient实例
		//根据com/切割来删除文件夹下的文件名，暂未实现根据全路径删除功能
		//String str = "http://douya-welding.oss-cn-shenzhen.aliyuncs.com/User/20180102023504138-31.jpg";
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.deleteObject(bucketName, fileName.split("com/")[1]);
		ossClient.shutdown();
	}

}