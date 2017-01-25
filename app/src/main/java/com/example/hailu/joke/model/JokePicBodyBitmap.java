package com.example.hailu.joke.model;

import java.util.List;

/**
 * Created by hailu on 2016/6/4.
 */
public class JokePicBodyBitmap {
    private int allNum;
    private int allPages;
    private List<JokePicContentBitmap> contentlist;


    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<JokePicContentBitmap> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<JokePicContentBitmap> contentlist) {
        this.contentlist = contentlist;
    }
}
