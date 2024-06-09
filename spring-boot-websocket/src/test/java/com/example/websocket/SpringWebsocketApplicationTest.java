package com.example.websocket;

import com.example.websocket.client.MyWebSocketClient;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author nisang
 * 2023/12/31 14:14
 * @version 1.0
 * Ruoyi-Cloud-Plus开发小组
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebsocketApplicationTest {
    @Resource
    private MyWebSocketClient myWebSocketClient;

    /**
     * 客户端向服务端发送一条消息
     */
    @Test
    public void pushMsgToServer() {
        myWebSocketClient.sendMessage("Hello World");
    }


}
