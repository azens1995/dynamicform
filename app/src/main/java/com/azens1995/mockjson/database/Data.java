package com.azens1995.mockjson.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "data")
public class Data {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String desc;
    private String type;

    @TypeConverters(DataConverter.class)
    private List<String> options;

    public Data() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
