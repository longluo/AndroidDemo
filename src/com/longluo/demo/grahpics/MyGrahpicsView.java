package com.longluo.demo.grahpics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.view.View;

public class MyGrahpicsView extends View {
	private Context mContext;
	
	public MyGrahpicsView(Context context) {
		super(context);
		
		mContext=context;
	}
	
	// 重写OnDraw() 函数，在每次重绘时自主实现绘图
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		//设置画笔基本属性
//		Paint paint=new Paint();
//		paint.setAntiAlias(true);//抗锯齿功能
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		paint.setStyle(Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
//		paint.setStrokeWidth(5);//设置画笔宽度
//		paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
		
		//设置画布背景颜色     
//        canvas.drawRGB(255, 255,255);
		
        //画直线 
		
		Bitmap overlay = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
		

		
		//自定义字体，，，迷你简罗卜
		Paint paint=new Paint();
		paint.setColor(Color.RED);  //设置画笔颜色    
		
		//圆角矩形
		Path path = new Path();
		
		RectF rect1 =  new RectF(50, 50, 240, 200);
		path.addRoundRect(rect1, 10, 15 , Direction.CCW);
		
		RectF rect2 =  new RectF(290, 150, 480, 300);
		float radii[] = {10,15,20,25,30,35,40,45};
		path.addRoundRect(rect2, radii, Direction.CCW);
		
		canvas.drawPath(path, paint);
		
		
//		paint.setStrokeWidth (5); //设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(60); //设置文字大小
//		paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
//		
//		AssetManager mgr=m_context.getAssets();//得到AssetManager
//		Typeface typeface=Typeface.createFromAsset(mgr, "fonts/jian_luobo.ttf");//根据路径得到Typeface
//		paint.setTypeface(typeface);
//		Log.v("msg",typeface.toString());
//		canvas.drawText("欢迎光临Harvic的博客",10,100, paint);//两个构造函数
		
		
//		//使用系统自带字体绘制		
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(60);//设置文字大小
//		paint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充
//		
//		String familyName = "宋体";
//		Typeface font = Typeface.create(familyName,Typeface.NORMAL);
//		paint.setTypeface(font);
//		canvas.drawText("欢迎光临Harvic的博客",10,100, paint);
		
		
		//沿路径绘制
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(45);//设置文字大小
//		paint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充
//		
//		String string="风萧萧兮易水寒，壮士一去兮不复返";
//		
//		//先创建两个相同的圆形路径，并先画出两个路径原图
//		Path circlePath=new Path();
//		circlePath.addCircle(220,200, 180, Path.Direction.CCW);//逆向绘制,还记得吗,上篇讲过的
//		canvas.drawPath(circlePath, paint);//绘制出路径原形
//		
//		Path circlePath2=new Path();
//		circlePath2.addCircle(750,200, 180, Path.Direction.CCW);
//		canvas.drawPath(circlePath2, paint);//绘制出路径原形
//		
//		paint.setColor(Color.GREEN);
//		//hoffset、voffset参数值全部设为0，看原始状态是怎样的
//		canvas.drawTextOnPath(string, circlePath, 0, 0, paint);
//		//第二个路径，改变hoffset、voffset参数值
//		canvas.drawTextOnPath(string, circlePath2, 80, 30, paint);
 		
		
		
		//指定个个文字位置
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(80);//设置文字大小
//		paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充	
//		
//		float []pos=new float[]{80,100,
//								80,200,
//								80,300,
//								80,400};
//		canvas.drawPosText("画图示例", pos, paint);//两个构造函数
		
	
		
		//水平方向拉伸设置(paint.setTextScaleX(2))
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(80);//设置文字大小
//		paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充	
//		
//		//变通样式字体
//		canvas.drawText("欢迎光临Harvic的博客", 10,100, paint);
//		
//		//水平方向拉伸两倍
//		paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
//		canvas.drawText("欢迎光临Harvic的博客", 10,200, paint);
//		
//		//写在同一位置,不同颜色,看下高度是否看的不变
//		paint.setTextScaleX(1);//先还原拉伸效果
//		canvas.drawText("欢迎光临Harvic的博客", 10,300, paint);
//		
//		paint.setColor(Color.GREEN);
//		paint.setTextScaleX(2);//重新设置拉伸效果
//		canvas.drawText("欢迎光临Harvic的博客", 10,300, paint);
		
		

		//文字样式及倾斜度设置
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(80);//设置文字大小
//		paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充	
//		
//		//样式设置
//		paint.setFakeBoldText(true);//设置是否为粗体文字
//		paint.setUnderlineText(true);//设置下划线
//		paint.setStrikeThruText(true);//设置带有删除线效果
//		
//		//设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜
//		paint.setTextSkewX((float) -0.25);
//		canvas.drawText("欢迎光临Harvic的博客", 10,100, paint);
//		
//		//水平倾斜度设置为：0.25，往左斜
//		paint.setTextSkewX((float) 0.25);
//		canvas.drawText("欢迎光临Harvic的博客", 10,200, paint);
		
		
		//绘图样式的区别
//		Paint paint=new Paint();
//		paint.setColor(Color.RED);  //设置画笔颜色    
//		
//		paint.setStrokeWidth (5);//设置画笔宽度
//		paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//		paint.setTextSize(80);//设置文字大小
//		
//		//绘图样式，设置为填充	
//		paint.setStyle(Paint.Style.FILL);	
//		canvas.drawText("欢迎光临Harvic的博客", 10,100, paint);
//		
//		//绘图样式设置为描边	
//		paint.setStyle(Paint.Style.STROKE);
//		canvas.drawText("欢迎光临Harvic的博客", 10,200, paint);
//		
//		//绘图样式设置为填充且描边	
//		paint.setStyle(Paint.Style.FILL_AND_STROKE);
//		canvas.drawText("欢迎光临Harvic的博客", 10,300, paint);
		

//		//弧		
//		
//		Path path = new Path();
//		RectF rect =  new RectF(50, 50, 240, 200);
//		path.addArc(rect, 0, 100);
//		
//		canvas.drawPath(path, paint);//画出路径
		
		
//		//椭圆路径
//		Path path = new Path();
//		RectF rect =  new RectF(50, 50, 240, 200);
//		path.addOval(rect, Direction.CCW);
//		canvas.drawPath(path, paint);

//		//圆形路径
//		Path path = new Path();
//		path.addCircle(200, 200, 100, Direction.CCW);
//		canvas.drawPath(path, paint);


		//矩形路径
//		//先创建两个大小一样的路径
//		//第一个逆向生成
//		Path CCWRectpath = new Path();
//		RectF rect1 =  new RectF(50, 50, 240, 200);
//		CCWRectpath.addRect(rect1, Direction.CCW);
//		
//		//第二个顺向生成
//		Path CWRectpath = new Path();
//		RectF rect2 =  new RectF(290, 50, 480, 200);
//		CWRectpath.addRect(rect2, Direction.CW);
//		
//		//先画出这两个路径 
//		canvas.drawPath(CCWRectpath, paint);
//		canvas.drawPath(CWRectpath, paint);
//		
//		//依据路径写出文字
		//使用系统字体 ，我使用其它字体没作为，不知道为什么
//		String text="风萧萧兮易水寒，壮士一去兮不复返";
//		paint.setColor(Color.GRAY);
//		paint.setTextSize(35);
//		canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针生成
//		canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针生成


		//直线路径
//		path.moveTo(10, 10); //设定起始点
//		path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
//		path.lineTo(300, 100);//画第二条直线
//		path.lineTo(500, 100);//第三条直线
//		path.close();//闭环
		

	}

}



