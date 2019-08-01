package com.zhoug.widget.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 描述：Json字符串工具
 * zhougan
 * 2019/3/23
 **/
public class JsonUtil {

    public static String toJson(Object obj){
        return new Gson().toJson(obj);
    }

    public static String toJsonWithoutExpose(Object obj){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(obj);
    }

    public static <T> T fromJson(String json,Class<T> cls){
        return new Gson().fromJson(json,cls);
    }

    public static <T> T fromJsonList(String json,Class<T> cls){
        Type type=new TypeToken<List<T>>(){}.getType();
        return new Gson().fromJson(json,type);
    }


}
