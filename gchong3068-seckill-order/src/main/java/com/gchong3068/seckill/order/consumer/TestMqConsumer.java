package com.gchong3068.seckill.order.consumer;

import com.gchong3068.seckill.common.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: gchong3068
 * @Date: 2026/6/15 12:45
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Component
@Slf4j
public class TestMqConsumer {

    /**
     * 监听测试队列，收到消息后打印日志
     *
     * @param message
     */
    @RabbitListener(queues = RabbitMQConfig.TEST_QUEUE)
    public void consume(String message) {
        log.info("## 收到测试消息: {}", message);
    }
}
