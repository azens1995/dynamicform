package com.azens1995.mockjson.api;
import com.azens1995.mockjson.DataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Azens Eklak on 8/7/18.
 */
public interface DataClient {

    @GET("5b7e501c300000760084c0b8")
    Call<DataResponse> getData();

}
