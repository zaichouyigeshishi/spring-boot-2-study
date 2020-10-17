package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 聊天 demo （使用Java提供的@ServerEndpoint注解实现）
 * @author hudongshan
 * 参考文章：
 * https://www.cnblogs.com/yjmyzz/p/spring-boot-2-websocket-sample.html
 * https://www.cnblogs.com/JohanChan/p/12522001.html
 * https://blog.csdn.net/z1790424577/article/details/81011416
 * 在基于Spring的应用中使用WebSocket一般可以有以下三种方式：
 * 1、使用Java提供的@ServerEndpoint注解实现
 * 2、使用Spring提供的低层级WebSocket API实现
 * 3、使用STOMP消息实现
 *
 * SockJS 是一种兼容不支持 websocket 到旧版浏览器实现的，浏览器如果支持 websocket 就走websocket，不支持就走轮询
 * STOMP 是一种文本协议，用来定义消息语义，在于规范和定于websocket消息传输内容的格式
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebsocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 连接建立后
     * @param session
     * @param userName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String userName) {

        log.info("连接建立，onOpen，session = " + session + ", userName = " + userName);

        sessionPools.put(userName, session);

        addOnlineCount();

        log.info(userName + "加入webSocket！当前人数为" + onlineNum);

        try {

            sendToSession(session, "欢迎" + userName + "加入连接！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭后
     * @param userName
     */
    @OnClose
    public void onClose(Session session,@PathParam(value = "sid") String userName) {

        log.info("连接关闭，onClose，session = " + session + ", userName = " + userName);

        sessionPools.remove(userName);

        subOnlineCount();

        log.info(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 收到客户端消息
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session,@PathParam(value = "sid") String userName,String message){

        log.info("收到客户端消息，onMessage，session = " + session + ", userName = " + userName + ", message = " + message);

        message = "你好：" + userName + "，你发送的消息：" + message + "，服务端已收到";

        for (Session s: sessionPools.values()) {
            try {
                sendToSession(s, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 连接错误
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, @PathParam(value = "sid") String userName,Throwable throwable){

        log.error("session = " + session + ", userName = " + userName + ", throwable = " + throwable);

        throwable.printStackTrace();
    }

    /**
     * ------------------------业务方法------------------------
     */
    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    /**
     * 发送消息
     * @param session
     * @param message
     * @throws IOException
     */
    public void sendToSession(Session session, String message) throws IOException {
        if(session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 给指定用户发送信息
     * @param userName
     * @param message
     */
    public void sendToUser(String userName, String message){
        Session session = sessionPools.get(userName);
        try {
            sendToSession(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * ------------------------业务方法------------------------
     */
}
