package com.gchong3068.seckill.order.service;


import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author: gchong3068
 * @date: 2026年07月24日17:44
 * @Version: 1.0
 * @Description:
 */
public interface SeckillOrderResultNotifyService {


    /**
     * 订阅秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/24 17:45
     * @param userId
     * @param orderNo
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    SseEmitter subscribe(Long userId, String orderNo);


    /**
     * 推送秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/24 17:46
     * @param userId
     * @param result
     */
    void notifyOrderResult(Long userId, FindSeckillOrderResultRspVO result);


}
