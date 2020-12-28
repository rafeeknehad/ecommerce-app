package com.example.onlineshoppingisa.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(ProductRoom productRoom);

    @Delete
    void delete(ProductRoom productRoom);

    @Query("select * from ProductRoom where userId like :userID")
    Single<List<ProductRoom>> getAllData(String userID);
}
