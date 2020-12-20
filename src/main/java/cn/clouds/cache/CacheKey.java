package cn.clouds.cache;

import cn.clouds.context.SpringContextHolder;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author clouds
 * @version 1.0
 */
public enum CacheKey {
    /**
     * 配置缓存
     */
    CONFIG_CACHE("configCache");

    private final String cacheName;

    CacheKey(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void refreshCacheData(String key) {
        Cache<?> cache = SpringContextHolder.getBean(key);
        if (StringUtils.isEmpty(key)) {
            cache.updateCacheData();
        }
        cache.updateCacheDate(key);
    }

    public String getCacheKey(Object data) {
        Cache<?> cache = SpringContextHolder.getBean(cacheName);
        return cache.getCacheKey(data);
    }

    public <T> T getCacheDataByKey(String key) {
        Cache<T> cache = SpringContextHolder.getBean(cacheName);
        return cache.getCacheData(key);
    }

    public Cache<?> getCache() {
        try {
            return SpringContextHolder.getBean(cacheName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CacheKey from(String cacheName) {
        if (StringUtils.isEmpty(cacheName)) {
            return null;
        }
        for (CacheKey cacheKey : values()) {
            if (Objects.equals(cacheKey.getCacheName(), cacheName)) {
                return cacheKey;
            }
        }
        return null;
    }
}