//paint.setStyle(Style.FILL);//填充样式改为描边 



//椭圆
//RectF rect = new RectF(100, 10, 300, 100);
//canvas.drawRect(rect, paint);//画矩形
//
//paint.setColor(Color.GREEN);//更改画笔颜色
//canvas.drawOval(rect, paint);//同一个矩形画椭圆


//圆
//canvas.drawCircle(150, 150, 100, paint);



//圆角矩形
//RectF rect = new RectF(100, 10, 300, 100);
//canvas.drawRoundRect(rect, 20, 10, paint);


//矩形
//canvas.drawRect(10, 10, 100, 100, paint);//直接构造
//
//RectF rect = new RectF(120, 10, 210, 100);
//canvas.drawRect(rect, paint);//使用RectF构造
//
//Rect rect2 =  new Rect(230, 10, 320, 100); 
//canvas.drawRect(rect2, paint);//使用Rect构造
//


//多个点
//float []pts={10,10,100,100,200,200,400,400};
//canvas.drawPoints(pts, 2, 4, paint);

//点
//canvas.drawPoint(100, 100, paint);



//多条直线
//float []pts={10,10,100,100,200,200,400,400};
//canvas.drawLines(pts, paint);

//画直线 
//canvas.drawLine(100, 100, 200, 200, paint);

