package com.atguigu.maoyan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.atguigu.maoyan.R;

/**
 * 热映界面-ListView-item点击跳转的界面
 */
public class MoviePlayingItemActivity extends Activity {
    private WebView webview;
    private String url;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_playing_item);

        webview = (WebView)findViewById(R.id.webview);

        url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
        webSettings = webview.getSettings();
        //设置支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //用户双击页面变大变小 --要页面支持才可以
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮--页面支持才可以
        webSettings.setBuiltInZoomControls(true);
        //网页加载完成之后，需要进行的操作可以写在setWebViewClient中，具体可百度
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super .onPageFinished(view, url);
            }
        });

    }
}
