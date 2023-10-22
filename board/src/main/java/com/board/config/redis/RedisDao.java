package com.board.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * redisTemplate를 직접 사용하지 않고
 * RedisDao를 통해 사용한다.
 * - key-Value 쌍으로 데이터를 입력, 조회, 삭제한다.
 */
@Component
public class RedisDao {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisDao(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 데이터 입력
     * @param key 키
     * @param data 값
     */
    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    /**
     * 데이터 입력
     * @param key 키
     * @param data 값
     * @param duration 유효기간
     */
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    /**
     * 데이터 조회
     * @param key
     * @return 키에 대한 값
     */
    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    /**
     * 데이터 삭제
     * @param key
     */
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

}
