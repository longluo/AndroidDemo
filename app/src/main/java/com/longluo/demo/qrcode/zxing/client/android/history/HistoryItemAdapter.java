package com.longluo.demo.qrcode.zxing.client.android.history;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.longluo.demo.R;

import java.util.ArrayList;

final class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {

    private final Context activity;

    HistoryItemAdapter(Context activity) {
        super(activity, R.layout.history_list_item, new ArrayList<HistoryItem>());
        this.activity = activity;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View layout;
        if (view instanceof LinearLayout) {
            layout = view;
        } else {
            LayoutInflater factory = LayoutInflater.from(activity);
            layout = factory.inflate(R.layout.history_list_item, viewGroup, false);
        }

        HistoryItem item = getItem(position);
        Result result = item.getResult();

        CharSequence title;
        CharSequence detail;
        if (result != null) {
            title = result.getText();
            detail = item.getDisplayAndDetails();
        } else {
            Resources resources = getContext().getResources();
            title = resources.getString(R.string.history_empty);
            detail = resources.getString(R.string.history_empty_detail);
        }

        ((TextView) layout.findViewById(R.id.history_title)).setText(title);
        ((TextView) layout.findViewById(R.id.history_detail)).setText(detail);

        return layout;
    }

}
