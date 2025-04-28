package io.github.doquanghop.walletsystem.infrastructure.service.implement;

import io.github.doquanghop.walletsystem.infrastructure.service.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? Optional.of((T) value) : Optional.empty();
    }

    @Override
    public <T> void set(String key, T value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public boolean lock(String key, Duration timeout) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key + ":lock", "locked", timeout);
        return success != null && success;
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key + ":lock");
    }

    @Override
    public void setBlacklist(String key, Duration ttl) {
        String blacklistKey = key + ":blacklist";
        redisTemplate.opsForValue().set(blacklistKey, "true", ttl);
    }

    @Override
    public boolean isBlacklisted(String key) {
        String blacklistKey = key + ":blacklist";
        return Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey));
    }
}