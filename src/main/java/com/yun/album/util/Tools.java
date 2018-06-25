package com.yun.album.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.album.exception.MD5DigestException;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Tools {
	/** 时间格式化(yyyy-MM-dd HH:mm:ss) */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
	
	/**
	 * 利用MD5进行加密
	 * @param str 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String md5Digest(String str) throws MD5DigestException {
		try {
			String msg = DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
			return msg;
		} catch (Exception e) {
			throw new MD5DigestException(e);
		}
	}
	
	/**
	 * 计算文件的MD5摘要
	 * @param file 待计算的文件
	 * @return 文件的MD5摘要
	 */
	public static String md5Digest(File file) throws MD5DigestException {
		try (FileInputStream in = new FileInputStream(file)) {
			String md5 = DigestUtils.md5DigestAsHex(in);
			return md5;
		} catch (Exception e) {
			throw new MD5DigestException(e);
		}
	}
	
	/**
	 * 时间格式化(yyyy-MM-dd HH:mm:ss)
	 * @param temporal 时间对象
	 * @return 时间字符串
	 */
	public static String dateTimeFormat(TemporalAccessor temporal) {
		return formatter.format(temporal);
	}

	/**
	 * 将对象转成Json格式的字符串
	 * @param obj 对象
	 * @return Json格式的字符串
	 * @throws JsonProcessingException 转换Json格式字符串异常
	 */
	public static String toJsonString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(obj);
		return json;
	}

	/**
	 * 将Json格式的字符串转成Map对象
	 * @param jsonString Json格式字符串成
	 * @return Map
	 * @throws IOException Json格式的字符串转换Map对象异常
	 */
	public static Map<String, Object> stringToJson(String jsonString) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);
		HashMap<String, Object> map = mapper.readValue(jsonString, javaType);
		return map;
	}

	/**
	 * 将Json格式的字符串转成Java对象
	 * @param jsonString Json格式字符串成
	 * @return Java对象
	 * @throws IOException Json格式的字符串转换Java对象异常
	 */
	public static <T> T stringToObject(String jsonString, Class<T> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, clazz);
	}
	
}
