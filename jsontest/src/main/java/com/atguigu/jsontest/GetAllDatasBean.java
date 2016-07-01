package com.atguigu.jsontest;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static com.atguigu.jsontest.JsonBean.Provinces;
import static com.atguigu.jsontest.JsonBean.Provinces.Country;

/**
 * Created by hp on 2016/6/25.
 */
public class GetAllDatasBean {
    public JsonBean getAllDatas(Context context) {

        // 整体Bean类
        JsonBean bean = new JsonBean();
        bean.Provinces = new ArrayList<JsonBean.Provinces>();


        // 获取到数据
//        String jsonString = context.getResources().getString(
//                R.string.jsonstring);
        String jsonString = "";

        // json解析者
        JsonParser jsonParser = new JsonParser();

        // 解析成Json对象
        JsonObject asJsonObject = jsonParser.parse(jsonString)
                .getAsJsonObject();

        // 遍历里头的数据
        Set<Map.Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {

            // 省bean类
            Provinces province = new Provinces();
            province.cities = new ArrayList<JsonBean.Provinces.City>();

            JsonArray asJsonArray = entry.getValue().getAsJsonArray();
            String asString = asJsonArray.get(0).getAsString();// 省份

            // 赋值
            province.provinceName = asString;
            System.out.println("asString>>" + asString);

            int asInt = asJsonArray.get(1).getAsInt();// 那个数字

            // 赋值
            province.shengNumber = asInt;

            // 获取到第3个，又是一个json对象
            JsonObject asJsonObject2 = asJsonArray.get(2).getAsJsonObject();

            // 遍历第2级数据
            Set<Map.Entry<String, JsonElement>> entrySet2 = asJsonObject2
                    .entrySet();
            for (Map.Entry<String, JsonElement> entry2 : entrySet2) {
                JsonArray asJsonArray2 = entry2.getValue().getAsJsonArray();

                // 市bean类
                Provinces.City city = new Provinces.City();
                city.countries = new ArrayList<JsonBean.Provinces.Country>();

                if (asJsonArray2.size() == 1) {// 这一个判断是因为在河北市（42249）里头只有一级，没有下一级了
                    String asString2 = asJsonArray2.get(0).getAsString();// 市级

                    city.cityName = asString2;
                    city.chengshiNumber = -1;// 表示没有
                    city.countries = null;// 表示没有，使用的时候要记得判断

                } else {
                    String asString2 = asJsonArray2.get(0).getAsString();// 市级

                    // 赋值
                    city.cityName = asString2;

                    int asInt2 = asJsonArray2.get(1).getAsInt();// 数字

                    // 赋值
                    city.chengshiNumber = asInt2;

                    // 获取第三级数据（县级/区）
                    JsonObject asJsonObject3 = asJsonArray2.get(2)
                            .getAsJsonObject();

                    // 遍历第三级数据
                    Set<Map.Entry<String, JsonElement>> entrySet3 = asJsonObject3
                            .entrySet();
                    for (Map.Entry<String, JsonElement> entry3 : entrySet3) {
                        JsonArray asJsonArray3 = entry3.getValue()
                                .getAsJsonArray();

                        String asString3 = asJsonArray3.get(0).getAsString();// 县级数据

                        // 县级/区数据bean类
                        Country country = new Country();

                        country.countryNmae = asString3;
                        city.countries.add(country);// 添加县/区
                    }
                }
                province.cities.add(city);
            }
            bean.Provinces.add(province);
        }
        return bean;
    }
}
