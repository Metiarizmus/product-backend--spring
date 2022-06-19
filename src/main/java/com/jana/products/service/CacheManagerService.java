package com.jana.products.service;

import com.jana.products.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CacheManagerService<E> {

    private final RedisService redisService;

    public List<E> cachedList(String key, List<E> list) {

        if (!redisService.hasKey(key)) {
            redisService.lPushAll(key, list);
            return list;
        }
        List<E> cachedLogs = (List<E>) redisService.lRange(key, 0, redisService.lSize(key));
        return cachedLogs;
    }

    public E cachedObject(String key, E obj) {
        if (!redisService.hasKey(key)) {
            redisService.lPush(key, obj);
            return obj;
        }
        E cachedObject = (E) redisService.lRange(key, 0, redisService.lSize(key));
        return cachedObject;
    }

    public Optional<E> getByKey(String key) {
        return (Optional<E>) redisService.get(key);
    }


    public void deleteFromCache(String key) {
        redisService.del(key);
    }

}
