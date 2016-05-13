package com.android.volley.toolbox;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.model.BaseUploadFile;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhenai on 2015/10/2.
 */
public class UploadFileRequest extends JsonFormRequest {

    public static String FILE_PATH = "UPLOAD_FILE_PATH";

    public static String BOUNDARY = "--------------520-13-14"; //数据分隔线
    private static String MULTIPART_FORM_DATA = "multipart/form-data";

    public ArrayList<? extends BaseUploadFile> mUploadFiles;


    public UploadFileRequest(int method, String url, ArrayList<? extends BaseUploadFile> uploadFiles,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mUploadFiles = uploadFiles;
        //设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
        setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public String getBodyContentType() {
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }

}
