package com.longluo.demo.qrcode.zxing.client.android.share;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.List;

public final class AppPickerActivity extends ListActivity {

    private AsyncTask<Object, Object, List<AppInfo>> backgroundTask;

    @Override
    protected void onResume() {
        super.onResume();
        backgroundTask = new LoadPackagesAsyncTask(this);
        backgroundTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onPause() {
        AsyncTask<?, ?, ?> task = backgroundTask;
        if (task != null) {
            task.cancel(true);
            backgroundTask = null;
        }
        super.onPause();
    }

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        Adapter adapter = getListAdapter();
        if (position >= 0 && position < adapter.getCount()) {
            String packageName = ((AppInfo) adapter.getItem(position)).getPackageName();
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra("url", "market://details?id=" + packageName); // Browser.BookmarkColumns.URL
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

}
