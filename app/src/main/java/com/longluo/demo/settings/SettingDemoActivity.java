package com.longluo.demo.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.longluo.demo.R;
import com.longluo.demo.widget.ListItemLinearLayout;


/**
 * Created by luolong on 2016/5/31.
 */
public class SettingDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListItemLinearLayout mListItemLinearlayout_setting_push,
            mListItemLinearlayout_about,
            mListitemlinearlayout_setting_app_recommed,
            mListitemlinearlayout_setting_feedback,
            mListitemlinearlayout_setting_clear_image_cache,
            mListItemLinearlayout_setting_change_phone,
            mListItemLinearlayout_setting_check_version,
            mListitemlinearlayout_setting_change_passsword;

    private View mViewLine;

    private Button mBtnQuit;

//    private CommonTextDialog commonTextDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        initViews();
    }

    private void initViews() {
        mViewLine = findViewById(R.id.view_line);

        mListItemLinearlayout_setting_push = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_push);
        mListItemLinearlayout_setting_push.setOnClickListener(this);

        mListItemLinearlayout_about = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_about_us);
        mListItemLinearlayout_about.setOnClickListener(this);

        mListitemlinearlayout_setting_app_recommed = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_app_recommed);
        mListitemlinearlayout_setting_app_recommed.setOnClickListener(this);

        mListitemlinearlayout_setting_change_passsword = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_change_password);
        mListitemlinearlayout_setting_change_passsword.setOnClickListener(this);

        mListitemlinearlayout_setting_app_recommed.setVisibility(View.GONE);
        mViewLine.setVisibility(View.GONE);

        mListitemlinearlayout_setting_app_recommed.getRightIcon().setText(null);

        mListitemlinearlayout_setting_feedback = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_feedback);
        mListitemlinearlayout_setting_feedback.setOnClickListener(this);

        mListitemlinearlayout_setting_clear_image_cache = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_clear_image_cache);
        mListitemlinearlayout_setting_clear_image_cache
                .setOnClickListener(this);

        mListItemLinearlayout_setting_change_phone = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_change_phone);
        mListItemLinearlayout_setting_change_phone.setOnClickListener(this);

        mListItemLinearlayout_setting_check_version = (ListItemLinearLayout) findViewById(R.id.listitemlinearlayout_setting_check_update);
        mListItemLinearlayout_setting_check_version.setOnClickListener(this);

        mBtnQuit = (Button) findViewById(R.id.listitemlinearlayout_setting_sign_out);
        mBtnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listitemlinearlayout_setting_push:

                break;

            case R.id.listitemlinearlayout_setting_about_us:
                Intent intent = new Intent(SettingDemoActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.listitemlinearlayout_setting_clear_image_cache:

                break;

            case R.id.listitemlinearlayout_setting_invite_friends:

                break;

            case R.id.listitemlinearlayout_setting_app_recommed:

                break;

            case R.id.listitemlinearlayout_setting_feedback:
                Intent intent_feedback = new Intent(SettingDemoActivity.this,
                        FeedBackActivity.class);
                startActivity(intent_feedback);
                break;

            case R.id.listitemlinearlayout_setting_change_phone:

                break;


            case R.id.listitemlinearlayout_setting_sign_out:

                break;

            case R.id.listitemlinearlayout_setting_check_update:
                checkedNewVersion();
                break;

            case R.id.listitemlinearlayout_setting_change_password: // 更改密码

                break;

            default:
                break;
        }
    }

    private void checkedNewVersion() {

    }
}
