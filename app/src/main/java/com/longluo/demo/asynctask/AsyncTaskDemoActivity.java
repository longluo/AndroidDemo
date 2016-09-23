package com.longluo.demo.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.longluo.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luolong on 2016/8/8.
 */
public class AsyncTaskDemoActivity extends Activity {
    private static int ID = 0;
    private static final int TASK_COUNT = 9;

    private static ExecutorService SINGLE_TASK_EXECUTOR;
    private static ExecutorService LIMITED_TASK_EXECUTOR;
    private static ExecutorService FULL_TASK_EXECUTOR;

    static {
        SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(7);
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_asynctask_demo);

        String title = "AsyncTask of API " + Build.VERSION.SDK_INT;
        setTitle(title);
        final ListView taskList = (ListView) findViewById(R.id.task_list);
        taskList.setAdapter(new AsyncTaskAdapter(getApplication(), TASK_COUNT));
    }

    private class AsyncTaskAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mFactory;
        private int mTaskCount;
        List<SimpleAsyncTask> mTaskList;

        public AsyncTaskAdapter(Context context, int taskCount) {
            mContext = context;
            mFactory = LayoutInflater.from(mContext);
            mTaskCount = taskCount;
            mTaskList = new ArrayList<SimpleAsyncTask>(taskCount);
        }

        @Override
        public int getCount() {
            return mTaskCount;
        }

        @Override
        public Object getItem(int position) {
            return mTaskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mFactory.inflate(R.layout.layout_asynctask_demo_item, null);
                SimpleAsyncTask task = new SimpleAsyncTask((TaskItem) convertView);
                /*
                 * It only supports five tasks at most. More tasks will be scheduled only after
                 * first five finish. In all, the pool size of AsyncTask is 5, at any time it only
                 * has 5 threads running.
                 */
//                task.execute();
                // use AsyncTask#SERIAL_EXECUTOR is the same to #execute();
//                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                // use AsyncTask#THREAD_POOL_EXECUTOR is the same to older version #execute() (less than API 11)
                // but different from newer version of #execute()
//                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                // one by one, same to newer version of #execute()
//                task.executeOnExecutor(SINGLE_TASK_EXECUTOR);
                // execute tasks at some limit which can be customized
//                task.executeOnExecutor(LIMITED_TASK_EXECUTOR);
                // no limit to thread pool size, all tasks run simultaneously
                task.executeOnExecutor(FULL_TASK_EXECUTOR);

                mTaskList.add(task);
            }
            return convertView;
        }
    }

    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {
        private TaskItem mTaskItem;
        private String mName;

        public SimpleAsyncTask(TaskItem item) {
            mTaskItem = item;
            mName = "Task #" + String.valueOf(++ID);
        }

        @Override
        protected Void doInBackground(Void... params) {
            int prog = 1;

            while (prog < 101) {
                SystemClock.sleep(100);
                publishProgress(prog);
                prog++;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onPreExecute() {
            mTaskItem.setTitle(mName);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mTaskItem.setProgress(values[0]);
        }
    }
}

