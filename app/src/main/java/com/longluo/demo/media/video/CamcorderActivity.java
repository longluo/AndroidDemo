package com.longluo.demo.media.video;

import com.longluo.demo.R;

import android.app.Activity;
import android.media.CamcorderProfile;
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
		setContentView(R.layout.activity_camcorder);

		initViews();
	}

	private void initViews() {
		mTextProfile = (TextView) findViewById(R.id.tv_camcorder_profile);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_camcorder, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.id_menu_high_quality_profile:
			profileInfo(QUALITY_HIGH);
			break;

		case R.id.id_menu_low_quality_profile:
			profileInfo(QUALITY_LOW);
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	private void profileInfo(int quality_type) {
		CamcorderProfile camcorderProfile = CamcorderProfile.get(quality_type);
		String selectedProfile = "";
		String profileInfo;
		
		if (quality_type == QUALITY_HIGH) {
			selectedProfile = "Quality High";
		} else if (quality_type == QUALITY_LOW) {
			selectedProfile = "Quality Low";
		}
		
		profileInfo = selectedProfile + " : \n" + camcorderProfile.toString() + "\n";
		profileInfo += "AudioBitRate: " + String.valueOf(camcorderProfile.audioBitRate) + "\n"
				+ "AudioChannels: " + String.valueOf(camcorderProfile.audioChannels) + "\n"
				+ "AudioCodec: " + AudioCodecInString(camcorderProfile.audioCodec) + "\n"
				+ "AudioSampleRate: " + String.valueOf(camcorderProfile.audioSampleRate) + "\n"
				+ "Duration: " + String.valueOf(camcorderProfile.duration) + "\n"
				+ "FileFormat: " + FileFormatInString(camcorderProfile.fileFormat) + "\n"
				+ "Quality: " + String.valueOf(camcorderProfile.quality) + "\n"
				+ "VideoBitRate: " + String.valueOf(camcorderProfile.videoBitRate) + "\n"
				+ "VideoCodec: " + videoCodecInString(camcorderProfile.videoCodec) + "\n"
				+ "VideoFrameRate: " + String.valueOf(camcorderProfile.videoFrameRate) + "\n"
				+ "VideoFrameWidth: " + String.valueOf(camcorderProfile.videoFrameWidth) + "\n"
				+ "VideoFrameHeight: " + String.valueOf(camcorderProfile.videoFrameHeight);
		
		mTextProfile.setText(profileInfo);
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
