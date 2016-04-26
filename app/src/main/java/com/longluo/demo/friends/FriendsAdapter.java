package com.longluo.demo.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.widget.image.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    private static final String TAG = FriendsAdapter.class.getSimpleName();

    private Context mContext;
    private List<FriendData> mDatas = new ArrayList<FriendData>();

    class ViewHolder {
        RoundImageView mAvatarIV;
        ;
        TextView mNameTV;
        TextView mLocationTV;
        ImageView mSnapshotIV;
    }

    public FriendsAdapter(Context context) {
        mContext = context;

    }

    public FriendsAdapter(Context context, List<FriendData> datas) {
        mContext = context;

        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_friend_item, null);
            viewHolder.mAvatarIV = (RoundImageView) convertView.findViewById(R.id.avatar);
            viewHolder.mNameTV = (TextView) convertView.findViewById(R.id.id_tv_name);
            viewHolder.mLocationTV = (TextView) convertView.findViewById(R.id.id_tv_location);
            viewHolder.mSnapshotIV = (ImageView) convertView.findViewById(R.id.id_iv_snapshot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // TODO
        FriendData friendData = mDatas.get(position);
        viewHolder.mNameTV.setText("小莉");
        viewHolder.mLocationTV.setText("马尔代夫");

        return convertView;
    }

}
