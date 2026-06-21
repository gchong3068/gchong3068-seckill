package com.gchong3068.seckill.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Profile;

/**
 * @Author: gchong3068
 * @Date: 2026/6/15 12:36
 * @Version: v1.0.0
 * @Description: RabbitMQ 配置类
 **/
@Configuration
public class RabbitMQConfig  {


    /** 秒杀订单交换机名称 */
    public static final String SECKILL_EXCHANGE = "seckill.order.exchange";

    /** 秒杀订单队列名称 */
    public static final String SECKILL_QUEUE = "seckill.order.queue";

    /** 秒杀下单路由键 */
    public static final String SECKILL_ROUTING_KEY = "seckill.order.create";



    /**
     * 秒杀订单交换机（Direct 类型，持久化）
     */
    @Bean
    public DirectExchange seckillOrderExchange() {
        // 参数：名称、是否持久化(durable)、是否自动删除(autoDelete)
        return new DirectExchange(SECKILL_EXCHANGE, true, false);
    }

    /**
     * 秒杀订单队列（持久化）
     */
    @Bean
    public Queue seckillOrderQueue() {
        // 参数：名称、是否持久化(durable)
        return new Queue(SECKILL_QUEUE, true);
    }

    /**
     * 将秒杀订单队列绑定到秒杀订单交换机，指定路由键
     */
    @Bean
    public Binding seckillOrderBinding(Queue seckillOrderQueue, DirectExchange seckillOrderExchange) {
        return BindingBuilder.bind(seckillOrderQueue)
                .to(seckillOrderExchange)
                .with(SECKILL_ROUTING_KEY);
    }


}
