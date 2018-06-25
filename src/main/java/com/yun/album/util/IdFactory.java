package com.yun.album.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * id生成器<br>
 * id生成规则（年+月+日+时+分+秒+服务器编号+流水号）<br>
 * 年（占用14位）<br>月（占用4位）<br>日（占用5位）<br>时（占用5位）<br>分（占用6位）<br>秒（占用6位）<br>服务器编号（占用10位）<br>流水号（占用13位）
 */
public class IdFactory {
	public static final IdFactory instance = new IdFactory();
	private final Logger logger = LoggerFactory.getLogger(IdFactory.class);
	/** 最大服务器编号 */
	private final long SERVER_MAX_ID = 1023;
	/** 一秒钟内最多产生Id数 */
	private final int COUNT = 8191;
	/** 服务器编号 */
	private long serverId = 0;
	/** 产生Id的时间 */
	private LocalDateTime dateTime = LocalDateTime.now();
	/** 相同时间内产生Id流水号 */
	private int nowCount = 0;
	
	private IdFactory() {}
	
	/**
	 * 初始化服务器编号
	 * @param serverId 服务器编号
	 */
	public void init(int serverId) throws Exception {
		if(serverId > SERVER_MAX_ID){
			throw new Exception("server id is too big. max id=" + SERVER_MAX_ID);
		}

		if(this.serverId == 0){
			this.serverId = serverId;
		}
	}
	
	/**
	 * 比较两个时间
	 * @param time1 时间1
	 * @param time2 时间2
	 * @return 当返回值等于0时time1 = time2；当返回值小于0时time1 < time2；当返回值大于0时time1 > time2
	 */
	private int compareDateTime(LocalDateTime time1, LocalDateTime time2) {
		int cmp = Integer.compare(time1.getYear(), time2.getYear());
        if(cmp == 0){
            cmp = Integer.compare(time1.getMonthValue(), time2.getMonthValue());
            if(cmp == 0){
                cmp = Integer.compare(time1.getDayOfMonth(), time2.getDayOfMonth());
            }
        }
        
        if(cmp == 0){
        	cmp = Integer.compare(time1.getHour(), time2.getHour());
            if(cmp == 0){
                cmp = Integer.compare(time1.getMinute(), time2.getMinute());
                if (cmp == 0) {
                    cmp = Integer.compare(time1.getSecond(), time2.getSecond());
                }
            }
        }
        return cmp;
    }
	
	/**
	 * 产生一个Id
	 * @return id
	 * @throws Exception 产生Id失败
	 */
	public synchronized long createId() throws Exception {
		if(serverId <= 0) {
			throw new Exception("server id is no set.");
		}
		
		LocalDateTime now = LocalDateTime.now();
		if(compareDateTime(dateTime, now) != 0){
			dateTime = now;
			nowCount = 0;
		}
		
		++nowCount;
		if(nowCount > COUNT){
			throw new Exception("id factory, flow number upper limit of unit time, one second max flow number 511.");
		}
		
		long year = dateTime.getYear();
		long month = dateTime.getMonthValue();
		long day = dateTime.getDayOfMonth();
		long hour = dateTime.getHour();
		long minute = dateTime.getMinute();
		long second = dateTime.getSecond();
		
		long id = year << 49;
		id |= month << 45;
		id |= day << 40;
		id |= hour << 35;
		id |= minute << 29;
		id |= second << 23;
		id |= serverId << 13;
		id |= nowCount;
		return id;
	}

	/**
	 * 产生一个新Id，如果失败将在1秒钟后重试，如果依旧失败将返回Null
	 * @return id
	 */
	public Long createNewId() {
		try {
			return IdFactory.instance.createId();
		} catch (Exception e) {
			logger.error("create new id error.", e);
			try {
				Thread.sleep(1000);
				return IdFactory.instance.createId();
			} catch (Exception e1) {
				logger.error("thread sleep error.", e1);
				return null;
			}
		}
	}
	
}
