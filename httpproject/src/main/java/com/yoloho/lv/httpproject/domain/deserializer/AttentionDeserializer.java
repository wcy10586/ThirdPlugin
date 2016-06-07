package com.yoloho.lv.httpproject.domain.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.yoloho.lv.httpproject.domain.forum.AttentionInfoBean;
import com.yoloho.lv.httpproject.domain.forum.Piclist;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 重新写反序列化的方式,联系人关注人
 * <p/>
 * <ul>
 * 参考文献
 * <li>完全理解Gson（1）简单入门:http://www.importnew.com/16630.html</li>
 * <li>完全理解Gson（2）自定义序列化方式: http://www.importnew.com/16638.html
 * </li>
 * <li>完全理解Gson（3）Gson反序列化:http://www.importnew.com/16786.html
 * 原文(需要vpn访问):http://www.javacreed.com/gson-deserialiser-example/</li>
 * </ul>
 * Created by mylinux on 16/05/19.
 */
public class AttentionDeserializer implements JsonDeserializer<AttentionInfoBean> {
    public AttentionDeserializer() {
    }

    public AttentionDeserializer(Class cls) {
    }

    //If we are to deserialise this JSON object, we first need to convert the given JsonElement into a JsonObject as shown next.
    @Override
    public AttentionInfoBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        // The variable 'json' is passed as a parameter to the deserialize() method
        final JsonObject jsonObject = json.getAsJsonObject();
        AttentionInfoBean bean = new AttentionInfoBean();
        //TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换
        bean.piclist = context.deserialize(jsonObject.get("piclist"), (new TypeToken<List<Piclist>>() {
        }.getType()));
        // The elements within the a JsonObject can be retrieved by their name. For example, to retrieve the title element from the JSON listed above we can do the following.
        bean.id = jsonObject.get("id").getAsString();
        bean.createUid = jsonObject.get("createUid").getAsString();
        bean.createDate = jsonObject.get("createDate").getAsString();
        //The object returned is not a String but yet another JsonElement. This can be converted to String by invoking the getAsString() as shown below.
        bean.viewnum = jsonObject.get("viewnum").getAsString();
        bean.favnum = jsonObject.get("favnum").getAsString();
        bean.answernum = jsonObject.get("answernum").getAsString();
        bean.topicgroupid = jsonObject.get("topicgroupid").getAsString();
        bean.topicTypeId = jsonObject.get("topicTypeId").getAsString();
        bean.createSortDate = jsonObject.get("create_sort_date").getAsString();
        bean.defaultSortDate = jsonObject.get("default_sort_date").getAsString();
        bean.isAd = jsonObject.get("isAd").getAsString();
        bean.adFlag = jsonObject.get("adFlag").getAsString();
        //
        bean.nickName = jsonObject.get("nickName").getAsString();
        bean.topicGroupName = jsonObject.get("topicGroupName").getAsString();
        bean.isessence = jsonObject.get("isessence").getAsString();
        bean.ishot = jsonObject.get("ishot").getAsString();
        bean.isevent = jsonObject.get("isevent").getAsString();
        bean.ismedical = jsonObject.get("ismedical").getAsString();
        bean.isfav = jsonObject.get("isfav").getAsString();
        bean.dateline = jsonObject.get("dateline").getAsString();
        bean.content = jsonObject.get("content").getAsString();
        bean.isOnWhitelist = jsonObject.get("is_on_whitelist").getAsString();
        //
        bean.contents = jsonObject.get("contents").getAsString();
        bean.viewstr = jsonObject.get("viewstr").getAsString();
        bean.stepInfo = jsonObject.get("step_info").getAsString();
        bean.userIcon = jsonObject.get("user_icon").getAsString();
        bean.likecount = jsonObject.get("likecount").getAsString();
        bean.islike = jsonObject.get("islike").getAsString();
        bean.adLink = jsonObject.get("adLink").getAsString();
        bean.adId = jsonObject.get("adId").getAsString();
        bean.isalltop = jsonObject.get("isalltop").getAsString();
        bean.title = jsonObject.get("title").getAsString();
        return bean;
    }
}
