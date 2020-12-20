package cn.clouds.socket.thread;

import cn.clouds.socket.CloudSocketServer;

/**
 * @author clouds
 * @version 1.0
 */
public class SocketManagerThread extends Thread {
    private boolean shortLink;

    public boolean isShortLink() {
        return shortLink;
    }

    public void setShortLink(boolean shortLink) {
        this.shortLink = shortLink;
    }

    @Override
    public void run() {

    }

    public void readFullMsgMethod(CloudSocketServer cloudSocketServer, String methodName) {
    }

    public void socketClose(CloudSocketServer cloudSocketServer, String methodName) {
    }
}
