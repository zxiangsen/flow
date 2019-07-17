package com.douya.servlet;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Client {
	private String serverIp = "127.0.0.1";
	private int port =9876;

	private Socket socket;
	private volatile boolean running = false;

	private ObjectOutputStream oos;

	private ObjectInputStream ois;

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Client client = new Client();
		client.start();
	}

	public void start() throws UnknownHostException, IOException {
		// 已经运行
		socket = new Socket(serverIp, port);
		System.out.println("本地端口：" + socket.getLocalPort());
		//socket.setKeepAlive(true);
		new Thread(new KeepAliveWatchDog()).start();
		//new Thread(new ReceiveWatchDog()).start();
	}

	public void sendObject(Object obj) throws IOException {
		if (oos == null){
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		oos.writeObject(obj);
		oos.flush();
	}

	public void stop() {
		if (running)
			running = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				socket = null;
			}
		}
		System.out.println("关闭客户端");
	}

	class KeepAliveWatchDog implements Runnable {

		@Override
		public void run() {
			OutputStream outputStream = null;
			PrintWriter writer = null;
			writer = new PrintWriter(outputStream, true);
			try {
				outputStream = socket.getOutputStream();
				String str = "33 00 30 31 32 33 34 35 00 32 30 31 37 31 32 32 33 32 31 34 36 32 34 00 4F 50 45 4E 00 53 2C 32 39 35 39 2E 39 39 32 35 53 31 32 30 30 30 2E 30 30 39 30 45 00";
				writer.println(str);
				writer.flush();
				/**
				 * 作用是通知服务器客户端已经写完了，此时reader.readLine()返回-1，因为服务器不知道客户端什么时候写完，会一直在阻塞的等客户端写完，
				 * 此时服务器读取的状态都会是-1，也不会去阻塞等待，此时服务器会因为while (true)无限循环读取null的值
				 */
				socket.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ReceiveWatchDog implements Runnable {
		private long checkDelay = 100;
		// 这个时间要比心跳包发送间隔长一点，
		// 否则可能导致没有获取到服务器端的响应而结束
		long keepAliveDelay = 11000;
		private long lastReceiveTime = System.currentTimeMillis();

		@Override
		public void run() {
			while (running) {
				if (System.currentTimeMillis() - lastReceiveTime > keepAliveDelay) {
					Client.this.stop();
				} else {
					try {
						InputStream in = socket.getInputStream();
						if (in.available() > 0) {
							if (ois == null){
								ois = new ObjectInputStream(in);
							}
							lastReceiveTime = System.currentTimeMillis();
							System.out.println("接收服务器端的信息来自"+socket.getPort()+"端口：");
							System.out.println("读取服务端接受的数据为:" + ois.readObject());
						} else {
							Thread.sleep(checkDelay);
						}
					} catch (Exception e) {
						e.printStackTrace();
						Client.this.stop();
					}
				}
			}
		}
	}
}
