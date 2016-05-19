package com.yoloho.lv.httpproject.domain.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.yoloho.lv.httpproject.domain.forum.AttentionDataBean;
import com.yoloho.lv.httpproject.domain.forum.AttentionInfoBean;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 物品的关注人的反序列化,重新定义格式进行处理
 * Created by mylinux on 16/05/19.
 */
public class AttentionDataDeserializer implements JsonDeserializer<AttentionDataBean> {

    @Override
    public AttentionDataBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        AttentionDataBean bean = new AttentionDataBean();
        // Delegate the deserialization to the context
        //TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换
        bean.dataList = context.deserialize(jsonObject.get("list"), (new TypeToken<List<AttentionInfoBean>>() {}.getType()));
        bean.recommendList = context.deserialize(jsonObject.get("recommendList"), (new TypeToken<List<AttentionInfoBean>>() {}.getType()));
        bean.refreshtime = jsonObject.get("refreshtime").getAsString();
        bean.timestamp = jsonObject.get("timestamp").getAsString();
        bean.errno = jsonObject.get("errno").getAsString();
        bean.errdesc = jsonObject.get("errdesc").getAsString();
        return bean;
    }
}
