package com.longluo.demo.filemanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longluo.demo.recyclerview.RecyclerViewAdapter;

/**
 * Created by luolong on 2016/6/2.
 */
public class FileManagerAdapter extends RecyclerView.Adapter<FileManagerAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }

    public RecyclerViewAdapter.OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(RecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public MyViewHolder(View view) {
            super(view);


        }

    }
}
