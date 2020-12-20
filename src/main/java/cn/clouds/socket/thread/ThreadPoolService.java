package cn.clouds.socket.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author clouds
 * @version 1.0
 */
@Service
public class ThreadPoolService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        logger.info("ExecutorService init start");
        executorService = Executors.newCachedThreadPool();
        logger.info("ExecutorService init end");

    }

    public void execute(Runnable runnable) {
        try {
            executorService.execute(runnable);
        } catch (Exception e) {
            logger.error("ExecutorService execute error" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        if (null != executorService) {
            executorService.shutdown();
        }
    }

}
