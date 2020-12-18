package com.qingting.middleware.util.thread;

import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author Gage
 * @describe 并行任务参数
 * @date 2019-09-09 10:27
 */
@Data
public class MultiParallelContext {
    /**
     * 运行的任务
     */
    private Runnable task;

    /**
     * 运行的任务
     */
    private Callable call;
    /**
     * 子线程倒计数锁
     */
    private CountDownLatch childLatch;
    /**
     * 子线程异常
     */
    private ChildThreadException childException;

    public MultiParallelContext() {
    }

    public MultiParallelContext(Runnable task, CountDownLatch childLatch, ChildThreadException childException) {
        this.task = task;
        this.childLatch = childLatch;
        this.childException = childException;
    }

    public MultiParallelContext(Callable task, CountDownLatch childLatch, ChildThreadException childException) {
        this.call = task;
        this.childLatch = childLatch;
        this.childException = childException;
    }
}
