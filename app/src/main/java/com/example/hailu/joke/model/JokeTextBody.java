package com.example.hailu.joke.model;

import java.util.List;

/**
 * Created by hailu on 2016/6/4.
 */
public class JokeTextBody {
    public int allNum;
    public int allPage;
    List<JokeTextContent> contentlist;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPage() {
        return allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public List<JokeTextContent> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<JokeTextContent> contentlist) {
        this.contentlist = contentlist;
    }
}
