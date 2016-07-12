package com.longluo.demo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ContentFragment extends Fragment {

    private String mArgument;
    public static final String ARGUMENT = "argument";
    public static final String RESPONSE = "response";
    public static final String EVALUATE_DIALOG = "evaluate_dialog";
    public static final int REQUEST_EVALUATE = 0X110;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }

    }

    public static ContentFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random random = new Random();
        TextView tv = new TextView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setText(mArgument);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.argb(random.nextInt(100),
                random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        // set click
        tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EvaluateDialog dialog = new EvaluateDialog();
                //注意setTargetFragment
                dialog.setTargetFragment(ContentFragment.this, REQUEST_EVALUATE);
                dialog.show(getFragmentManager(), EVALUATE_DIALOG);
            }
        });
        return tv;
    }

    //接收返回回来的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EVALUATE) {
            String evaluate = data
                    .getStringExtra(EvaluateDialog.RESPONSE_EVALUATE);
            Toast.makeText(getActivity(), evaluate, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(RESPONSE, evaluate);
            getActivity().setResult(ListTitleFragment.REQUEST_DETAIL, intent);
        }

    }
}
