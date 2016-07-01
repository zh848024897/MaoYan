package com.atguigu.maoyan.parse;

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
    public CinamePagerJsonBean getAllDatas(Context context,String json) {

        // 整体Bean类
        CinamePagerJsonBean bean = new CinamePagerJsonBean();
        bean.areas = new ArrayList<>();


        // 获取到数据
//        String jsonString = context.getResources().getString(
//                R.string.jsonstring);
        String jsonString = json;

        // json解析者
        JsonParser jsonParser = new JsonParser();

        // 解析成Json对象
        JsonObject asJsonObject = jsonParser.parse(jsonString).getAsJsonObject();

        // 遍历里头的数据
        Set<Map.Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {

            //
            CinamePagerJsonBean.Areas area = bean.new Areas();
            area.areasAddresses = new ArrayList<CinamePagerJsonBean.Areas.AreasAddress>();

            JsonArray asJsonArray = entry.getValue().getAsJsonArray();

            for(int i  = 0; i < asJsonArray.size(); i++) {
                JsonObject asJsonObject2 = asJsonArray.get(i).getAsJsonObject();
                CinamePagerJsonBean.Areas.AreasAddress areasAddress = area.new AreasAddress();
                areasAddress.addr = asJsonObject2.get("addr").getAsString();
                areasAddress.area = asJsonObject2.get("area").getAsString();
                areasAddress.brd = asJsonObject2.get("brd").getAsString();
                areasAddress.brdId = asJsonObject2.get("brdId").getAsInt();
                areasAddress.ct = asJsonObject2.get("ct").getAsString();
                areasAddress.deal = asJsonObject2.get("deal").getAsInt();
                areasAddress.dealPrice = asJsonObject2.get("dealPrice").getAsInt();
                areasAddress.dis = asJsonObject2.get("dis").getAsString();
                areasAddress.distance = asJsonObject2.get("distance").getAsInt();
                areasAddress.follow = asJsonObject2.get("follow").getAsInt();
                areasAddress.id = asJsonObject2.get("id").getAsInt();
                areasAddress.imax = asJsonObject2.get("imax").getAsInt();
                areasAddress.lat = asJsonObject2.get("lat").getAsDouble();
                areasAddress.lng = asJsonObject2.get("lng").getAsDouble();
                areasAddress.nm = asJsonObject2.get("nm").getAsString();
                areasAddress.poiId = asJsonObject2.get("poiId").getAsInt();
                areasAddress.preferential = asJsonObject2.get("preferential").getAsInt();
                areasAddress.referencePrice = asJsonObject2.get("referencePrice").getAsInt();
                areasAddress.sell = asJsonObject2.get("sell").getAsBoolean();
                areasAddress.sellPrice = asJsonObject2.get("sellPrice").getAsDouble();
                areasAddress.sellmin = asJsonObject2.get("sellmin").getAsInt();
                areasAddress.showCount = asJsonObject2.get("showCount").getAsInt();

                area.areasAddresses.add(areasAddress);
            }



            bean.areas.add(area);
        }
        return bean;
    }

}
