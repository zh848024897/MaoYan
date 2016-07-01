package com.atguigu.hecheng;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by hp on 2016/6/25.
 */
public class GetAllDatasBean {
    public JsonBean getAllDatas(Context context) {

        // 整体Bean类
        JsonBean bean = new JsonBean();
        bean.areas = new ArrayList<>();


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

            //
            JsonBean.Areas area = new JsonBean.Areas();
            area.areasAddresses = new ArrayList<JsonBean.Areas.AreasAddress>();

            JsonArray asJsonArray = entry.getValue().getAsJsonArray();

            // 又是一个json对象
            JsonObject asJsonObject2 = asJsonArray.get(0).getAsJsonObject();

            //
            Set<Map.Entry<String, JsonElement>> entrySet2 = asJsonObject2
                    .entrySet();
            for (Map.Entry<String, JsonElement> entry2 : entrySet2) {
                JsonArray asJsonArray2 = entry2.getValue().getAsJsonArray();

                // 市bean类
                JsonBean.Areas.AreasAddress areasAddress = new JsonBean.Areas.AreasAddress();

                String asString2 = asJsonArray2.get(0).getAsString();
                areasAddress.addr = asString2;
                areasAddress.area = asJsonArray2.get(1).getAsString();
                areasAddress.brd = asJsonArray2.get(2).getAsString();
                areasAddress.brdId = asJsonArray2.get(3).getAsInt();
                areasAddress.ct = asJsonArray2.get(4).getAsString();
                areasAddress.deal = asJsonArray2.get(5).getAsInt();
                areasAddress.dealPrice = asJsonArray2.get(6).getAsInt();
                areasAddress.dis = asJsonArray2.get(7).getAsString();
                areasAddress.distance = asJsonArray2.get(8).getAsInt();
                areasAddress.follow = asJsonArray2.get(9).getAsInt();
                areasAddress.id = asJsonArray2.get(10).getAsInt();
                areasAddress.imax = asJsonArray2.get(11).getAsInt();
                areasAddress.lat = asJsonArray2.get(12).getAsDouble();
                areasAddress.lng = asJsonArray2.get(13).getAsDouble();
                areasAddress.nm = asJsonArray2.get(14).getAsString();
                areasAddress.poiId = asJsonArray2.get(15).getAsInt();
                areasAddress.preferential = asJsonArray2.get(16).getAsInt();
                areasAddress.referencePrice = asJsonArray2.get(17).getAsInt();
                areasAddress.sell = asJsonArray2.get(18).getAsBoolean();
                areasAddress.sellPrice = asJsonArray2.get(19).getAsDouble();
                areasAddress.preferential = asJsonArray2.get(20).getAsInt();
                areasAddress.preferential = asJsonArray2.get(21).getAsInt();

                area.areasAddresses.add(areasAddress);

            }
            bean.areas.add(area);
        }
        return bean;
    }
}
