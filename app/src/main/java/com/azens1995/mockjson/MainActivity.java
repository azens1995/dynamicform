package com.azens1995.mockjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.azens1995.mockjson.DataAdapter;

import com.azens1995.mockjson.adapter.MyAdapter;
import com.azens1995.mockjson.api.DataClient;
import com.azens1995.mockjson.api.ServiceGenerator;
import com.azens1995.mockjson.database.AppDatabase;
import com.azens1995.mockjson.database.Data;
import com.azens1995.mockjson.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName() ;
    private RecyclerView recyclerView;

    AppDatabase db;
    List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        db = AppDatabase.getAppDatabase(this);

        //db.dataDao().getAllData().size() ==0
        if (NetworkUtils.isOnline(this) && db.dataDao().getAllData().size() ==0) {
                loadJSON();
        }else {
            DataAdapter dataAdapter = new
                    DataAdapter(this, new ArrayList<Data>(db.dataDao().getAllData()));
            recyclerView.setAdapter(dataAdapter);
//            MyAdapter myAdapter = new MyAdapter(MainActivity.this, new ArrayList<Data>(db.dataDao().getAllData()), new MyAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(Data data) {
//                    Toast.makeText(getApplicationContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
//            recyclerView.setAdapter(myAdapter);
        }

    }


    private List<Data> fetchDataFromDatabase() {
        Log.d(TAG, "fetchDataFromDatabase: fetching data from db");
        return db.dataDao().getAllData();
    }

    private void loadJSON() {
        Log.d(TAG, "retrofitRequest: requesting retrofit data");
        DataClient client = ServiceGenerator.createService(DataClient.class);
        Call<DataResponse> call = client.getData();
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                Log.d(TAG, "onResponse: positive response");
                List<Data> data = response.body().getData();
                recyclerView.setAdapter(new DataAdapter(MainActivity.this, data));
//                recyclerView.setAdapter(new MyAdapter(MainActivity.this, data, new MyAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(Data data) {
//                        Toast.makeText(getApplicationContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//                    }
//                }));
                saveData(data);
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(List<Data> data) {
        Log.d(TAG, "fetchDataFromDatabase: fetching data from db");
        db.dataDao().deleteAll();
        db.dataDao().insertAll(Arrays.asList(data.toArray(new Data[data.size()])));
    }
}
