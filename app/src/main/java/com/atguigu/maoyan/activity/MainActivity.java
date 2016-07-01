package com.atguigu.maoyan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.FrameLayout;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.fragment.ContentFragment;

public class MainActivity extends FragmentActivity {
    private FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fl_content = (FrameLayout)findViewById(R.id.fl_content);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content,new ContentFragment());
        ft.commit();

    }
}
