package com.gchong3068.seckill.order.consumer;


import com.gchong3068.seckill.common.config.RabbitMQConfig;
import com.gchong3068.seckill.order.model.dto.SeckillOrderMqDTO;
import com.gchong3068.seckill.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: gchong3068
 * @date: 2026年06月21日21:05
 * @Version: 1.0
 * @Description: 秒杀订单消费者，异步扣库存、建订单
 */
@Component
@Slf4j
public class SeckillOrderConsumer {

    @Resource
    private OrderService  orderService;


    /**
     * 监听秒杀订单队列
     * @author gchong3068
     * @date 2026/6/21 21:07
     * @param message
     */
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void consume(SeckillOrderMqDTO message){
        log.info("## 收到秒杀订单信息: {}",message);
        orderService.createSeckillOrder(message);
    }

}
