package com.longluo.demo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.longluo.demo.R;

/**
 * DialogDemoActivity
 *
 * @author luolong
 * @date 2015-6-5 上午10:39:42
 */
public class DialogDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);

        customDialog();
//        customAlertDialog();
//        myAlertDialog();
    }

    public void customDialog() {
        Dialog dialog = new Dialog(this);

        // setContentView可以设置为一个View也可以简单地指定资源ID
        // LayoutInflater
        // li=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // View v=li.inflate(R.layout.dialog_layout, null);
        // dialog.setContentView(v);
        dialog.setContentView(R.layout.layout_dialog);

//        dialog.setTitle("Custom Dialog");

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
        // lp.x = 100; // 新位置X坐标
        // lp.y = 100; // 新位置Y坐标

        lp.width = 1000; // 宽度
        lp.height = 1500; // 高度
        lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);

        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        // WindowManager m = getWindowManager();
        // Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        // WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        // p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.95
        // dialogWindow.setAttributes(p);

        dialog.show();
    }

    public void customAlertDialog() {
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();

        int width = display.getWidth();
        int height = display.getHeight();

        Log.d("Test", " " + width + "," + height);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_alert_dialog, null);

        TextView text = (TextView) view.findViewById(R.id.tv_content);
        text.setText("自定义AlertDialog");

        AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.getWindow().setLayout(width / 2, height / 4);
        alert.setTitle("测试");

        // alert.getWindow().setContentView(R.layout.layout_alert_dialog);
        alert.setContentView(view);

        alert.show();
    }

    @SuppressWarnings("deprecation")
    public void myAlertDialog() {
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();

        int width = display.getWidth();
        int height = display.getHeight();

        AlertDialog tipsDialog = new AlertDialog.Builder(this).create();

        final View tipsView = LayoutInflater.from(this).inflate(R.layout.layout_alert_dialog, null);
        TextView tvContent = (TextView) tipsView.findViewById(R.id.tv_content);
        tvContent.setText("ceshi");
        tipsDialog.setView(tipsView);

        tipsDialog.setButton("Know", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface v, int which) {
                v.dismiss();
            }
        });

        // tipsDialog.setPositiveButton("Know", new DialogInterface.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface v, int which) {
        // v.dismiss();
        // }
        // });

//        tipsDialog.getWindow().setLayout(1000, 1800);

        tipsDialog.show();
    }

}
