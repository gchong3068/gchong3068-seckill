package com.gchong3068.seckill.order.mq;


import com.gchong3068.seckill.common.config.RabbitMQConfig;
import com.gchong3068.seckill.order.model.dto.SeckillOrderMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: gchong3068
 * @date: 2026年07月17日11:31
 * @Version: 1.0
 * @Description:
 */
@Component
@Slf4j
public class SeckillOrderMessageSender {



    @Resource
    private RabbitTemplate rabbitTemplate;


    public void  send(SeckillOrderMqDTO message){
        // 使用订单号作为关联 ID，方便 ConfirmCallback 中定位是哪一笔订单消息发送失败。
        CorrelationData correlationData = new CorrelationData(message.getOrderNo());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.SECKILL_EXCHANGE,
                RabbitMQConfig.SECKILL_ROUTING_KEY,
                message,
                correlationData
        );

        log.info("==> 秒杀下单消息发送请求已提交, orderNo: {}, userId: {}, activityId: {}, goodsId: {}",
                message.getOrderNo(), message.getUserId(), message.getActivityId(), message.getGoodsId());

    }


}
