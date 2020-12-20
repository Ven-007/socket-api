package cn.clouds.socket;

import ch.qos.logback.classic.Level;
import cn.clouds.socket.thread.ThreadPoolService;
import cn.clouds.utils.LogUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author clouds
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StartSocketServerListener implements ApplicationContextAware {

    @NonNull
    private ThreadPoolService threadPoolService;

    private CloudSocketServer socketServer;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 开启socket server监听入口
        initSocketServer(applicationContext);
        // TODO 是否需要设置缓存属性
    }

    private void initSocketServer(ApplicationContext applicationContext) {
        SocketRecognitionManage socketRecognitionManage = (SocketRecognitionManage) applicationContext.getBean("socketRecognitionManage");
        this.socketServer = new CloudSocketServer();
        try {
            socketServer.init("", 8885, socketRecognitionManage);
            LogUtil.commonLog(Level.INFO, "SocketServerListener start success");
        } catch (Exception e) {
            LogUtil.commonLog(Level.ERROR, "SocketServerListener start fail", false, e);
        }
    }
}
