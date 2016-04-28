package com.longluo.demo.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.longluo.demo.R;

import java.io.File;

/**
 * Created by luolong on 2016/4/28.
 */
public class FileManagerDemoActivity extends Activity {
    private static final int RESULT_FILE = 100;
    private static final int RESULT_IMAGE = 200;
    private static final int RESULT_VIDEO = 300;
    private static final int RESULT_CAMERA = 400;

    private static final String IMAGE_TYPE = "image/*";
    private static final String VIDEO_TYPE = "video/*";

    public static String TEMP_IMAGE_PATH;

    private Button mBtnOpenFileManager, mBtnChooseImage, mBtnChooseVideo, mBtnCamera;

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemanager_demo);

        TEMP_IMAGE_PATH =
                Environment.getExternalStorageDirectory().getPath() +
                        "/temp.png";
        initViews();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Demo", "onActivityResult, requestCode=" + requestCode + ",resultCode=" + resultCode
                + ",data=" + data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_FILE) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                String url;
                try {
                    url = uri.getPath();
                    String fileName = url.substring(url.lastIndexOf("/") + 1);

                    updateTextContent(url);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_IMAGE && data != null) {
                // 相册
                Cursor cursor = this.getContentResolver().query(
                        data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(
                        cursor.getColumnIndex("_data"));

                updateTextContent(imagePath);

            } else if (requestCode == RESULT_VIDEO) {


            } else if (requestCode == RESULT_CAMERA) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {
        mBtnOpenFileManager = (Button) findViewById(R.id.btn_open_filemanager);
        mBtnOpenFileManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();

            }
        });

        mBtnChooseImage = (Button) findViewById(R.id.btn_choose_image);
        mBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImageChooser();

            }
        });

        mBtnChooseVideo = (Button) findViewById(R.id.btn_choose_video);
        mBtnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mBtnCamera = (Button) findViewById(R.id.btn_camera);
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        mTvContent = (TextView) findViewById(R.id.tv_desc);

    }

    private void updateTextContent(String content) {
        Log.d("Demo", "updateTextContent, content=" + content);

        mTvContent.setText(content);

    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(intent, RESULT_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(FileManagerDemoActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(
                Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_TYPE);
        startActivityForResult(intent, RESULT_IMAGE);
    }

    private void openCamera() {
        // 系统相机
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                photoUri);
        startActivityForResult(intent, RESULT_CAMERA);
    }

}
