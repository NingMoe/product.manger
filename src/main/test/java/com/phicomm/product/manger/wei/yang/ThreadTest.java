package com.phicomm.product.manger.wei.yang;

import org.junit.Test;

/**
 * 看看Thread
 * Created by wei.yang on 2017/7/23.
 */
public class ThreadTest {

    @Test
    public void test(){
        ThreadGroup root=Thread.currentThread().getThreadGroup();
        System.out.println(root.activeCount());
        System.out.println(root.getMaxPriority());
        Thread[] threads = new Thread[10];
        root.enumerate(threads);
        System.out.println(threads.length);
        for (Thread thread:threads){
            if (thread!=null){
                System.out.println(thread.getName());
            }
        }
    }
}
