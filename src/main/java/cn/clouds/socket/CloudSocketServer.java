package cn.clouds.socket;

import cn.clouds.socket.thread.SocketManagerThread;
import cn.clouds.socket.thread.SocketServerThread;
import cn.clouds.socket.thread.ThreadPoolService;

/**
 * @author clouds
 * @version 1.0
 */
public class CloudSocketServer {

    private SocketServerThread socketServerThread;

    private SocketManagerThread socketManagerThread;

    private ThreadPoolService threadPoolService;

    private SocketRecognitionManage socketRecognitionManage;

    public void init(String ip, int port, SocketRecognitionManage socketRecognitionManage) {
        this.socketRecognitionManage = socketRecognitionManage;
        socketManagerThread = new SocketManagerThread();
        socketManagerThread.setName("CLOUD-" + this.hashCode());
        socketManagerThread.setShortLink(true);
        socketManagerThread.readFullMsgMethod(this, "methodName");
        socketManagerThread.socketClose(this, "methodName");
        socketManagerThread.start();

        socketServerThread = new SocketServerThread();
        socketServerThread.setName("CLOUD-" + this.hashCode());
        socketServerThread.setListenerAddr(ip, port);
        socketServerThread.acceptMethod(this, "methodName");
        socketServerThread.socketClose(this, "methodName");
        socketServerThread.start();

        threadPoolService = new ThreadPoolService();
        threadPoolService.init();
    }
}
