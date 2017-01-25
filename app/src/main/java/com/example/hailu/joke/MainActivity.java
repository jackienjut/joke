package com.example.hailu.joke;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hailu.joke.model.JokeAdapter;
import com.example.hailu.joke.model.JokePicBody;
import com.example.hailu.joke.model.JokePicBodyBitmap;
import com.example.hailu.joke.model.JokePicContentBitmap;
import com.example.hailu.joke.model.JokeTextBody;
import com.example.hailu.joke.model.JokeTextContent;
import com.example.hailu.joke.service.JokeService;
import com.example.hailu.joke.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.CustomerSpotView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.hailu.joke.Constant.ADD_MORE_JOKE;
import static com.example.hailu.joke.Constant.LOADING_VIEW_START;
import static com.example.hailu.joke.Constant.LOAD_JOKE;
import static com.example.hailu.joke.Constant.PROGRESS_END;
import static com.example.hailu.joke.Constant.PROGRESS_START;


public class MainActivity extends Activity {
    private static final String TAG = "Joke Xiaolin";

    private Context mContext;

    // ad
    private CustomerSpotView customerSpotView;
    private RelativeLayout nativeAdLayout;

    private Gson gson = new Gson();

    private ListView mainLV;
    private ProgressBar progressBar;
    private WebView webView;
    private JokeTextBody jokeTextBody;
    private Button loadMore;
    private LinearLayout loading;

    LinearLayout footview;
    private JokePicBodyBitmap jokePicBodyBitmap;

    JokeAdapter adapter;
    List<JokeTextContent> joke_text_list = new ArrayList<JokeTextContent>();
    List<JokePicContentBitmap> joke_pic_list = new ArrayList<JokePicContentBitmap>();

    private static int pagenum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mContext = this;

        initUI();
        initFootView();
        requestWithHttp(pagenum);

