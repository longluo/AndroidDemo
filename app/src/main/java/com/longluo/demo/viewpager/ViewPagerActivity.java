package com.longluo.demo.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.longluo.demo.R;
import com.longluo.demo.viewpager.anim.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luolong
 * @date 2015-06-07 17:42:48
 */
public class ViewPagerActivity extends Activity {
    private static final String TAG = ViewPagerActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private int[] mImgIds = new int[]{R.drawable.guide_image1,
            R.drawable.guide_image2, R.drawable.guide_image3};
    private List<ImageView> mImgViews = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_viewpager);

        mViewPager = (ViewPager) findViewById(R.id.vp_main);

//		mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(new MyViewPagerAdapter());
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(ViewPagerActivity.this);

            imageView.setImageResource(mImgIds[position]);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            container.addView(imageView);
            mImgViews.add(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImgViews.get(position));
        }

        @Override
        public int getCount() {
            return mImgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
