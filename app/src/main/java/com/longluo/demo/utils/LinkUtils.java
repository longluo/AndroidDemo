package com.longluo.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.longluo.demo.R;

//import com.longluo.demo.activitycard.MonthActivityCardActivity;
//import com.longluo.demo.animation.AnimationActivity;
//import com.longluo.demo.badgeview.BadgeViewDemoActivity;
//import com.longluo.demo.calendarcard.CalendarCardDemoActivity;
//import com.longluo.demo.numberprogressbar.NumberProgressBarActivity;
//import com.longluo.demo.searchview.SearchViewActivity;
//import com.longluo.demo.viewpager.ViewPagerActivity;
//import com.longluo.demo.viewpager.fragments.ViewPagerMultiFragmentActivity;

public class LinkUtils {

	public static void urlOpen(Context context, String url) {
		Uri uriUrl = Uri.parse(url);
		Intent i = new Intent(Intent.ACTION_VIEW, uriUrl);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	public static void initAuthorInfo(final Activity activity) {
		if (activity == null) {
			return;
		}

		Button btnAuthorInfoTv = (Button) activity.findViewById(R.id.btn_longluo_info);
		final String[] result = getText(activity);
		if (result == null) {
			return;
		}

		Spanned text = Html.fromHtml(result[1]);
		
		btnAuthorInfoTv.setText(text);
		btnAuthorInfoTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				urlOpen(activity, result[0]);
			}
		});
	}

	private static String[] getText(Activity activity) {
		if (activity == null) {
			return null;
		}

		int prefixSrcId = R.string.description, contentSrcId;
		String url = null;
		Class<?> sourClass = activity.getClass();

    /*
		if (sourClass == SearchViewActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_search_view;
		} else if (sourClass == ViewPagerMultiFragmentActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_view_pager_multi_page;
		} else if (sourClass == BadgeViewDemoActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_download_manager;
		} else if (sourClass == MonthActivityCardActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_slide_gallery;
		} else if (sourClass == ViewPagerActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_view_pager;
		} else if (sourClass == NumberProgressBarActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_service;
		} else if (sourClass == CalendarCardDemoActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_broadcast_receiver;
		} else if (sourClass == AnimationActivity.class) {
			url = "http://www.imlongluo.com/blog";
			contentSrcId = R.string.desc_border_scroll_view;
		} else {
            */
			prefixSrcId = R.string.profile;
			url = "http://www.imlongluo.com";
			contentSrcId = R.string.desc_default;
//		}


		String[] result = new String[] {
				url,
				getUrlInfo(activity.getString(prefixSrcId), url,
						activity.getString(contentSrcId)) };
		return result;
	}

	private static String getUrlInfo(String prefix, String url, String content) {
		return new StringBuilder().append(prefix).append("<a href=\"")
				.append(url).append("\">").append(content).append("</a>")
				.toString();
	}
}
