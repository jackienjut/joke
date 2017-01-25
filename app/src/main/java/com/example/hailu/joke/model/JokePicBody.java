package com.example.hailu.joke.model;

import java.util.List;

/**
 * Created by hailu on 2016/6/4.
 */
public class JokePicBody {
    private int allNum;
    private int allPages;
    private List<JokePicContent> contentlist;


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

    public List<JokePicContent> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<JokePicContent> contentlist) {
        this.contentlist = contentlist;
    }
}
