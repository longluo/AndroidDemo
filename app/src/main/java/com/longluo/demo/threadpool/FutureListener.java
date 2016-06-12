package com.longluo.demo.threadpool;

public interface FutureListener<T> {
    public void onFutureDone(Future<T> future);
}
