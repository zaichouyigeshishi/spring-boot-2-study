package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 推送通知 WebSocket 处理器
 * @author hudongshan
 */
@Slf4j
@Service
public class PushWebSocketService implements WebSocketHandler {

    /**
     * 在线用户列表
     */
    private static final Map<String, WebSocketSession> users;

    static {
        users = new HashMap<>();
    }

    /**
     * 建立连接后
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connect websocket successful!");

        String ID = session.getUri().toString().split("ID=")[1];
        log.info(ID);
        if (ID != null) {
            users.put(ID, session);
            session.sendMessage(new TextMessage("{\"message\":\"socket successful connection!\"}"));
            log.info("id:" + ID + ",session:" + session + "");
        }
        log.info("current user number is:"+users.size());
    }

    /**
     * 客户端消息处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        try {

            log.info("receive message:" + message.getPayload());

            // 立即给用户发送一个消息
            sendMessageToUser(2+"", new TextMessage("{\"message\":\"server received message,hello!\"}"));

        } catch (Exception e) {
            log.error("e",e);
        }
    }

    /**
     * 传输异常处理
     * @param session
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {

        if(session.isOpen()){
            session.close();
        }

        log.error("connect error",throwable);
        users.remove(getClientId(session));
    }

    /**
     * 连接关闭后
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        log.error("连接关闭:{}",closeStatus);

        // 从在线用户中移除该用户
        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        log.info("sendMessage:" + message);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            log.error("e", e);
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                log.error("e", e);
                allSendSuccess = false;
            }
        }
        return allSendSuccess;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            String clientId = (String) session.getAttributes().get("WEBSOCKET_USERID");
            return clientId;
        } catch (Exception e) {
            log.error("e", e);
            return null;
        }
    }
    /**
     * 获取在线人数
     * @return
     */
    public synchronized int getOnlineNum(){
        return users.size();
    }
}
