package com.longluo.demo.qrcode.zxing.client.android.share;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This class is only needed because I can't successfully send an ACTION_PICK intent to
 * com.android.browser.BrowserBookmarksPage. It can go away if that starts working in the future.
 */
public final class BookmarkPickerActivity extends ListActivity {

/*
    private static final String[] BOOKMARK_PROJECTION = {
            Browser.BookmarkColumns.TITLE,
            Browser.BookmarkColumns.URL
    };
*/

    static final int TITLE_COLUMN = 0;
    static final int URL_COLUMN = 1;

    // Without this selection, we'd get all the history entries too
    private static final String BOOKMARK_SELECTION = "bookmark = 1";

    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

//        cursor = getContentResolver().query(Browser.BOOKMARKS_URI, BOOKMARK_PROJECTION,
//                BOOKMARK_SELECTION, null, null);
        startManagingCursor(cursor);
        setListAdapter(new BookmarkAdapter(this, cursor));
    }

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        if (cursor.moveToPosition(position)) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//            intent.putExtra(Browser.BookmarkColumns.TITLE, cursor.getString(TITLE_COLUMN));
//            intent.putExtra(Browser.BookmarkColumns.URL, cursor.getString(URL_COLUMN));
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
