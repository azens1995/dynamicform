package com.azens1995.mockjson.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface DataDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Data> data);

    @Query("SELECT * FROM data")
    List<Data> getAllData();

    @Query("DELETE FROM data")
    void deleteAll();
}
