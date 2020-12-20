package cn.clouds.cache;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
public interface Cache<T> {

    /**
     * 获取所有缓存数据
     *
     * @return List
     */
    List<T> getCacheData();

    /**
     * 根据key获取缓存对象
     *
     * @param key
     * @return T
     */
    T getCacheData(String key);

    /**
     * 根据object获取缓存key
     *
     * @param data
     * @return string
     */
    String getCacheKey(Object data);

    /**
     * 更新所用缓存
     */
    void updateCacheData();

    /**
     * 根据key指定更新特定缓存
     *
     * @param key 键
     */
    void updateCacheDate(String key);

    /**
     * 获取当前缓存name
     *
     * @return name
     */
    String getCacheName();
}
