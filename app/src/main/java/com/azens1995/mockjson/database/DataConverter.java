package com.azens1995.mockjson.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromOptionsList(List<String> options){
        if (options == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){
        }.getType();
        String json = gson.toJson(options, type);
        return json;
    }

    @TypeConverter
    public List<String> toOptionsList(String optionsString){
        if (optionsString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){
        }.getType();
        List<String> optionsList = gson.fromJson(optionsString,type);
        return optionsList;

    }
}
