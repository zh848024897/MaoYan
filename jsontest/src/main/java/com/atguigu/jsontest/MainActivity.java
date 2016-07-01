package com.atguigu.jsontest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner mProvinces;
    private Spinner mCities;
    private Spinner mCountries;
    private JsonBean mDatas;
    private List<JsonBean.Provinces> mProDatas;
    private List<JsonBean.Provinces.City> mCitDatas;
    private List<JsonBean.Provinces.Country> mCounDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initDatas();
        initEvent();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mProvinces = (Spinner) this.findViewById(R.id.provinces);
        mCities = (Spinner) this.findViewById(R.id.cities);
        mCountries = (Spinner) this.findViewById(R.id.countries);
    }

    private void initDatas() {

        mDatas = new GetAllDatasBean().getAllDatas(this);

        mProDatas = mDatas.Provinces;
        Log.d("message", mProDatas.size() + ">>>>");

        mProvinces.setAdapter(new ProvincesAdapter(mProDatas));
    }

    private class ProvincesAdapter extends MyBaseAdapter<JsonBean.Provinces> {

        public ProvincesAdapter(List<JsonBean.Provinces> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(mProDatas.get(position).provinceName);
            return tv;
        }

    }

    /**
     * 第二级数据目录
     */
    private void initSecondDatas(int position) {
        mCitDatas = mProDatas.get(position).cities;

        // 设置适配器
        mCities.setAdapter(new CitiesAdapter(mCitDatas));

    }

    private class CitiesAdapter extends MyBaseAdapter<JsonBean.Provinces.City> {

        public CitiesAdapter(List<JsonBean.Provinces.City> datas) {
            super(datas);
        }

        // 复写方法
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(mCitDatas.get(position).cityName);
            return tv;
        }

    }

    private void initCountrieDatas(int position) {

        // 获取数据
        mCounDatas = mCitDatas.get(position).countries;

        // 设置适配器
        mCountries.setAdapter(new CountriesAdapter(mCounDatas));

    }

    private class CountriesAdapter extends MyBaseAdapter<JsonBean.Provinces.Country> {

        public CountriesAdapter(List<JsonBean.Provinces.Country> datas) {
            super(datas);
        }

        // 复写一个方法
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            tv.setText(mCounDatas.get(position).countryNmae);
            return tv;
        }
    }

    private void initEvent() {
        // 省级数据监听
        mProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                initSecondDatas(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // 市级数据监听
        mCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // 去设定县级/区的数据
                initCountrieDatas(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
