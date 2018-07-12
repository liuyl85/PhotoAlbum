package com.yun.album.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private HashOperations<String, String, Object> hashOperations;
    @Resource
    private ValueOperations<String, Object> valueOperations;

    /**
     * 查询Key支持模糊查询
     * @param key 传过来时Key的前后端已经加入了*，或者根据具体处理
     * @return 集合
     */
    public Set<String> keys(String key){
        return redisTemplate.keys(key);
    }

    /**
     * 重命名Key
     * @param key 旧Key
     * @param newKey 新key
     */
    public void renameKey(String key, String newKey){
        redisTemplate.rename(key, newKey);
    }

    /**
     * 添加信息(字符串方式)
     * @param key 键
     * @param obj 值
     */
    public void set(String key, Object obj){
        valueOperations.set(key, obj);
    }

    /**
     * 添加信息(字符串方式)
     * @param key 键
     * @param obj 值
     * @param expireTime 到期时间(秒)
     */
    public void set(String key, Object obj, long expireTime){
        valueOperations.set(key, obj, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 获取值(字符串方式)
     * @param key 键
     * @return 值
     */
    public <T> T get(String key){
        return (T)valueOperations.get(key);
    }

    /**
     * 删除Key及对应的值
     * @param key 键
     */
    public void delete(String key){
        valueOperations.getOperations().delete(key);
    }

    /**
     * 添加单个对象
     * @param key 键
     * @param filed 字段名
     * @param domain 值
     */
    public void hset(String key, String filed, Object domain){
        hashOperations.put(key, filed, domain);
    }

    public void hset(String key, String filed, Object domain, long expireTime){
        hashOperations.put(key, filed, domain);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 添加Map
     * @param key 键
     * @param map Map集合
     */
    public void hset(String key, Map<String, Object> map){
        hashOperations.putAll(key, map);
    }

    /**
     * 根据Key和Field查询值
     * @param key 键
     * @param field 字段名
     * @return 值
     */
    public Object hget(String key, String field) {
        return hashOperations.get(key, field);
    }

    /**
     * 根据Key查询对应的值
     * @param key 键
     * @return 结果
     */
    public Object hget(String key) {
        return hashOperations.entries(key);
    }

    /**
     * 删除Key及对应的值
     * @param key 键
     */
    public void deleteKey(String key) {
        hashOperations.getOperations().delete(key);
    }

    /**
     * 根据Key和Field判断是否有值
     * @param key 键
     * @param field 字段名
     * @return 结果
     */
    public Boolean hasKey(String key, String field) {
        return hashOperations.hasKey(key,field);
    }

    /**
     * 判断key下是否有值
     * @param key 键
     * @return 结果
     */
    public Boolean hasKey(String key) {
        return hashOperations.getOperations().hasKey(key);
    }

}
