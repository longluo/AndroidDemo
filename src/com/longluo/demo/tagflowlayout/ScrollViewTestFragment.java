package com.longluo.demo.tagflowlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

import com.longluo.demo.R;
import com.longluo.demo.widgets.tagflowlayout.FlowLayout;
import com.longluo.demo.widgets.tagflowlayout.TagAdapter;
import com.longluo.demo.widgets.tagflowlayout.TagFlowLayout;

public class ScrollViewTestFragment extends Fragment {

	private String[] mVals = new String[] { "Hello", "Android", "Weclome Hi ",
			"Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView", "Hello", "Android",
			"Weclome Hi ", "Button", "TextView", "Hello", "Android", "Weclome",
			"Button ImageView", "TextView", "Helloworld", "Android",
			"Weclome Hello", "Button Text", "TextView" };

	private TagFlowLayout mFlowLayout;
	private TagAdapter<String> mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_main_sc, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		final LayoutInflater mInflater = LayoutInflater.from(getActivity());
		mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
		// mFlowLayout.setMaxSelectCount(3);

		mFlowLayout.setAdapter(mAdapter = new TagAdapter<String>(mVals) {

			@Override
			public View getView(FlowLayout parent, int position, String s) {
				TextView tv = (TextView) mInflater.inflate(R.layout.tv,
						mFlowLayout, false);
				tv.setText(s);
				return tv;
			}
		});
		mAdapter.setSelectedList(1, 3, 5, 7, 8, 9);
		mFlowLayout
				.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
					@Override
					public boolean onTagClick(View view, int position,
							FlowLayout parent) {
						// Toast.makeText(getActivity(), mVals[position],
						// Toast.LENGTH_SHORT).show();
						// view.setVisibility(View.GONE);
						return true;
					}
				});

		mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
			@Override
			public void onSelected(Set<Integer> selectPosSet) {
				getActivity().setTitle("choose:" + selectPosSet.toString());
			}
		});
	}
}
