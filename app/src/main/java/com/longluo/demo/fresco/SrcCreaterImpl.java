package com.longluo.demo.fresco;

import java.util.List;

public interface SrcCreaterImpl {
    //获取一张图片
    public String getPic();

    //获取一组图片
    public List<String> getPicList();

    //获取一张Gif图片
    public String getGif();

    //获取一组Gif图片
    public List<String> getGifList();
}
