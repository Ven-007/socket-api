package cn.clouds.webSocket;

import ch.qos.logback.classic.Level;
import cn.clouds.utils.LogUtil;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

/**
 * @author clouds
 * @version 1.0
 */
@Component
@ServerEndpoint("/socketApi/{id}")
public class WebSocketManager {
    /**
     * 用来记录房间的人数
     */
    private static AtomicInteger onlinePersons = new AtomicInteger(0);

    /**
     * 用来记录房间及人数
     */
    private static Map<String, Set<Session>> roomMap = new ConcurrentHashMap<String, Set<Session>>(8);

    @OnOpen
    public void open(@PathParam("id") String id, Session session) throws IOException {
        Set<Session> set = roomMap.get(id);
        // 如果是新的房间，则创建一个映射，如果房间已存在，则把用户放进去
        if (set == null) {
            set = new CopyOnWriteArraySet<Session>();
            set.add(session);
            roomMap.put(id, set);
        } else {
            set.add(session);
        }
        // 房间人数+1
        onlinePersons.incrementAndGet();
        String info = MessageFormat.format("新用户{0}进入聊天,房间人数:{1}", session.getId(), onlinePersons);
        LogUtil.commonLog(Level.INFO, info);
    }

    @OnClose
    public void close(@PathParam("id") String id, Session session) {
        // 如果某个用户离开了，就移除相应的信息
        if (roomMap.containsKey(id)) {
            roomMap.get(id).remove(session);
        }
        // 房间人数-1
        onlinePersons.decrementAndGet();
        String info = MessageFormat.format("用户{0}退出聊天,房间人数:{1}", session.getId(), onlinePersons);
        LogUtil.commonLog(Level.INFO, info);
    }

    @OnMessage
    public void receiveMessage(@PathParam("id") String id, Session session, String message) throws IOException {
        String info = MessageFormat.format("接收到用户{0}的数据:{1}", session.getId(), message);
        LogUtil.commonLog(Level.INFO, info);
        // 拼接一下用户信息
        String msg = session.getId() + " : " + message;
        Set<Session> sessions = roomMap.get(id);
        // 给房间内所有用户推送信息
        for (Session s : sessions) {
            s.getBasicRemote().sendText(msg);
        }
    }

    @OnError
    public void error(Throwable throwable) {
        try {
            throw throwable;
        } catch (Throwable e) {
            LogUtil.commonLog(Level.ERROR, "未知错误");
        }
    }
}
