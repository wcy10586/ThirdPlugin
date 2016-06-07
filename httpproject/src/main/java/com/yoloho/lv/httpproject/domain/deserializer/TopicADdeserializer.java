package com.yoloho.lv.httpproject.domain.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yoloho.lv.httpproject.domain.AD.Advert;

import java.lang.reflect.Type;

/**
 * Created by mylinux on 16/06/05.
 */
public class TopicADdeserializer implements JsonDeserializer<Advert> {
    @Override
    public Advert deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        Advert ad = new Advert();
//        ad.list = jsonObject.get("list").getAsString();
        return ad;
    }
}
