package com.example.hailu.joke.model;

import android.graphics.Bitmap;

/**
 * Created by hailu on 2016/6/4.
 */
public class JokePicContentBitmap {
    private String ct;
    private Bitmap img;
    private String url;
    private String title;
    private int type;

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
