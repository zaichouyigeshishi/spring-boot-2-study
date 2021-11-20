package com.example.controller;

import com.example.bean.MyClientMessage;
import com.example.bean.MyTopicResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hudongshan
 * 参考：https://www.cnblogs.com/kiwifly/p/11729304.html
 */
@Slf4j
@Controller
public class WebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * @MessageMapping 这个注解来暴露节点路径，
     * 有点类似 @RequestMapping。注意这里虽然写的是 hello ，
     * 但是我们客户端调用的真正地址是** /app/hello。
     * 因为我们在上面的 config 里配置了registry.setApplicationDestinationPrefixes("/app")。
     *
     * @SendTo 这个注解会把返回值的内容发送给订阅了 /topic/my_topic 的客户端，
     * 与之类似的还有一个@SendToUser 只不过他是发送给用户端一对一通信的。这两个注解一般是应答时响应的，
     * 如果服务端主动发送消息可以通过 simpMessagingTemplate类的convertAndSend方法。
     * 注意 simpMessagingTemplate.convertAndSendToUser(token, "/msg", msg) ，
     * 联系到我们上文配置的 registry.setUserDestinationPrefix("/user/"),
     * 这里客户端订阅的是/user/{token}/msg,千万不要搞错。
     * @param myClientMessage
     * @return
     */
    @MessageMapping("/hello")
    @SendTo("/topic/my_topic")
    public MyTopicResponseMessage hello(MyClientMessage myClientMessage) {

        log.info("myClientMessage = {}", myClientMessage);

        return new MyTopicResponseMessage("服务端接收到你发的：" + myClientMessage.getName());
    }

    @GetMapping("/sendMsgByUser")
    @ResponseBody
    public Object sendMsgByUser(String token, String msg) {
        simpMessagingTemplate.convertAndSendToUser(token, "/msg", msg);
        return "success";
    }

    @GetMapping("/sendMsgByAll")
    public @ResponseBody Object sendMsgByAll(String msg) {
        simpMessagingTemplate.convertAndSend("/topic", msg);
        return "success";
    }

    /**
     * web 客户端测试地址
     * @return
     */
    @GetMapping("/client")
    public String test() {
        return "client.html";
    }
}
