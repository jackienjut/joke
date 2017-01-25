package com.example.hailu.joke.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.hailu.joke.FullPicture;
import com.example.hailu.joke.LoadingViewActivity;
import com.example.hailu.joke.R;
import com.example.hailu.joke.key.BaiduApiKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by hailu on 2016/6/10.
 * <p>
 * Self Adapter extends BaseAdapter class
 */
public class JokeAdapter extends BaseAdapter {

    // data of joke
    private List<JokeTextContent> listText;
    private List<JokePicContentBitmap> listPic;

    private Context context;

    private LayoutInflater listContainer;

    public JokeAdapter(Context context, List<JokeTextContent> listText, List<JokePicContentBitmap> listPic) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listText = listText;
        this.listPic = listPic;
    }

    @Override
    public int getCount() {
        return listText.size();
    }

    //获得position 位置的时候相对应的对象
    @Override
    public Object getItem(int position) {
        return listText.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JokeTextContent jokeTextContent = (JokeTextContent) getItem(position);
        final JokePicContentBitmap jokePicContent = listPic.get(position);
        View view = listContainer.inflate(R.layout.list_item, null);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

        TextView pic_title = (TextView) view.findViewById(R.id.pic_title);
        TextView text_title = (TextView) view.findViewById(R.id.text_title);

        imageView.setImageBitmap(jokePicContent.getImg());
        textView.setText(Html.fromHtml(jokeTextContent.getText()));

        pic_title.setText(jokePicContent.getTitle());
        text_title.setText(jokeTextContent.getTitle());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context , FullPicture.class);
                intent.putExtra("img" , jokePicContent.getUrl());
                context.startActivity(intent);
            }
        });

        return view;
    }


}
