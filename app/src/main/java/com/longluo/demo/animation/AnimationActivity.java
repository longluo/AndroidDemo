package com.longluo.demo.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;

/**
 * @author luolong
 *         <p/>
 *         Animation Types:
 *         1. Tween
 *         Alpha
 *         Scale
 *         Translate
 *         Rotate
 *         <p/>
 *         2. Frame
 *         3. Layout
 *         4. Property
 */
public class AnimationActivity extends Activity {
    private static final String TAG = AnimationActivity.class.getSimpleName();

    private Button mBtnAllAnimType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initViews();
    }

    private void initViews() {
        mBtnAllAnimType = (Button) findViewById(R.id.btn_all_type);

        mBtnAllAnimType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimationActivity.this, AllAnimationTypeActivity.class);
                startActivity(intent);
            }
        });
    }


}
