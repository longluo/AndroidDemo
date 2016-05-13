package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by zhenai on 2015/9/26.
 */
public class JsonFormRequest extends Request<JSONObject> {

    private final Response.Listener<JSONObject> mListener;

    public JsonFormRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        JSONObject json = new JSONObject();
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            json = new JSONObject(parsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
