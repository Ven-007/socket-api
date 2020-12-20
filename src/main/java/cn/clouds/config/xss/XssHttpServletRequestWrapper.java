package cn.clouds.config.xss;

import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * @author clouds
 * @version 1.0
 */

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            values[i] = xssClean(values[i]);
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> newMap = Maps.newHashMap();
        map.forEach((k, v) -> {
            if (null != k) {
                k = xssClean(k);
            }
            for (int i = 0; i < v.length; i++) {
                v[i] = xssClean(v[i]);
            }
            newMap.put(k, v);
        });
        return newMap;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (null != value) {
            value = xssClean(value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (null != header) {
            header = xssClean(header);
        }
        return header;
    }

    public static String xssClean(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("<", "&lt;");
        value = value.replaceAll(">", "&gt;");
        value = value.replaceAll("'", "&apos;");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
        value = value.replaceAll("(?i)<.*?script:.*?>.*?</.*?>", "");
        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
        return value;

    }
}
