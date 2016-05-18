package com.longluo.demo.fresco;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.longluo.demo.R;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by luolong on 2016/5/18.
 */
public class FrescoDemoActivity extends AppCompatActivity {

    private Context mContext;

    private Creater mCreater;

//    private SimpleDraweeView mSimpleDraweeView;
//    private SimpleDraweeView mSimpleDraweeView1;
//    private SimpleDraweeView mSimpleDraweeView2;
//    private SimpleDraweeView mSimpleDraweeView3;
//    private SimpleDraweeView mSimpleDraweeView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_demo);

//        init();
//        testHttpData();
    }

/*    private void init() {
        mContext = this;

        mSimpleDraweeView = (SimpleDraweeView) this.findViewById(R.id.id_simple_drawee_view);
        mSimpleDraweeView1 = (SimpleDraweeView) this.findViewById(R.id.id_simple_drawee_view1);
        mSimpleDraweeView2 = (SimpleDraweeView) this.findViewById(R.id.id_simple_drawee_view2);
        mSimpleDraweeView3 = (SimpleDraweeView) this.findViewById(R.id.id_simple_drawee_view3);
        mSimpleDraweeView4 = (SimpleDraweeView) this.findViewById(R.id.id_simple_drawee_view4);

        mCreater = new Creater();
    }

    private void testHttpData() {
        //初始化HTTP的图片数据源
        mCreater.init(Creater.Type.NET);

        //显示一张HTTP图片
        mSimpleDraweeView.setImageURI(Uri.parse(mCreater.getPic()));

        //显示一张HTTP图片保持一定宽高比例，如果4:3(1.33f)，注意xml里的写法
        mSimpleDraweeView1.setImageURI(Uri.parse(mCreater.getPic()));
        mSimpleDraweeView1.setAspectRatio(1.33f);

        //显示一张HTTP的GIF图片
        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(mCreater.getGif()))
                .setAutoPlayAnimations(true).build();
        mSimpleDraweeView2.setController(draweeController1);

        //显示一张HTTP的图片，以圆形图片显示
        mSimpleDraweeView3.setImageURI(Uri.parse(mCreater.getPic()));

        //显示一张HTTP的图片，以圆形带边框图片显示
        mSimpleDraweeView4.setImageURI(Uri.parse(mCreater.getPic()));
    }*/

}
