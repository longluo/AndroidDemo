package com.longluo.demo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EvaluateDialog extends DialogFragment {
    private String[] mEvaluteVals = new String[]{"GOOD", "BAD", "NORMAL"};
    public static final String RESPONSE_EVALUATE = "response_evaluate";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Evaluate :").setItems(mEvaluteVals,
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(which);
                    }
                });
        return builder.create();
    }

    // 设置返回数据
    protected void setResult(int which) {
        // 判断是否设置了targetFragment
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(RESPONSE_EVALUATE, mEvaluteVals[which]);
        getTargetFragment().onActivityResult(ContentFragment.REQUEST_EVALUATE,
                Activity.RESULT_OK, intent);

    }
}
