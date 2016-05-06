package com.longluo.demo.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);
        // QQ登录
        mQQButton = (LinearLayout) findViewById(R.id.qq_login_button);
        mQQButton.setOnClickListener(this);
        // 支付宝钱包隐藏
        mQQBtuttonVisible = (LinearLayout) findViewById(R.id.llayout);
        mLinearlayout_content = (LinearLayout) findViewById(R.id.linearlayout_content);
        mAccountInput = (EditText) findViewById(R.id.account_input);
        mAccountInput.setOnFocusChangeListener(this);
        mPasswordInput = (EditText) findViewById(R.id.password_input);

        mPasswordInput.setOnFocusChangeListener(this);

        mForgetpassword_text = (TextView) findViewById(R.id.forgetpassword_text);
        mForgetpassword_text.setOnClickListener(this);

        mPasswordImageView = (ImageView) findViewById(R.id.password_imageview);
        mPhoneImageView = (ImageView) findViewById(R.id.phone_imageview);
        mPhoneDelIV = (ImageView) findViewById(R.id.imageView);
        mPasswordDelIV = (ImageView) findViewById(R.id.imageView2);
        mPhoneDelIV.setOnClickListener(this);
        mPasswordDelIV.setOnClickListener(this);

        mAccountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mPhoneDelIV.setVisibility(View.GONE);
                } else {
                    mPhoneDelIV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        mPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mPasswordDelIV.setVisibility(View.GONE);
                } else {
                    mPasswordDelIV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });


    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }


}