        initYoumiAd();
    }

    private void initUI() {
        mainLV = (ListView) findViewById(R.id.mainLV);
        progressBar = (ProgressBar) findViewById(R.id.footprogress);
        webView = (WebView) findViewById(R.id.webview);

        webView.loadDataWithBaseURL(null, "<HTML><body bgcolor='#f3f3f3'><div align=center><IMG src='file:///android_asset/loading2.gif'/></div><div><p>     加载中...</p></div></body></html>", "text/html", "UTF-8", null);

        adapter = new JokeAdapter(MainActivity.this, joke_text_list, joke_pic_list);

        handler.sendEmptyMessage(LOADING_VIEW_START);
    }

    private void initFootView() {
        footview = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.listfoot, null);
        loading = (LinearLayout) footview.findViewById(R.id.foot_loading);
        loadMore = (Button) footview.findViewById(R.id.loadMore);

        loadMore.setOnClickListener(new View.OnClickListener() {    //加载更多的响应事件
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loadMore.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                pagenum++;
                requestWithHttp(pagenum);
            }
        });

    }

    public Handler getHandler() {
        return handler;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_VIEW_START:

                    //   MainActivity.this.startActivity(new Intent(MainActivity.this, LoadingViewActivity.class));
                    break;
                case LOAD_JOKE:
                    //TODO will use other ways to instead
                    webView.setVisibility(View.GONE);
                    mainLV.removeFooterView(footview);

                    List<JokeTextContent> joke_text_list_sub = jokeTextBody.getContentlist();
                    List<JokePicContentBitmap> joke_pic_list_sub = jokePicBodyBitmap.getContentlist();

                    joke_text_list.addAll(joke_text_list_sub);
                    joke_pic_list.addAll(joke_pic_list_sub);


                    mainLV.addFooterView(footview);
                    mainLV.setAdapter(adapter);

                    handler.sendEmptyMessage(PROGRESS_END);
                    break;
                case ADD_MORE_JOKE:
                    mainLV.removeFooterView(footview);
                    // TODO
                    List<JokeTextContent> joke_text_list_sub2 = jokeTextBody.getContentlist();
                    List<JokePicContentBitmap> joke_pic_list_sub2 = jokePicBodyBitmap.getContentlist();

                    joke_text_list.addAll(joke_text_list_sub2);
                    joke_pic_list.addAll(joke_pic_list_sub2);
                    mainLV.addFooterView(footview);
                    adapter.notifyDataSetChanged();

                    loadMore.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    break;
                case PROGRESS_END:

                    break;
                case PROGRESS_START:

                default:
                    break;
            }
        }
    };

    private void requestWithHttp(final int pagenum) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                String txt = JokeService.getJokeText(pagenum);
                String pic = JokeService.getJokePic(pagenum);

                JsonParser jsonParser = new JsonParser();
                JsonObject joTxt = (JsonObject) jsonParser.parse(txt);
                JsonObject joPic = (JsonObject) jsonParser.parse(pic);


                if (joTxt.get("showapi_res_code").toString().trim().equals("0") && joPic.get("showapi_res_code").toString().trim().equals("0")) {

                    jokeTextBody = gson.fromJson(joTxt.get("showapi_res_body"), JokeTextBody.class);
                    JokePicBody jokePicBody = gson.fromJson(joPic.get("showapi_res_body"), JokePicBody.class);

                    jokePicBodyBitmap = Utils.convertToBitmap(jokePicBody);

                    if (pagenum == 1)
                        message.what = LOAD_JOKE;
                    else
                        message.what = ADD_MORE_JOKE;
                    handler.sendMessage(message);
                } else {

                }
            }
        }).start();
    }


    class DownloadTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            // do nothing
        }

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //initialize youmi advertisement
    private void initYoumiAd() {
        // set ad version
        setupAppVersionInfo();
        setupSpotAd();
        setNativeSpotAd();
        setupBannerAd();
    }

    // set youmu ad
    private void setupAppVersionInfo() {
        // TODO this method is for setup app version info
        // but now we do not need this to show the app version
    }

    private void setupSpotAd() {
        AdManager.getInstance(MainActivity.this).init("d43700a760c68ad2", "5c28c228dd1df115", true);
        SpotManager.getInstance(MainActivity.this).loadSpotAds();
        SpotManager.getInstance(mContext).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);

        SpotManager.getInstance(MainActivity.this).setAnimationType(SpotManager.ANIM_ADVANCE);

        SpotManager.getInstance(MainActivity.this).showSpotAds(MainActivity.this, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                Log.i(TAG, "插屏展示成功");
            }

            @Override
            public void onShowFailed() {
                Log.i(TAG, "插屏展示失败");
            }

            @Override
            public void onSpotClosed() {
                Log.i(TAG, "插屏被关闭");
            }

            @Override
            public void onSpotClick(boolean isWebPath) {
                Log.i(TAG, "插屏被点击，isWebPath = " + isWebPath);
            }
        });
    }

    private void setNativeSpotAd() {
        nativeAdLayout = (RelativeLayout) findViewById(R.id.rl_native_ad);

        // 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图
        SpotManager.getInstance(mContext).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);
    }

    private void setupBannerAd() {

        //		/**
        //		 * 普通布局
        //		 */
        //		//　实例化广告条
        //		AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
        //		LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.ll_banner);
        //		bannerLayout.addView(adView);
        /**
         * 悬浮布局
         */
        // 实例化LayoutParams(重要)
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //　设置广告条的悬浮位置，这里示例为右下角
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        //　实例化广告条
        AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
        // 监听广告条接口
        adView.setAdListener(new AdViewListener() {

            @Override
            public void onSwitchedAd(AdView adView) {
                Log.i(TAG, "广告条切换");
            }

            @Override
            public void onReceivedAd(AdView adView) {
                Log.i(TAG, "请求广告条成功");
            }

            @Override
            public void onFailedToReceivedAd(AdView adView) {
                Log.i(TAG, "请求广告条失败");
            }
        });
        // 调用Activity的addContentView函数
        ((Activity) mContext).addContentView(adView, layoutParams);
    }

    private String getAppVersionName() {
        try {
            PackageManager packageManager = getPackageManager();
            return packageManager.getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        //原生控件点击后退关闭
        if (nativeAdLayout != null && nativeAdLayout.getVisibility() == View.VISIBLE) {
            nativeAdLayout.removeAllViews();
            nativeAdLayout.setVisibility(View.GONE);
            return;
        }
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(mContext).disMiss()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        SpotManager.getInstance(mContext).onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 调用插屏，开屏，退屏时退出
        SpotManager.getInstance(mContext).onDestroy();
        super.onDestroy();
    }

}
