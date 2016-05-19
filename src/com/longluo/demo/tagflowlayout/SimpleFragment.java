package com.longluo.demo.tagflowlayout;

import com.longluo.demo.R;
import com.longluo.demo.widget.tagflowlayout.FlowLayout;
import com.longluo.demo.widget.tagflowlayout.TagAdapter;
import com.longluo.demo.widget.tagflowlayout.TagFlowLayout;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {
	private String[] mVals = new String[] { "Hello", "Android", "Weclome Hi ",
			"Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView" };

	private TagFlowLayout mFlowLayout;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_main, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		final LayoutInflater mInflater = LayoutInflater.from(getActivity());
		mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);

		mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
			@Override
			public View getView(FlowLayout parent, int position, String s) {
				TextView tv = (TextView) mInflater.inflate(R.layout.tv,
						mFlowLayout, false);
				tv.setText(s);
				return tv;
			}
		});
	}
}
