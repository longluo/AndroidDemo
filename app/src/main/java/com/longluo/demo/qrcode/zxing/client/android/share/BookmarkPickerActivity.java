package com.longluo.demo.qrcode.zxing.client.android.share;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * This class is only needed because I can't successfully send an ACTION_PICK intent to
 * com.android.browser.BrowserBookmarksPage. It can go away if that starts working in the future.
 */
public final class BookmarkPickerActivity extends ListActivity {

    private static final String TAG = BookmarkPickerActivity.class.getSimpleName();

    private static final String[] BOOKMARK_PROJECTION = {
            "title", // Browser.BookmarkColumns.TITLE
            "url", // Browser.BookmarkColumns.URL
    };
    // Copied from android.provider.Browser.BOOKMARKS_URI:
    private static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");

    static final int TITLE_COLUMN = 0;
    static final int URL_COLUMN = 1;

    private static final String BOOKMARK_SELECTION = "bookmark = 1 AND url IS NOT NULL";

    private Cursor cursor;

    @Override
    protected void onResume() {
        super.onResume();
        cursor = getContentResolver().query(BOOKMARKS_URI, BOOKMARK_PROJECTION,
                BOOKMARK_SELECTION, null, null);
        if (cursor == null) {
            Log.w(TAG, "No cursor returned for bookmark query");
            finish();
            return;
        }
        setListAdapter(new BookmarkAdapter(this, cursor));
    }

    @Override
    protected void onPause() {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        super.onPause();
    }

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        if (!cursor.isClosed() && cursor.moveToPosition(position)) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra("title", cursor.getString(TITLE_COLUMN)); // Browser.BookmarkColumns.TITLE
            intent.putExtra("url", cursor.getString(URL_COLUMN)); // Browser.BookmarkColumns.URL
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
