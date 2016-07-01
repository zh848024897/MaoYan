package com.atguigu.jsontest;

import java.util.List;

/**
 * Created by hp on 2016/6/25.
 */
public class JsonBean {
    public List<Provinces> Provinces;

    public static class Provinces{
        public String provinceName;
        public int shengNumber;
        public List<City> cities;

        public static class City {
            public String cityName;
            public int chengshiNumber;
            public List<Country> countries;
        }

        public static class Country {
            public String countryNmae;
        }

    }


}
