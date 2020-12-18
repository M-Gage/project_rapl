package com.qingting.sysadmin;

import com.qingting.middleware.util.thread.ChildThreadException;
import com.qingting.middleware.util.thread.MultiParallelThreadHandler;
import com.qingting.middleware.util.thread.MultiThreadHandler;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test  {



    public static class ThreadCase implements Runnable{
        private String name;
        private Map<String, Object> result;

        public ThreadCase(String name, Map<String, Object> result) {
            this.name = name;
            this.result = result;
        }
        @Override
        public void run() {
            // 模拟线程执行1000ms
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 模拟线程1和线程3抛出异常
//        if(name.equals("1") || name.equals("3"))
//            throw new RuntimeException(name + ": throw exception");

        }

    }


    @org.junit.Test
    public void threadTest() throws Exception {
        System.out.println("main begin \t=================");
        Map<String, Object> resultMap = new HashMap<String, Object>(8, 1);
        MultiThreadHandler handler = new MultiParallelThreadHandler();
//        ExecutorService service = Executors.newFixedThreadPool(3);
//        MultiThreadHandler handler = new ParallelTaskWithThreadPool(service);
        ThreadCase task = null;
        // 启动5个子线程作为要处理的并行任务，共同完成结果集resultMap
        for(int i=1; i<=5 ; i++){
            int finalI = i;
            Callable runnable = new Callable() {
                @Override
                public Object call() {

                    System.out.println(finalI +"afad");
                    return System.currentTimeMillis();
                }
            };
            handler.addTask(runnable);
        }
        try {
            List<Object> call = handler.call();
            for (Object o : call) {
                System.out.println(o.toString());
            }
        } catch (ChildThreadException e) {
            System.out.println(e.getAllStackTraceMessage());
        }
//        service.shutdown();
        System.out.println("main end \t=================");
    }

    public static void main(String[] args) {


    }





}
