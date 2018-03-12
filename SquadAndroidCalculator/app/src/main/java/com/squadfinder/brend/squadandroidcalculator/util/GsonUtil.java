package com.squadfinder.brend.squadandroidcalculator.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by brend on 3/12/2018.
 */

public class GsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(Object o){
        return gson.toJson(o);
    }

    public static <T> T toType( String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    public static <T> T toType( String json, TypeToken<T> type){
        return gson.fromJson(json, type.getType());
    }
}
