package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义客户端发送的消息对象
 * @author hudongshan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyClientMessage {
    private String name;
}
