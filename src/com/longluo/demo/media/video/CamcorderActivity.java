package com.longluo.demo.media.video;

import android.app.Activity;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CamcorderActivity extends Activity {
	private static final int QUALITY_LOW = 0;
	private static final int QUALITY_HIGH = 1;

	private TextView mTextProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private void profileInfo(int quality_type) {

	}

	private String AudioCodecInString(int audioCodec) {
		switch (audioCodec) {
		case AudioEncoder.AAC:
			return "AAC";

		case AudioEncoder.AMR_NB:
			return "AMR_NB";

		case AudioEncoder.AMR_WB:
			return "AMR_WB";

		case AudioEncoder.DEFAULT:
			return "DEFAULT";

		default:
			return "unknown";
		}
	}

	private String FileFormatInString(int fileFormat) {
		switch (fileFormat) {
		case OutputFormat.AMR_NB:
			return "AMR_NB";

		case OutputFormat.AMR_WB:
			return "AMR_WB";

		case OutputFormat.MPEG_4:
			return "MPEG_4";

		case OutputFormat.THREE_GPP:
			return "THREE_GPP";

		case OutputFormat.DEFAULT:
			return "DEFAULT";

		default:
			return "unknown";
		}
	}

	private String videoCodecInString(int videoCodec) {
		switch (videoCodec) {
		case VideoEncoder.H263:
			return "H263";

		case VideoEncoder.H264:
			return "H264";

		case VideoEncoder.MPEG_4_SP:
			return "MPEG_4_SP";

		case VideoEncoder.DEFAULT:
			return "DEFAULT";

		default:
			return "unknown";
		}
	}
}
