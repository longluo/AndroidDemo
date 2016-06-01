package com.longluo.demo.task;


import android.os.AsyncTask;
import android.widget.Toast;

import com.longluo.demo.DemoApp;
import com.longluo.demo.utils.ToastUtils;

import java.util.HashMap;


/**
 * 任务监听器.
 *
 * @param <T> 任务执行结果的返回类型
 */
@SuppressWarnings("all")
public abstract class TaskListener<T> {

    /**
     * 关联的异步任务列表
     */
    protected HashMap<Integer, BaseTask> mTaskMap;

    public TaskListener(HashMap<Integer, BaseTask> taskMap) {
        this.mTaskMap = taskMap;
    }

    /**
     * 提前任务前先添加到任务观察器管理中.
     *
     * @param task    实现了Observer接口的任务实例
     * @param taskKey 任务标识
     * @return 正常情况下返回true, 如果返回false, 应该终止该任务.
     */
    @SuppressWarnings("unchecked")
    public boolean preExecute(BaseTask<T> task, Integer taskKey) {

        boolean flag = true;

		/*
         * 如果当前Activity任务列表已存在对应的状态为RUNNING的任务
		 * 返回 false时取消任务
		 */
        if (mTaskMap != null) {
            BaseTask<T> taskInMap = mTaskMap.get(taskKey);

            if (taskInMap == null || taskInMap.getStatus() != AsyncTask.Status.RUNNING) {

                // 将任务添加到TaskObserver进行统一管理
                DemoApp.getTaskObserver().addObserver(task);

                // 将任务添加到异步任务列表中
                mTaskMap.put(taskKey, task);
            } else {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * 任务返回后将要执行的操作.
     *
     * @param result
     */
    public void onResult(TaskResult<T> result) {
        switch (result.getCode()) {
            case TaskResult.ERROR:
                ToastUtils.show(DemoApp.getContext(),
                        result.getErrorMsg(), Toast.LENGTH_LONG);
                break;
        }
    }

    /**
     * 释放资源.
     */
    public void release() {
        this.mTaskMap = null;
    }

}
