package com.qingting.middleware.util.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author Gage
 * @describe 并行线程处理
 * @date 2019-09-09 10:22
 */
public abstract class AbstractMultiParallelThreadHandler implements MultiThreadHandler{

    /**
     * 子线程倒计数锁
     */
    protected CountDownLatch childLatch;

    /**
     * 任务列表
     */
    protected List<Runnable> taskList;

    /**
     * 任务列表
     */
    protected List<Callable> callList;

    /**
     * 子线程异常
     */
    protected ChildThreadException childThreadException;

    public AbstractMultiParallelThreadHandler() {
        taskList = new ArrayList<Runnable>();
        callList = new ArrayList<Callable>();
        childThreadException = new ChildThreadException();
    }

    public void setCountDownLatch(CountDownLatch latch) {
        this.childLatch = latch;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTask(Runnable... tasks) {
        if (null == tasks) {
            taskList = new ArrayList<Runnable>();
        }else {
            taskList.addAll(Arrays.asList(tasks));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTask(Callable... tasks) {
        if (null == tasks) {
            callList = new ArrayList<Callable>();
        }else {
            callList.addAll(Arrays.asList(tasks));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void run() throws ChildThreadException;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract List<Object> call() throws ChildThreadException;


}
