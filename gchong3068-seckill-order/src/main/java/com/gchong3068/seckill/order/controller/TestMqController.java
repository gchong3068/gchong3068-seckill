package com.gchong3068.seckill.order.controller;

import com.gchong3068.seckill.common.config.RabbitMQConfig;
import com.gchong3068.seckill.common.utils.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author: gchong3068
 * @Date: 2026/6/15 12:43
 * @Version: v1.0.0
 * @Description: TODO
 **/
@RestController
@RequestMapping("/test/mq")
@Slf4j
public class TestMqController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送测试消息到 RabbitMQ
     *
     * @return
     */
    @GetMapping("/send")
    public Response<String> sendTestMessage() {
        String message = "Hello RabbitMQ! 发送时间: " + LocalDateTime.now();

        // 通过 RabbitTemplate 发送消息：指定交换机、路由键、消息内容
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TEST_EXCHANGE,
                RabbitMQConfig.TEST_ROUTING_KEY,
                message
        );

        log.info("==> 测试消息发送成功: {}", message);
        return Response.success("消息发送成功");
    }


}
