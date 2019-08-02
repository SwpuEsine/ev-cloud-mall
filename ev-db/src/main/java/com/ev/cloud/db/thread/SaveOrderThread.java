package com.ev.cloud.db.thread;

import com.ev.cloud.db.domain.LitemallOrder;
import com.ev.cloud.db.service.LitemallOrderService;

/**
 * @author
 * @create 2019-05-26 下午11:02
 **/
public class SaveOrderThread implements Runnable {

    private LitemallOrder order;

    private LitemallOrderService orderService;

    public SaveOrderThread(LitemallOrder order, LitemallOrderService orderService) {
        this.order=order;
        this.orderService=orderService;
    }

    @Override
    public void run() {
        orderService.add(order);
    }
}
