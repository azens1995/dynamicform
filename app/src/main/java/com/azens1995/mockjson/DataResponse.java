package com.azens1995.mockjson;

import com.azens1995.mockjson.database.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {

    @SerializedName("data")
    @Expose
    private List<Data> data;

    public DataResponse() {
    }

    public List<Data> getData() {
        return data;
    }
}
