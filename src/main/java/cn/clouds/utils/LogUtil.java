package cn.clouds.utils;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author clouds
 * @version 1.0
 */
public class LogUtil {

    private static final Logger detailLogger = LoggerFactory.getLogger(Constants.LOG_TYPE_DETAIL);

    public static void commonLog(Level level, String info) {
        commonLog(level, info, null, null);
    }

    public static void commonLog(Level level, String info, Boolean begin, Throwable throwable) {
        StackTraceElement[] stes = null;
        StackTraceElement ste0 = null;
        if (throwable != null) {
            stes = throwable.getStackTrace();
            ste0 = stes[0];
        } else {
            stes = Thread.currentThread().getStackTrace();
            ste0 = stes[3];
        }

        StringBuilder sbTemp = new StringBuilder();
        if (begin != null) {
            if (begin) {
                sbTemp.append("[method-start]");
            } else {
                sbTemp.append("[method-end ]");
            }
            if (ste0 != null) {
                sbTemp.append("[")
                        .append(ste0.getClassName()).append(".").append(ste0.getMethodName())
                        .append("]");
            }
            if (!StringUtils.isEmpty(info)) {
                sbTemp.append("[").append(info).append("]");
            }
        } else {
            sbTemp.append("[method-in ");
            if (ste0 != null) {
                sbTemp.append("[")
                        .append(ste0.getClassName()).append(".").append(ste0.getMethodName())
                        .append("]");
            }
            sbTemp.append("[").append(info).append("]");
        }

        switch (level.levelInt) {
            case Level.TRACE_INT:
                detailLogger.trace(sbTemp.toString());
                break;
            case Level.DEBUG_INT:
                detailLogger.debug(sbTemp.toString());
                break;
            case Level.INFO_INT:
                detailLogger.info(sbTemp.toString());
                break;
            case Level.WARN_INT:
                detailLogger.warn(sbTemp.toString());
                break;
            case Level.ERROR_INT:
                detailLogger.error(sbTemp.toString());
                break;
            default:
                detailLogger.info(sbTemp.toString());
                break;
        }
    }

    public static void exceptionLog(Logger logger, Exception exception, String data) {
        StringBuilder sbTemp = new StringBuilder();
        sbTemp.append("{\"").append(Constants.CLASS_METHOD).append("\":\"")
                .append(exception.getStackTrace()[Constants.STACKTRACE_CLASS_INDEX].toString()).append("\",")
                .append("\"").append(Constants.EXCEPTION).append("\":\"")
                .append(exception != null ?
                        exception.getMessage() : exception.toString()).append("\",")
                .append("\"").append(Constants.DATA).append("\":\"").append(data).append("\"}");
        logger.error(sbTemp.toString());
    }
}
