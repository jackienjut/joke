package com.example.hailu.joke.util;

import com.example.hailu.joke.model.JokePicBody;
import com.example.hailu.joke.model.JokePicBodyBitmap;
import com.example.hailu.joke.model.JokePicContent;
import com.example.hailu.joke.model.JokePicContentBitmap;
import com.example.hailu.joke.service.JokeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hailu on 2016/6/12.
 */
public class Utils {
    public static JokePicBodyBitmap convertToBitmap(JokePicBody jokePicBody) {
        JokePicBodyBitmap jokePicBodyBitmap = new JokePicBodyBitmap();

        jokePicBodyBitmap.setAllNum(jokePicBody.getAllNum());
        jokePicBodyBitmap.setAllPages(jokePicBody.getAllPages());
        List<JokePicContentBitmap> list = new ArrayList<>();

        for (JokePicContent jokePicContent : jokePicBody.getContentlist()) {
            JokePicContentBitmap jokePicContentBitmap = new JokePicContentBitmap();

            jokePicContentBitmap.setCt(jokePicContent.getCt());
            jokePicContentBitmap.setTitle(jokePicContent.getTitle());
            jokePicContentBitmap.setType(jokePicContent.getType());

            jokePicContentBitmap.setImg(JokeService.getImageFromHttp(jokePicContent.getImg()));

            jokePicContentBitmap.setUrl(jokePicContent.getImg());
            list.add(jokePicContentBitmap);
        }

        jokePicBodyBitmap.setContentlist(list);
        return jokePicBodyBitmap;
    }
}
