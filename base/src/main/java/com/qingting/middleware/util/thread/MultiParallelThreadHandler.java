package com.qingting.middleware.util.thread;

import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Gage
 * @describe 并行任务处理工具
 * @date 2019-09-09 10:24
 */
public class MultiParallelThreadHandler extends AbstractMultiParallelThreadHandler {

    /**
     * 无参构造器
     */
    public MultiParallelThreadHandler() {
        super();
    }

    /**
     * 根据任务数量运行任务
     */
    @Override
    public void run() throws ChildThreadException {
        if (null == taskList || taskList.size() == 0) {
            return;
        } else if (taskList.size() == 1) {
            //一个不需要计数
            runWithoutNewThread();
        } else {
            runInNewThread();
        }
    }

    /**
     * 根据任务数量运行任务
     *
     * @return
     */
    @Override
    public List<Object> call() throws ChildThreadException {
        List<Object> result = new ArrayList<>();
        if (null == callList || callList.size() == 0) {
            return null;
        } else if (callList.size() == 1) {
            //一个不需要计数
            try {
                result.add(callList.get(0).call());
            } catch (Exception e) {
                childThreadException.addException(e);
            }
            callList.clear();
            throwChildExceptionIfRequired();
        } else {
            try {
                callInNewThread(result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 新建线程运行任务
     *
     * @throws ChildThreadException
     */
    private void runInNewThread() throws ChildThreadException {
        //初始化线程计数锁
        childLatch = new CountDownLatch(taskList.size());
        //清空子线程异常集合
        childThreadException.clearExceptionList();
        for (Runnable task : taskList) {
            invoke(new MultiParallelRunnable(new MultiParallelContext(task, childLatch, childThreadException)));
        }
        //清空线程任务列表
        taskList.clear();
        try {
            //计数完成则放行
            childLatch.await();
        } catch (InterruptedException e) {
            childThreadException.addException(e);
        }
        //此处若线程异常集合有异常对象则抛出
        throwChildExceptionIfRequired();
    }

    /**
     * 新建线程运行任务
     *
     * @param result
     * @throws ChildThreadException
     */
    private void callInNewThread(List<Object> result) throws ChildThreadException, ExecutionException, InterruptedException {
        //初始化线程计数锁
        childLatch = new CountDownLatch(callList.size());
        //清空子线程异常集合
        childThreadException.clearExceptionList();
        for (Callable task : callList) {
            FutureTask futureTask = new FutureTask<>(new MultiParallelCallable(new MultiParallelContext(task, childLatch, childThreadException)));
            invoke(futureTask);
            result.add(futureTask.get());
        }
        //清空线程任务列表
        taskList.clear();
        try {
            //计数完成则放行
            childLatch.await();
        } catch (InterruptedException e) {
            childThreadException.addException(e);
        }
        //此处若线程异常集合有异常对象则抛出
        throwChildExceptionIfRequired();
    }

    /**
     * 默认线程执行方法
     *
     * @param command
     */
    protected void invoke(Runnable command) {
        //判断是否是Thread实现类
        if (command.getClass().isAssignableFrom(Thread.class)) {
            Thread.class.cast(command).start();
        } else {
            new Thread(command).start();
        }
    }


    /**
     * 在当前线程中直接运行
     *
     * @throws ChildThreadException
     */
    private void runWithoutNewThread() throws ChildThreadException {
        try {
            taskList.get(0).run();
        } catch (Exception e) {
            childThreadException.addException(e);
        }
        taskList.clear();
        throwChildExceptionIfRequired();
    }

    /**
     * 根据需要抛出子线程异常
     *
     * @throws ChildThreadException
     */
    private void throwChildExceptionIfRequired() throws ChildThreadException {
        if (childThreadException.hasException()) {
            childExceptionHandler(childThreadException);
        }
    }

    /**
     * 默认抛出子线程异常
     *
     * @param e
     * @throws ChildThreadException
     */
    protected void childExceptionHandler(ChildThreadException e) throws ChildThreadException {
        throw e;
    }
}
