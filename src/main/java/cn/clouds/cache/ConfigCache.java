package cn.clouds.cache;

import cn.clouds.entity.PropertiesConfig;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
@Service(value = "configCache")
public class ConfigCache extends BashCache<PropertiesConfig> {

    @Override
    public void updateCacheData() {
        List<PropertiesConfig> propertiesConfigs = Lists.newArrayList();
        for (PropertiesConfig p : propertiesConfigs) {
            this.add(p.getKey(), p);
        }
    }

    @Override
    public void updateCacheDate(String key) {
        PropertiesConfig p = new PropertiesConfig();
        this.add(p.getKey(), p);
    }

    @Override
    public String getCacheName() {
        return CacheKey.CONFIG_CACHE.getCacheName();
    }

    @Override
    public String getCacheKey(Object data) {
        PropertiesConfig propertiesConfig = (PropertiesConfig) data;
        return propertiesConfig.getKey();
    }
}
