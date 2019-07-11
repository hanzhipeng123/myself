package com.example.demo.PureNet;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络访问工具类
 * 
 * @author silk
 *
 */
public class PureNetUtil {
	/**
	 * get方法直接调用post方法
	 * 
	 * @param url 网络地址
	 * @return 返回网络数据
	 */
	public static String get(String url) {
		return post(url, null);
	}

	/**
	 * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
	 * 
	 * @param url   网络地址
	 * @param param 请求参数键值对
	 * @return 返回读取数据
	 */
	public static String post(String url, Map<String, String> param) {
		HttpURLConnection conn = null;


		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
            String checkSum = getCheckSum("4028a8a06a1e9ce8016a1e9ce8ec0000", "985e58d778fed4c671431c16181e13bf", String.valueOf(System.currentTimeMillis()/1000));
            conn.setRequestProperty("AppKey", "cnbot_qsjy201904");
			conn.setRequestProperty("Nonce", "985e58d778fed4c671431c16181e13bf");
			conn.setRequestProperty("CurTime", String.valueOf(System.currentTimeMillis()/1000));
			conn.setRequestProperty("CheckSum", checkSum);
            conn.setRequestProperty("Content-Type","application/json");
			StringBuffer sb = null;
			if (param != null) {// 如果请求参数不为空
				sb = new StringBuffer();
				/*
				 * A URL connection can be used for input and/or output. Set the DoOutput flag
				 * to true if you intend to use the URL connection for output, false if not. The
				 * default is false.
				 */
				// 默认为false,post方法需要写入参数,设定true
				conn.setDoOutput(true);
				// 设定post方法,默认get
				conn.setRequestMethod("POST");
				// 获得输出流
				OutputStream out = conn.getOutputStream();
				// 对输出流封装成高级输出流
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
				// 将参数封装成键值对的形式
				/*for (Map.Entry<String, String> s : param.entrySet()) {
					sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
				}*/
                String s = JSON.toJSONString(param);

                // 将参数通过输出流写入
                writer.write(s);
				writer.close();// 一定要关闭,不然可能出现参数不全的错误
				sb = null;
			}
			conn.connect();// 建立连接
			sb = new StringBuffer();
			// 获取连接状态码
			int recode = conn.getResponseCode();
			BufferedReader reader = null;
			if (recode == 200) {
				// Returns an input stream that reads from this open connection
				// 从连接中获取输入流
				InputStream in = conn.getInputStream();
				// 对输入流进行封装
				reader = new BufferedReader(new InputStreamReader(in));
				String str = null;
				sb = new StringBuffer();
				// 从输入流中读取数据
				while ((str = reader.readLine()) != null) {
					sb.append(str).append(System.getProperty("line.separator"));
				}
				// 关闭输入流
				reader.close();
				if (sb.toString().length() == 0) {
					return null;
				}
				return sb.toString().substring(0,
						sb.toString().length() - System.getProperty("line.separator").length());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null)// 关闭连接
				conn.disconnect();
		}
		return null;
	}
	public static String getCheckSum(String appSecret, String nonce, String curTime) {
		return encode("sha1", appSecret + nonce + curTime);
	}
	// 计算并获取 md5值
	public static String getMD5(String requestBody) {
		return encode("md5", requestBody);
	}
	public  Map<String, String> makePostMap( ) {
		// 鉴权参数

		Map<String, String> param = new HashMap<String, String>(8);
		// param.put("AppKey", AppKey);
		// param.put("Nonce", nonce);
		// param.put("CurTime", curTime);
		// param.put("CheckSum", checkSum);

		// 数据参数
		param.put("opType",  "");
		param.put("studentNo",  "");
		param.put("studentName","");
		param.put("deptNo", "");
		param.put("deptName", "");

		param.put("gender", "F");
		param.put("birthday", "");

		return param;
	}
	private static String encode(String algorithm, String value) {



		if (value == null) {
			return null;
		}
		try {
			MessageDigest messageDigest
					= MessageDigest.getInstance(algorithm);
			messageDigest.update(value.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};
}
