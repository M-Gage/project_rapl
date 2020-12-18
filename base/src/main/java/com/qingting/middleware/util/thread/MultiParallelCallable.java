package com.qingting.middleware.util.thread;

import java.util.concurrent.Callable;

/**
 * @author Gage
 * @describe 并行线程对象
 * @date 2019-09-09 15:25
 */
public class MultiParallelCallable implements Callable {
    /**
      * 并行任务参数
     */
    private MultiParallelContext context;

    /**
     * 构造函数
     * @param context
     */
    public MultiParallelCallable(MultiParallelContext context) {
        this.context = context;
    }

    /**
     * 运行任务
     */
    @Override
    public Object call() {
        try {
            //线程运行
            return context.getCall().call();
        } catch (Exception e) {
            //异常加入子线程异常列表
            e.printStackTrace();
            context.getChildException().addException(e);
        } finally {
            //计数
            context.getChildLatch().countDown();
        }
        return null;
    }
}
