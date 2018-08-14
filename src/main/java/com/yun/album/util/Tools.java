package com.yun.album.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.album.exception.MD5DigestException;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
			return DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
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
			return DigestUtils.md5DigestAsHex(in);
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
	 * 时间字符串转化为时间对象
	 * @param time 时间字符串(格式：yyyy-MM-dd HH:mm:ss)
	 * @return 时间对象
	 */
	public static LocalDateTime parseStringToDateTime(String time) {
		return LocalDateTime.parse(time, formatter);
	}

	/**
	 * 将对象转成Json格式的字符串
	 * @param obj 对象
	 * @return Json格式的字符串
	 * @throws JsonProcessingException 转换Json格式字符串异常
	 */
	public static String toJsonString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
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
		return mapper.readValue(jsonString, javaType);
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

	/**
	 * 产生一个随机数字符串
	 * @param numberSize 随机数长度
	 * @return 随机数字符串
	 */
	public static String getRandomNum(int numberSize) {
		StringBuilder temp = new StringBuilder();
		for(int i=numberSize; i>0; --i){
			temp.append((int)(Math.random() * 10));
		}
		return temp.toString();
	}

	/**
	 * 获取UUID
	 * @return UUID字符串
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
