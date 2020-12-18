package com.qingting.middleware.util.thread;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Gage
 * @describe 多任务处理
 * @date 2019-09-09 10:21
 */
public interface MultiThreadHandler {

    /**
     * 添加任务
     * @param tasks
     */
    void addTask(Runnable... tasks);

    /**
     * 添加任务
     * @param tasks
     */
    void addTask(Callable... tasks);
    /**
     * 执行任务
     * @throws ChildThreadException
     */
    void run() throws ChildThreadException;

    /**
     * 执行任务
     * @throws ChildThreadException
     */
    List<Object> call() throws ChildThreadException;
}
