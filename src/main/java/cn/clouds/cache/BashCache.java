package cn.clouds.cache;


import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author clouds
 * @version 1.0
 */
public abstract class BashCache<T> implements Cache<T> {

    public final Map<String, T> cache = Maps.newConcurrentMap();

    public Map<String, T> getCache() {
        return cache;
    }

    public void add(String key, T t) {
        cache.put(key, t);
    }

    public void delete(String key) {
        if (!StringUtils.isEmpty(key)) {
            cache.remove(key);
        }
    }

    public void delAll() {
        cache.clear();
    }

    @Override
    public List<T> getCacheData() {
        return null;
    }

    @Override
    public T getCacheData(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        this.updateCacheDate(key);
        return cache.get(key);
    }

    @Override
    public String getCacheKey(Object data) {
        return null;

    }

    @Override
    public void updateCacheData() {

    }

    @Override
    public void updateCacheDate(String key) {

    }

    @Override
    public String getCacheName() {
        return null;
    }
}
