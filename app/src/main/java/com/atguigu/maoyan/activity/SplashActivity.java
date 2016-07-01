package com.atguigu.maoyan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.WelcomeBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.volley.VolleyManager;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class SplashActivity extends Activity {
    private ImageView iv_splash;
    private Handler handler = new Handler();
    private String url;
    private WelcomeBean welcomeBean;
    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 隐藏顶部的状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        iv_splash = (ImageView)findViewById(R.id.iv_splash);

        //两秒之后进入
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                url = Contants.WELCOME_URL;
                String saveJson = CacheUtils.getString(SplashActivity.this, url);
//                if (!TextUtils.isEmpty(saveJson)) {
//                    processData(saveJson);
//                }
                getDataFromNet();
            }
        }, 1000);


    }

    private void getDataFromNet() {
        //请求文本
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogUtil.e("Volley联网成功=" + result);
                // 缓存数据
//                CacheUtils. putString(SplashActivity.this,url,result);
                // 解析和显示数据
                processData(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("Volley联网失败=" + volleyError.getMessage());
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }){
            //乱码解决
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, "UTF-8");
                    return Response.success (parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(response.data);
                }

                return super .parseNetworkResponse(response);
            }
        };
        //加入队列
        VolleyManager.getRequestQueue().add(request);
    }

    private void processData(String json) {
        welcomeBean = new Gson().fromJson(json, WelcomeBean.class);
        if(welcomeBean.getPosters().size() > 0){

            String imageUrl = welcomeBean.getPosters().get(0).getPic();

            loaderImager(iv_splash,imageUrl);
        }else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void loaderImager(final ImageView iv_splash, String imageurl) {

        iv_splash.setTag(imageurl);
        //直接在这里请求会乱位置
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer != null) {

                    if (iv_splash != null) {
                        if (imageContainer.getBitmap() != null) {
                            iv_splash.setImageBitmap(imageContainer.getBitmap());

                            scaleAnimation = new ScaleAnimation(1,1.2f,1,1.2f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                            scaleAnimation.setDuration(3000);
                            scaleAnimation.setFillAfter(true);
                            iv_splash.startAnimation(scaleAnimation);

                            //设置动画监听
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                        } else {
//                            iv_splash.setImageResource(R.drawable.splash_bg);
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            }
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //如果出错，则说明都不显示（简单处理），最好准备一张出错图片
//                iv_splash.setImageResource(R.drawable.splash_bg);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        VolleyManager.getImageLoader().get(imageurl, listener);
    }


    //启动时不允许按返回键

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
