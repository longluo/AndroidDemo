package com.longluo.demo.badgeview;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longluo.demo.R;
import com.longluo.demo.widget.badgeview.BadgeView;


/**
 * Demonstrates the BadgeView with a ListView.
 * Based on Android API-Demos -> List14
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ListAdapter(getActivity()));
    }

    private static class ListAdapter extends BaseAdapter {
        private static final String[] DATA = Cheeses.sCheeseStrings;

        private LayoutInflater mInflater;
        private Context mContext;

        public ListAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        @Override
        public int getCount() {
            return DATA.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_row_badge, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(android.R.id.text1);
                holder.badge = new BadgeView(mContext);
                holder.badge.setTargetView(holder.text);
                holder.badge.setBadgeGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                holder.badge.setBadgeMargin(0, 0, 8, 0);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(DATA[position]);
            holder.badge.setBadgeCount(DATA[position].length());

            return convertView;
        }

        static class ViewHolder {
            TextView text;
            BadgeView badge;
        }
    }
}
