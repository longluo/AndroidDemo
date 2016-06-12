package com.longluo.demo.threadpool;

import android.os.Process;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread factory that creates threads with a given thread priority.
 */
public class PriorityThreadFactory implements ThreadFactory {

    private final int mPriority;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final String mName;

    public class CancelableThread extends Thread {
        private boolean cancelled = false;

        public CancelableThread(Runnable r, String name) {
            super(r, name);
        }

        public boolean isThreadCancelled() {
            // This method automatically reset "cancelled" flag
            boolean wasCancelled = cancelled;
            cancelled = false;
            return wasCancelled;
        }

        public void cancelThread() {
            cancelled = true;
        }

        public void run() {
            Process.setThreadPriority(mPriority);
            super.run();
        }
    }

    public PriorityThreadFactory(String name, int priority) {
        mName = name;
        mPriority = priority;
    }

    public Thread newThread(Runnable r) {
        /*
        return new Thread(r, mName + '-' + mNumber.getAndIncrement()) {
            @Override
            public void run() {
                Process.setThreadPriority(mPriority);
                super.run();
            }
        };
        */
        return new CancelableThread(r, mName + '-' + mNumber.getAndIncrement());
    }

}
