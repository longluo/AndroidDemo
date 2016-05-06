package com.longluo.demo.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * @author luolong
 * @date 2015-9-25 20:39:54
 */
public class LoginDemoActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = LoginDemoActivity.class.getSimpleName();

    private Toolbar mToolbar;

    // 登录按钮，注册按钮，QQ登录按钮
    private Button mLoginButton;
    private LinearLayout mQQButton, mQQBtuttonVisible, mLinearlayout_content;
    private EditText mAccountInput, mPasswordInput;
    // 用于保存IphoneEditText的EditText数据,当页面转跳后返回,会出现所有EditText显示最后一个EditText内容,
    // 所以需要在onSaveInstanceState和onPause保存数据,后在onCreateView和onResume重置数据
    private String mstrAccount = "";
    private String mstrPassword = "";
    private TextView mForgetpassword_text;
    // QQ登录返回取得电话和ID号
    private String mMobile = null, mMemberId = null;

    private static final String SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends,"
            + "add_share,add_topic,list_album,upload_pic,add_album,set_user_face,get_vip_info,get_vip_rich_info,get_intimate_friends_weibo,match_nick_tips_weibo";
    // 图标
    private ImageView mPasswordImageView, mPhoneImageView, mPasswordDelIV,
            mPhoneDelIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_demo);

        initViews();


    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.login_demo_activity_title);
        setSupportActionBar(mToolbar);


    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }


}
