package com.example.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hudongshan
 */
@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    private String[] serverURIs;
    private String username;
    private String password;
    private String clientId;

    /**
     * 设置连接超时值（秒）
     * 该值定义了客户端将等待与MQTT服务器建立网络连接的最大时间间隔。默认超时为30秒。
     * 设置为0将禁用超时处理，这意味着客户端将一直等待，直到成功建立网络连接或失败。
     */
    private Integer connectionTimeout;

    /**
     * 设置如果连接断开，客户端是否将自动尝试重新连接到服务器。
     * 如果设置为false，则在连接丢失的情况下，客户端将不会尝试自动重新连接到服务器。
     * 如果设置为true，则在连接丢失的情况下，客户端将尝试重新连接到服务器。
     * 它最初将等待1秒钟，然后再尝试重新连接，对于每次失败的重新连接尝试，延迟将加倍，直到2分钟为止，此时延迟将保持2分钟。
     */
    private boolean automaticReconnect;

    /**
     * 设置“保持活动”间隔（秒）
     * 该值定义了发送或接收消息之间的最大时间间隔。
     * 它使客户端能够检测服务器是否不再可用，而不必等待TCP / IP超时。
     * 客户端将确保在每个有效期内，至少有一条消息在网络上传播。
     * 在这段时间内，如果没有与数据相关的消息，则客户端会发送一个很小的“ ping”消息，服务器将予以确认。
     * 设置为0将禁用客户端中的keepalive处理。
     */
    private Integer keepAliveInterval;

    /**
     * 设置客户端和服务器在重新启动和重新连接时是否应记住状态
     * 如果设置为false，则客户端和服务器将在客户端，服务器和连接的重新启动期间保持状态。维持状态：
     * - 即使重新启动客户端，服务器或连接，消息传递也将可靠地满足指定的QOS。
     * - 服务器会将订阅视为持久订阅。
     * 如果设置为true，则客户端和服务器将不会在客户端，服务器或连接的重新启动期间保持状态。这意味着
     * - 如果重新启动客户端，服务器或连接，则无法维持到指定QOS的消息传递
     * - 服务器会将订阅视为非持久订阅
     */
    private boolean cleanSession;

    /**
     * 设置“最大飞行”。请在通讯繁忙的环境中增大此值。预设值为10
     */
    private Integer maxInflight;


}