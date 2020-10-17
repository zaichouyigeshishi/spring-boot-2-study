package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * my_topic 主题响应消息对象
 * @author hudongshan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTopicResponseMessage {

    private String content;
}
