package com.longluo.demo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longluo.demo.R;

import java.util.List;

/**
 * Created by luolong on 2016/6/7.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;

    private List<String> mDatas;

    public DemoAdapter(Context context, List<String> datas) {
        mLayoutInflater = LayoutInflater.from(context);

        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(mLayoutInflater.inflate(R.layout.layout_list_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_name);
        }
    }
}
