package com.longluo.demo.crash;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import com.longluo.demo.log.CrashReport;
import com.longluo.demo.log.CrashReportObject;
import com.longluo.demo.log.ReportObject;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {
	public static final boolean DEBUG = false;

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private static CrashHandler INSTANCE;

	private CrashHandler() {

	}

	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	public void init(Context ctx) {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}

	private boolean handleException(final Throwable ex) {
		Log.e("crash111", "crashcrashcrashcrash");
		if (ex == null) {
			return false;
		}
		final StackTraceElement[] stack = ex.getStackTrace();
		final String message = ex.getMessage();

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);

		ReportObject object = CrashReport.getInstance().getReportObject();
		if (object != null) {
			Log.e("crashobject", "crashcrash");

			if (object instanceof CrashReportObject) {
				((CrashReportObject) object).clientResponseCode = -3;
				((CrashReportObject) object).exceptionMsg = sw.toString();
			}
			CrashReport.getInstance().appendOneLog(object);
		}

		// 使用Toast来显示异常信息
		/*
		 * new Thread() {
		 * 
		 * @Override public void run() { Looper.prepare(); SimpleDateFormat
		 * formatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); Date
		 * curDate=new Date(System.currentTimeMillis()); String
		 * strCurTime=formatter.format(curDate); //
		 * 可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐 String fileName =
		 * "ZaLog_" + strCurTime + ".log"; File file = new
		 * File(Environment.getExternalStorageDirectory(), fileName); try {
		 * FileOutputStream fos = new FileOutputStream(file, true);
		 * fos.write(message.getBytes()); for (int i = 0; i < stack.length; i++)
		 * { fos.write(stack[i].toString().getBytes()); } fos.flush();
		 * fos.close(); } catch (Exception e) { } Looper.loop(); }
		 * 
		 * }.start();
		 */
		return false;
	}

}
