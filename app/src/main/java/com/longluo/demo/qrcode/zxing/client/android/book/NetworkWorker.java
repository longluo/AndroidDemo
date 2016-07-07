package com.longluo.demo.qrcode.zxing.client.android.book;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.longluo.demo.R;
import com.longluo.demo.qrcode.zxing.client.android.HttpHelper;
import com.longluo.demo.qrcode.zxing.client.android.LocaleManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

final class NetworkWorker implements Runnable {

    private static final String TAG = NetworkWorker.class.getSimpleName();

    private final String isbn;
    private final String query;
    private final Handler handler;

    NetworkWorker(String isbn, String query, Handler handler) {
        this.isbn = isbn;
        this.query = query;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            // These return a JSON result which describes if and where the query was found. This API may
            // break or disappear at any time in the future. Since this is an API call rather than a
            // website, we don't use LocaleManager to change the TLD.
            String uri;
            if (LocaleManager.isBookSearchUrl(isbn)) {
                int equals = isbn.indexOf('=');
                String volumeId = isbn.substring(equals + 1);
                uri = "http://www.google.com/books?id=" + volumeId + "&jscmd=SearchWithinVolume2&q=" + query;
            } else {
                uri = "http://www.google.com/books?vid=isbn" + isbn + "&jscmd=SearchWithinVolume2&q=" + query;
            }

            try {
                String content = HttpHelper.downloadViaHttp(uri, HttpHelper.ContentType.JSON);
                JSONObject json = new JSONObject(content);
                Message message = Message.obtain(handler, R.id.search_book_contents_succeeded);
                message.obj = json;
                message.sendToTarget();
            } catch (IOException ioe) {
                Message message = Message.obtain(handler, R.id.search_book_contents_failed);
                message.sendToTarget();
            }
        } catch (JSONException je) {
            Log.w(TAG, "Error accessing book search", je);
            Message message = Message.obtain(handler, R.id.search_book_contents_failed);
            message.sendToTarget();
        }
    }

}
