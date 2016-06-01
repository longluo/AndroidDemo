package com.longluo.demo.task;


import java.util.Observable;

/**
 * 异步任务统一管理类.
 * 所有的异步任务实例都需要调用 TaskObserver.addObserver() 添加到此管理器中进行统一管理.
 */
public class TaskObserver extends Observable {

    /**
     * 取消,中断一个正在执行的异常任务.
     *
     * @param taskKey 任务ID
     */
    public void cancelTask(Integer taskKey) {
        setChanged();
        notifyObservers(taskKey);
    }

}

