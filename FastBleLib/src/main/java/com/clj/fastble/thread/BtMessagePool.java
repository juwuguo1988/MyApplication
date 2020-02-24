package com.clj.fastble.thread;

import com.clj.fastble.utils.BleHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hello on 18/9/7.
 */

public class BtMessagePool {

    private static final LinkedBlockingDeque<BleHandler> QUEUE = new LinkedBlockingDeque<>();

    public static LinkedBlockingDeque<BleHandler> getQUEUE() {
        return QUEUE;
    }

    public void btStart() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(new BtMessageSendJob(), 0, 500, TimeUnit.MILLISECONDS);
    }

    public synchronized void setQueue(BleHandler queue) {
        QUEUE.add(queue);
    }
}
