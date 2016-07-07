package com.longluo.demo.qrcode.zxing.client.android.result.supplement;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.google.zxing.client.result.URIParsedResult;
import com.longluo.demo.R;
import com.longluo.demo.qrcode.zxing.client.android.HttpHelper;
import com.longluo.demo.qrcode.zxing.client.android.history.HistoryManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

final class URIResultInfoRetriever extends SupplementalInfoRetriever {

    private static final int MAX_REDIRECTS = 5;

    private final URIParsedResult result;
    private final String redirectString;

    URIResultInfoRetriever(TextView textView,
                           URIParsedResult result,
                           Handler handler,
                           HistoryManager historyManager,
                           Context context) {
        super(textView, handler, historyManager);
        redirectString = context.getString(R.string.msg_redirect);
        this.result = result;
    }

    @Override
    void retrieveSupplementalInfo() throws IOException, InterruptedException {
        URI oldURI;
        try {
            oldURI = new URI(result.getURI());
        } catch (URISyntaxException e) {
            return;
        }
        URI newURI = HttpHelper.unredirect(oldURI);
        int count = 0;
        while (count++ < MAX_REDIRECTS && !oldURI.equals(newURI)) {
            append(result.getDisplayResult(),
                    null,
                    new String[]{redirectString + " : " + newURI},
                    newURI.toString());
            oldURI = newURI;
            newURI = HttpHelper.unredirect(newURI);
        }
    }

}
