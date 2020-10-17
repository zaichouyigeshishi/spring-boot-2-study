package com.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Websocket 拦截器
 * @author hudongshan
 */
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 握手前执行（token 验证等）继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
     * @param request
     * @param response
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        log.info("WebSocketInterceptor beforeHandshake");

        if (request instanceof ServletServerHttpRequest) {
            String ID = request.getURI().toString().split("ID=")[1];
            log.info("current session id is:"+ID);
            map.put("WEBSOCKET_USERID",ID);
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        log.info("WebSocketInterceptor afterHandshake");
    }

}
