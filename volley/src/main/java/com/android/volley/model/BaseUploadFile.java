package com.android.volley.model;

/**
 * Created by zhenai on 2015/10/3.
 */
public abstract class BaseUploadFile {
    //参数的名称
    private String mName;
    //文件名
    private String mFileName;
    //文件的 mime，需要根据文档查询
    private String mMime;

    private String mFilePath;

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getMime() {
        return getFileMime();
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public abstract String getFileMime();
}
