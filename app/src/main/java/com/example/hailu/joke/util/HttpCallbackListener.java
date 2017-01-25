package com.example.hailu.joke.util;

/**
 * Created by hailu on 2016/4/18.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
