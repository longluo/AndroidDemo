package com.longluo.demo.qrcode.zxing.client.android.result.supplement;

import android.content.Context;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.qrcode.zxing.client.android.HttpHelper;
import com.longluo.demo.qrcode.zxing.client.android.LocaleManager;
import com.longluo.demo.qrcode.zxing.client.android.history.HistoryManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Kamil Kaczmarczyk
 * @author Sean Owen
 */
final class BookResultInfoRetriever extends SupplementalInfoRetriever {

    private final String isbn;
    private final String source;
    private final Context context;

    BookResultInfoRetriever(TextView textView, String isbn, HistoryManager historyManager, Context context) {
        super(textView, historyManager);
        this.isbn = isbn;
        this.source = context.getString(R.string.msg_google_books);
        this.context = context;
    }

    @Override
    void retrieveSupplementalInfo() throws IOException {

        CharSequence contents = HttpHelper.downloadViaHttp("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn,
                HttpHelper.ContentType.JSON);

        if (contents.length() == 0) {
            return;
        }

        String title;
        String pages;
        Collection<String> authors = null;

        try {

            JSONObject topLevel = (JSONObject) new JSONTokener(contents.toString()).nextValue();
            JSONArray items = topLevel.optJSONArray("items");
            if (items == null || items.isNull(0)) {
                return;
            }

            JSONObject volumeInfo = ((JSONObject) items.get(0)).getJSONObject("volumeInfo");
            if (volumeInfo == null) {
                return;
            }

            title = volumeInfo.optString("title");
            pages = volumeInfo.optString("pageCount");

            JSONArray authorsArray = volumeInfo.optJSONArray("authors");
            if (authorsArray != null && !authorsArray.isNull(0)) {
                authors = new ArrayList<>(authorsArray.length());
                for (int i = 0; i < authorsArray.length(); i++) {
                    authors.add(authorsArray.getString(i));
                }
            }

        } catch (JSONException e) {
            throw new IOException(e);
        }

        Collection<String> newTexts = new ArrayList<>();
        maybeAddText(title, newTexts);
        maybeAddTextSeries(authors, newTexts);
        maybeAddText(pages == null || pages.isEmpty() ? null : pages + "pp.", newTexts);

        String baseBookUri = "http://www.google." + LocaleManager.getBookSearchCountryTLD(context)
                + "/search?tbm=bks&source=zxing&q=";

        append(isbn, source, newTexts.toArray(new String[newTexts.size()]), baseBookUri + isbn);
    }

}
