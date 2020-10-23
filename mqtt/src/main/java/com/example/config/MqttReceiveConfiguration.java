package com.example.config;

import com.example.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author hudongshan
 */
@Slf4j
@Configuration
public class MqttReceiveConfiguration {

    private final MqttProperties mqttProperties;

    public MqttReceiveConfiguration(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    /**
     * 初始化连接配置
     * @return
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setServerURIs(mqttProperties.getServerURIs());
        mqttConnectOptions.setUserName(mqttProperties.getUsername());
        mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
        mqttConnectOptions.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        mqttConnectOptions.setAutomaticReconnect(mqttProperties.isAutomaticReconnect());
        mqttConnectOptions.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        mqttConnectOptions.setCleanSession(mqttProperties.isCleanSession());
//        mqttConnectOptions.setWill(MqttTopic.);
        return mqttConnectOptions;
    }

    /**
     * 初始化 mqtt client 工厂
     * @return
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * 接收通道
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 通过通接收通道获取数据
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler mqttInputChannelHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.info("主题：{}，消息接收到的数据：{}",
                        message.getHeaders().get("mqtt_receivedTopic"),
                        message.getPayload()
                );
            }
        };
    }

}
