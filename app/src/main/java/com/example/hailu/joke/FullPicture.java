package com.example.hailu.joke;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.hailu.joke.service.JokeService;

import static com.example.hailu.joke.Constant.*;

/**
 * Created by hailu on 2016/6/26.
 */
public class FullPicture extends Activity {

    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fullpicture);

        imageView = (ImageView) findViewById(R.id.full_image);

        Intent intent = getIntent();
        final String url = intent.getStringExtra("img");
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = JokeService.getImageFromHttp(url);
                handler.sendEmptyMessage(UPDATE_FULL);
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_FULL: {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    };
}
