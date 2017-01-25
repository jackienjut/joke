package com.example.hailu.joke.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.example.hailu.joke.R;


/**
 * Created by hailu on 2016/6/27.
 */
public class GifImageView extends View {
    private long movieStart;
    private Movie movie;

    public GifImageView(Context context) {
        super(context);
    }

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long curTime = android.os.SystemClock.uptimeMillis();

        if (movieStart == 0) {
            int duraction = movie.duration();
            int relTime = (int) ((curTime-movieStart)%duraction);
            movie.setTime(relTime);
            movie.draw(canvas, 0, 0);
//强制重绘
            invalidate();
        }
        super.onDraw(canvas);

    }
}
