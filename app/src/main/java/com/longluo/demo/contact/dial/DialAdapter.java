package com.longluo.demo.contact.dial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longluo.demo.R;

import java.util.List;

public class DialAdapter extends BaseAdapter {

    private Context ctx;
    private List<CallLogBean> callLogs;
    private LayoutInflater inflater;

    public DialAdapter(Context context, List<CallLogBean> callLogs) {
        this.ctx = context;
        this.callLogs = callLogs;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return callLogs.size();
    }

    @Override
    public Object getItem(int position) {
        return callLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_record_list_item,
                    null);
            holder = new ViewHolder();
            holder.call_type = (ImageView) convertView
                    .findViewById(R.id.call_type);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.call_btn = (TextView) convertView
                    .findViewById(R.id.call_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CallLogBean callLog = callLogs.get(position);
        switch (callLog.getType()) {
            case 1:
                holder.call_type
                        .setBackgroundResource(R.drawable.ic_calllog_outgoing_nomal);
                break;
            case 2:
                holder.call_type
                        .setBackgroundResource(R.drawable.ic_calllog_incomming_normal);
                break;
            case 3:
                holder.call_type
                        .setBackgroundResource(R.drawable.ic_calllog_missed_normal);
                break;
        }
        holder.name.setText(callLog.getName());
        holder.number.setText(callLog.getNumber());
        holder.time.setText(callLog.getDate());

        addViewListener(holder.call_btn, callLog, position);

        return convertView;
    }

    private static class ViewHolder {
        ImageView call_type;
        TextView name;
        TextView number;
        TextView time;
        TextView call_btn;
    }

    private void addViewListener(View view, final CallLogBean callLog,
                                 final int position) {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + callLog.getNumber());
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                ctx.startActivity(intent);
            }
        });
    }
}
