package com.longluo.demo.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.longluo.demo.R;


/**
 * Created by luolong on 2016/5/31.
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private ImageButton mImageButton;
    private TextView mWordsTextView;

    // 标签字体个数(包括标点)
    private int labelWordsSize = 0;
    private Context mContxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);

        mContxt = this;

        mEditText = (EditText) findViewById(R.id.input);
        mEditText.addTextChangedListener(mTxtWatcher);

        mImageButton = (ImageButton) findViewById(R.id.delete_btn);
        mImageButton.setOnClickListener(this);

        mWordsTextView = (TextView) findViewById(R.id.words_textview);

        setWordsTextView(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_btn:
                this.mEditText.setText("");
                break;

            default:
                break;
        }
    }

/*    private TaskListener<Void> mRecordOpinionsTaskListener = new TaskListener<Void>(
            getTaskMap()) {
        @Override
        public void onResult(TaskResult<Void> result) {
            switch (result.getCode()) {
                case TaskResult.OK:
                    ToastUtils.show(mContxt, R.string.feedback_sumbit_success,
                            Toast.LENGTH_SHORT);
                    break;

                case TaskResult.FAILED:
                    ToastUtils.show(mContxt, R.string.get_data_error,
                            Toast.LENGTH_SHORT);
                    break;

                default:
                    super.onResult(result);
                    break;
            }
        }
    };*/

    @Override
    protected void onDestroy() {
        mContxt = null;
        super.onDestroy();
    }

    private void setWordsTextView(int wordsSize) {
        mWordsTextView.setText(wordsSize + "/1500");
    }

    TextWatcher mTxtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mImageButton.setVisibility(View.GONE);
            } else {
                mImageButton.setVisibility(View.VISIBLE);
            }
            setWordsTextView(s.length() + labelWordsSize);
        }
    };


}
