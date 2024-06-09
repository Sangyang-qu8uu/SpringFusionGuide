package org.example.mqtt.config;


import org.example.mqtt.MqttCondition;
import org.example.mqtt.client.MqttAcceptClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 启动服务的时候开启监听客户端
 *
 * @author nisang
 * 2024/1/4 15:21
 * @version 1.0
 * Ruoyi-Cloud-Plus开发小组
 */
@Configuration
public class MqttConfig {

    @Resource
    private MqttAcceptClient mqttAcceptClient;

    /**
     * 订阅mqtt
     *
     * @return
     */
    @Conditional(MqttCondition.class)
    @Bean
    public MqttAcceptClient getMqttPushClient() {
        mqttAcceptClient.connect();
        return mqttAcceptClient;
    }
}
