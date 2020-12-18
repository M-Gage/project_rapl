package com.qingting.middleware.util.thread;

/**
 * @author Gage
 * @describe 并行线程对象
 * @date 2019-09-09 10:26
 */
public class MultiParallelRunnable implements Runnable {
    /**
     * 并行任务参数
     */
    private MultiParallelContext context;

    /**
     * 构造函数
     * @param context
     */
    public MultiParallelRunnable(MultiParallelContext context) {
        this.context = context;
    }

    /**
     * 运行任务
     */
    @Override
    public void run() {
        try {
            //线程运行
            context.getTask().run();
        } catch (Exception e) {
            //异常加入子线程异常列表
            e.printStackTrace();
            context.getChildException().addException(e);
        } finally {
            //计数
            context.getChildLatch().countDown();
        }
    }
}
