package io.github.doquanghop.walletsystem.infrastructure.service;


import java.time.Duration;
import java.util.Optional;

public interface CacheService {
    <T> Optional<T> get(String key);

    <T> void set(String key, T value, Duration ttl);

    <T> void set(String key, T value);

    boolean delete(String key);

    boolean exists(String key);

    boolean lock(String key, Duration timeout);

    void unlock(String key);

    void setBlacklist(String key, Duration ttl);

    boolean isBlacklisted(String key);

}