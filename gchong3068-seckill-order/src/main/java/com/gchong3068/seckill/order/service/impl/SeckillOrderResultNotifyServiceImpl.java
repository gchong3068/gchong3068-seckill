package com.gchong3068.seckill.order.service.impl;


import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO;
import com.gchong3068.seckill.order.service.SeckillOrderResultNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: gchong3068
 * @date: 2026年07月24日17:44
 * @Version: 1.0
 * @Description:
 */
@Service
@Slf4j
public class SeckillOrderResultNotifyServiceImpl  implements SeckillOrderResultNotifyService {

    /**
     * SSE连接超时时间 60s
     */
    private static final long SSE_TIMEOUT_MILLIS = 60000L;

    /**
     * 储存用户维度 SSE 连接
     */
    private final Map<String,SseEmitter > emitterMap = new ConcurrentHashMap<>();



    /**
     * 订阅秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/24 17:48
     * @param userId
     * @param orderNo
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    @Override
    public SseEmitter subscribe(Long userId, String orderNo) {
        String emitterKey = userId +  ":" + orderNo;

        //创建SSE 连接
        SseEmitter sseEmitter = new SseEmitter(SSE_TIMEOUT_MILLIS);

        //储存SSe连接
        emitterMap.put(emitterKey,sseEmitter);

        // 浏览器断开、连接超时或推送异常时，要清理一下连接，避免内存泄漏
        sseEmitter.onCompletion(() -> emitterMap.remove(emitterKey));
        sseEmitter.onTimeout(() -> emitterMap.remove(emitterKey));
        sseEmitter.onError(e -> emitterMap.remove(emitterKey));

        log.info("==> 秒杀订单结果 SSE 订阅成功, userId: {}, orderNo: {}", userId, orderNo);
        return sseEmitter;

    }


    /**
     * 推送秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/24 17:51
     * @param userId
     * @param result
     */
    @Override
    public void notifyOrderResult(Long userId, FindSeckillOrderResultRspVO result) {

        // 连接唯一标识
        String emitterKey = userId + ":" + result.getOrderNo();
        SseEmitter sseEmitter = emitterMap.remove(emitterKey);

        if (Objects.isNull(sseEmitter)) {
            log.info("==> 秒杀订单结果SSE 连接不存在，跳过推送，userId: {}, orderNo: {}", userId, result.getOrderNo());
            return;

        }

        try{
            //推送结果
            sseEmitter.send(SseEmitter.event()
                    .name("seckill-order-result")
                    .data(result));

            //关闭SSE连接
            sseEmitter.complete();

            log.info("==> 秒杀订单结果 SSE 推送成功, userId: {}, orderNo: {}, status: {}", userId, result.getOrderNo(), result.getStatus());

        }catch (IOException e){
            log.error("==> 秒杀订单结果 SSE 推送失败, userId: {}, orderNo: {}", userId, result.getOrderNo(), e);
        }



    }
}
